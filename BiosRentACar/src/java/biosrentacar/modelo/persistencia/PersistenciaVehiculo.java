/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biosrentacar.modelo.persistencia;

import biosrentacar.modelo.compartidos.beans.datatypes.*;
import biosrentacar.modelo.compartidos.beans.excepciones.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Bruno
 */
public class PersistenciaVehiculo implements IPersistenciaVehiculo{
   
    private static PersistenciaVehiculo instancia = null;
    
    public static PersistenciaVehiculo getInstancia() {    
        
        if (instancia == null) {
            instancia = new PersistenciaVehiculo();
        }
        
        return instancia;
    }
    
    private PersistenciaVehiculo() {
        
    }
    
    protected DTVehiculo crearVehiculo(ResultSet datos) throws ExcepcionPersonalizada, SQLException {
        DTVehiculo vehiculo = new DTVehiculo();
        
        vehiculo.setMatricula(datos.getString("Matricula"));
        vehiculo.setDescripcion(datos.getString("Descripcion"));
        vehiculo.setSucursal(FabricaPersistencia.getPersistenciaSucursal().buscar(datos.getInt("CodigoSucursal")));
        vehiculo.setPrecioPorDia(datos.getDouble("PrecioPorDia"));
        vehiculo.setTipo(datos.getString("Tipo"));
        
        return vehiculo;
    }
    
    public DTVehiculo BuscarVehiculo(String matricula) throws ExcepcionPersistencia {
        
        Connection cnn = null;
        PreparedStatement statement = null;
        ResultSet rt = null;
        DTVehiculo vehiculo = null;
        
        try {
            cnn = UtilidadesPersistencia.getConexion();            
            statement = cnn.prepareCall("SELECT Vehiculos.*, SucursalVehiculo.CodigoSucursal FROM Vehiculos LEFT JOIN SucursalVehiculo ON Vehiculos.Matricula = SucursalVehiculo.Matricula WHERE Vehiculos.Matricula = ?;");
            statement.setString(1, matricula);
            rt = statement.executeQuery();
            if(rt.next()){
                vehiculo = crearVehiculo(rt);
            }
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo buscar el vehiculo.", ex);
        }    
        finally{
            UtilidadesPersistencia.cerrarRecursos(rt, statement, cnn);
        }
        return vehiculo;
    }
    
    public List<DTVehiculo> ListarVehiculosSucursal(int pSucursal) throws ExcepcionPersistencia {
        List<DTVehiculo> vehiculos = new ArrayList();
        
        Connection conexion = null;
        CallableStatement statement = null;
        ResultSet datos = null;
        
        try {
            conexion = UtilidadesPersistencia.getConexion();
            statement = conexion.prepareCall("{CALL ListarVehiculosSucursal(?)}");
            statement.setInt(1, pSucursal);
            datos = statement.executeQuery();
            
            while (datos.next()) {
                vehiculos.add(crearVehiculo(datos));
            }
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo listar los vehiculos.", ex);
        } finally {
            UtilidadesPersistencia.cerrarRecursos(datos, statement, conexion);
        }
        
        return vehiculos;
    }
    
    public void AltaVehiculo(DTVehiculo vehiculo) throws ExcepcionPersistencia{
        Connection cnn = null;
        CallableStatement statement = null;
        
        try {
            cnn = UtilidadesPersistencia.getConexion();
            statement = cnn.prepareCall("{call altaVehiculo(?, ?, ?, ?, ?, ?)}");
            // altaVehiculo(pMatricula VARCHAR(7), pTipo VARCHAR(50), pDescripcion VARCHAR(50), pPrecioPorDia FLOAT, OUT pMensajeError VARCHAR(100))

            statement.setString("pMatricula", vehiculo.getMatricula());
            statement.setString("pTipo", vehiculo.getTipo());
            statement.setString("pDescripcion", vehiculo.getDescripcion());
            statement.setFloat("pPrecioPorDia", (float)(vehiculo.getPrecioPorDia()));
            statement.setInt("pCodigoSucursal", (vehiculo.getSucursal().getCodigo()));
            statement.registerOutParameter("pMensajeError", java.sql.Types.VARCHAR);

            statement.executeUpdate();
            
            String mensajeError = statement.getString("pMensajeError");
            if (mensajeError != null)
            {
                throw new ExcepcionPersistencia(mensajeError);
            }            
        } catch (Exception ex) {
            throw new ExcepcionPersistencia(ex.getMessage(), ex);
        }
        finally{
                UtilidadesPersistencia.cerrarRecursos(statement, cnn);                 
            }
    }
    
    public void ModificarVehiculo(DTVehiculo vehiculo)  throws ExcepcionPersistencia{
        Connection cnn = null;
        CallableStatement statement = null;
        
        try {
            cnn = UtilidadesPersistencia.getConexion();
            statement = cnn.prepareCall("{ Call modificarVehiculo (?, ?, ?, ?, ?) }");
            // pMatricula VARCHAR(7), pTipo VARCHAR(50), pDescripcion VARCHAR(50), pPrecioPorDia FLOAT, OUT pMensajeError VARCHAR(100))
            
            int p = 0;
            statement.setString(++p, vehiculo.getMatricula());
            statement.setString(++p, vehiculo.getTipo());
            statement.setString(++p, vehiculo.getDescripcion());
            statement.setFloat(++p, (float)(vehiculo.getPrecioPorDia()));
            statement.registerOutParameter(++p, java.sql.Types.VARCHAR);

            statement.executeUpdate();
            
            String mensajeError = statement.getString(p);
            if (mensajeError != null)
            {
                throw new ExcepcionPersistencia(mensajeError);
            }            
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo modificar el vehiculo. ", ex);
        }
        finally{
                UtilidadesPersistencia.cerrarRecursos(statement, cnn);                 
            }
    }
    
    public void BorrarVehiculo(String matricula) throws ExcepcionPersistencia{
        Connection cnn = null;
        CallableStatement statement = null;
        try {
            cnn = UtilidadesPersistencia.getConexion();
            statement = cnn.prepareCall("{ CALL bajaVehiculo (?, ?)}");
            statement.setString(1, matricula);
            statement.registerOutParameter(2, java.sql.JDBCType.VARCHAR);
            
            statement.executeUpdate();
            String mensajeError = statement.getString(2);
            if(mensajeError != null){
                throw new ExcepcionPersistencia(mensajeError);
            }
            
        } catch (Exception e) {
            throw new ExcepcionPersistencia("Error al eliminar el vehiculo. " + e.getMessage());
        }
        finally{
            UtilidadesPersistencia.cerrarRecursos(statement, cnn);
        }
    }
    
    public void TrasladarVehiculo(String matricula, int sucursalDestino) throws ExcepcionPersistencia {
        Connection cnn = null;
        CallableStatement statement = null;
        
        try {
            DTVehiculo vehiculo = null;
            cnn = UtilidadesPersistencia.getConexion();            
            statement = cnn.prepareCall("{ CALL TrasladarVehiculo(?, ?, ?) }");
            
            int p = 0;
            statement.setString(++p, matricula);
            statement.setInt(++p, sucursalDestino);
            statement.registerOutParameter(++p, java.sql.Types.VARCHAR);
            
            statement.executeUpdate();
            
            String mensajeError = statement.getString(p);
            
            if (mensajeError != null) {
                throw new ExcepcionPersistencia(mensajeError);
            }
            
        } catch (ExcepcionPersonalizada ex) { 
            throw ex;
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo trasladar el vehiculo.", ex);
        }    
        finally{
            UtilidadesPersistencia.cerrarRecursos(statement, cnn);
        }
    }
    
    public void AlquilarVehiculo(DTAlquiler alquiler) throws ExcepcionPersistencia {
        Connection cnn = null;
        CallableStatement statement = null;
        /*
        (pMatricula varchar(7),pCiCliente VARCHAR(8), pCodigoSucursal INT,pCantidadDias INT, pImporteSeguro FLOAT, pImporteDeposito FLOAT, OUT pMensajeError VARCHAR(100))
        */
        try {
            cnn = UtilidadesPersistencia.getConexion();            
            statement = cnn.prepareCall("{ CALL AlquilarVehiculo(?, ?, ?, ?, ?, ?, ?) }");
            
            int p = 0;
            statement.setString(++p, alquiler.getVehiculo().getMatricula());
            statement.setString(++p, alquiler.getCliente().getCi());
            statement.setInt(++p, alquiler.getSucursal().getCodigo());
            statement.setInt(++p, alquiler.getCantidadDeDias());
            statement.setFloat(++p, alquiler.getImporteSeguro());
            statement.setFloat(++p, alquiler.getImporteDeposito());
            statement.registerOutParameter(++p, java.sql.Types.VARCHAR);
            
            statement.executeUpdate();
            
            String mensajeError = statement.getString(p);
            
            if (mensajeError != null) {
                throw new ExcepcionPersistencia(mensajeError);
            }
        } catch (ExcepcionPersonalizada ex) { 
            throw ex;    
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo alquilar el vehiculo.", ex);
        }    
        finally{
            UtilidadesPersistencia.cerrarRecursos(statement, cnn);
        }
    }
    
    @Override
    public void DevolverVehiculo(int pIdAlquiler, int pCodigoSucursal, Date pFecha) throws ExcepcionPersonalizada {
        Connection conexion = null;
        CallableStatement consulta = null;
        try {
            conexion = UtilidadesPersistencia.getConexion();
            consulta = conexion.prepareCall("{ CALL AltaDevolucion(?,?,?,?)}");
            
            int p = 0;
            consulta.setInt(++p, pIdAlquiler);
            consulta.setInt(++p, pCodigoSucursal);
            consulta.setDate(++p, new java.sql.Date(pFecha.getTime()));
            consulta.registerOutParameter(++p, java.sql.Types.VARCHAR);
            
            consulta.executeUpdate();
            
            String mensaje = consulta.getString(p);
            
            if (mensaje != null){
                throw new ExcepcionPersistencia(mensaje);
            }
            
            
        } catch (ExcepcionPersistencia ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No realizar la devolucion.", ex);
        } finally {
            UtilidadesPersistencia.cerrarRecursos(consulta,conexion);
        }
    }
    
    @Override
    public DTAlquiler ObtenerAlquiler(String pCiCliente) throws ExcepcionPersonalizada {
        Connection conexion = null;
        CallableStatement consulta = null;
        ResultSet datos = null;
        DTAlquiler alquiler = null;
        try {
            conexion = UtilidadesPersistencia.getConexion();
            consulta = conexion.prepareCall("{ CALL AlquilerActual(?)}");
            
            int p = 0;
            consulta.setString(++p, pCiCliente);
            
            datos = consulta.executeQuery();
            
            if (datos.next()) {
                alquiler = crearAlquiler(datos);
            } else {
                alquiler = null;
            }
            
            return alquiler;
        } catch (ExcepcionPersistencia ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo obtener el alquiler", ex);
        } finally {
            UtilidadesPersistencia.cerrarRecursos(datos,consulta,conexion);
        }
    }

    private DTAlquiler crearAlquiler(ResultSet datos)  throws ExcepcionPersonalizada, SQLException {
        DTAlquiler alquiler = new DTAlquiler();
        alquiler.setId(datos.getInt("Id"));
        alquiler.setCliente(FabricaPersistencia.getPersistenciaCliente().obtener(datos.getString("CiCliente")));
        alquiler.setSucursal(FabricaPersistencia.getPersistenciaSucursal().buscar(datos.getInt("CodigoSucursal")));
        alquiler.setVehiculo(this.BuscarVehiculo(datos.getString("Matricula")));
        alquiler.setCantidadDeDias(datos.getInt("CantidadDias"));
        alquiler.setFecha(datos.getDate("Fecha"));
        alquiler.setImporteDeposito(datos.getFloat("ImporteDeposito"));
        alquiler.setImporteSeguro(datos.getFloat("ImporteSeguro"));
        return alquiler;
    }
}

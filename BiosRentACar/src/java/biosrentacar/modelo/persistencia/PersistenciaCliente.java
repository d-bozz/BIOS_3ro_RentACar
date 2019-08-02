/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biosrentacar.modelo.persistencia;

import biosrentacar.modelo.compartidos.beans.datatypes.DTCliente;
import biosrentacar.modelo.compartidos.beans.datatypes.DTVehiculo;
import biosrentacar.modelo.compartidos.beans.excepciones.ExcepcionPersistencia;
import biosrentacar.modelo.compartidos.beans.excepciones.ExcepcionPersonalizada;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 *
 * @author tm-01
 */
public class PersistenciaCliente implements IPersistenciaCliente{
    
    private static PersistenciaCliente instancia = null;
    
    public static PersistenciaCliente getInstancia() {
        if (instancia == null) {
            instancia = new PersistenciaCliente();
        }
        
        return instancia;
    }
    
    private PersistenciaCliente() {
        
    }

    @Override
    public void altaCliente(DTCliente cliente) 
            throws ExcepcionPersonalizada {
        Connection conexion = null;
        CallableStatement consulta = null;
        
       try {
           
           conexion = UtilidadesPersistencia.getConexion();
            
           consulta = conexion.prepareCall("{call altaCliente (?,?,?,?)}");
            
            consulta.setString(1, cliente.getCi());
            consulta.setString(2, cliente.getNombreCompleto());
            consulta.setString(3, cliente.getTelefono());
            consulta.registerOutParameter(4, java.sql.Types.VARCHAR);
            
            consulta.executeUpdate();
            
            String mensaje = consulta.getString(4);
                    if (mensaje != null )
                    {
                        throw new ExcepcionPersistencia(mensaje);
       
                    }
        }
       
       catch (ExcepcionPersonalizada ex) {
            throw ex;
        }  catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo agregar el cliente.", ex);
        } finally {
            UtilidadesPersistencia.cerrarRecursos(consulta, conexion);
        }
    }
    
    @Override
    public void modificarCliente(DTCliente cliente)
            throws ExcepcionPersonalizada {
        Connection conexion = null;
        CallableStatement consulta = null;
        
        
        try {
            conexion = UtilidadesPersistencia.getConexion();
            
            consulta = conexion.prepareCall("{call modificarCliente (?,?,?,?)}");
            
            consulta.setString(1, cliente.getCi());
            consulta.setString(2, cliente.getNombreCompleto());
            consulta.setString(3, cliente.getTelefono());
            consulta.registerOutParameter(4, java.sql.Types.VARCHAR);
            
            consulta.executeUpdate();
            
            String mensaje = consulta.getString(4);
                    if (mensaje != null )
                    {
                        throw new ExcepcionPersistencia(mensaje);
       
                    }
        }
            
         catch (ExcepcionPersonalizada ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo modificar el cliente.", ex);
        } finally {
            UtilidadesPersistencia.cerrarRecursos(consulta, conexion);
        }
    }
    
    @Override
    public ArrayList<DTCliente> buscar(String criterio)
            throws ExcepcionPersonalizada {
        Connection conexion = null;
        PreparedStatement consulta = null;
        ResultSet resultadoConsulta = null;
        
        try {
            conexion = UtilidadesPersistencia.getConexion();
            
            consulta = conexion.prepareStatement("SELECT * FROM Clientes WHERE Ci = ? OR NombreCompleto LIKE ?;");
            
            consulta.setString(1, criterio);
            consulta.setString(2, "%" + criterio + "%");
            
            resultadoConsulta = consulta.executeQuery();
            
            ArrayList<DTCliente> clientes = new ArrayList();
            DTCliente cliente;
            
            String cedula;
            String nombre;
            String telefono;
            
            while (resultadoConsulta.next()) {
                cedula = resultadoConsulta.getString("Ci");
                nombre = resultadoConsulta.getString("NombreCompleto");
                telefono = resultadoConsulta.getString("Telefono");
                
                cliente = new DTCliente(cedula, nombre, telefono);
                clientes.add(cliente);
            }
            
            return clientes;
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo buscar los clientes.", ex);
        } finally {
            UtilidadesPersistencia.cerrarRecursos(resultadoConsulta, consulta, conexion);
        }
    }
    
    @Override
    public DTCliente obtener(String cedula)
            throws ExcepcionPersonalizada {
        Connection conexion = null;
        PreparedStatement consulta = null;
        ResultSet resultadoConsulta = null;
        
        try {
            conexion = UtilidadesPersistencia.getConexion();
            
            consulta = conexion.prepareStatement("SELECT * FROM Clientes WHERE Ci = ?;");
            
            consulta.setString(1, cedula);
            
            resultadoConsulta = consulta.executeQuery();
            
            DTCliente cliente = null;
            
            String nombre;
            String telefono;
            
            if (resultadoConsulta.next()) {
                nombre = resultadoConsulta.getString("NombreCompleto");
                telefono = resultadoConsulta.getString("Telefono");
                
                cliente = new DTCliente(cedula, nombre, telefono);
            }
            
            return cliente;
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo buscar el cliente.", ex);
        } finally {
            UtilidadesPersistencia.cerrarRecursos(resultadoConsulta, consulta, conexion);
        }
    }
    
    @Override
    public void eliminarCliente(String cedula)
            throws ExcepcionPersonalizada {
        Connection conexion = null;
        CallableStatement consulta = null;
        
        try {
            conexion = UtilidadesPersistencia.getConexion();
            
            consulta = conexion.prepareCall("{call bajaCliente(?,?)}");
            
            consulta.setString(1, cedula);
            consulta.registerOutParameter(2, java.sql.Types.VARCHAR);
            
            consulta.executeUpdate();
            
            String mensaje = consulta.getString(2);
                    if (mensaje != null )
                    {
                        throw new ExcepcionPersistencia(mensaje);
       
                    }
            
        } catch (ExcepcionPersonalizada ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo eliminar el cliente.", ex);
        } finally {
            UtilidadesPersistencia.cerrarRecursos(consulta, conexion);
        }
    }
    
    @Override
    public ArrayList<DTCliente> listar()
            throws ExcepcionPersonalizada {
        Connection conexion = null;
        Statement consulta = null;
        ResultSet resultadoConsulta = null;
        
        try {
            conexion = UtilidadesPersistencia.getConexion();
            
            consulta = conexion.createStatement();
            
            resultadoConsulta = consulta.executeQuery("SELECT * FROM Clientes;");
            
            ArrayList<DTCliente> clientes = new ArrayList();
            DTCliente cliente;
            
            String cedula;
            String nombre;
            String telefono;
            
            while (resultadoConsulta.next()) {
                cedula = resultadoConsulta.getString("Ci");
                nombre = resultadoConsulta.getString("NombreCompleto");
                telefono = resultadoConsulta.getString("Telefono");
                
                cliente = new DTCliente(cedula, nombre, telefono);
                clientes.add(cliente);
            }
            
            return clientes;
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo listar los clientes.", ex);
        } finally {
            UtilidadesPersistencia.cerrarRecursos(resultadoConsulta, consulta, conexion);
        }
    }
    
    public int CantidadDeAlquileres(String cedula) throws ExcepcionPersistencia {
        Connection cnn = null;
        CallableStatement statement = null;
        
        try {
            cnn = UtilidadesPersistencia.getConexion();            
            statement = cnn.prepareCall("{ CALL CantidadAlquileres(?, ?) }");
            
            int p = 0;
            statement.setString(++p, cedula);
            statement.registerOutParameter(++p, java.sql.Types.INTEGER);
            
            statement.executeUpdate();
            
            int cantidadDeAlquileres = statement.getInt(p);
            
            return cantidadDeAlquileres;
            
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo obtener la cantidad de alquileres.", ex);
        }    
        finally{
            UtilidadesPersistencia.cerrarRecursos(statement, cnn);
        }
    }
}

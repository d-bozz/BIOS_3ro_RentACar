/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biosrentacar.modelo.persistencia;

import biosrentacar.modelo.compartidos.beans.datatypes.DTEmpleado;
import biosrentacar.modelo.compartidos.beans.datatypes.DTSucursal;
import biosrentacar.modelo.compartidos.beans.excepciones.ExcepcionPersistencia;
import biosrentacar.modelo.compartidos.beans.excepciones.ExcepcionPersonalizada;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sistemas
 */
public class PersistenciaSucursal implements IPersistenciaSucursal {

    private static PersistenciaSucursal instancia = null;
    
    public static PersistenciaSucursal getInstancia() {
        if (instancia == null) {
            instancia = new PersistenciaSucursal();
        }
        
        return instancia;
    }
    
    private PersistenciaSucursal() {
        
    }
    
    protected DTSucursal crearSucursal (ResultSet datos) throws SQLException {
        DTSucursal sucursal = new DTSucursal();
        sucursal.setCodigo(datos.getInt("Codigo"));
        sucursal.setNombre(datos.getString("Nombre"));
        return sucursal;
    }
    
    @Override
    public List<DTSucursal> listar() throws ExcepcionPersonalizada {
        Connection conexion = null;
        Statement consulta = null;
        ResultSet resultadoConsulta = null;
        List<DTSucursal> sucursales = new ArrayList<DTSucursal>();
        try {
            conexion = UtilidadesPersistencia.getConexion();
            consulta = conexion.createStatement();
            resultadoConsulta = consulta.executeQuery("SELECT * FROM Sucursales");
            
            while(resultadoConsulta.next()){
                sucursales.add(crearSucursal(resultadoConsulta));
            }
            
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo listar las sucursales.", ex);
        } finally {
            UtilidadesPersistencia.cerrarRecursos(resultadoConsulta, consulta, conexion);
        }
        return sucursales;
    }
    
    @Override
    public DTSucursal buscar(int pCodigoSucursal) 
     throws ExcepcionPersonalizada {
        Connection conexion = null;
        PreparedStatement consulta = null;
        ResultSet resultadoConsulta = null;
        
        try {
            DTSucursal sucursal = null; 
            conexion = UtilidadesPersistencia.getConexion();
            consulta = conexion.prepareCall("SELECT * FROM Sucursales WHERE Codigo = ?;");
            consulta.setInt(1, pCodigoSucursal);
            
            resultadoConsulta = consulta.executeQuery();
            
            if (resultadoConsulta.next()){
                sucursal = new DTSucursal();
                sucursal.setCodigo(resultadoConsulta.getInt("Codigo"));
                sucursal.setNombre(resultadoConsulta.getString("Nombre"));
            }
            
            return sucursal;
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo buscar la sucursal.", ex);
        }
        finally {
            UtilidadesPersistencia.cerrarRecursos(resultadoConsulta, consulta, conexion);
        }   
    }
    
}

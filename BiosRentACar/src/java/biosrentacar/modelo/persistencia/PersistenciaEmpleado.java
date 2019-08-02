/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biosrentacar.modelo.persistencia;

import biosrentacar.modelo.compartidos.beans.datatypes.DTEmpleado;
import biosrentacar.modelo.compartidos.beans.datatypes.DTSucursal;
import biosrentacar.modelo.compartidos.beans.excepciones.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author sistemas
 */
public class PersistenciaEmpleado implements IPersistenciaEmpleado {
 
    private static PersistenciaEmpleado instancia = null;
    
    public static PersistenciaEmpleado getInstancia() {
        if (instancia == null) {
            instancia = new PersistenciaEmpleado();
        }
        
        return instancia;
    }
    
    private PersistenciaEmpleado() {
        
    }

    @Override
    public DTEmpleado buscar(String usuario, String contrasena) 
            throws ExcepcionPersonalizada {
        Connection conexion = null;
        PreparedStatement consulta = null;
        ResultSet resultadoConsulta = null;
        
        try {
            DTEmpleado empleado = null; 
            conexion = UtilidadesPersistencia.getConexion();
            consulta = conexion.prepareCall("SELECT * FROM Empleados WHERE Usuario = ? AND Contrasena = ?;");
            consulta.setString(1, usuario);
            consulta.setString(2, contrasena);
            
            resultadoConsulta = consulta.executeQuery();
            
            if (resultadoConsulta.next()){
                empleado = new DTEmpleado();
                empleado.setNombreUsuario(resultadoConsulta.getString("Usuario"));
                empleado.setContrasenia(resultadoConsulta.getString("Contrasena"));
                empleado.setSucursal(FabricaPersistencia.getPersistenciaSucursal().buscar(resultadoConsulta.getInt("CodigoSucursal")));
            }
            
            return empleado;
        } catch (Exception ex) {
            throw new ExcepcionPersistencia("No se pudo buscar el empleado.", ex);
        }
        finally {
            UtilidadesPersistencia.cerrarRecursos(resultadoConsulta, consulta, conexion);
        }
    }
    
    
}

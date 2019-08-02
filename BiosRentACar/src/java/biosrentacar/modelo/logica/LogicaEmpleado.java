/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biosrentacar.modelo.logica;

import biosrentacar.modelo.compartidos.beans.datatypes.DTEmpleado;
import biosrentacar.modelo.compartidos.beans.excepciones.ExcepcionPersonalizada;
import biosrentacar.modelo.persistencia.FabricaPersistencia;
import biosrentacar.modelo.persistencia.IPersistenciaEmpleado;

/**
 *
 * @author sistemas
 */
public class LogicaEmpleado implements ILogicaEmpleado {
    private static LogicaEmpleado instancia = null;
    
    private IPersistenciaEmpleado persistencia = FabricaPersistencia.getPersistenciaEmpleado();
    
    private LogicaEmpleado(){
        
    }
    
    public static LogicaEmpleado getInstancia(){
        if (instancia == null)
            instancia = new LogicaEmpleado();
        return instancia;
    }

    @Override
    public DTEmpleado Buscar(String nombreUsuario, String contrasena) 
    throws ExcepcionPersonalizada {
        return persistencia.buscar(nombreUsuario, contrasena);
    }
}

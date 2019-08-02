/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biosrentacar.modelo.logica;

import biosrentacar.modelo.compartidos.beans.datatypes.DTSucursal;
import biosrentacar.modelo.compartidos.beans.excepciones.ExcepcionPersonalizada;
import biosrentacar.modelo.persistencia.FabricaPersistencia;
import biosrentacar.modelo.persistencia.IPersistenciaSucursal;
import java.util.List;

/**
 *
 * @author sistemas
 */
public class LogicaSucursal implements ILogicaSucursal {

    private IPersistenciaSucursal persistencia = FabricaPersistencia.getPersistenciaSucursal();
    private static LogicaSucursal instancia;
    
    private LogicaSucursal() {
    }
    
    public static LogicaSucursal getInstancia() {
        if (instancia == null)
            instancia = new LogicaSucursal();
        return instancia;
    }
    
    @Override
    public List<DTSucursal> listar() throws ExcepcionPersonalizada {
        return persistencia.listar();
    }

    @Override
    public DTSucursal buscar(int pCodigo) throws ExcepcionPersonalizada {
        return persistencia.buscar(pCodigo);
    }
    
}

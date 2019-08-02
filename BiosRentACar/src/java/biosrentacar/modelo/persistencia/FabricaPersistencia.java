/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biosrentacar.modelo.persistencia;

/**
 *
 * @author sistemas
 */
public class FabricaPersistencia {
    
    public static IPersistenciaEmpleado getPersistenciaEmpleado() {
        return PersistenciaEmpleado.getInstancia();
    }
    public static IPersistenciaCliente getPersistenciaCliente() {
        return PersistenciaCliente.getInstancia();
    }
    
    public static IPersistenciaVehiculo getPersistenciaVehiculo() {
        return PersistenciaVehiculo.getInstancia();
    }
    
    public static IPersistenciaSucursal getPersistenciaSucursal() {
        return PersistenciaSucursal.getInstancia();
    }
}

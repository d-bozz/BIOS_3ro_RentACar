/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biosrentacar.modelo.logica;

/**
 *
 * @author sistemas
 */
public class FabricaLogica {
    public static ILogicaEmpleado getLogicaEmpleado() {
        return LogicaEmpleado.getInstancia();
    }
    public static ILogicaCliente getLogicaCliente() {
        return LogicaCliente.getInstancia();
    }
    public static ILogicaVehiculo getLogicaVehiculo() {
        return LogicaVehiculo.getInstancia();
    }
    
    public static ILogicaSucursal getLogicaSucursal() {
        return LogicaSucursal.getInstancia();
    }
}

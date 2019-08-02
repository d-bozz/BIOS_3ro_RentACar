/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biosrentacar.modelo.logica;

import biosrentacar.modelo.compartidos.beans.datatypes.*;
import biosrentacar.modelo.compartidos.beans.excepciones.ExcepcionPersonalizada;
import java.util.Date;
import java.util.List;

/**
 *
 * @author sistemas
 */
public interface ILogicaVehiculo {
    List<DTVehiculo> ListarVehiculosSucursal(int pSucursal) throws ExcepcionPersonalizada;
    DTVehiculo BuscarVehiculo(String matricula) throws ExcepcionPersonalizada;
    void AltaVehiculo(DTVehiculo vehiculo) throws ExcepcionPersonalizada;
    void ModificarVehiculo(DTVehiculo vehiculo) throws ExcepcionPersonalizada;
    void BorrarVehiculo(String matricula) throws ExcepcionPersonalizada;
    void TrasladarVehiculo(String matricula, int sucursalDestino) throws ExcepcionPersonalizada;
    void AlquilarVehiculo(String ciCliente, String matricula, int codigoSucursal, int cantidadDeDias, float importeSeguro, float importeDeposito) throws ExcepcionPersonalizada;
    int obtenerTarifa(String tipoTarifa);
    void DevolverVehiculo(int pIdAlquiler, int pCodigoSucursal, Date pFecha) throws ExcepcionPersonalizada;
    DTAlquiler ObtenerAlquiler(String pCiCliente) throws ExcepcionPersonalizada;
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biosrentacar.modelo.persistencia;

import biosrentacar.modelo.compartidos.beans.datatypes.DTAlquiler;
import biosrentacar.modelo.compartidos.beans.datatypes.DTVehiculo;
import biosrentacar.modelo.compartidos.beans.excepciones.ExcepcionPersistencia;
import biosrentacar.modelo.compartidos.beans.excepciones.ExcepcionPersonalizada;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Bruno
 */
public interface IPersistenciaVehiculo {
    List<DTVehiculo> ListarVehiculosSucursal(int pSucursal) throws ExcepcionPersistencia;
    DTVehiculo BuscarVehiculo(String matricula) throws ExcepcionPersistencia;
    void AltaVehiculo(DTVehiculo vehiculo) throws ExcepcionPersistencia ;
    void ModificarVehiculo(DTVehiculo vehiculo) throws ExcepcionPersistencia ;
    void BorrarVehiculo(String matricula) throws ExcepcionPersistencia;
    void TrasladarVehiculo(String matricula, int sucursalDestino) throws ExcepcionPersistencia;
    void AlquilarVehiculo(DTAlquiler alquiler) throws ExcepcionPersistencia;
    void DevolverVehiculo(int pIdAlquiler, int pCodigoSucursal, Date pFecha) throws ExcepcionPersonalizada;
    DTAlquiler ObtenerAlquiler(String pCiCliente) throws ExcepcionPersonalizada;
}

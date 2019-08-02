/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biosrentacar.modelo.persistencia;

import biosrentacar.modelo.compartidos.beans.datatypes.DTCliente;
import biosrentacar.modelo.compartidos.beans.excepciones.ExcepcionPersistencia;
import biosrentacar.modelo.compartidos.beans.excepciones.ExcepcionPersonalizada;
import java.util.ArrayList;

/**
 *
 * @author tm-01
 */
public interface IPersistenciaCliente {

    ArrayList<DTCliente> buscar(String criterio) throws ExcepcionPersonalizada;
    void altaCliente(DTCliente cliente) throws ExcepcionPersonalizada;
    void modificarCliente(DTCliente cliente)throws ExcepcionPersonalizada;
    DTCliente obtener(String cedula) throws ExcepcionPersonalizada;
    void eliminarCliente(String cedula) throws ExcepcionPersonalizada;
    ArrayList<DTCliente> listar() throws ExcepcionPersonalizada;
    int CantidadDeAlquileres(String cedula) throws ExcepcionPersistencia;
}

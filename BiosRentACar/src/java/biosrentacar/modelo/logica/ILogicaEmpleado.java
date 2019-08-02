/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biosrentacar.modelo.logica;

import biosrentacar.modelo.compartidos.beans.datatypes.DTEmpleado;
import biosrentacar.modelo.compartidos.beans.excepciones.ExcepcionPersonalizada;

/**
 *
 * @author sistemas
 */
public interface ILogicaEmpleado {
    DTEmpleado Buscar(String nombreUsuario, String contrasena) throws ExcepcionPersonalizada;
}

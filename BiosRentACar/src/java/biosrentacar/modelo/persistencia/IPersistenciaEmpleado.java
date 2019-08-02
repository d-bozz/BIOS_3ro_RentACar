/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biosrentacar.modelo.persistencia;

import biosrentacar.modelo.compartidos.beans.datatypes.DTEmpleado;
import biosrentacar.modelo.compartidos.beans.excepciones.ExcepcionPersonalizada;

/**
 *
 * @author sistemas
 */
public interface IPersistenciaEmpleado {
    
    DTEmpleado buscar(String usuario, String contrasena) throws ExcepcionPersonalizada;
}

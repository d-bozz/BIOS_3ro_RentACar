/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biosrentacar.modelo.logica;

import biosrentacar.modelo.compartidos.beans.datatypes.DTSucursal;
import biosrentacar.modelo.compartidos.beans.excepciones.ExcepcionPersonalizada;
import java.util.List;

/**
 *
 * @author sistemas
 */
public interface ILogicaSucursal {
    List<DTSucursal> listar() throws ExcepcionPersonalizada;
    DTSucursal buscar(int pCodigo) throws ExcepcionPersonalizada;
}

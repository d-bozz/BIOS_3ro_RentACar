/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biosrentacar.modelo.compartidos.beans.excepciones;

import java.io.Serializable;

/**
 *
 * @author DBD Software
 */
public class ExcepcionPersonalizada extends Exception implements Serializable {
    
    public ExcepcionPersonalizada() {
        
    }
    
    public ExcepcionPersonalizada(String mensaje) {
        super(mensaje);
    }
    
    public ExcepcionPersonalizada(String mensaje, Exception excepcionInterna) {
        super(mensaje, excepcionInterna);
    }
    
}

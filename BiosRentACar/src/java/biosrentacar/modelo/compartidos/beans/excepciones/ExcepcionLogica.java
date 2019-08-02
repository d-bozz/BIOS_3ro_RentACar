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
public class ExcepcionLogica extends ExcepcionPersonalizada implements Serializable {
    
    public ExcepcionLogica() {
        
    }
    
    public ExcepcionLogica(String mensaje) {
        super(mensaje);
    }
    
    public ExcepcionLogica(String mensaje, Exception excepcionInterna) {
        super(mensaje, excepcionInterna);
    }
    
}

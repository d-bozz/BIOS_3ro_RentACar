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
public class ExcepcionPresentacion extends ExcepcionPersonalizada implements Serializable {
    
    public ExcepcionPresentacion() {
        
    }
    
    public ExcepcionPresentacion(String mensaje) {
        super(mensaje);
    }
    
    public ExcepcionPresentacion(String mensaje, Exception excepcionInterna) {
        super(mensaje, excepcionInterna);
    }
    
}

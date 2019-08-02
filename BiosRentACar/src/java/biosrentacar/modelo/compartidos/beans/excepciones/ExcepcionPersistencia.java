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
public class ExcepcionPersistencia extends ExcepcionPersonalizada implements Serializable {
    
    public ExcepcionPersistencia() {
        
    }
    
    public ExcepcionPersistencia(String mensaje) {
        super(mensaje);
    }
    
    public ExcepcionPersistencia(String mensaje, Exception excepcionInterna) {
        super(mensaje, excepcionInterna);
    }
    
}

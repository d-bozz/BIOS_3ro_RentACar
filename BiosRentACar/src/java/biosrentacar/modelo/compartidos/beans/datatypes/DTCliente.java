/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biosrentacar.modelo.compartidos.beans.datatypes;

import java.io.Serializable;

/**
 *
 * @author sistemas
 */
public class DTCliente implements Serializable {
    private String ci;
    private String nombreCompleto;
    private String telefono;

    public DTCliente(int cedula, String nombre, int telefono) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public DTCliente(){
        this("0","N/A","0");
    }
    
    public DTCliente(String ci,String nombreCompleto, String telefono) {
        this.setCi(ci);
        this.setNombreCompleto(nombreCompleto);
        this.setTelefono(telefono);
    }
}

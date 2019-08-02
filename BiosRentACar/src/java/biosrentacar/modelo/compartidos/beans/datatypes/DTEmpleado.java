/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biosrentacar.modelo.compartidos.beans.datatypes;

import java.io.Serializable;

/**
 *
 * @author lospib
 */
public class DTEmpleado implements Serializable{
    private String nombreUsuario;
    private String contrasenia;
    private DTSucursal sucursal;

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public DTSucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(DTSucursal sucursal) {
        this.sucursal = sucursal;
    }

    public DTEmpleado(String nombreUsuario, String contrasenia, DTSucursal sucursal) {
        setNombreUsuario(nombreUsuario);
        setContrasenia(contrasenia);
        setSucursal(sucursal);
    }

    public DTEmpleado() {
        this("N/A","N/A",new DTSucursal());
    }
}

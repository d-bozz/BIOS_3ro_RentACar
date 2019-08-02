/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biosrentacar.modelo.compartidos.beans.datatypes;

import java.util.Date;

/**
 *
 * @author DBD Software
 */
public class DTDevolucion {
    private DTAlquiler alquiler;
    private DTSucursal sucursal;
    private Date fecha;

    public DTAlquiler getAlquiler() {
        return alquiler;
    }

    public void setAlquiler(DTAlquiler alquiler) {
        this.alquiler = alquiler;
    }

    public DTSucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(DTSucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public DTDevolucion() {
        this(new DTAlquiler(),new DTSucursal(), new Date());
    }
    
    public DTDevolucion(DTAlquiler alquiler, DTSucursal sucursal, Date fecha){
        this.setAlquiler(alquiler);
        this.setSucursal(sucursal);
        this.setFecha(fecha);
    }
}

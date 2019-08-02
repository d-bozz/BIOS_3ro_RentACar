/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biosrentacar.modelo.compartidos.beans.datatypes;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Chadi
 */
public class DTAlquiler implements Serializable{
    
    private int id;
    private Date fecha;
    private DTCliente cliente;
    private DTSucursal sucursal;
    private DTVehiculo vehiculo;
    private int cantidadDeDias;
    private float importeSeguro;
    private float importeDeposito;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public DTCliente getCliente() {
        return cliente;
    }

    public void setCliente(DTCliente cliente) {
        this.cliente = cliente;
    }

    public DTSucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(DTSucursal sucursal) {
        this.sucursal = sucursal;
    }

    public DTVehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(DTVehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }
    
    public int getCantidadDeDias() {
        return cantidadDeDias;
    }

    public void setCantidadDeDias(int cantidadDeDias) {
        this.cantidadDeDias = cantidadDeDias;
    }
    
    public float getImporteSeguro() {
        return importeSeguro;
    }
    
    public void setImporteSeguro(float importeSeguro) {
        this.importeSeguro = importeSeguro;
    }
    
    public float getImporteDeposito() {
        return importeDeposito;
    }
    
    public void setImporteDeposito(float importeDeposito) {
        this.importeDeposito = importeDeposito;
    }
    
    public double getImporteTotal() {
        return cantidadDeDias * vehiculo.getPrecioPorDia() + importeSeguro + importeDeposito;
    };
    
    public DTAlquiler () {
        this(-1,new Date(),new DTCliente(),new DTSucursal(), new DTVehiculo(), -1,0,0);
    }
    
    public DTAlquiler(int id, Date fecha, DTCliente cliente, DTSucursal sucursal, DTVehiculo vehiculo, int cantidadDeDias, float importeSeguro, float importeDeposito) {
        setId(id);
        setFecha(fecha);
        setCliente(cliente);
        setSucursal(sucursal);
        setVehiculo(vehiculo);
        setCantidadDeDias(cantidadDeDias);
        setImporteSeguro(importeSeguro);
        setImporteDeposito(importeDeposito);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biosrentacar.modelo.compartidos.beans.datatypes;

import java.io.Serializable;

/**
 *
 * @author Chadi
 */
public class DTVehiculo implements Serializable{
    private String matricula;
    private String tipo;
    private String descripcion;
    private double precioPorDia;
    private DTSucursal sucursal;

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioPorDia() {
        return precioPorDia;
    }

    public void setPrecioPorDia(double precioPorDia) {
        this.precioPorDia = precioPorDia;
    }
    
     public DTSucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(DTSucursal sucursal) {
        this.sucursal = sucursal;
    }  

    public DTVehiculo() {
        this("XXX1234","N/A","N/A",0,new DTSucursal());
    }

    public DTVehiculo(String matricula, String tipo, String descripcion, double precioPorDia, DTSucursal sucursal) {
        setMatricula(matricula);
        setTipo(tipo);
        setDescripcion(descripcion);
        setPrecioPorDia(precioPorDia);
        setSucursal(sucursal);
    }
    
    
}

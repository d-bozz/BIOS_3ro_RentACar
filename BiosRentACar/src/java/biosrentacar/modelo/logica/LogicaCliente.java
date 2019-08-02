/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biosrentacar.modelo.logica;

import biosrentacar.modelo.compartidos.beans.datatypes.DTCliente;
import biosrentacar.modelo.compartidos.beans.excepciones.ExcepcionLogica;
import biosrentacar.modelo.compartidos.beans.excepciones.ExcepcionPersonalizada;
import biosrentacar.modelo.persistencia.FabricaPersistencia;
import biosrentacar.modelo.persistencia.IPersistenciaCliente;
import java.util.ArrayList;

/**
 *
 * @author tm-01
 */
public class LogicaCliente implements ILogicaCliente{
 private static LogicaCliente instancia = null;
    
    private IPersistenciaCliente persistencia = FabricaPersistencia.getPersistenciaCliente();
    
    private LogicaCliente(){
        
    }
    
    public static LogicaCliente getInstancia(){
        if (instancia == null)
            instancia = new LogicaCliente();
        return instancia;
    }
    
    
    @Override
    public ArrayList<DTCliente> buscar(String criterio)
            throws ExcepcionPersonalizada {
        if (criterio == null || criterio.length() == 0) {
            return listar();
        }
        
        return persistencia.buscar(criterio);
    }
    
  
   @Override
    public void validar(DTCliente cliente)
            throws ExcepcionPersonalizada {
        if (cliente == null) {
            throw new ExcepcionLogica("El cliente no puede ser nulo");
        }
        
        try {
            Integer ci = Integer.parseInt(cliente.getCi());
            if (ci < 0) {
                throw new ExcepcionLogica("La cedula no puede ser negativa");
            }
            
        } catch (Exception e) {
            throw new ExcepcionLogica("El formato de la cedula no es correcto");
        }
        
        if (cliente.getCi().length() != 8) {
            throw new ExcepcionLogica("El largo de la cedula no es correcto");
        }
        
                
        if (cliente.getCi().trim().equals("")) {
            throw new ExcepcionLogica("La cedula no puede quedar vacía");
        }
        
        if (cliente.getNombreCompleto().length() > 50) {
            throw new ExcepcionLogica("El nombre no puede exceder los 50 caracteres de longitud");
        }
        
        
        if (cliente.getNombreCompleto().trim().equals("")) {
            throw new ExcepcionLogica("El nombre no puede ser vacio");
        }
        
        try {
            Integer tel = Integer.parseInt(cliente.getTelefono());
             if (tel < 0) {
            throw new ExcepcionLogica("El telefono no puede ser negativo");
            }
                    
        } catch (Exception e) {
            throw new ExcepcionLogica("El formato del telefono no es correcto");
        }
        
        if (cliente.getTelefono().trim().equals("")) {
            throw new ExcepcionLogica("El telefono no puede ser vacio");
        }
        
        if (cliente.getTelefono().length() > 20) {
            throw new ExcepcionLogica("El telefono no puede superar los 20 caracteres");
        }
           
    }
    
    @Override
    public void altaCliente(DTCliente cliente)
            throws ExcepcionPersonalizada {
        validar(cliente);
        persistencia.altaCliente(cliente); 
    }
    
    @Override
    public DTCliente obtener(String cedula)
            throws ExcepcionPersonalizada {
        return persistencia.obtener(cedula);
    }
    
     @Override
    public void modificarCliente(DTCliente cliente)
            throws ExcepcionPersonalizada {
        validar(cliente);
        
        persistencia.modificarCliente(cliente); 
    }
    
    @Override
    public void eliminarCliente(String cedula)
            throws ExcepcionPersonalizada {
        persistencia.eliminarCliente(cedula);
    }
    
    @Override
    public ArrayList<DTCliente> listar()
            throws ExcepcionPersonalizada {
        return persistencia.listar();
    }
    
    @Override
    public int CantidadDeAlquileres(String cedula)
            throws ExcepcionPersonalizada {
        return persistencia.CantidadDeAlquileres(cedula);
    }
}
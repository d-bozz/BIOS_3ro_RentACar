/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biosrentacar.modelo.logica;

import biosrentacar.modelo.compartidos.beans.datatypes.*;
import biosrentacar.modelo.compartidos.beans.excepciones.*;
import biosrentacar.modelo.persistencia.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author sistemas
 */
public class LogicaVehiculo implements ILogicaVehiculo {

    private static LogicaVehiculo instancia = null;
    
    private IPersistenciaVehiculo persistencia = FabricaPersistencia.getPersistenciaVehiculo();
    
    private LogicaVehiculo(){
        
    }
    
    public static LogicaVehiculo getInstancia(){
        if (instancia == null)
            instancia = new LogicaVehiculo();
        return instancia;
    }
    
    @Override
    public List<DTVehiculo> ListarVehiculosSucursal(int pSucursal) throws ExcepcionPersonalizada {
        return persistencia.ListarVehiculosSucursal(pSucursal);
    }

    @Override
    public DTVehiculo BuscarVehiculo(String matricula) throws ExcepcionPersonalizada {
        return persistencia.BuscarVehiculo(matricula);
    }

    @Override
    public void AltaVehiculo(DTVehiculo vehiculo) throws ExcepcionPersonalizada{
        validarVehiculo(vehiculo);
        persistencia.AltaVehiculo(vehiculo);
    }

    @Override
    public void ModificarVehiculo(DTVehiculo vehiculo) throws ExcepcionPersonalizada {
        validarVehiculo(vehiculo);
        persistencia.ModificarVehiculo(vehiculo);
    }

    @Override
    public void BorrarVehiculo(String matricula) throws ExcepcionPersonalizada{
        persistencia.BorrarVehiculo(matricula);
    }

    @Override
    public void TrasladarVehiculo(String matricula, int sucursalDestino) throws ExcepcionPersonalizada {
        persistencia.TrasladarVehiculo(matricula, sucursalDestino);
    }
    
    @Override
    public void AlquilarVehiculo(String ciCliente, String matricula, int codigoSucursal, int cantidadDeDias, float importeSeguro, float importeDeposito) throws ExcepcionPersonalizada {
        
        DTVehiculo vehiculo = LogicaVehiculo.getInstancia().BuscarVehiculo(matricula);
        DTSucursal sucursal = LogicaSucursal.getInstancia().buscar(codigoSucursal);
        
        DTCliente cliente = LogicaCliente.getInstancia().obtener(ciCliente);
        
        DTAlquiler alquiler = new DTAlquiler(0,new Date(), cliente, sucursal, vehiculo, cantidadDeDias, importeSeguro, importeDeposito);
        
        validarAlquiler(alquiler);
        persistencia.AlquilarVehiculo(alquiler);
    }
    
    public void validarAlquiler(DTAlquiler alquiler) throws ExcepcionPersonalizada {
        if (alquiler.getCliente() == null) {
            throw new ExcepcionLogica("Debe indicar un cliente.");
        }
        
        if (alquiler.getSucursal() == null) {
            throw new ExcepcionLogica("Debe indicar una sucursal");
        }
        
        if (alquiler.getVehiculo() == null) {
            throw new ExcepcionLogica("Debe indicar un vehiculo.");
        }
        
        if (alquiler.getCantidadDeDias() <= 0) {
            throw new ExcepcionLogica("La cantidad de dias debe ser mayor a cero.");
        }
        
        if (alquiler.getImporteDeposito() < 0) {
            throw new ExcepcionLogica("El importe del deposito debe ser positivo.");
        }
        
        if (alquiler.getImporteSeguro()< 0) {
            throw new ExcepcionLogica("El importe del seguro debe ser positivo.");
        }
        
    }
    
    public int obtenerTarifa(String tipoTarifa) {
        int tarifa = 0;
        String fileName = "tarifa_adicionales.txt";
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream(fileName)))) {

            String line;
            
            while ((line = br.readLine()) != null) {
                String valor = line.replaceAll("[^-?0-9]+", ""); 
                if (line.contains(tipoTarifa)){
                    tarifa = Integer.parseInt(valor);
                }
            }
        } catch (Exception ex){
            tarifa = 0;
        }
        
        return tarifa;
    }

    @Override
    public void DevolverVehiculo(int pIdAlquiler, int pCodigoSucursal, Date pFecha) throws ExcepcionPersonalizada {
        persistencia.DevolverVehiculo(pIdAlquiler, pCodigoSucursal, pFecha);
    }

    @Override
    public DTAlquiler ObtenerAlquiler(String pCiCliente) throws ExcepcionPersonalizada {
        return persistencia.ObtenerAlquiler(pCiCliente);
    }
    
    public void validarVehiculo(DTVehiculo vehiculo) throws ExcepcionPersonalizada{
        
        if (vehiculo.getMatricula() == null || vehiculo.getMatricula().trim().length() == 0)
            throw new ExcepcionLogica("Debe ingresar una matricula.");
        
        if (! Pattern.matches("[a-s][a-s][a-s][0-9][0-9][0-9][0-9]", vehiculo.getMatricula().toLowerCase()))
            throw new ExcepcionLogica("La matricula debe estar compuesta por 3 letras (de la A a la S) y 4 digitos.");
        
        if (vehiculo.getTipo() == null || vehiculo.getTipo().trim().length() == 0)
            throw new ExcepcionLogica("Debe ingresar el tipo.");
        
        if (vehiculo.getDescripcion()== null || vehiculo.getDescripcion().trim().length() == 0)
            throw new ExcepcionLogica("Debe ingresar la descripción.");
        
        if (vehiculo.getPrecioPorDia() <= 0)
            throw new ExcepcionLogica("El precio por dia debe ser mayor a 0.");
        
    }     
    
}

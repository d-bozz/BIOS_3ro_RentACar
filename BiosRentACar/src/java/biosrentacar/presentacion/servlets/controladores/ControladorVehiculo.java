/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biosrentacar.presentacion.servlets.controladores;

import biosrentacar.modelo.compartidos.beans.datatypes.*;
import biosrentacar.modelo.compartidos.beans.excepciones.ExcepcionLogica;
import biosrentacar.modelo.compartidos.beans.excepciones.ExcepcionPersonalizada;
import biosrentacar.modelo.compartidos.beans.excepciones.ExcepcionPresentacion;
import biosrentacar.modelo.logica.FabricaLogica;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.round;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sistemas
 */
@MultipartConfig
public class ControladorVehiculo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null) accion = "listar";
        
        switch (accion) {
                
            case "listar":
                listar_get(request, response);
                break;
            case "trasladar":
                trasladar_get(request, response);
                break;
            case "alquilar":
                alquilar_get(request, response);
                break;
            case "devolver":
                devolver_get(request,response);
                break;
            case "devolucion":
                devolucion_get(request,response);
                break;
                
            case "agregar":
                agregar_get(request, response);
                break;
            case "modificar":
                modificar_get(request, response);
                break;
            case "eliminar":
                eliminar_get(request, response);
                break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        
        switch (accion) {
            case "trasladar":
                trasladar_post(request, response);    
                break;
            case "alquilar":
                alquilar_post(request, response);    
                break;
            case "confirmarAlquiler":
                confirmarAlquiler_post(request, response);    
                break;
            case "devolucion":
                devolucion_post(request,response);
                break;
            case "agregar":
                agregar_post(request, response);
                break;
            case "modificar":
                modificar_post(request, response);
                break;
            case "eliminar":
                eliminar_post(request, response);
                break;
            
        }
    }

    private void listar_get(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        try {
            DTEmpleado empleado = (DTEmpleado)request.getSession().getAttribute("empleadoLogueado");
            List<DTVehiculo> vehiculos = FabricaLogica.getLogicaVehiculo().ListarVehiculosSucursal(empleado.getSucursal().getCodigo());
            request.getSession().setAttribute("vehiculos", vehiculos);
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/listar.jsp").forward(request, response);
        } catch (ExcepcionPersonalizada ex) {
            request.setAttribute("mensaje", ex.getMessage());
            request.getRequestDispatcher("WEB-INF/vistas/inicio/index.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("mensaje", "No se pudo listar los vehiculos.");
            request.getRequestDispatcher("WEB-INF/vistas/inicio/index.jsp").forward(request, response);
        }
        
    }

    private void trasladar_get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String matricula = request.getParameter("matricula");
            if (matricula == null)
                throw new ExcepcionPresentacion("La matricula no puede ser vacia.");
            DTVehiculo vehiculo = FabricaLogica.getLogicaVehiculo().BuscarVehiculo(matricula);
            List<DTSucursal> sucursales = FabricaLogica.getLogicaSucursal().listar();
            
            DTSucursal sucursalActual = ((DTEmpleado)request.getSession().getAttribute("empleadoLogueado")).getSucursal();
            for (int i = 0; i < sucursales.size(); i++) {
                if (sucursales.get(i).getCodigo() == sucursalActual.getCodigo() ) {
                    sucursales.remove(i);
                    break;
                }
            }
            
            request.setAttribute("vehiculo", vehiculo);
            request.getSession().setAttribute("sucursales", sucursales);
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/trasladar.jsp").forward(request,response);
        } catch (ExcepcionPersonalizada ex) {
            request.setAttribute("mensaje", ex.getMessage());
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/listar.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("mensaje", "No se pudo obtener la vista de traslado.");
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/listar.jsp").forward(request, response);
        }
    }
    
    private void alquilar_get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String matricula = request.getParameter("matricula");
            if (matricula == null)
                throw new ExcepcionPresentacion("La matricula no puede ser vacia.");
            
            DTVehiculo vehiculo = FabricaLogica.getLogicaVehiculo().BuscarVehiculo(matricula);
            List<DTCliente> clientes = FabricaLogica.getLogicaCliente().listar();
            request.setAttribute("vehiculo", vehiculo);
            request.getSession().setAttribute("clientes", clientes);
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/alquilar.jsp").forward(request,response);
        } catch (ExcepcionPersonalizada ex) {
            request.setAttribute("mensaje", ex.getMessage());
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/listar.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("mensaje", "No se pudo alquilar el vehículo.");
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/listar.jsp").forward(request, response);
        }
    }

    private void trasladar_post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String matricula = request.getParameter("matricula");
            if (matricula == null)
                throw new ExcepcionPresentacion("La matricula no puede ser vacia.");
            
            int codigoSucursal = Integer.parseInt(request.getParameter("sucursal"));
            FabricaLogica.getLogicaVehiculo().TrasladarVehiculo(matricula, codigoSucursal);
            request.getSession().setAttribute("mensaje", "Traslado realizado con exito.");
            response.sendRedirect("/BiosRentACar/vehiculos");
        } catch (ExcepcionPersonalizada ex) {
            request.setAttribute("mensaje", ex.getMessage());
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/trasladar.jsp").forward(request,response);
        } catch (Exception ex) {
            request.setAttribute("mensaje", "No se pudo trasladar el vehiculo.");
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/trasladar.jsp").forward(request,response);
        }
    }
    
    private void alquilar_post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String ciCliente = request.getParameter("ciCliente");
            if (ciCliente == null)
                throw new ExcepcionPresentacion("La cedula no puede ser vacia.");
            
            DTAlquiler alquiler = FabricaLogica.getLogicaVehiculo().ObtenerAlquiler(ciCliente);
            if (alquiler != null)
                throw new ExcepcionLogica("El cliente ya tiene un alquiler activo.");
            
            String matricula = request.getParameter("matricula");
            if (matricula == null)
                throw new ExcepcionPresentacion("La matricula no puede ser vacia.");
            
            DTVehiculo vehiculo = FabricaLogica.getLogicaVehiculo().BuscarVehiculo(matricula);
            int codigoSucursal = Integer.parseInt(request.getParameter("codigoSucursal"));
            
            int cantidadDeDias = Integer.parseInt(request.getParameter("cantidadDeDias"));
            float precioAlquiler = (float) (vehiculo.getPrecioPorDia() * cantidadDeDias);
            float precioSeguro = 0;
            
            if (request.getParameter("contrataSeguro") != null)
                precioSeguro = FabricaLogica.getLogicaVehiculo().obtenerTarifa("seguro");
            
            float precioDeposito = 0;
            int cantidadDeAlquileres = FabricaLogica.getLogicaCliente().CantidadDeAlquileres(ciCliente);
            if (cantidadDeAlquileres < 3)
                precioDeposito = FabricaLogica.getLogicaVehiculo().obtenerTarifa("deposito");
            
            float precioTotal = precioAlquiler + precioSeguro + precioDeposito;
            boolean puedeAlquilar = true;
            request.setAttribute("vehiculo", vehiculo);
            request.setAttribute("ciCliente", ciCliente);
            request.setAttribute("codigoSucursal", codigoSucursal);
            request.setAttribute("cantidadDeDias", cantidadDeDias);
            request.setAttribute("precioAlquiler", precioAlquiler);
            request.setAttribute("precioSeguro", precioSeguro);
            request.setAttribute("precioDeposito", precioDeposito);
            request.setAttribute("precioTotal", precioTotal);
            request.setAttribute("puedeAlquilar", puedeAlquilar);
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/confirmarAlquiler.jsp").forward(request, response);
        } catch (ExcepcionPersonalizada ex) {
            request.setAttribute("mensaje", ex.getMessage());
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/alquilar.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("mensaje", "No se pudo confirmar el alquiler.");
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/alquilar.jsp").forward(request, response);
        }
    }
    
    private void confirmarAlquiler_post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String matricula = request.getParameter("matricula");
            if (matricula == null)
                throw new ExcepcionPresentacion("La matricula no puede ser vacia.");
            
            int codigoSucursal = Integer.parseInt(request.getParameter("codigoSucursal"));
            
            String ciCliente = request.getParameter("ciCliente");
            if (ciCliente == null)
                throw new ExcepcionPresentacion("La cedula no puede ser vacia.");
            
            int cantidadDeDias = Integer.parseInt(request.getParameter("cantidadDeDias"));
            float precioSeguro = Float.parseFloat(request.getParameter("precioSeguro"));
            float precioDeposito = Float.parseFloat(request.getParameter("precioDeposito"));
            FabricaLogica.getLogicaVehiculo().AlquilarVehiculo(ciCliente, matricula, codigoSucursal, cantidadDeDias, precioSeguro, precioDeposito);
            request.getSession().setAttribute("mensaje", "Alquiler realizado con exito.");
            response.sendRedirect("/BiosRentACar/vehiculos");
        } catch (ExcepcionPersonalizada ex) {
            request.setAttribute("mensaje", ex.getMessage());
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/confirmarAlquiler.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("mensaje", "No se pudo alquilar el vehiculo.");
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/confirmarAlquiler.jsp").forward(request, response);
        }
    }

    private void devolver_get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/vistas/vehiculos/devolver.jsp").forward(request, response);
    }
    
    private void devolucion_get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String ciCliente;
       
            ciCliente = request.getParameter("CiCliente");
            if (ciCliente == null) { 
                throw new ExcepcionPresentacion("¡ERROR! La cedula no puede ser vacía.");
            }
            
            DTAlquiler alquiler = FabricaLogica.getLogicaVehiculo().ObtenerAlquiler(ciCliente);
            if (alquiler == null)
                throw new ExcepcionPresentacion("No se encontro un alquiler activo");
            
            Calendar c = Calendar.getInstance();
            c.setTime(alquiler.getFecha());
            c.add(Calendar.DATE, alquiler.getCantidadDeDias() - 1);
            Date fechaDevolucion = c.getTime();
            Date fechaDeHoy = new Date();
            
            long diff = fechaDeHoy.getTime() - fechaDevolucion.getTime();
            long diffDias = diff / (24 * 60 * 60 * 1000);
            
            double importeAtraso = 0;
            if (diffDias > 0) {
                importeAtraso = round(diffDias * alquiler.getVehiculo().getPrecioPorDia() * 1.1);
            }
            
            request.setAttribute("diasDeAtraso", diffDias);
            request.setAttribute("importeAtraso", importeAtraso);
            request.setAttribute("alquiler", alquiler);
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/devolucion.jsp").forward(request, response);
        } catch (ExcepcionPersonalizada ex) {
            request.setAttribute("mensaje", ex.getMessage());
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/devolver.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("mensaje", "No se pudo obtener los datos del alquiler.");
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/devolver.jsp").forward(request, response);
        }
    }

    private void devolucion_post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        try {
            
        int idAlquiler;
        try {
            idAlquiler = Integer.parseInt(request.getParameter("IdAlquiler"));
            
        } catch (Exception ex) {
            throw new ExcepcionPresentacion("El id del alquiler no es válido.");
        }
        
        int codigoSucursal;
        codigoSucursal = ((DTEmpleado)request.getSession().getAttribute("empleadoLogueado")).getSucursal().getCodigo();
        
        FabricaLogica.getLogicaVehiculo().DevolverVehiculo(idAlquiler, codigoSucursal, new Date());
        request.getSession().setAttribute("mensaje", "Devolucion realizada con exito.");
        response.sendRedirect("/BiosRentACar/vehiculos");
        } catch (ExcepcionPersonalizada ex) {
            request.setAttribute("mensaje", ex.getMessage());
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/devolver.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("mensaje", "¡ERROR! Se produjo un error al devolver el vehiculo.");
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/devolver.jsp").forward(request, response);
        }
    }

    private void agregar_get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/agregar.jsp").forward(request, response);
            
        }
        catch (Exception ex) {
            request.setAttribute("mensaje", "No se puede obtener la vista de agregar un vehículo.");
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos").forward(request, response);
        }
    }

    private void agregar_post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            DTSucursal sucursal = ((DTEmpleado)(request.getSession().getAttribute("empleadoLogueado"))).getSucursal();
            
            String matricula = request.getParameter("matricula");
            if (matricula == "")
                throw new ExcepcionPresentacion("La matricula no puede estar vacía.");
            
            String tipo = request.getParameter("tipo");
            if (tipo == "")
                throw new ExcepcionPresentacion("El tipo no puede estar vacío.");
            
            String descripcion = request.getParameter("descripcion");
            if (descripcion == "")
                throw new ExcepcionPresentacion("La descripcion no puede estar vacía.");
                   
            if (request.getParameter("precioPorDia") == "")
                throw new ExcepcionPresentacion("El precio por día no puede estar vacío.");
            
            double precioPorDia = 0;          
            try {
                precioPorDia = Double.parseDouble(request.getParameter("precioPorDia"));
            } catch (Exception e) {
                throw new ExcepcionPresentacion("El precio por día solo permite valores numéricos.");
            }
            
            BufferedImage imagen = ImageIO.read(request.getPart("imagen").getInputStream());
            if (imagen != null) {
                ServletContext contextoAplicacion = getServletContext();
                String rutaImagenes = contextoAplicacion.getRealPath("/Imagenes/");
                File archivo = new File(rutaImagenes + matricula + ".png");
                archivo.createNewFile();
                ImageIO.write(imagen, "png", archivo);
            }
            else {
                throw new ExcepcionPresentacion("Debe ingresar una foto del vehículo.");
            }
            
            FabricaLogica.getLogicaVehiculo().AltaVehiculo(new DTVehiculo(matricula, tipo, descripcion, precioPorDia, sucursal));
           
            
            
            request.getSession().setAttribute("mensaje", "El vehículo con matricula: "+ matricula + " ha sido agregado con exito");
            response.sendRedirect("vehiculos");
        }
    catch (ExcepcionPersonalizada e){
        request.setAttribute("mensaje", e.getMessage());
        request.getRequestDispatcher("WEB-INF/vistas/vehiculos/agregar.jsp").forward(request, response);
    }
    catch (Exception e) {
            request.setAttribute("mensaje", "Error - No se pudo agregar el vehículo.");
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/agregar.jsp").forward(request, response);
        }
    }
    
    private void modificar_get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            DTVehiculo vehiculo = FabricaLogica.getLogicaVehiculo().BuscarVehiculo(request.getParameter("matricula"));
            request.setAttribute("vehiculo", vehiculo);
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/modificar.jsp").forward(request, response);
        }catch (ExcepcionPersonalizada e){
            request.setAttribute("mensaje", e.getMessage());
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/modificar.jsp").forward(request, response);
        }    
         catch (Exception ex) {
            request.setAttribute("mensaje", "No se pudo modificar el vehículo.");
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos").forward(request, response);
        }
    }
    
    private void modificar_post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         try {
            DTSucursal sucursal = ((DTEmpleado)(request.getSession().getAttribute("empleadoLogueado"))).getSucursal();
            
            String matricula = request.getParameter("matricula");
            if (matricula == "")
                throw new ExcepcionPresentacion("La matricula no puede estar vacía.");
            
            String tipo = request.getParameter("tipo");
            if (tipo == "")
                throw new ExcepcionPresentacion("El tipo no puede estar vacío.");
            
            String descripcion = request.getParameter("descripcion");
            if (descripcion == "")
                throw new ExcepcionPresentacion("La descripcion no puede estar vacía.");
                   
            if (request.getParameter("precioPorDia") == "")
                throw new ExcepcionPresentacion("El precio por día no puede estar vacío.");
            
            double precioPorDia = 0;          
            try {
                precioPorDia = Double.parseDouble(request.getParameter("precioPorDia"));
            } catch (Exception e) {
                throw new ExcepcionPresentacion("El precio por día solo permite valores numéricos.");
            }
            
            BufferedImage imagen = ImageIO.read(request.getPart("imagen").getInputStream());
            if (imagen != null) {
                ServletContext contextoAplicacion = getServletContext();
                String rutaImagenes = contextoAplicacion.getRealPath("/Imagenes/");
                File archivo = new File(rutaImagenes + matricula + ".png");
                archivo.createNewFile();
                ImageIO.write(imagen, "png", archivo);
            }
            
            FabricaLogica.getLogicaVehiculo().ModificarVehiculo(new DTVehiculo(matricula, tipo, descripcion, precioPorDia, sucursal));
            
            request.getSession().setAttribute("mensaje", "El vehículo con matricula: "+ matricula + " ha sido modificado con éxito");
            response.sendRedirect("vehiculos");
        }
        catch (NumberFormatException e){
            request.setAttribute("mensaje", "No se pudo modificar el vehículo. " + e.getMessage());
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/modificar.jsp").forward(request, response);
        }
        catch (ExcepcionPersonalizada e){
            request.setAttribute("mensaje", e.getMessage());
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/modificar.jsp").forward(request, response);
        }
        catch (Exception e) {
            request.setAttribute("mensaje", "Error. " + e.getMessage());
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/modificar.jsp").forward(request, response);
        }
     }
     
    private void eliminar_get(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
         try {
            DTVehiculo vehiculo = FabricaLogica.getLogicaVehiculo().BuscarVehiculo(request.getParameter("matricula"));
            request.setAttribute("vehiculo", vehiculo);
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/Eliminar.jsp").forward(request, response);
        
         }catch (ExcepcionPersonalizada e){
            request.setAttribute("mensaje", e.getMessage());
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/Eliminar.jsp").forward(request, response);
        }
        catch (Exception ex) {
            request.setAttribute("mensaje", "No se ha encontrado el vehículo.");
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/Eliminar.jsp").forward(request, response);
        }
     }
     
    private void eliminar_post(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
         try {
            String matricula = "";
            matricula = request.getParameter("matricula");
            if(matricula == "")
                throw new ExcepcionPresentacion("La matricula no puede estar vacía!");
            FabricaLogica.getLogicaVehiculo().BorrarVehiculo(matricula);
            
            request.getSession().setAttribute("mensaje", "El vehículo con matricula: "+ matricula + " ha sido eliminado con éxito");
            response.sendRedirect("vehiculos");
        
        }catch (ExcepcionPersonalizada e){
            request.setAttribute("mensaje", e.getMessage());
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/Eliminar.jsp").forward(request, response);
        }
        catch (Exception ex) {
            request.setAttribute("mensaje", "No se ha encontrado el vehículo.");
            request.getRequestDispatcher("WEB-INF/vistas/vehiculos/Eliminar.jsp").forward(request, response);
        }
     }
     
     
}

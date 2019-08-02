/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biosrentacar.presentacion.servlets.controladores;

import biosrentacar.modelo.compartidos.beans.datatypes.DTCliente;
import biosrentacar.modelo.compartidos.beans.excepciones.*;
import biosrentacar.modelo.logica.FabricaLogica;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author tm-01
 */
public class ControladorCliente extends HttpServlet {

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null) accion = "index";
        
        switch (accion) {
                
            case "index":
                index_get(request,response);
                
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
        if (accion == null) accion = "index";
            
        switch (accion) {
            
            case "index":
                index_get(request,response);
                       
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
    
    private void index_get(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<DTCliente> clientes = FabricaLogica.getLogicaCliente().buscar(request.getParameter("buscar"));
            
            request.getSession().setAttribute("clientes", clientes);
            request.setAttribute("mensaje", "Cantidad de clientes: " + clientes.size());
        
            String mensajeSesion = (String)request.getSession().getAttribute("mensaje");
        
            if (mensajeSesion != null) {
                String mensaje = (String)request.getAttribute("mensaje");

            if (mensaje == null) {
                request.setAttribute("mensaje", mensajeSesion);
            } else {
                request.setAttribute("mensaje", mensajeSesion + "<br /><br />" + mensaje);
            }
            
            request.getSession().removeAttribute("mensaje");
        }
        } catch (ExcepcionPersonalizada ex) {
            request.setAttribute("mensaje", "¡ERROR! " + ex.getMessage());
            request.getRequestDispatcher("WEB-INF/vistas/inicio/index.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("mensaje", "No se pudo listar los clientes.");
            request.getRequestDispatcher("WEB-INF/vistas/inicio/index.jsp").forward(request, response);
        }
        
        request.getRequestDispatcher("WEB-INF/vistas/clientes/index.jsp").forward(request, response);
    }
    
    private void agregar_get(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/vistas/clientes/agregar.jsp").forward(request, response);
    }
    
    private void agregar_post(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
        String cedula = "";
        
        try {
            
            Integer.parseInt(request.getParameter("Ci"));
            cedula = request.getParameter("Ci");
        } catch (NumberFormatException ex) {
            throw new ExcepcionPresentacion("La cédula no es válida.");
        }
                
        String nombre = request.getParameter("Nombre");;
        if (nombre == null)
            throw new ExcepcionPresentacion("El nombre no puede ser vacio.");

        String telefono = "";
        
        try {
            Integer.parseInt(request.getParameter("Telefono"));
            telefono = request.getParameter("Telefono");
        } catch (NumberFormatException ex) {
            throw new ExcepcionPresentacion("El telefono no es válido.");
        }
        
        DTCliente cliente = new DTCliente(cedula, nombre, telefono);
        
        
            FabricaLogica.getLogicaCliente().altaCliente(cliente);
            
            request.getSession().setAttribute("mensaje", "¡Cliente agregado con éxito!");
            
            response.sendRedirect("clientes");
        } catch (ExcepcionPersonalizada ex) {
            request.setAttribute("mensaje", "¡ERROR! " + ex.getMessage());
            
            request.getRequestDispatcher("WEB-INF/vistas/clientes/agregar.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("mensaje", "¡ERROR! Se produjo un error al agregar el cliente.");
            
            request.getRequestDispatcher("WEB-INF/vistas/clientes/agregar.jsp").forward(request, response);
        }
    }
    
    private void modificar_get(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String cedula = "";
        
            try {

                Integer.parseInt(request.getParameter("cedula"));
                cedula = request.getParameter("cedula");
            } catch (NumberFormatException ex) {
                throw new ExcepcionPresentacion("La cédula no es válida.");
            }

        
            DTCliente cliente = FabricaLogica.getLogicaCliente().obtener(cedula);
            
            if (cliente == null) {
                throw new ExcepcionPresentacion(" No se encontró ningún cliente con la cédula " + cedula + ".");
            }
            request.setAttribute("cliente", cliente);
            request.getRequestDispatcher("WEB-INF/vistas/clientes/modificar.jsp").forward(request, response);

        } catch (ExcepcionPersonalizada ex) {
            request.setAttribute("mensaje", "¡ERROR! " + ex.getMessage());
            request.getRequestDispatcher("WEB-INF/vistas/clientes/index.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("mensaje", "¡ERROR! Se produjo un error al buscar el cliente.");
            request.getRequestDispatcher("WEB-INF/vistas/clientes/index.jsp").forward(request, response);
        }
        
        
    }
    
    private void modificar_post(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
        String cedula = "";
        
        try {
            Integer.parseInt(request.getParameter("Ci"));
            cedula = request.getParameter("Ci");
        } catch (NumberFormatException ex) {
            throw new ExcepcionPresentacion("La cédula no es válida.");
        }
        
        String nombre = request.getParameter("Nombre");;
        if (nombre == null) {
            throw new ExcepcionPresentacion("El nombre no puede ser vacio.");
        }

        String telefono = "";
        
        try {
            Integer.parseInt(request.getParameter("Telefono"));
            telefono = request.getParameter("Telefono");
        } catch (NumberFormatException ex) {
            throw new ExcepcionPresentacion("El telefono no es válido.");
        }
        
        DTCliente cliente = new DTCliente(cedula, nombre, telefono);
        
        
            FabricaLogica.getLogicaCliente().modificarCliente(cliente); 
            
            request.getSession().setAttribute("mensaje", "¡Cliente modificado con éxito!");
            
            response.sendRedirect("clientes");
        } catch (ExcepcionPersonalizada ex) {
            request.setAttribute("mensaje", "¡ERROR! " + ex.getMessage());
            request.getRequestDispatcher("WEB-INF/vistas/clientes/modificar.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("mensaje", "¡ERROR! Se produjo un error al modificar el cliente.");
            request.getRequestDispatcher("WEB-INF/vistas/clientes/modificar.jsp").forward(request, response);
        }
    }

    private void eliminar_get(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String cedula = "";

            try {
                Integer.parseInt(request.getParameter("cedula"));
                cedula = request.getParameter("cedula");
            } catch (NumberFormatException ex) {
                throw new ExcepcionPresentacion("La cédula no es válida.");
            }


            DTCliente cliente = FabricaLogica.getLogicaCliente().obtener(cedula);
            if (cliente == null) {
                throw new ExcepcionPresentacion("No se encontró ningún cliente con la cédula " + cedula + ".");
            }
            request.setAttribute("cliente", cliente);
            request.getRequestDispatcher("WEB-INF/vistas/clientes/eliminar.jsp").forward(request, response);
        } catch (ExcepcionPersonalizada ex) {
            request.setAttribute("mensaje", "¡ERROR! " + ex.getMessage());
            request.getRequestDispatcher("WEB-INF/vistas/clientes/index.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("mensaje", "¡ERROR! Se produjo un error al buscar el cliente.");
            request.getRequestDispatcher("WEB-INF/vistas/clientes/index.jsp").forward(request, response);
        }
    }
    
    private void eliminar_post(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String cedula = "";

            try {
                Integer.parseInt(request.getParameter("cedula"));
                cedula = request.getParameter("cedula");
            } catch (NumberFormatException ex) {
                throw new ExcepcionPresentacion("La cédula no es válida.");
            }


            FabricaLogica.getLogicaCliente().eliminarCliente(cedula);
            
            request.getSession().setAttribute("mensaje", "¡Cliente eliminado con éxito!");
            
            response.sendRedirect("clientes");
        } catch (ExcepcionPersonalizada ex) {
            request.setAttribute("mensaje", "¡ERROR! " + ex.getMessage());
            
            request.getRequestDispatcher("WEB-INF/vistas/clientes/eliminar.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("mensaje", "¡ERROR! Se produjo un error al eliminar el cliente.");
            
            request.getRequestDispatcher("WEB-INF/vistas/clientes/eliminar.jsp").forward(request, response);
        }
    }
}

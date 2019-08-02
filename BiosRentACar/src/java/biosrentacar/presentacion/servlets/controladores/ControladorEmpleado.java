/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biosrentacar.presentacion.servlets.controladores;

import biosrentacar.modelo.compartidos.beans.datatypes.DTEmpleado;
import biosrentacar.modelo.compartidos.beans.excepciones.ExcepcionPersonalizada;
import biosrentacar.modelo.compartidos.beans.excepciones.ExcepcionPresentacion;
import biosrentacar.modelo.logica.FabricaLogica;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Raul
 */
public class ControladorEmpleado extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null) accion = "logIn";
        
        switch (accion) {
            case "logIn":
                logIn_get(request, response);
                
                break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        
        switch (accion) {
            case "logIn":
                logIn_post(request, response);
                
                break;
            case "logOut":
                logOut_post(request, response);
                
                break;
        }
    }
    
    private void logIn_get(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("empleadoLogueado") == null) {
            request.getRequestDispatcher("WEB-INF/vistas/empleados/login.jsp").forward(request, response);
        } else {
            response.sendRedirect("inicio");
        }
    }
    
    private void logIn_post(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String nombreUsuario = request.getParameter("nombreUsuario");
            String claveAcceso = request.getParameter("claveAcceso");
            
            DTEmpleado empleado = FabricaLogica.getLogicaEmpleado().Buscar(nombreUsuario, claveAcceso);
            
            if (empleado == null) {
                throw new ExcepcionPresentacion("Las credenciales no son válidas.");
            }
            request.getSession().setAttribute("empleadoLogueado", empleado);
            response.sendRedirect("inicio");
        } catch (ExcepcionPersonalizada ex) {
            request.setAttribute("mensaje", ex.getMessage());
            request.getRequestDispatcher("WEB-INF/vistas/empleados/login.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("mensaje", "No se pudo loguear el empleado.");
            request.getRequestDispatcher("WEB-INF/vistas/empleados/login.jsp").forward(request, response);
        }
    }
    
    private void logOut_post(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().removeAttribute("empleadoLogueado");
        response.sendRedirect("empleados?accion=logIn");
    }
    
}

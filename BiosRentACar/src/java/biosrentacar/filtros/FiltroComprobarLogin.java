/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biosrentacar.filtros;

import biosrentacar.modelo.compartidos.beans.datatypes.DTEmpleado;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sistemas
 */
public class FiltroComprobarLogin implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig)
            throws ServletException {
        
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpSession sesion = ((HttpServletRequest)request).getSession();
        
        DTEmpleado empleado = (DTEmpleado)sesion.getAttribute("empleadoLogueado");
        
        if (empleado != null) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse)response).sendRedirect("empleados?accion=logIn");
        }
    }
    
    @Override
    public void destroy() {
        
    }
    
}
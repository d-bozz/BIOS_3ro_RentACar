<%-- 
    Document   : index
    Created on : 23/06/2019, 02:05:06 PM
    Author     : d-bozz
--%>

<%@page import="biosrentacar.modelo.compartidos.beans.datatypes.DTCliente"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/head.jspf"%>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/cabezal.jspf"%>
        <h1>Clientes</h1>
        <h2>Listado de clientes</h2>
        
        <a href="clientes?accion=agregar">[ Agregar ]</a>
        
        <table class="listado">
            <%if (((List<DTCliente>)request.getSession().getAttribute("clientes")).size() > 0){ %>
            <tr>
                <th>CEDULA</th><th>NOMBRE COMPLETO</th><th>TELEFONO</th><th colspan="2"></th>
            </tr>
            
            <% for (DTCliente c : (List<DTCliente>)request.getSession().getAttribute("clientes")) { %>
                <tr>
                    <td class="texto-izquierda"><%= c.getCi()%></td>
                    <td class="texto-centrado"><%= c.getNombreCompleto() %></td>
                    <td class="texto-derecha"><%= c.getTelefono() %></td>
                    
                    <td><a href="clientes?accion=modificar&cedula=<%= c.getCi() %>">[ Modificar ]</a></td>
                    <td><a href="clientes?accion=eliminar&cedula=<%= c.getCi() %>">[ Eliminar ]</a></td>
                    
                </tr>
            <% }
                }else {%>
                <br>
                <br>
                <a>Actualmente no hay ningún cliente registrado.</a>
            <%          } %>
        </table>
        
        <%@include file="/WEB-INF/jspf/mostrar-mensaje.jspf"%>
    </body>
</html>

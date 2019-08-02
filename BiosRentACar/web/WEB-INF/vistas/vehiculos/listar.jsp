<%-- 
    Document   : listar
    Created on : 22-jun-2019, 13:20:03
    Author     : sistemas
--%>

<%@page import="java.util.List"%>
<%@page import="biosrentacar.modelo.compartidos.beans.datatypes.DTVehiculo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/head.jspf"%>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/cabezal.jspf"%>
        <h1>Vehiculos</h1>
        <h2>Disponibles de la sucursal.</h2>
        
        <table class="listado">
            <tr>
                <a href="vehiculos?accion=agregar">[ Agregar ]</a>
            </tr>
            
            <tr>
                <%if (((List<DTVehiculo>)request.getSession().getAttribute("vehiculos")).size() > 0){ %>
                <th>MATRICULA</th><th>DESCRIPCION</th><th>TIPO</th><th>PRECIO X DIA</th><th colspan="4"></th>
            </tr>
            
            <% for (DTVehiculo v : (List<DTVehiculo>)request.getSession().getAttribute("vehiculos")) { %>
                <tr>
                    <td class="texto-izquierda"><%= v.getMatricula() %></td>
                    <td class="texto-centrado"><%= v.getDescripcion() %></td>
                    <td class="texto-centrado"><%= v.getTipo() %></td>
                    <td class="texto-derecha"><%= v.getPrecioPorDia() %></td>
                    <%-- TODO: Agregar mas <td> para implementar Eliminar, Modificar y Alquilar --%>
                    <td><a href="vehiculos?accion=modificar&matricula=<%= v.getMatricula() %>">[ Modificar ]</a></td>
                    <td><a href="vehiculos?accion=eliminar&matricula=<%= v.getMatricula() %>">[ Eliminar ]</a></td>
                    <td><a href="vehiculos?accion=trasladar&matricula=<%= v.getMatricula() %>">[ Trasladar ]</a></td>
                    <td><a href="vehiculos?accion=alquilar&matricula=<%= v.getMatricula() %>">[ Alquilar ]</a></td>
                </tr>
            <%  }
               }else {%>
                    <br>
                    <br>
                    <a>Actualmente no hay vehículos disponibles en esta sucursal.</a>
                <%  }%>
        </table>
        
        <%@include file="/WEB-INF/jspf/mostrar-mensaje.jspf"%>
    </body>
</html>

<%-- 
    Document   : trasladar
    Created on : 22-jun-2019, 17:23:28
    Author     : sistemas
--%>
<%@page import="java.util.List"%>
<%@page import="biosrentacar.modelo.compartidos.beans.datatypes.DTSucursal"%>
<%@page import="biosrentacar.modelo.compartidos.beans.datatypes.DTVehiculo"%>
<%!
    List<DTSucursal> sucursales;
%>
<% 
    sucursales = (List<DTSucursal>)request.getSession().getAttribute("sucursales");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/head.jspf"%>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/cabezal.jspf"%>
        <h2>Realizar traslado de ${!empty vehiculo ? vehiculo.getMatricula() : param.matricula }</h2>
        
        <form action="vehiculos" method="post">
            <div>
                <label for="sucursal">Seleccione la sucursal de Destino: </label>
                <input type="hidden" name="matricula" value="${!empty vehiculo ? vehiculo.getMatricula() : param.matricula }" />
                <select name="sucursal">
                    <% for(DTSucursal sucursal : sucursales) { %>
                        <option value="<%= sucursal.getCodigo() %>"> <%=sucursal.getNombre() %></option>
                    <% } %>
                </select>
            </div>
            <div>
                <input type="hidden" name="accion" value="trasladar" />
                <input type="submit" name="botonTrasladar" value="Trasladar Vehiculo" />
            </div>
        </form>
        
        <%@include file="/WEB-INF/jspf/mostrar-mensaje.jspf"%>
        <br>
        <p><a href="vehiculos">Volver...</a></p>
    </body>
</html>

<%-- 
    Document   : devolucion
    Created on : 06-jul-2019, 16:11:50
    Author     : sistemas
--%>

<%@page import="biosrentacar.modelo.compartidos.beans.datatypes.*"%>
<%!
    DTAlquiler alquiler;
%>
<% 
    alquiler = (DTAlquiler)request.getAttribute("alquiler");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/head.jspf"%>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/cabezal.jspf"%>
        <h1>Datos del Alquiler <%= alquiler.getId() %></h1>
        <form action="vehiculos" method="post">
            
            <input type="hidden" name="IdAlquiler" value="<%= alquiler.getId() %>"/>
            <input type="hidden" name="CodigoSucursal" value="<%= alquiler.getSucursal().getCodigo() %>"/>
            <div>           
                <label>Cliente: <%= alquiler.getCliente().getNombreCompleto() %></label>
            </div>
            <div>
                <label>Vehiculo: <%= alquiler.getVehiculo().getMatricula() %></label>
            </div>
            <div>
                <label>Fecha de Alquiler: <%= alquiler.getFecha() %></label>
            </div>
            <div>    
                <label>Dias alquilados: <%= alquiler.getCantidadDeDias() %></label>
            </div>
            <div>    
                <label>Importe del Seguro: <%= alquiler.getImporteSeguro() %></label>
            </div>
            <div>    
                <label>Importe del Depósito: <%= alquiler.getImporteDeposito() %></label>
            </div>
            <div>    
                <label>Importe Total del Alquiler: <%= alquiler.getImporteTotal() %></label>
            </div>
            <div>
                <% if((long)request.getAttribute("diasDeAtraso") > 0) {%>
                <label>Multa por atraso: <%= (double)request.getAttribute("importeAtraso") %> </label>
                <%} else {%>
                    <label>No hay recargos adicionales!</label>
                <%}%>
            </div>
            <div>
                <input type="hidden" name="accion" value="devolucion" />
                <input type="submit" name="botonDevolverVehiculo" value="Devolver Vehiculo" />
            </div>
        </form>
        <%@include file="/WEB-INF/jspf/mostrar-mensaje.jspf"%>
        <br>
        <p><a href="vehiculos">Volver...</a></p>
    </body>
</html>

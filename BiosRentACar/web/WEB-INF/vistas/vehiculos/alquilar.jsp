<%-- 
    Document   : alquilar
    Created on : 29-jun-2019, 19:54:57
    Author     : sistemas
--%>

<%@page import="java.util.List"%>
<%@page import="biosrentacar.modelo.compartidos.beans.datatypes.*"%>
<%!
    List<DTCliente> clientes;
%>
<% 
    clientes = (List<DTCliente>)request.getSession().getAttribute("clientes");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/head.jspf"%>
        

    </head>
    <body>
        <%@include file="/WEB-INF/jspf/cabezal.jspf"%>
        <h1>Alquilar ${!empty vehiculo ? vehiculo.getMatricula() : param.matricula }</h1>
        
        <form action="vehiculos" method="post">
            <div>
                <input type="hidden" name="matricula" value="${!empty vehiculo ? vehiculo.getMatricula() : param.matricula }" />
                <input type="hidden" name="codigoSucursal" value="${!empty empleadoLogueado ? empleadoLogueado.getSucursal().getCodigo() : param.codigoSucursal}" />
                
                <label for="ciCliente">Seleccione un cliente: </label>
                <select name="ciCliente">
                    <% for(DTCliente cliente : clientes) { %>
                        <option value="<%= cliente.getCi() %>"> <%=cliente.getNombreCompleto() %></option>
                    <% } %>
                </select>
            </div>
            <div>
                <label for="cantidadDeDias">Dias a alquilar:</label>
                <input id="cantidadDeDias" type="number" name="cantidadDeDias" value=1/>
            </div>
            <div>
                <label>
                    <input id="contratarSeguro" type="checkbox" name="contrataSeguro" checked="true" />Contrata Seguro
                </label>
            </div>
            <div>
                <input type="hidden" name="accion" value="alquilar" />
                <input type="submit" name="botonSiguiente" value="Siguiente" />
            </div>
        </form>
        
        <%@include file="/WEB-INF/jspf/mostrar-mensaje.jspf"%>
        <br>
        <p><a href="vehiculos">Volver...</a></p>
    </body>
</html>

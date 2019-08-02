<%-- 
    Document   : confirmarAlquiler
    Created on : 29-jun-2019, 21:20:08
    Author     : sistemas
--%>

<%@page import="java.util.List"%>
<%@page import="biosrentacar.modelo.compartidos.beans.datatypes.*"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/head.jspf"%>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/cabezal.jspf"%>
        <h1>Confirmar alquiler del Vehiculo ${!empty vehiculo ? vehiculo.getMatricula() : param.matricula } </h1>
        
        <% if((request.getAttribute("puedeAlquilar") != null && (Boolean)request.getAttribute("puedeAlquilar")) || "true".equals(request.getParameter("puedeAlquilar"))) {%>
        <form action="vehiculos" method="post">
            <div>
                <input type="hidden" name='puedeAlquilar' value='${!empty puedeAlquilar ? puedeAlquilar : param.puedeAlquilar }'>
                <input type="hidden" name='matricula' value='${!empty vehiculo ? vehiculo.getMatricula() : param.matricula }'>
                <input type="hidden" name='ciCliente' value='${!empty ciCliente ? ciCliente : param.ciCliente}'>
                <input type="hidden" name='codigoSucursal' value='${!empty codigoSucursal ? codigoSucursal : param.codigoSucursal}'>
                <input type="hidden" name='cantidadDeDias' value='${!empty cantidadDeDias ? cantidadDeDias : param.CantidadDeDias}'>
                <input type="hidden" name='precioSeguro' value='${!empty precioSeguro ? precioSeguro : param.precioSeguro}'>
                <input type="hidden" name='precioDeposito' value='${!empty precioDeposito ? precioDeposito : param.precioDeposito}'>
            </div>
            <div>
                <label>Cedula del Cliente: ${!empty ciCliente ? ciCliente : param.ciCliente}</label>
            </div>
            <div>
                <label>Matricula del Vehiculo: ${!empty vehiculo ? vehiculo.getMatricula() : param.matricula }</label>
            </div>
            <div>
                <label>Cantidad de dias de Alquiler: ${!empty cantidadDeDias ? cantidadDeDias : param.CantidadDeDias}</label>
            </div>
            <div>
                <label>Precio Alquiler: ${!empty precioAlquiler ? precioAlquiler : param.precioAlquiler}</label>
                <input type="hidden" name='precioAlquiler' value="${!empty precioAlquiler ? precioAlquiler : param.precioAlquiler}">
            </div>
            <div>    
                <label>Precio Seguro: ${!empty precioSeguro ? precioSeguro : param.precioSeguro}</label>
                <input type="hidden" name='precioSeguro' value="${!empty precioSeguro ? precioSeguro : param.precioSeguro}">
            </div>
            <div>
                <label>Precio Deposito: ${!empty precioDeposito ? precioDeposito : param.precioDeposito}</label>
                <input type="hidden" name='precioDeposito' value="${!empty precioDeposito ? precioDeposito : param.precioDeposito}">
            </div>
            <div>    
                <label>Precio Total: ${!empty precioTotal ? precioTotal : param.precioTotal}</label>
                <input type="hidden" name='precioTotal' value="${!empty precioTotal ? precioTotal : param.precioTotal}">
            </div>
            <div>
                <input type="hidden" name="accion" value="confirmarAlquiler" />
                <input type="submit" name="botonAlquilar" value="Alquilar Vehiculo" />
            </div>
        </form>
        <% } %>
        <%@include file="/WEB-INF/jspf/mostrar-mensaje.jspf"%>
        <br>
        <p><a href="vehiculos">Volver...</a></p>
    </body>
</html>

<%-- 
    Document   : devolver
    Created on : 06-jul-2019, 16:11:41
    Author     : sistemas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/head.jspf"%>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/cabezal.jspf"%>

        <h1>Devolver un vehiculo</h1>
        <form action="vehiculos" method="get">
            <label for="CiCliente">Ingrese una Ci:</label>
            <input type="number" name="CiCliente" min="0"/>
            <input type="hidden" name="accion" value="devolucion" />
            <input type="submit" name="botonVerAlquiler" value="Ver Alquiler" />
        </form>
        <%@include file="/WEB-INF/jspf/mostrar-mensaje.jspf"%>
        <br>
        <p><a href="vehiculos">Volver...</a></p>
    </body>
</html>

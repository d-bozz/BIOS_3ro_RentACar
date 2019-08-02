<%-- 
    Document   : Eliminar
    Created on : Jul 7, 2019, 11:16:03 PM
    Author     : Chadi
--%>

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
        <h2>Eliminar</h2>
        
        <%DTVehiculo v = (DTVehiculo)request.getAttribute("vehiculo");%>
        
        <%if (v != null){ %>
            <p>¿Confirma que desea eliminar el vehículo con matricula ${vehiculo.getMatricula()} ?</p>
            
            <form method="post" accept-charset="ISO-8859-1">
                <div>
                <p>
                    <input type="hidden" name="matricula" value="${ vehiculo.getMatricula()}" /> 
                    <input type="submit" name="accion" value="Eliminar" />
                </p>
                </div>
            </form>
        <br>
       
        <%}%>
          <%@include file="/WEB-INF/jspf/mostrar-mensaje.jspf"%>          
        <br>
        <p><a href="vehiculos">Volver...</a></p>
    </body>
</html>

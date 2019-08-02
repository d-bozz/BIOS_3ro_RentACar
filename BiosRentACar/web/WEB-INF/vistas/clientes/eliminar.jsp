
<%-- 
    Document   : eliminar
    Created on : 23/06/2019, 01:54:48 PM
    Author     : d-bozz
--%>

<%@page import="biosrentacar.modelo.compartidos.beans.datatypes.DTCliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
    <head>
         <%@include file="/WEB-INF/jspf/head.jspf"%>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/cabezal.jspf"%>
        <h1>Clientes</h1>
        <h2>Eliminar</h2>
        
        <%DTCliente c = (DTCliente)request.getAttribute("cliente");%>
        
        <%if (c != null){ %>
            <p>¿Confirma que desea eliminar el cliente con cédula ${ cliente.getCi()} (${cliente.nombreCompleto})?</p>
            
            <form method="post" accept-charset="ISO-8859-1">
                <div>
                <p>
                    <input type="hidden" name="cedula" value="${ cliente.getCi()}" /> 
                    <input type="submit" name="accion" value="Eliminar" />
                </p>
                </div>
            </form>
        
        
        <%}%>
          <%@include file="/WEB-INF/jspf/mostrar-mensaje.jspf"%>
            <p><a href="clientes">Volver...</a></p>
    </body>
</html>
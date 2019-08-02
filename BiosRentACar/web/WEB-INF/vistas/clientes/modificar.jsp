
<%-- 
    Document   : modificar
    Created on : 23/06/2019, 01:04:21 PM
    Author     : d-bozz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
          <%@include file="/WEB-INF/jspf/head.jspf"%>
    </head>
    <body>
        
          <%@include file="/WEB-INF/jspf/cabezal.jspf"%>
        
        <h1>Clientes</h1>
        <h2>Modificar</h2>
        
        <form action="clientes" method="post">
            <div>
                <label for="Ci">Cedula:</label>
                <input type="number" min="0" name="Ci" id="Ci" value="${!empty cliente ? cliente.ci : param.Ci}" readonly = "readonly"/>
            </div>
            <div>
                <label for="Nombre">Nombre completo:</label>
                <input type="text" name="Nombre" id="Nombre" value="${!empty cliente ? cliente.nombreCompleto : param.Nombre}"/>
            </div>
            <div>
                <label for="Telefono">Telefono:</label>
                <input type="number" min="0" name="Telefono" id="Telefono" value="${!empty cliente ? cliente.telefono : param.Telefono}"/>
            </div>
            <div>
                <input type="hidden" name="accion" value="modificar" />
                <input type="submit" name="botonModificar" value="Modificar" />
            </div>
        </form>
  
        
        <%@include file="/WEB-INF/jspf/mostrar-mensaje.jspf"%>
        <br>
        <p><a href="clientes">Volver...</a></p>
    </body>
</html>
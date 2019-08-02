<%-- 
    Document   : agregar
    Created on : 19/06/2019, 03:53:44 PM
    Author     : tm-01
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
        <h2>Agregar</h2>
        
        <form action="clientes" method="post">
            <div>
                <label for="Ci">Cedula:</label>
                <input type="number" min="0" name="Ci" id="Ci" value="${param.Ci}" />
            </div>
            <div>
                <label for="Nombre">Nombre completo:</label>
                <input type="text" name="Nombre" id="Nombre" value="${param.Nombre}"/>
            </div>
            <div>
                <label for="Telefono">Telefono:</label>
                <input type="text" min="0" name="Telefono" id="Telefono" value="${param.Telefono}"/>
            </div>
            <div>
                <input type="hidden" name="accion" value="agregar" />
                <input type="submit" name="botonAgregar" value="agregar" />
            </div>
        </form>
        
        <%@include file="/WEB-INF/jspf/mostrar-mensaje.jspf"%>
        
         <p><a href="clientes">Volver...</a></p>
        
        <script>
            document.getElementById('Ci').select();
        </script>
    </body>
</html>
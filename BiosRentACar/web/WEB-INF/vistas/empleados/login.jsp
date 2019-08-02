<%-- 
    Document   : login
    Created on : 18-jun-2019, 20:32:32
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
        
        <h1>Empleados</h1>
        <h2>Log In</h2>
        
        <form action="empleados" method="post">
            <div>
                <label for="nombreUsuario">Nombre de Usuario:</label>
                <input type="text" name="nombreUsuario" id="nombreUsuario" value="${param.nombreUsuario}" />
            </div>
            <div>
                <label for="claveAcceso">Clave de acceso:</label>
                <input type="password" name="claveAcceso" id="claveAcceso" />
            </div>
            <div>
                <input type="hidden" name="accion" value="logIn" />
                <input type="submit" name="botonLogIn" value="Log In" />
            </div>
        </form>
        
        <%@include file="/WEB-INF/jspf/mostrar-mensaje.jspf"%>
        
        <script>
            document.getElementById('nombreUsuario').select();
        </script>
    </body>
</html>

<%-- 
    Document   : agregar
    Created on : Jul 5, 2019, 2:45:03 PM
    Author     : Chadi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/head.jspf"%>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/cabezal.jspf"%>
        
        <h1>Vehiculos</h1>
        <h2>Agregar</h2>
        
        <form action="vehiculos" method="post" enctype="multipart/form-data">
            <div>
                <label for="matricula">Matricula:</label> 
                <input type="text" name="matricula" id="matricula" value="${param.matricula}" placeholder="AAA0000" maxlength="7" />
            </div>
            <div>
                <label for="tipo">Tipo:</label>
                <input type="text" name="tipo" id="tipo" value="${param.tipo}" placeholder="Pickup, sedan, compacto.." maxlength="50"/>
            </div>
            <div>
                <label for="descripcion">Descripcion: </label>
                <textarea name="descripcion" id="descripcion" placeholder="Breve descripcion.." max="50">${param.descripcion}</textarea>
            </div>
            <div>
                <label for="precioPorDia">Precio por dia: </label>
                <input type="text" name="precioPorDia" id="precioPorDia" value="${param.precioPorDia}" placeholder="00.00" maxlength="10"/>
            </div>
            <div>
                <label for="imagen">Imagen:</label>
                <input type="file" name="imagen" id="imagen" />
            </div>
            <div></div>
            <div>
                <input type="hidden" name="accion" value="agregar" />
                <input type="submit" name="botonAgregar" value="Agregar" />
            </div>
        </form>
        <br>
        <%@include file="/WEB-INF/jspf/mostrar-mensaje.jspf"%>
        <br>
        <p><a href="vehiculos">Volver...</a></p>
        
        
    </body>
</html>

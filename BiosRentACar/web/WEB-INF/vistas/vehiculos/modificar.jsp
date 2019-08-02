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
        <h2>Modificar</h2>
        
        <form action="vehiculos" method="post" enctype="multipart/form-data" >
            <div>
                <label for="matricula">Matricula:</label> 
                <input type="text" name="matricula" id="matricula" value="${!empty vehiculo ? vehiculo.getMatricula() : param.matricula }" placeholder="AAA0000" maxlength="7" readonly='readonly'/>
            </div>
            <div>
                <label for="tipo">Tipo:</label>
                <input type="text" name="tipo" id="tipo" value="${ !empty vehiculo ? vehiculo.getTipo() : param.tipo}" placeholder="Pickup, sedan, compacto.." maxlength="50"/>
            </div>
            <div>
                <label for="descripcion">Descripcion: </label>
                <textarea name="descripcion" id="descripcion" placeholder="Breve descripcion.." max="50">${!empty vehiculo ? vehiculo.getDescripcion() : param.descripcion}</textarea>
            </div>
            <div>
                <label for="precioPorDia">Precio por dia: </label>
                <input type="text" name="precioPorDia" id="precioPorDia" value="${ !empty vehiculo ? vehiculo.getPrecioPorDia() : param.precioPorDia}" placeholder="00.00" maxlength="10"/>
            </div>
            <div>
                <label for="imagen">Imagen:</label>
                <input type="file" name="imagen" id="imagen" /> <img src="Imagenes/${!empty vehiculo ? vehiculo.getMatricula() : param.matricula }.png" alt="${!empty vehiculo ? vehiculo.getMatricula() : param.matricula }" 
                     height="200" style="vertical-align: middle;"  />
            </div>
            <div>
                <input type="hidden" name="accion" value="modificar" />
                <input type="submit" name="botonAgregar" value="Modificar" />
            </div>
        </form>
        <%@include file="/WEB-INF/jspf/mostrar-mensaje.jspf"%>
        <br>
        <p><a href="vehiculos">Volver...</a></p>
        
    </body>
</html>

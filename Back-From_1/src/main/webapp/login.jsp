<%--
  Created by IntelliJ IDEA.
  User: Luis Nuñez
  Date: 16/08/2022
  Time: 11:58 p. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language= "java" %>
<html>
<head>
    <title>Title</title>
</head>

<body>

<div> Alerta</div>
 <div>
     <form action="ServletValidacion" method="post">
         <label for="correo">Correo</label>
         <input type="email" name="correo" id="correo" placeholder= "user@dominio.ext" required>
         <label for="clave">Clave</label>
         <input type="password" name="clave" id="clave" placeholder="User1111" required>

         <button type="submit">Iniciar Sesion </button>
         <button type="reset">Limpiar Formulario </button>




     </form>

 </div>



</body>
</html>

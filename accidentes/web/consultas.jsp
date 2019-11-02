<%--
  Created by IntelliJ IDEA.
  User: 67799924
  Date: 02/11/2019
  Time: 1:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Consultas</title>
</head>
<body>
<h1> Consultas </h1>

<form action="/app/consultas" method="post">
    consulta: <input type="text" name="param1" with="10"/>
    <input type="submit" value="busqueda"/>

</form>
<p> param1: ${param1}</p>
</body>
</html>

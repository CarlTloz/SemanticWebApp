<%--
  Created by IntelliJ IDEA.
  User: 67799924
  Date: 02/11/2019
  Time: 1:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html class="no-js" lang="">

<head>
    <meta charset="utf-8">
    <title>biciaccidentes</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="manifest" href="site.webmanifest">
    <link rel="apple-touch-icon" href="icon.png">

    <meta name="theme-color" content="#fafafa">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">


    <style>
        div {
            margin: 70px;
            border: 1px solid #af3f02;
        }
    </style>

</head>

<body>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>




<div class="alert alert-success" role="alert">
    <h3 class="alert-heading">
        Accidentes por calle:


    </h3>
    <form action="/app/consultas" method="post">
        consulta: <input type="text" name="param1" />
        <input type="submit" value="busqueda"/>
    </form>
    Resultado:
         ${param1}
    <hr>
</div>

<div class="alert alert-success" role="alert">
    <h4 class="alert-heading">REGISTROS DE LA CONSULTA</h4>
    <p>  DATOS DE LA CONSULTA DE NUESTRA ONTOLOGIA  </p>

    <hr>
</div>


</body>
</html>



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
            margin: 0px;
            border: 0px ;
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
<div class="p-3 mb-2 bg-success text-white " >
    BiciAccident
</div>
<div class="container-fluid">
    <div class="row">
        <div class="col-3">
            <div class="list-group justify-content-start">
                <a href="http://biciaccidentes.es:8080/app/resource/street" class="list-group-item list-group-item-action" style="height: 50px;width: 300px;">street</a>
                <a href="http://biciaccidentes.es:8080/app/resource/district" class="list-group-item list-group-item-action" style="height: 50px;width: 300px;">district</a>
                <a href="http://biciaccidentes.es:8080/app/resource/weather" class="list-group-item list-group-item-action" style="height: 50px;width: 300px;">weather</a>
                <a href="http://biciaccidentes.es:8080/app/resource/lesivity" class="list-group-item list-group-item-action" style="height: 50px;width: 300px;">lesivity</a>
                <a href="http://biciaccidentes.es:8080/app/resource/accident" class="list-group-item list-group-item-action" style="height: 50px;width: 300px;">accident type</a>
                <a href="http://biciaccidentes.es:8080/app/resource/date" class="list-group-item list-group-item-action" style="height: 50px;width: 300px;">date</a>
            </div>
        </div>
        <div class="col-lg">
            <h3 class="alert-heading">
                Accidentes por ${nombre}:
            </h3>
            <div>
                <form action="${uri}" method="post">
                    <textarea class="form-control" rows="1" id="comment" name="palabra-clave"></textarea>
                    <input type="submit" value="consultar"/>
                </form>
            </div>
            <div>
                ${contenido}
            </div>
        </div>
    </div>
    </table>
</div>
</body>
</html>


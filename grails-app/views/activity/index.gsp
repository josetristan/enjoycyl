<%@ page import="com.jtristan.enjoycyl.Activity" %>

<!DOCTYPE html>

<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Enjoy Cyl</title>

    <meta name="layout" content="main" />

</head>

<body>

<div id="wrapper">

    <g:render template="/layouts/menu"/>

    <div id="page-wrapper" class="gray-bg">
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-12">
                <h2>Cultura y gastronomía en Castilla y León</h2>
            </div>
            <div class="col-lg-8">
                <g:form name="form" action="listProposal" class="form-inline">
                    <div class="col-sm-3"><select class="form-control m-b" name="locality" required="">
                        <option value="">Provincia...</option>
                        <option value="AVILA">Ávila</option>
                        <option value="BURGOS">Burgos</option>
                        <option value="LEON">León</option>
                        <option value="PALENCIA">Palencia</option>
                        <option value="SORIA">Soria</option>
                        <option value="SEGOVIA">Segovia</option>
                        <option value="VALLADOLID">Valladolid</option>
                        <option value="ZAMORA">Zamora</option>
                    </select>
                    </div>

                    <div class="col-sm-4"><select class="form-control m-b" name="category">
                        <option value="">Categoría...</option>
                        <option value="BOOKS">Libros y Lectura</option>
                        <option value="CINEMA">Cine</option>
                        <option value="CONCERTS">Conciertos</option>
                        <option value="CONFERENCES">Conferencias y Cursos</option>
                        <option value="EXHIBITIONS">Exposiciones</option>
                        <option value="OTHERS">Otros</option>
                    </select>
                    </div>

                    <div class="col-sm-1">
                        <button class="btn btn-primary btn-sm" type="submit">Buscar</button>
                    </div>
                </g:form>
            </div>
            <div class="col-lg-4">

            </div>
        </div>
        <cyl:card proposals="${proposals}"/>



    </div>
</div>

<script>
    $( document ).ready(function() {

        removeOldLikes();
        markEventsAsLike();
        $(".like").click(function () {
            if ($(this).css("color") == 'rgb(103, 106, 108)') { //white
                setColor(this, "green");
                saveLike(this);
                postLike(this.dataset.eventid, "${request.contextPath}");
            } else {
                setColor(this, "inherit");
                //$( this ).css("color","inherit");
            }
        })

        //Initialice Graphics
        var sparklineCharts = function(){
            $("#sparkline5").sparkline([1, 4], {
                type: 'pie',
                height: '140',
                sliceColors: ['#1ab394', '#F5F5F5']
            });
        };

        var sparkResize;

        $(window).resize(function(e) {
            clearTimeout(sparkResize);
            sparkResize = setTimeout(sparklineCharts, 500);
        });

        sparklineCharts();

    });

</script>


</body>

</html>

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
                <h2>Valora el evento: ${title}</h2>
            </div>

            <div class="ibox-content">
                <g:form name="form" url="[resource:userOpinionInstance, action:'save']">

                    <g:hiddenField name="eventId" value="${activityId}"/>

                    <div class="form-group row">
                        <div class="col-sm-4">
                            <h3><label for="adults" class="control-label">Número de adultos</label></h3>
                        </div>
                        <div class="col-sm-8">
                            <input type="number" id="adults" name="adults" class="form-control" required="">
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-4">
                            <h3><label for="adults" class="control-label">Número de niños</label></h3>
                        </div>
                        <div class="col-sm-8">
                            <input type="number" id="children" name="children" class="form-control" required="">
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-4">
                            <h3><label for="placeRated" class="control-label">Lugar:</label></h3>
                        </div>
                        <div class="col-sm-8">
                            <input id="placeRated" name="placeRated" class="rating-loading" required="">
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-4">
                            <h3><label for="timeRated" class="control-label">Fecha y hora:</label></h3>
                        </div>
                        <div class="col-sm-8">
                            <%-- <input id="star-rating" name="timeRated" class="rating rating-loading" data-min="0" data-max="5" data-step="1" required=""> --%>
                            <input id="timeRated" name="timeRated" class="rating-loading"required="">
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-4">
                            <h3><label for="eventRated" class="control-label">Evento:</label></h3>
                        </div>
                        <div class="col-sm-8">
                            <input id="eventRated" name="eventRated" class="rating-loading" required="">
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-12">
                            <button class="btn btn-primary btn-sm" type="submit">Enviar</button>
                        </div>
                    </div>
                </g:form>
            </div>

        </div>




    </div>
</div>

<script>
    $( document ).ready(function() {
        //https://plugins.krajee.com/star-rating/plugin-options
        $(".rating-loading").rating({min:0, max:5, step:1, size:'md', language:'es'});
    });

</script>


</body>

</html>

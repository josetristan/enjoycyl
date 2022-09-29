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
            <div class="col-sm-12">
                <g:form name="form" action="accurateSearch" class="form">
                    <div class="ibox-content m-b-sm border-bottom">
                        <div class="row">
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label class="col-form-label" for="locality">Provincia</label>
                                    <cyl:localitySelect value="${command?.locality}"></cyl:localitySelect>
                                </div>
                            </div>

                            <div class="col-sm-5">
                                <div class="form-group">
                                    <label class="col-form-label" for="region">Región</label>
                                    <g:textField name="region" value="${command?.region}" class="form-control"/>
                                </div>
                            </div>

                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label class="col-form-label" for="category">Categoría</label>
                                    <select class="form-control m-b" name="category">
                                        <option value="">Categoría...</option>
                                        <option value="BOOKS">Libros y Lectura</option>
                                        <option value="CINEMA">Cine</option>
                                        <option value="CONCERTS">Conciertos</option>
                                        <option value="CONFERENCES">Conferencias y Cursos</option>
                                        <option value="EXHIBITIONS">Exposiciones</option>
                                        <option value="OTHERS">Otros</option>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-sm-5">
                                <div class="form-group">
                                    <label class="col-form-label" for="startDate">Desde:</label>
                                    <div class="input-group date">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input name="startDate" id="startDate" type="text" class="form-control" value="${g.formatDate(format:'dd/MM/yyyy', date:command?.startDate)}">
                                    </div>
                                </div>
                            </div>

                            <div class="col-sm-5">
                                <div class="form-group">
                                    <label class="col-form-label" for="endDate">Hasta:</label>
                                    <div class="input-group date">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input name="endDate" id="endDate" type="text" class="form-control" value="${g.formatDate(format:'dd/MM/yyyy', date:command?.endDate)}">
                                    </div>
                                </div>
                            </div>

                            <div class="col-sm-2">
                                <div class="form-group">
                                    <button class="btn btn-primary btn-lg" type="submit" id="submit">Buscar</button>
                                </div>
                            </div>
                        </div>

                    </div>
                </g:form>
            </div>
        </div>

        <cyl:activityCard activities="${activities}" isGeneric="${isGeneric}"/>

    </div>
</div>

<script>
    $( document ).ready(function() {

        let storage = readLocation();
        $("#locality").val(storage.locality);
        $("#region").val(storage.region);

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

        $("#submit").click(function(){
            saveLocation($( "#locality option:selected" ).val(),$("#region").val())
        });

        $('#startDate').datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true,
            format: 'dd/mm/yyyy'
        });

        $('#endDate').datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true,
            format: 'dd/mm/yyyy'
        });

    });

</script>


</body>

</html>

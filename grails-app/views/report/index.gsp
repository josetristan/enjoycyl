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
            <div class="col-lg-10">
                <h2>Análisis</h2>
            </div>
        </div>

        <div id="wrapper wrapper-content animated fadeInRight ecommerce" class="gray-bg">

                <div class="ibox">
                <div class="ibox-title">
                    <h5>Búsqueda</h5>
                    <div class="ibox-tools">
                        <a class="collapse-link">
                            <i class="fa fa-chevron-up"></i>
                        </a>
                    </div>
                </div>

                <g:form name="form" action="search">
                <div class="ibox-content m-b-sm border-bottom">
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-form-label" for="locality">Provincia</label>
                                <select class="form-control m-b" name="locality" value="${command.locality}">
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
                        </div>

                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-form-label" for="category">Categoría</label>
                                <select class="form-control m-b" name="category" value="${command.category}">
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
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-form-label" for="startDate">Desde:</label>
                                <div class="input-group date">
                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input name="startDate" id="startDate" type="text" class="form-control" value="${g.formatDate(format:'dd/MM/yyyy', date:command.startDate)}" required="">
                                </div>
                            </div>
                        </div>

                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-form-label" for="endDate">Hasta:</label>
                                <div class="input-group date">
                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input name="endDate" id="endDate" type="text" class="form-control" value="${g.formatDate(format:'dd/MM/yyyy', date:command.endDate)}" required="">
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-form-label" for="title">Título</label>
                                <g:textField name="title" value="${command.title}" class="form-control"/>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-6">
                            <div class="form-group">
                                <button class="btn btn-primary btn-sm" type="submit">Buscar</button>
                            </div>
                        </div>
                    </div>

                </div>

                </g:form>
                </div>




            <g:if test="${individualInfo.size()>0}">
                <g:render template="tableIndividualInfo"></g:render>
            </g:if>
            <g:if test="${mostLiked.size()>0}">
                <g:render template="tableMostLiked"></g:render>
            </g:if>

           <g:render template="graphs"/>

            <g:if test="${mostRated.size()>0}">
                <g:render template="tableMostRated"></g:render>
            </g:if>

           <g:render template="ratedGraphs"/>






        </div> <%--ecommerce--%>
    </div> <%--page-wrapper--%>
</div> <%--wrapper--%>






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

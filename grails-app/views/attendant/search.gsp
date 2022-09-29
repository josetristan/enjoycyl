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
                <h2>Búsqueda de eventos para su control y valoración</h2>
            </div>
        </div>

        <div class="wrapper wrapper-content animated fadeInRight">

            <div class="row">
                <div class="col-lg-12">
                    <div class="ibox"></div>
                    <div class="row ibox-content">
                        <g:render template="search"/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-12">
                    <div class="ibox"></div>
                    <div class="row ibox-content">
                        <g:render template="result"/>
                    </div>
                </div>
            </div>


        </div>  <%--class="wrapper wrapper-content animated fadeInRight--%>
    </div>  <%--class="gray-bg--%>
</div> <%--class="wrapeer--%>

<script>
    $( document ).ready(function() {

    });

</script>


</body>

</html>


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


    <nav class="navbar-default navbar-static-side" role="navigation">
    </nav>

    <div id="page-wrapper" class="gray-bg">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
            </nav>
        </div>

        <div class="wrapper wrapper-content animated fadeInRight">

            <div class="row">
                <div class="col-lg-12">

                    <div class="ibox product-detail">
                        <div class="ibox-content">

                            <div class="row">
                                <div class="col-md-5">


                                    <div class="product-images">

                                        <div>
                                            <div class="image-imitation">
                                                <img src="${activity?.urlImage}" alt="Cultura CyL">
                                            </div>
                                        </div>

                                    </div>

                                </div>
                                <div class="col-md-7">

                                    <h2 class="font-bold m-b-xs">
                                        ${activity?.title}
                                    </h2>
                                    <small>${activity?.category?.name}</small>
                                    <div class="m-t-md">
                                        <h2 class="product-main-price">
                                            <g:if test="${activity?.endDate}!=''">
                                                Desde el
                                            </g:if>
                                            <g:formatDate format="dd/MM/yyyy" date="${activity?.startDate}"/>
                                            <g:if test="${activity?.endDate}!=''">
                                                hasta el <g:formatDate format="dd/MM/yyyy" date="${activity?.endDate}"/></h2>
                                            </g:if>
                                    </div>
                                    <hr>

                                    <h4>Descripción</h4>
                                    <div class="small text-muted">
                                        <cyl:description description="${activity.description}"/>
                                    </div>
                                    <dl class="small m-t-md">
                                        <dt>Lugar</dt>
                                        <dd>${activity?.celebrationPlace}</dd>
                                        <dd>${activity?.region}</dd>
                                        <dd>${activity?.locality?.name}</dd>
                                        <dt>Dirección</dt>
                                        <dd>${activity?.address}</dd>
                                        <dt>Enlace</dt>
                                        <dd><a href="${activity?.urlContent}">${activity?.urlContent}</a></dd>
                                    </dl>
                                    <hr>

                                    <div>
                                        <div class="btn-group">
                                            <g:link id="rated" controller="rated" action="index" params="[activityId:activity.eventId]" class="btn btn-primary btn-sm rated"
                                                    data-eventid="${activity.eventId}"><i class="fa fa-star"></i> Valorar </g:link>
                                        </div>
                                    </div>



                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
    </div>
</div>

    <script>
        $( document ).ready(function() {

            markEventsAsRated();
            $(".rated").click(function () {
                removeOldUserOpinions();
                saveRated(this);
                markEventsAsRated();
            })
        });

    </script>



</body>

</html>

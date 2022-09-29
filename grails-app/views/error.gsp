<!doctype html>
<html>
    <head>
        <title><g:if env="development">Grails Runtime Exception</g:if><g:else>Error</g:else></title>
        <meta name="layout" content="main" />
    </head>
    <body class="gray-bg">

    <div class="middle-box text-center animated fadeInDown">
        <h1>404</h1>
        <h3 class="font-bold">Página no encontrada</h3>

        <div class="error-desc">
            Lo sentimos, pero la página que estás buscando no se ha encontrado.

            <g:if env="development">
                <g:if test="${Throwable.isInstance(exception)}">
                    <g:renderException exception="${exception}" />
                </g:if>
                <g:elseif test="${request.getAttribute('javax.servlet.error.exception')}">
                    <g:renderException exception="${request.getAttribute('javax.servlet.error.exception')}" />
                </g:elseif>
                <g:else>
                    <ul>
                        <li>An error has occurred</li>
                        <li>Exception: ${exception}</li>
                        <li>Message: ${message}</li>
                        <li>Path: ${path}</li>
                        <li>Error: ${error}</li>
                    </ul>
                </g:else>
            </g:if>
            <g:else>
                <ul>
                    <li>${error}</li>
                </ul>
            </g:else>
        </div>
    </div>

    <g:if env="development">
        <g:if test="${Throwable.isInstance(exception)}">
            <g:renderException exception="${exception}" />
        </g:if>
        <g:elseif test="${request.getAttribute('javax.servlet.error.exception')}">
            <g:renderException exception="${request.getAttribute('javax.servlet.error.exception')}" />
        </g:elseif>
        <g:else>
            <ul>
                <li>An error has occurred</li>
                <li>Exception: ${exception}</li>
                <li>Message: ${message}</li>
                <li>Path: ${path}</li>
                <li>Error: ${error}</li>
            </ul>
        </g:else>
    </g:if>

    </body>
</html>

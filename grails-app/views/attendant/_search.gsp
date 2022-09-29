<div id="wrapper wrapper-content animated fadeInRight ecommerce" class="gray-bg">

    <g:form name="form" action="search">

        <div class="ibox-content m-b-sm border-bottom">
            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-form-label" for="locality">Provincia</label>
                        <cyl:localitySelect value="${command?.locality}"></cyl:localitySelect>
                        <%-- <select class="form-control m-b" name="locality">
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
                        --%>
                    </div>
                </div>

                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="col-form-label" for="region">Región</label>
                        <g:textField name="region" value="${command.region}" class="form-control"/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-6">
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
                <div class="col-sm-12">
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

<div class="row">
    <div class="col-lg-12">
        <div class="ibox">
            <div class="ibox-content">
                <h2>Eventos<span class="text-navy"> más votados</span></h2>

                <table class="footable table table-stripped toggle-arrow-tiny footable-loaded tablet breakpoint" data-page-size="15">
                    <thead>
                    <tr>

                        <th class="footable-visible footable-sortable footable-first-column">Provincia<span class="footable-sort-indicator"></span></th>
                        <th data-hide="phone" class="footable-sortable footable-visible" style="">Municipio<span class="footable-sort-indicator"></span></th>
                        <th data-hide="phone" class="footable-sortable footable-visible" style="">Categoría<span class="footable-sort-indicator"></span></th>
                        <th data-hide="phone" class="footable-sortable footable-visible" style="">Título<span class="footable-sort-indicator"></span></th>
                        <th data-hide="phone" class="footable-sortable footable-visible" style="">Likes<span class="footable-sort-indicator"></span></th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each var="info" in="${individualInfo}" status="i">
                        <tr class="${(i % 2) == 0 ? 'footable-even' : 'footable-odd'}" style="">
                            <td class="footable-visible footable-first-column"><span class="footable-toggle"></span>
                                ${info.locality?.name}
                            </td>
                            <td class="footable-visible footable-first-column"><span class="footable-toggle"></span>
                                ${info.region}
                            </td>
                            <td class="footable-visible" style="">
                                ${info.category?.name}
                            </td>
                            <td class="footable-visible" style="">
                                ${info.title}
                            </td>
                            <td class="footable-visible" style="">
                                ${info.likes}
                            </td>
                        </tr>
                    </g:each>
                    </tbody>

                </table>

            </div>
        </div>
    </div>
</div>  <%--end  table--%>
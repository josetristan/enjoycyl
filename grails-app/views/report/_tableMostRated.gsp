<div class="row">
    <div class="col-lg-12">
        <div class="ibox">
            <div class="ibox-content">
                <h2>Eventos<span class="text-navy"> más valorados</span></h2>

                <table class="footable table table-stripped toggle-arrow-tiny footable-loaded tablet breakpoint" data-page-size="15">
                    <thead>
                    <tr>

                        <th class="footable-visible footable-sortable footable-first-column">Provincia<span class="footable-sort-indicator"></span></th>
                        <th data-hide="phone" class="footable-sortable footable-visible" style="">Municipio<span class="footable-sort-indicator"></span></th>
                        <th data-hide="phone" class="footable-sortable footable-visible" style="">Categoría<span class="footable-sort-indicator"></span></th>
                        <th data-hide="phone" class="footable-sortable footable-visible" style="">Título<span class="footable-sort-indicator"></span></th>
                        <th data-hide="phone" class="footable-sortable footable-visible" style="">Valoración<span class="footable-sort-indicator"></span></th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each var="rated" in="${mostRated}" status="i">
                        <tr class="${(i % 2) == 0 ? 'footable-even' : 'footable-odd'}" style="">
                            <td class="footable-visible footable-first-column"><span class="footable-toggle"></span>
                                ${rated[1]?.name}
                            </td>
                            <td class="footable-visible footable-first-column"><span class="footable-toggle"></span>
                                ${rated[2]}
                            </td>
                            <td class="footable-visible" style="">
                                ${rated[3]?.name}
                            </td>
                            <td class="footable-visible" style="">
                                ${rated[4]}
                            </td>
                            <td class="footable-visible" style="">
                                ${rated[0]}
                            </td>
                        </tr>
                    </g:each>
                    </tbody>

                </table>

            </div>
        </div>
    </div>
</div>  <%--end  table--%>
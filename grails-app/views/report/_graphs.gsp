<div class="row">
    <div class="col-lg-4">
        <div class="ibox ">
            <div class="ibox-title">
                <h5>Me gusta <span class="text-navy"> por provincias</span></h5>
            </div>
            <div class="ibox-content text-center h-200">
                <canvas id="chartLocality" width="400" height="400"></canvas>
                <%--<span id="sparkline7"><canvas width="150" height="150" style="display: inline-block; width: 150px; height: 150px; vertical-align: top;"></canvas></span>--%>
            </div>
        </div>
    </div>
    <div class="col-lg-4">
        <div class="ibox ">
            <div class="ibox-title">
                <h5>Me gusta <span class="text-navy"> por categorías</span></h5>
            </div>
            <div class="ibox-content text-center h-200">
                <canvas id="chartCategory" width="400" height="400"></canvas>
            </div>
        </div>
    </div>
    <div class="col-lg-4">
        <div class="ibox ">
            <div class="ibox-title">
                <h5>Me gusta <span class="text-navy"> por meses</span></h5>
            <div class="ibox-content text-center h-200">
                <canvas id="chartMonth" width="400" height="400"></canvas>
            </div>
        </div>
    </div>
</div>
</div>

<div class="row">
    <div class="col-lg-4">
        <div class="ibox ">
            <div class="ibox-title">
                <h5>Número <span class="text-navy"> por provincias</span></h5>
            </div>
            <div class="ibox-content text-center h-200">
                <canvas id="chartNumberLocality" width="400" height="400"></canvas>
                <%--<span id="sparkline7"><canvas width="150" height="150" style="display: inline-block; width: 150px; height: 150px; vertical-align: top;"></canvas></span>--%>
            </div>
        </div>
    </div>
    <div class="col-lg-4">
        <div class="ibox ">
            <div class="ibox-title">
                <h5>Número <span class="text-navy"> por categorías</span></h5>
            </div>
            <div class="ibox-content text-center h-200">
                <canvas id="chartNumberCategory" width="400" height="400"></canvas>
            </div>
        </div>
    </div>
    <div class="col-lg-4">
        <div class="ibox ">
            <div class="ibox-title">
                <h5>Número <span class="text-navy"> por meses</span></h5>
                <div class="ibox-content text-center h-200">
                    <canvas id="chartNumberMonth" width="400" height="400"></canvas>
                </div>
            </div>
        </div>
    </div>
</div>


<cyl:barChart id="chartLocality" data="${likesByLocality}" type="${com.jtristan.enjoycyl.GraphsTagLib.Type.LOCALITY}" labelTitle="likes"></cyl:barChart>
<cyl:pieChart id="chartCategory" data="${likesByCategory}" type="${com.jtristan.enjoycyl.GraphsTagLib.Type.CATEGORY}" labelTitle="likes"></cyl:pieChart>
<cyl:lineChart id="chartMonth" data="${likesByMonth}" type="${com.jtristan.enjoycyl.GraphsTagLib.Type.MONTH}" labelTitle="likes"></cyl:lineChart>

<cyl:barChart id="chartNumberLocality" data="${activitiesByLocality}" type="${com.jtristan.enjoycyl.GraphsTagLib.Type.LOCALITY}" labelTitle="número"></cyl:barChart>
<cyl:pieChart id="chartNumberCategory" data="${activitiesByCategory}" type="${com.jtristan.enjoycyl.GraphsTagLib.Type.CATEGORY}" labelTitle="número"></cyl:pieChart>
<cyl:lineChart id="chartNumberMonth" data="${activitiesByMonth}" type="${com.jtristan.enjoycyl.GraphsTagLib.Type.MONTH}"  labelTitle="número"></cyl:lineChart>





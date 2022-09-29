<div class="row">
    <div class="col-lg-4">
        <div class="ibox ">
            <div class="ibox-title">
                <h5>Valoración <span class="text-navy"> por provincias</span></h5>
            </div>
            <div class="ibox-content text-center h-200">
                <canvas id="chartRateLocality" width="400" height="400"></canvas>
            </div>
        </div>
    </div>
    <div class="col-lg-4">
        <div class="ibox ">
            <div class="ibox-title">
                <h5>Valoración <span class="text-navy"> por categorías</span></h5>
            </div>
            <div class="ibox-content text-center h-200">
                <canvas id="chartRateCategory" width="400" height="400"></canvas>
            </div>
        </div>
    </div>
    <div class="col-lg-4">
        <div class="ibox ">
            <div class="ibox-title">
                <h5>Valoración <span class="text-navy"> por meses</span></h5>
            <div class="ibox-content text-center h-200">
                <canvas id="chartRateMonth" width="400" height="400"></canvas>
            </div>
        </div>
    </div>
</div>
</div>

<cyl:barChart id="chartRateLocality" data="${ratedByLocality}" type="${com.jtristan.enjoycyl.GraphsTagLib.Type.LOCALITY}" labelTitle="valoración"></cyl:barChart>
<cyl:pieChart id="chartRateCategory" data="${ratedByCategory}" type="${com.jtristan.enjoycyl.GraphsTagLib.Type.CATEGORY}" labelTitle="valoración"></cyl:pieChart>
<cyl:lineChart id="chartRateMonth" data="${ratedByMonth}" type="${com.jtristan.enjoycyl.GraphsTagLib.Type.MONTH}" labelTitle="valoración"></cyl:lineChart>



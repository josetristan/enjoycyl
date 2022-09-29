package com.jtristan.enjoycyl.ui

import com.jtristan.enjoycyl.ActivityStatisticMessageService
import com.jtristan.enjoycyl.pojo.StatisticPojo
import groovy.transform.CompileStatic

@CompileStatic
class ActivityStatisticsCardService {

    ActivityStatisticMessageService activityStatisticMessageService

    /**
     * Widgets.
     * Build a group of progress bar
     * @param statistics
     * @return
     */
    def severalProgressStatisticCard(List<StatisticPojo> statistics){

        int totalActivities = getTotalActivities(statistics)

        def html = '''
            <div class="product-desc card-title">   
                Sabías qué ...
            </div>
            
            <div class="ibox-content">
                
            '''
        statistics.each{
            html+=buildProgressLine(it, totalActivities)
        }

        html+="</div>"

        html+='''                    
            <div class="product-desc">                                                                                    
            '''

        def title
        if (statistics.size()==0)
            title = "Se ha producido un error"
        else
            title = getTitle(statistics.get(0))

        html+="<a href=\"#\" class=\"product-name\"> ${title}</a>"
        html+='<div class="small m-t-xs">'
        html+=activityStatisticMessageService.buildMessage(statistics)
        html+='</div> '

        html+='</div> '

        return html
    }


    def percentageStatisticCard(List<StatisticPojo> statistics){
        def total = getTotalActivities(statistics)
        def percentage = calculatePercentage(statistics.get(0).number, total)
        def html = '''
            <div class="product-desc card-title">   
                Sabías qué ...
            </div>
            <div class="ibox">
                <div class="ibox-content">
                '''

        html+="<h5>${statistics.get(0).category.name}</h5>"
        html+="<h2>${statistics.get(0).number}/${total}</h2>"
        html+='''                                                
                    <div class="text-center">
                        <div id="sparkline5"><canvas style="display: inline-block; width: 140px; height: 140px; vertical-align: top;" width="140" height="140"></canvas></div>
                    </div>
                </div>
            </div>
            '''


        html+='''        
                        </div>
                        <div class="product-desc">                                                                                    
            '''
        html+="<a href=\"#\" class=\"product-name\"> ${getTitle(statistics.get(0))}</a>"
        html+="</div>"
        html+='<div class="small m-t-xs">'
        html+=activityStatisticMessageService.buildMessage(statistics)
        html+='</div>'

        return html
    }

    def String buildProgressLine(StatisticPojo statisticPojo, int total) {
        def value
        value = (statisticPojo.groupedBy==StatisticPojo.GroupedBy.LOCALITY)?statisticPojo.locality.name:statisticPojo.category.name

        def html = "<div> <span>${value}</span> <small class=\"float-right\">${statisticPojo.number}/${total}</small> </div>"
        html+="<div class=\"progress progress-small\"> <div style=\"width: ${calculatePercentage(statisticPojo.number,total)}%;\" class=\"progress-bar\"></div> </div>"
        return html
    }

    def String getTitle(StatisticPojo statisticPojo) {
        if (statisticPojo.groupedBy == StatisticPojo.GroupedBy.LOCALITY) {
            return "${statisticPojo.category.name} ${statisticPojo.period.typePeriod.message}"
        } else if (statisticPojo.groupedBy == StatisticPojo.GroupedBy.CATEGORY){
            return "Eventos culturales en ${statisticPojo.locality.name} ${statisticPojo.period.typePeriod.message}"
        }

    }


    private int getTotalActivities(List<StatisticPojo> statisticPojos) {
        int total=0
        statisticPojos.each {
            total+=it.number
        }
        return total
    }

    private int calculatePercentage(int obtained, int total) {
        return (int) (obtained * 100 / total)
    }
}

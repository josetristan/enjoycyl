package com.jtristan.enjoycyl

import com.jtristan.enjoycyl.exceptions.ExceptionProposalEmpty
import com.jtristan.enjoycyl.pojo.Proposal
import com.jtristan.enjoycyl.pojo.StatisticPojo
import com.jtristan.enjoycyl.ui.ActivityCardService
import com.jtristan.enjoycyl.ui.ActivityStatisticsCardService
import com.jtristan.enjoycyl.ui.GridService
import com.jtristan.enjoycyl.ui.TierraSaborCardService

import java.time.format.DateTimeFormatter

class ActivityTabLibTagLib {

    static namespace = "cyl"

    public final static int GRID_ROWS = 4
    public final static int GRID_COLUMNS = 3

    ActivityStatisticsCardService activityStatisticsCardService
    TierraSaborCardService tierraSaborCardService
    ActivityCardService activityCardService
    GridService gridService

    /**
     * If we add directly the description of the activity to the gsp it is considered as a text of block without
     * apply css to the tags.
     * description: activity description
     */
    def description={attrs->

        out << attrs?.description
    }

    /**
     * Show a card with the event information plus the statistic and the tierra de sabor products
     * Generates a grid of 3*4 cards.
     * Grid structure:
     * first row: event event (TierraSabor or statistic)
     * rest of rows: random with a least one event in case exist
     * Description: shows a maximun of 500 characteres
     * activities: list of activity matched with filter
     */
    def card = { attrs ->
        int i=0
        ArrayList<Object> data = new ArrayList<>()
        Object[][] a
        boolean progressStatisticCard = false

        if (!attrs?.proposals) return out //throwTagError('The activities are necessary to draw the card')

        Proposal proposals = attrs.proposals

        def hasProposalActivities =  hasActivities(proposals)

        def grid = gridService.generateGrid(proposals)

        def html='<div class="wrapper wrapper-content animated fadeInRight">'

        if (hasProposalActivities==false){
            html+="<div class=\"alert alert-danger\">No existen eventos que cumplan los criterios de búsqueda.</div>"
        }

        int index1 = 0
        for(int row=0;row<grid.length;row++){
            for(int column=0;column<grid[row].length;column++){
                if (index1!=0 && index1%3==0) html+='</div>' //end row until create the new one
                if (index1%3==0) html+='<div class="row">'
                html+= '''                        
                    <div class="col-md-4">
                        <div class="ibox">
                            <div class="ibox-content product-box">
                                <div class="product-imitation">
                    '''
                def proposal = grid[row][column]

                if (proposal instanceof Activity){
                    html+=activityCardService.activityCardProposal(proposal)
                } else if (proposal instanceof List<StatisticPojo>){
                    if (progressStatisticCard==false){
                        html+=activityStatisticsCardService.severalProgressStatisticCard(proposal)
                        progressStatisticCard=true
                    }else{
                        html+=activityStatisticsCardService.percentageStatisticCard(proposal)
                    }
                }else if (proposal instanceof TierraSaborCompany){
                    html+=tierraSaborCardService.tierraSaborCard(proposal)
                }

                html+='''                    
                                </div>    
                            </div>
                        </div>
                    </div>            
                    '''
                index1++

            }
        }

        html+='</div>' //end last row
        html+='</div>' //end wrapper

        //Some text formatted in html that contains strong break the html
        html = html.replace("<strong>","").replace("</strong>","")

        out << html
        return out

    }

    /**
     * Check if there aren't activities in the proposal because the API didn't return values
     * @param proposal
     * @return
     */
    def boolean hasActivities(Proposal proposal) {
        return proposal.activities.size()>0
    }


    def localitySelect = { attrs ->

        String defaultValue = attrs.value

        StringBuilder select = new StringBuilder()
        select.append('<select class="form-control" name="locality" id="locality">')
        select.append('<option value="">Provincia...</option>')
        Activity.Locality.values().each{
            if (defaultValue==it.name())
                select.append(buildOptionSelected(it.name(),it.getName()))
            else
                select.append(buildOption(it.name(),it.getName()))
        }
        select.append('</select>')

        out<<select.toString()
    }
    
    def buildOption(String key, String name) {
        return "<option value=\"${key}\">${name}</option>"
    }
    
    def buildOptionSelected(String key, String name) {
        return "<option value=\"${key}\" selected=\"selected\">${name}</option>"
    }


    /**
     * Show a card with the event information
     * Generates a grid of 3*4 cards.
     * Description: shows a maximun of 500 characteres
     * activities: list of activity matched with filter
     * isGeneric: if the search doesn't contains any parameter the card show the locality-region
     */
    def activityCard = { attrs ->
        ArrayList<Object> data = new ArrayList<>()
        Activity[][] grid = new Activity[4][3]

        if (!attrs?.activities) return out //throwTagError('The activities are necessary to draw the card')
        def isGeneric = attrs?.isGeneric

        List<Activity> activities = attrs.activities

        def html='<div class="wrapper wrapper-content animated fadeInRight">'

        if (!activities){
            html+="<div class=\"alert alert-danger\">No existen eventos que cumplan los criterios de búsqueda.</div>"
        }

        activities.eachWithIndex {Activity activity, index->
            if (index!=0 && index%3==0) html+='</div>' //end row until create the new one
            if (index%3==0) html+='<div class="row">'
            html+= '''                        
                    <div class="col-md-4">
                        <div class="ibox">
                            <div class="ibox-content product-box">
                                <div class="product-imitation">
                    '''

            html+=activityCardService.activityCard(activity, isGeneric)

            html+='''                    
                            </div>
                        </div>
                    </div>            
                    '''
            index ++
        }

        html+='</div>' //end last row
        html+='</div>' //end wrapper

        //Some text formatted in html that contains strong break the html
        html = html.replace("<strong>","").replace("</strong>","")

        out << html
        return out

    }
    
}

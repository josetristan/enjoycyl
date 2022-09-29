package com.jtristan.enjoycyl.ui

import com.jtristan.enjoycyl.Activity
import groovy.transform.CompileStatic

import java.time.format.DateTimeFormatter

@CompileStatic
class ActivityCardService {


    def activityCardProposal(Activity activity){
        def html
        html=  "<img src=\"${activity.urlImage}\" alt=\"${activity.title}\"/>"
        activity.urlImage //"info"
        //</div> close the <div class="product-imitation"> and only contains the image
        html+='''        
                        </div>
                        <div class="product-desc">
                            <span>                                
                                <div class="text-right">
                                    <a href="#" class="btn btn-xs btn-white like" 
        '''
        html+="data-eventid=\"${activity.eventId}\"><i class=\"fa fa-thumbs-up\"></i> Like </a>"

        html+='''                                                                        
                                </div>
                            </span>
                            
                            <small class="text-muted">Título</small>
            '''
        html+="<a href=\"#\" class=\"product-name\"> ${activity.title}</a>"
        html+='''
                            <small class="text-muted">Fecha</small>
                            <div class="small m-t-xs">
            '''
        html+=activity.startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        html+="</div>"
        html+='''
                            <small class="text-muted">Descripción</small>
                            <div class="small m-t-xs">
            '''
        html+=getShortDescription(activity.description)
        html+='''        
                            </div>
                            <div class="m-t text-righ">
            '''

        html+=buildLinkDetail(activity.eventId)
        html+="</div>"  //end m-t text-right link

        return html
    }


    def activityCard(Activity activity, boolean isGeneric){
        def html
        html=  "<img src=\"${activity.urlImage}\" alt=\"${activity.title}\"/>"
        activity.urlImage //"info"
        //</div> close the <div class="product-imitation"> and only contains the image
        html+='''        
                        </div>
                        <div class="product-desc">
                            <span>                                
                                <div class="text-right">
                                    <a href="#" class="btn btn-xs btn-white like" 
        '''
        html+="data-eventid=\"${activity.eventId}\"><i class=\"fa fa-thumbs-up\"></i> Like </a>"

        html+='''                                                                        
                                </div>
                            </span>
                            
                            <small class="text-muted">Título</small>
            '''
        html+="<a href=\"#\" class=\"product-name\"> ${activity.title}</a>"
        html+='''
                            <small class="text-muted">Fecha</small>
                            <div class="small m-t-xs">
            '''
        html+=activity.startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        html+="</div>"

        if (isGeneric) {
            html += '''
                                <small class="text-muted">Localidad</small>
                                <div class="small m-t-xs">
                '''
            html += activity.locality.name
            if (activity.region && activity.locality.name.trim() != activity.region.trim()) html += ' : ' + activity.region
            html += "</div>"
        }


        html+='''
                            <small class="text-muted">Descripción</small>
                            <div class="small m-t-xs">
            '''
        html+=getShortDescription(activity.description)
        html+='''        
                            </div>
                            <div class="m-t text-righ">
            '''

        html+=buildLinkDetail(activity.eventId)
        html+="</div>"  //end m-t text-right link
        html+="</div>"  //end <product-desc



        return html
    }

    private String buildLinkDetail(String activityId){
        return '<a href="/activity/detail/' +activityId+ '" class="btn btn-xs btn-outline btn-primary">Info <i class="fa fa-long-arrow-right"></i> </a>'
    }

    private String getShortDescription(String description) {
        int spaceLength = 1
        int length
        StringBuilder shortDescription = new StringBuilder()
        String descriptionWithoutDivs = removeDivs(description)
        final String[] words = descriptionWithoutDivs.split(" ")
        for(String word:words){
            length += word.length()+spaceLength
            if (length<500) shortDescription.append(word).append(" ")else break
        }
        return shortDescription.append(" ...").toString()
    }

    /**
     * As field show a short description it is possible to have son started div without show the closed div
     * @param text
     * @return
     */
    def String removeDivs(String text) {
       return text.replace("<div>","").replace("</div>","")
    }
}

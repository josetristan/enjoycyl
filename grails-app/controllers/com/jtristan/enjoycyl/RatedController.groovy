package com.jtristan.enjoycyl

import com.jtristan.enjoycyl.repository.ActivityStatisticsRepositoryService
import com.jtristan.enjoycyl.repository.RatedRepositoryService

class RatedController {

    RatedRepositoryService ratedRepositoryService
    ActivityStatisticsRepositoryService activityStatisticsRepositoryService

    def index() {
        def activityId = params.activityId
        def title = activityStatisticsRepositoryService.getTitle(activityId)
        title = (title==null)?'':title
        [activityId: activityId, title:title]
    }

    def save(Rated userOpinionInstance){
        userOpinionInstance = new Rated(params)
        if (userOpinionInstance){
            if (userOpinionInstance.validate()){
                ratedRepositoryService.save(userOpinionInstance)
            }else{
                userOpinionInstance.hasErrors().each {
                    log.error(it)
                }
            }
        }
        redirect controller: 'activity', action: 'detail', params:[id: userOpinionInstance.eventId]
    }
}

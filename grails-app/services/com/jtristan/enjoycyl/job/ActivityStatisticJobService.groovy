package com.jtristan.enjoycyl.job

import com.jtristan.enjoycyl.Activity
import com.jtristan.enjoycyl.ActivityStatisticService
import com.jtristan.enjoycyl.CultureCalendarClientService
import com.jtristan.enjoycyl.Statistic
import com.jtristan.enjoycyl.pojo.ActivityPojo
import com.jtristan.enjoycyl.pojo.Field
import com.jtristan.enjoycyl.pojo.Link
import com.jtristan.enjoycyl.pojo.RecordFather
import com.jtristan.enjoycyl.repository.ActivityStatisticsRepositoryService
import grails.gorm.transactions.Transactional

import java.time.LocalDate


class ActivityStatisticJobService {
    private int MAXIMUN_ROWS_LIMIT = 100
    CultureCalendarClientService cultureCalendarClientService
    ActivityStatisticsRepositoryService activityStatisticsRepositoryService

    /**
     * Execute the job to get all activities since the current day and convert into statistics objects.
     * Save the statistics in the DB
     * @return
     */
    public void executeJob(){
        List<ActivityPojo> activityPojos = getAllActivities()
        List<Statistic> statistics =  convertIntoStatistic(activityPojos)
        activityStatisticsRepositoryService.save(statistics)
    }

    /**
     * Call the Activity API in order to get all activities since the current day.
     * Work with the information about the links that contains the own response where we can now which is the
     * request href to call the next pagination result.
     */
    private List<ActivityPojo> getAllActivities() {
        List<ActivityPojo> pojos = new ArrayList<>()
        Map<CultureCalendarClientService.Filter, Object> filter = new HashMap<>()
        //TODO: tiene que ser con el día actual
        LocalDate date = LocalDate.of(2022,9,25)
        filter.put(CultureCalendarClientService.Filter.START_DATE_GREATER_THAN, date)

        ActivityPojo activityPojo = cultureCalendarClientService.GET(filter,MAXIMUN_ROWS_LIMIT)
        pojos<<activityPojo

        String nextHref
        do {
            nextHref=null
            activityPojo.links.each { Link it ->
                if (it.rel.toUpperCase() == "NEXT") {
                    nextHref = it.href
                }
            }
            if (nextHref){
                activityPojo = cultureCalendarClientService.GET(nextHref)
                pojos<<activityPojo
            }
        }while(nextHref)

        return pojos

    }

    private List<Statistic> convertIntoStatistic(List<ActivityPojo> activityPojos) {
        List<Statistic> statistics  =new ArrayList<>()
        activityPojos.each {ActivityPojo activity->
            activity.records.each { RecordFather recordFather->
                Statistic statistic = new Statistic()
                Field field = recordFather.record.field
                statistic.eventId = field.eventId
                statistic.title = field.title
                statistic.celebrationPlace = field.celebrationPlace
                statistic.region = field.region
                statistic.category = Activity.Category.findByKey(field.category)
                statistic.isLibraryEvent = field.isLibraryEvent
                statistic.startDate = field.startDate
                statistic.endDate = field.endDate
                statistic.locality = Activity.Locality.findByKey(field.locality.toUpperCase())
                statistic.buildPeriod()

                statistics<<statistic
            }
        }
        return statistics
    }
}

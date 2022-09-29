package com.jtristan.enjoycyl.convert

import com.jtristan.enjoycyl.Activity
import com.jtristan.enjoycyl.Statistic
import com.jtristan.enjoycyl.pojo.ActivityPojo
import com.jtristan.enjoycyl.pojo.Record
import com.jtristan.enjoycyl.pojo.RecordFather
import groovy.transform.CompileStatic

/**
 * Convertion between pojo and activity
 */
@CompileStatic
class ActivityMapService {

    /**
     * Convert the pojo return by the API to the domain activity
     * @param pojo
     * @return
     */
    public List<Activity> mapToActivities(ActivityPojo pojo){
        def activities = new ArrayList<Activity>()

        pojo.records.each { RecordFather father->
            def it = father.record
            if (it.field){
                Activity activity = new Activity()
                def field = it.field
                activity.eventId = field.eventId
                activity.title = field.title
                activity.celebrationPlace = field.celebrationPlace
                activity.region = field.region
                activity.address = field.address
                activity.category = Activity.Category.findByKey(field.category)
                activity.setLibraryEvent(field.isLibraryEvent)
                activity.startDate =  field.startDate
                activity.endDate = field.endDate
                activity.urlImage = field.urlImage
                activity.locality = Activity.Locality.findByKey(field.locality.toUpperCase())
                activity.description = field.description
                activity.urlContent = field.urlContent

                activities.add(activity)
            }
        }

        return activities

    }

    /**
     * Convert the pojo return by the API to the domain statistic
     * @param pojo
     * @return
     */
    public List<Statistic> mapToStatistics(ActivityPojo pojo){
        def statistics = new ArrayList<Statistic>()

        pojo.records.each { RecordFather father->
            def it = father.record
            Statistic statistic = new Statistic()
            def field = it.field
            statistic.eventId = field.eventId
            statistic.title = field.title
            statistic.celebrationPlace = field.celebrationPlace
            statistic.region = field.region
            statistic.category = Activity.Category.findByKey(field.category)
            statistic.setLibraryEvent(field.isLibraryEvent)
            statistic.startDate =  field.startDate
            statistic.endDate = field.endDate
            statistic.locality = Activity.Locality.findByKey(field.locality.toUpperCase())

            statistics.add(statistic)

        }

        return statistics

    }


    def Activity mapToActivity(ActivityPojo pojo) {
        def list = mapToActivities(pojo)
        return (list)? list.first():null as Activity
    }
}

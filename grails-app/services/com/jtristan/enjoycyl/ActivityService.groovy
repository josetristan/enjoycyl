package com.jtristan.enjoycyl

import com.jtristan.enjoycyl.client.helper.DateRange
import com.jtristan.enjoycyl.command.ActivityCommand
import com.jtristan.enjoycyl.convert.ActivityMapService
import com.jtristan.enjoycyl.pojo.ActivityPojo
import com.jtristan.enjoycyl.pojo.Field
import com.jtristan.enjoycyl.pojo.Record
import com.jtristan.enjoycyl.pojo.RecordFather
import groovy.transform.CompileStatic

import java.time.LocalDate


/**
 * Response the activities based of the request toguether statistics, foods, ...
 */
@CompileStatic
class ActivityService {

    CultureCalendarClientService cultureCalendarClientService
    ActivityMapService activityMapService

    def List<Activity> getActivities(ActivityCommand activityCommand, int numberRecords) {
        return getActivitiesByNumberRow(activityCommand, numberRecords)
    }

    def List<Activity> getActivities(ActivityCommand activityCommand) {
        return getActivitiesByNumberRow(activityCommand, CultureCalendarClientService.ROWS)
    }

    private List<Activity> getActivitiesByNumberRow(ActivityCommand activityCommand, int numberRecords) {

        Map<CultureCalendarClientService.Filter, Object> filter = buildFilter(activityCommand)

        ActivityPojo activityPojo = cultureCalendarClientService.GET(filter,numberRecords)

        //TODO: voy a dar certeza a los datos que vengan del API ya que las fechas no me indican siempre si una actividad se ha acabado.
        //P. ejemplo, una acti. que se celebran todos los jueves y cuya fecha de comienzo fue hace dos meses
        //Una actividad que empieza hace un mes y que en la descripción indica que durará n meses pero no hay fecha de fin
        //activityPojo = removeActivitiesOutOfDateRange(activityPojo, activityCommand)

        def activities = activityMapService.mapToActivities(activityPojo)
        //The same activity could be schedule several times so order by date
        Collections.sort(activities)
        return activities

        //return activityMapService.mapToActivities(activityPojo)

    }

    private Map<CultureCalendarClientService.Filter, Object> buildFilter(ActivityCommand command){

        Map<CultureCalendarClientService.Filter, Object> filter = new HashMap<CultureCalendarClientService.Filter, Object>()
        if (command.category) {
            filter.put(CultureCalendarClientService.Filter.CATEGORY, command.category.name)
        }
        if (command.locality) {
            filter.put(CultureCalendarClientService.Filter.LOCALITY, command.locality.name)
        }
        if (command.startDate && !command.endDate){
            filter.put(CultureCalendarClientService.Filter.START_DATE, command.startDate)
        }
        if (command.startDate && command.endDate){
            filter.put(CultureCalendarClientService.Filter.START_DATE_BETWEEN, new DateRange(command.startDate, command.endDate))
        }

        return filter
    }

    def Activity getDetailedActivity(String eventId) {
        Map<CultureCalendarClientService.Filter, Object> filter = new HashMap<>()
        filter.put(CultureCalendarClientService.Filter.EVENT_ID, eventId)
        ActivityPojo activityPojo = cultureCalendarClientService.GET(filter,CultureCalendarClientService.ROWS)
        return activityMapService.mapToActivity(activityPojo)
    }

    /**
     * Remove dates in the past or if we filter by a date range out of that one.
     * @param activityPojo
     * @param command
     * @return
     */
    def ActivityPojo removeActivitiesOutOfDateRange(ActivityPojo activityPojo, ActivityCommand command) {
        LocalDate filterStartDate, filterEndDate
        filterStartDate = (command.startDate) ? command.startDate : LocalDate.now()
        filterEndDate =   command.endDate

        Iterator<RecordFather> ite = activityPojo.records.iterator()
        while (ite.hasNext()) {
            Record record = ite.next().record
            Field field = record.field
            if (IsInThePast(field.startDate, field.endDate, filterStartDate, filterEndDate)){
                ite.remove()
            }
        }
        return activityPojo
    }

    /**
     * @param startActivityDate
     * @param filterStartDate
     * @param filterEndDate
     * @return
     */
    private boolean IsInThePast(LocalDate startActivityDate, LocalDate endActivityDate, LocalDate filterStartDate, LocalDate filterEndDate){
        if (filterEndDate){
            return (isInDateRange(startActivityDate, filterStartDate,filterEndDate) || isInDateRange(endActivityDate, filterStartDate,filterEndDate))?true:false
        }else{
            if (startActivityDate.isBefore(filterStartDate) && endActivityDate==null) return true
            if (startActivityDate.isBefore(filterStartDate) && endActivityDate.isBefore(filterStartDate)) return true
        }
        return false
    }

    private boolean isInDateRange(LocalDate activityDate, LocalDate filterStartDate, LocalDate filterEndDate) {
        return (activityDate.isBefore(filterEndDate) && activityDate.isAfter(filterStartDate))
    }
}


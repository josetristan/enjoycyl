package com.jtristan.enjoycyl

import com.jtristan.enjoycyl.pojo.StatisticPojo
import com.jtristan.enjoycyl.statistic.Period
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic

import static com.jtristan.enjoycyl.pojo.StatisticPojo.*

/**
 * Generate the message to show in the statistic card
 */

class ActivityStatisticMessageService {

    private final static String CELEBRATE_FUTURE_MESSAGE = "celebrarán"
    private final static String CELEBRATE_FUTURE_PAST = "celebraron"

    /**
     * Generate the message
     * @param statistic
     * @return
     */
    @CompileStatic
    String buildMessage(List<StatisticPojo> statistics) {
        String message
        int maxNumber
        Activity.Category category
        int total = getTotalEvents(statistics)
        if (statistics.size()==0){
            return "Se ha producido un error. Por favor, comuníquelo a traves de las opciones de contacto"
        }
        switch (statistics.get(0).groupedBy){
            case GroupedBy.LOCALITY:
                //Books
                //  Palencia 1
                //  Leon     2
                return buildMessageByCategory(statistics,total,GroupedBy.CATEGORY)
            case GroupedBy.CATEGORY:
                //Palencia
                //  Books 1
                //  Shows 2
                return buildMessageByLocality(statistics,total,GroupedBy.LOCALITY)
        }
        return "Se ha producido un error. Por favor, comuníquelo a traves de las opciones de contacto"
    }

    private String buildMessageByCategory(List<StatisticPojo> statistics, int total, GroupedBy groupedBy){
        def(locality, maxNumber) = getMaxNumberOfEvent(statistics, groupedBy)
        return getMessageByCategory(statistics, total,locality,maxNumber)
    }


    private String buildMessageByLocality(List<StatisticPojo> statistics, int total, GroupedBy groupedBy){
        def(category, maxNumber) = getMaxNumberOfEvent(statistics,groupedBy)
        return getMessageByLocality(statistics, total,category,maxNumber)
    }

    @CompileStatic
    private int getTotalEvents(List<StatisticPojo> statisticPojos) {
        int total = 0
        statisticPojos.each {
            total+=it.number
        }
        return total
    }

    /**
     *  Build the message when was grouped by locality
     * @param statisticPojos
     * @param total
     * @param maxCategoryActivities: category with the maximun number of activities
     * @param maxNumber: máximun number of activities from one category
     * @return
     */
    @CompileStatic
    private String getMessageByLocality(List<StatisticPojo> statisticPojos, int total, Activity.Category maxCategoryActivitites, int maxNumber) {

        StringBuilder message = new StringBuilder()
        StatisticPojo statisticPojo  = statisticPojos.get(0)
        message.append("En la provincia de ${statisticPojo.locality.name} ")
        message.append("se ${getCelebrateTextAccordingTheTimeVerb(statisticPojo.period.typePeriod)} ")
        message.append("${statisticPojo.period.typePeriod.message} ")
        message.append("un total de ${total} eventos ")
        message.append("destacando ${maxNumber} ${maxCategoryActivitites.name}")
        return message.toString()

    }


    /**
     *  Build the message when was grouped by category
     * @param statisticPojos
     * @param total
     * @return
     */
    @CompileStatic
    private String getMessageByCategory(List<StatisticPojo> statisticPojos, int total, Activity.Locality maxLocalityActivities, int maxNumber) {
        StringBuilder message = new StringBuilder()
        StatisticPojo statisticPojo  = statisticPojos.get(0)
        message.append("En Castilla y León ${statisticPojo.period.typePeriod.message} ")
        message.append("se ${getCelebrateTextAccordingTheTimeVerb(statisticPojo.period.typePeriod)} ")
        message.append("un total de ${total} ${statisticPojo.category.name} ")
        message.append("destacando ${maxLocalityActivities.name} con ${maxNumber} ")
        return message.toString()
    }


    @CompileStatic
    private String getCelebrateTextAccordingTheTimeVerb(Period.TypePeriod typePeriod){
        switch (typePeriod){
            case [Period.TypePeriod.CURRENT_MONTH,Period.TypePeriod.TWO_NEXT_MONTHS]:
                return CELEBRATE_FUTURE_MESSAGE
            case Period.TypePeriod.LAST_YEAR:
                return CELEBRATE_FUTURE_PAST
        }
        log.error("typePeriod is empty or doesn't match: ${typePeriod}")
        return ""
    }

    private getMaxNumberOfEvent(List<StatisticPojo> statisticPojos, GroupedBy groupedBy) {
        def field
        int number=0
        def fieldName
        if (groupedBy==GroupedBy.CATEGORY)
            fieldName = "locality"
        else if (groupedBy==GroupedBy.LOCALITY)
            fieldName = "category"

        statisticPojos.each {
            if (it.number>number){
                field = it."$fieldName"
                number = it.number
            }
        }
        return [field, number]
    }
}

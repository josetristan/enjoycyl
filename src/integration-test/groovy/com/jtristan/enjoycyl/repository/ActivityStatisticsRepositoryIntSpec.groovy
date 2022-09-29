package com.jtristan.enjoycyl.repository

import com.jtristan.enjoycyl.Activity
import com.jtristan.enjoycyl.Statistic
import com.jtristan.enjoycyl.statistic.Period
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import spock.lang.Specification

import java.time.LocalDate

@Integration
@Rollback
class ActivityStatisticsRepositoryIntSpec extends Specification {

    ActivityStatisticsRepositoryService activityStatisticsRepositoryService

    def setup() {

    }

    def cleanup() {
    }

    void "obtiene número de eventos para una provincia por categorías y periodo"() {
        given:
        saveStatistics()
        def currentMonth = new Period(Period.TypePeriod.CURRENT_MONTH)
        when:
        def statistics = activityStatisticsRepositoryService.getStatistics(Activity.Locality.PALENCIA, currentMonth)
        then:
        getNumberActivities(statistics, Activity.Category.BOOKS)==2
        getNumberActivities(statistics, Activity.Category.CONFERENCES)==1
    }

    void "obtiene número de eventos para una provincia por categorías"() {
        given:
        saveStatistics()
        def currentMonth = new Period(Period.TypePeriod.CURRENT_MONTH)
        when:
        def statistics = activityStatisticsRepositoryService.getStatistics(Activity.Locality.PALENCIA, currentMonth)
        then:
        getNumberActivities(statistics, Activity.Category.BOOKS)==2
        getNumberActivities(statistics, Activity.Category.CONFERENCES)==1
    }

    void "obtiene el número de eventos para una categoría por provincias"(){
        given:
        saveStatistics()
        def currentMonth = new Period(Period.TypePeriod.CURRENT_MONTH)
        when:
        def statistics = activityStatisticsRepositoryService.getStatistics(Activity.Category.CONFERENCES, currentMonth)
        then:
        getNumberActivities(statistics, Activity.Locality.PALENCIA)==1
        getNumberActivities(statistics, Activity.Locality.VALLADOLID)==1

    }
    /**
     * We will use to analyze all the events but to proposing events
     * Palencia
     * Books
     * 2
     * Palencia
     * Exhibitions
     * 1
     * Zamora
     * Books
     * 3
     */
    void "obtiene el número de eventos por provincias y categorías"(){
        given:
        saveStatistics()
        def currentMonth = new Period(Period.TypePeriod.CURRENT_MONTH)
        when:
        def statistics = activityStatisticsRepositoryService.getStatistics(currentMonth)
        then:
        getNumberActivities(statistics, Activity.Locality.PALENCIA, Activity.Category.BOOKS)==2
        getNumberActivities(statistics, Activity.Locality.PALENCIA, Activity.Category.CONFERENCES)==1
        getNumberActivities(statistics, Activity.Locality.VALLADOLID, Activity.Category.CONFERENCES)==1
    }

    void "número de eventos para los dos siguientes meses"() {
        given:
        saveStatistics()
        def twoMonths = new Period(Period.TypePeriod.TWO_NEXT_MONTHS)
        when:
        def statistics = activityStatisticsRepositoryService.getStatistics(Activity.Locality.PALENCIA, twoMonths)
        then:
        getNumberActivities(statistics, Activity.Category.BOOKS)==2
        getNumberActivities(statistics, Activity.Category.CONFERENCES)==1
    }

    def Object saveStatistics() {
        new Statistic(region:"Dueñas", period:calculatePeriod(0),locality: Activity.Locality.PALENCIA, category: Activity.Category.BOOKS,eventId:123456).save(flush:true)
        new Statistic(region:"Villamuriel", period:calculatePeriod(0),locality: Activity.Locality.PALENCIA, category: Activity.Category.BOOKS,eventId:123457).save(flush:true)
        new Statistic(region:"Dueñas", period:calculatePeriod(0),locality: Activity.Locality.PALENCIA, category: Activity.Category.CONFERENCES,eventId:123458).save(flush:true)
        new Statistic(region:"Laguna", period:calculatePeriod(0),locality: Activity.Locality.VALLADOLID, category: Activity.Category.CONFERENCES,eventId:123459).save(flush:true)

        new Statistic(region:"Laguna", period:calculatePeriod(1),locality: Activity.Locality.VALLADOLID, category: Activity.Category.CONFERENCES,eventId:123465).save(flush:true)
        new Statistic(region:"Dueñas", period:calculatePeriod(2),locality: Activity.Locality.PALENCIA, category: Activity.Category.BOOKS,eventId:123471).save(flush:true)

    }

    def calculatePeriod(int monthsPlusCurrentPeriod){
        return LocalDate.now().plusMonths(monthsPlusCurrentPeriod).format(Period.FORMATTER)
    }

    def int getNumberActivities(list, Activity.Category cate) {
        return list.find{it.category==cate}.number
    }

    def int getNumberActivities(list, Activity.Locality loca) {
        return list.find{it.locality==loca}.number
    }

    def int getNumberActivities(list, Activity.Locality loca, Activity.Category cate) {
        return list.find{it.locality==loca && it.category==cate}.number
    }



}

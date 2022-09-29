package com.jtristan.enjoycyl

import com.jtristan.enjoycyl.command.ActivityCommand
import com.jtristan.enjoycyl.pojo.StatisticPojo
import com.jtristan.enjoycyl.repository.ActivityStatisticsRepositoryService
import com.jtristan.enjoycyl.statistic.Period
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

import com.jtristan.enjoycyl.Activity.Category
import com.jtristan.enjoycyl.Activity.Locality

class ActivityStatisticServiceSpec extends Specification implements ServiceUnitTest<ActivityStatisticService>{

    /*Class<?>[] getDomainClassesToMock(){
        //return [Type,Model,Format, Grouped, Component, ComponentHierarchy] as Class[]
        return [Statistic] as Class[]
    }*/

    def currentMonth = new Period(Period.TypePeriod.CURRENT_MONTH)
    def nextTwoMonths = new Period(Period.TypePeriod.TWO_NEXT_MONTHS)

    def a = [new StatisticPojo(locality: Locality.PALENCIA, category: Category.BOOKS, period: currentMonth)]
    def b = [new StatisticPojo(locality: Locality.BURGOS, category: Category.BOOKS, period: currentMonth)]
    def c = [new StatisticPojo(locality: Locality.PALENCIA, category: Category.SHOWS, period: currentMonth)]
    def d = [new StatisticPojo(locality: Locality.PALENCIA, category: Category.SHOWS, period: nextTwoMonths)]

    def setup() {
    }

    def cleanup() {
    }
    /**
     * TODO: This test doesn't work if we annotate the class as @ReadOnly
     */
   void "si se genera una estadística aleatoria que ya existe se elimina"() {
        given:

        ActivityStatisticsRepositoryService stub = Stub()
        service.activityStatisticsRepositoryService = stub

        stub.getStatistics(_,_)>>>[a,a,b,null,c]

        def command = new ActivityCommand(category: Activity.Category.BOOKS)

        when:'tiene que devolver 3 estadísticas (12-3)/3'
            def list = service.buildRamdonStatistics(command,3)
        then:
            list.size()==3
            statisticsAreNotDuplicated(list)==true
    }

    def boolean statisticsAreNotDuplicated(List<StatisticPojo> list) {

        return (list.get(0).get(0)==a.get(0) &&
        list.get(1).get(0)==b.get(0) &&
        list.get(2).get(0)==c.get(0))
    }
}

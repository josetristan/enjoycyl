package com.jtristan.enjoycyl

import com.jtristan.enjoycyl.pojo.StatisticPojo
import com.jtristan.enjoycyl.statistic.Period
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

import static com.jtristan.enjoycyl.pojo.StatisticPojo.*

class ActivityStatisticMessageServiceSpec extends Specification implements ServiceUnitTest<ActivityStatisticMessageService>{

    def setup() {
    }

    def cleanup() {
    }

    void "para una estadística de eventos por provincia en los próximos dos meses genera el mensaje"() {
        given:
        def expectedMessage = "En la provincia de Palencia se celebrarán durante los próximos dos meses "
        expectedMessage+= "un total de 6 eventos destacando 3 Conferencias y Cursos"
        def list = []
        def period = createPeriod(Period.TypePeriod.TWO_NEXT_MONTHS)
        list = createStatisticsGroupedByCategory(period)
        when:
        def message = service.buildMessage(list)
        then:
        message.equals(expectedMessage)
    }

    void "para una estadística de eventos por provincia en el mes actual genera el mensaje"() {
        given:
        def expectedMessage = "En la provincia de Palencia se celebrarán durante este mes "
        expectedMessage+= "un total de 6 eventos destacando 3 Conferencias y Cursos"
        def list = []
        def period = createPeriod(Period.TypePeriod.CURRENT_MONTH)
        list = createStatisticsGroupedByCategory(period)
        when:
        def message = service.buildMessage(list)
        then:
        message.equals(expectedMessage)
    }

    void "para una estadística de eventos por provincia en el año anterior genera el mensaje"() {
        given:
        def expectedMessage = "En la provincia de Palencia se celebraron en el último año "
        expectedMessage+= "un total de 6 eventos destacando 3 Conferencias y Cursos"
        def list = []
        def period = createPeriod(Period.TypePeriod.LAST_YEAR)
        list = createStatisticsGroupedByCategory(period)
        when:
        def message = service.buildMessage(list)
        then:
        message.equals(expectedMessage)
    }

    //*************************Category**********************************************//
    void "para una categoría de eventos en los próximos dos meses genera el mensaje"() {
        given:
        def expectedMessage = "En Castilla y León durante los próximos dos meses se celebrarán un total de 6 Conferencias y Cursos destacando Palencia con 3 "
        def list = []
        def period = createPeriod(Period.TypePeriod.TWO_NEXT_MONTHS)
        list = createStatisticsGroupedByLocality(period)
        when:
        def message = service.buildMessage(list)
        then:
        message.equals(expectedMessage)
    }

    void "para una categoría de eventos en el mes actual genera el mensaje"() {
        given:
        def expectedMessage = "En Castilla y León durante este mes se celebrarán un total de 6 Conferencias y Cursos destacando Palencia con 3 "
        def list = []
        def period = createPeriod(Period.TypePeriod.CURRENT_MONTH)
        list = createStatisticsGroupedByLocality(period)
        when:
        def message = service.buildMessage(list)
        then:
        message.equals(expectedMessage)
    }

    void "para una categoría de eventos en el año anterior genera el mensaje"() {
        given:
        def expectedMessage = "En Castilla y León en el último año se celebraron un total de 6 Conferencias y Cursos destacando Palencia con 3 "
        def list = []
        def period = createPeriod(Period.TypePeriod.LAST_YEAR)
        list = createStatisticsGroupedByLocality(period)
        when:
        def message = service.buildMessage(list)
        then:
        message.equals(expectedMessage)
    }


//*************************************************************************************************//

    def createStatistics(category, locality, period,number, groupedBy){
        return new StatisticPojo(category:category, locality: locality, period: period,number:number, groupedBy: groupedBy)
    }
    def createPeriod(type){
        return new Period(type)
    }

    def ArrayList createStatisticsGroupedByLocality(period) {
        def list=[]
        list<<createStatistics(Activity.Category.CONFERENCES, Activity.Locality.ZAMORA, period,1,  GroupedBy.LOCALITY)
        list<<createStatistics(Activity.Category.CONFERENCES, Activity.Locality.PALENCIA, period,3,  GroupedBy.LOCALITY)
        list<<createStatistics(Activity.Category.CONFERENCES, Activity.Locality.BURGOS, period,2,  GroupedBy.LOCALITY)
        return list
    }

    def ArrayList createStatisticsGroupedByCategory(period) {
        def list=[]
        list<<createStatistics(Activity.Category.BOOKS, Activity.Locality.PALENCIA, period,1,GroupedBy.CATEGORY)
        list<<createStatistics(Activity.Category.CONFERENCES, Activity.Locality.PALENCIA, period,3,  GroupedBy.CATEGORY)
        list<<createStatistics(Activity.Category.SHOWS, Activity.Locality.PALENCIA, period,2,  GroupedBy.CATEGORY)
        return list
    }
}

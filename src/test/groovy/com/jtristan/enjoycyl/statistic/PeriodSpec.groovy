package com.jtristan.enjoycyl.statistic

import spock.lang.Specification

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PeriodSpec extends Specification {

    def formatter = DateTimeFormatter.ofPattern("MMyyyy")

    def "devuelve el periodo del mes en curso"(){
        when:
        Period period = new Period(Period.TypePeriod.CURRENT_MONTH)
        then:
        period.startPeriod==  LocalDate.now().format(formatter)
    }

    def "devuelve un rango de periodos de un año"(){
        when:
        Period period = new Period(Period.TypePeriod.LAST_YEAR)
        then:
        period.startPeriod ==LocalDate.now().minusMonths(12).format(formatter)
        period.endPeriod ==  LocalDate.now().format(formatter)
    }

    def "devuelve un rango para los dos siguiente meses"(){
        when:
        Period period = new Period(Period.TypePeriod.TWO_NEXT_MONTHS)
        then:
        period.startPeriod == LocalDate.now().format(formatter)
        period.endPeriod == LocalDate.now().plusMonths(2).format(formatter)
    }

    def "devuelve un periodo personalizado"(){
        when: "solicitan un mes"
        Period period = new Period(Period.TypePeriod.CUSTOM,"032022")
        then:
        period.startPeriod == "032022"
        period.endPeriod == "032022"

        when: "solicitan un rango de meses"
        period = new Period(Period.TypePeriod.CUSTOM,"052022", "072022")
        then:
        period.startPeriod == "052022"
        period.endPeriod == "072022"

    }
}

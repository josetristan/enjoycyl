package com.jtristan.enjoycyl.jcylAPI

import com.jtristan.enjoycyl.Activity
import com.jtristan.enjoycyl.CultureCalendarClientService
import com.jtristan.enjoycyl.client.helper.DateRange
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

import java.nio.charset.StandardCharsets
import java.time.LocalDate

class JcylApiServiceSpec extends Specification implements ServiceUnitTest<JcylApiService>{

    def setup() {
    }

    def cleanup() {
    }

    void "dado un filtro con un rango de fechas construye el query param"() {

        given:
        DateRange dateRange = new DateRange(LocalDate.of(2022,4,13),LocalDate.of(2022,5,10))
        when:
        def expected = "fecha_inicio IN [date'20220413'..date'20220510']"
        def result =decode(service.buildQueryParamBetweenDates(CultureCalendarClientService.Filter.START_DATE_BETWEEN.name,dateRange,""))
        then:
        result==expected
    }

    void "dato un filtro con una fecha construye el query param de igualdad"(){
        expect:
        def  startDate =  LocalDate.of(2022,4,13)
        def expected = "fecha_inicio=date'20220413'"
        def result =decode(service.buildQueryParamDate(CultureCalendarClientService.Filter.START_DATE.name,startDate,""))
        result==expected
    }

    void "dato un filtro con una cadena construye el query param de igualdad"(){
        expect:
        def expected = "search(nombre_provincia,\"Palencia\")"
        def result =decode(service.buildQueryParamFacetString(CultureCalendarClientService.Filter.LOCALITY.name, Activity.Locality.PALENCIA.name,""))
        result==expected
    }

    void "dato un filtro por id de evento construye eun query search"(){
        expect:
        def expected = "\"12345678\""
        def result =decode(service.buildQueryParamFilterSearchQuery("12345678",""))
        result==expected
    }

    private String decode(text){
        return URLDecoder.decode(text, StandardCharsets.UTF_8.name())
    }
}

package com.jtristan.enjoycyl.network

import com.jtristan.enjoycyl.CultureCalendarClientService
import com.jtristan.enjoycyl.client.helper.DateRange
import com.jtristan.enjoycyl.jcylAPI.JcylApiService
import grails.testing.services.ServiceUnitTest
import io.micronaut.http.uri.UriBuilder
import spock.lang.Specification

import java.time.LocalDate
//TODO: make an integration test because I want to check the complete creation of the URI
class UriServiceSpec extends Specification implements ServiceUnitTest<UriService>{

    def setup() {
    }

    def cleanup() {
    }

    /*def "dependiendo del tipo de filtro construye los query params"(){
        JcylApiService jcylApiService = Stub()
        jcylApiService.buildQueryParamBetweenDates(_,_)>>"fecha_inicio+IN+%5Bdate%2720220413%27..date%2720220510%27%5D"
        jcylApiService.buildQueryParamDate(_,_)>>"fecha_inicio%3D20220413"
        jcylApiService.buildQueryParamString(_,_)>>"nombre_provincia%3DPalencia"
        service.jcylApiService = jcylApiService

        def expected = "/api/v2/catalog/datasets/eventos-de-la-agenda-cultural-categorizados-y-geolocalizados/records"
        given:
        UriBuilder uriBuilder = service.buildURIPath("eventos-de-la-agenda-cultural-categorizados-y-geolocalizados")
        DateRange dateRange = new DateRange(LocalDate.of(2022,03,14), LocalDate.of(2022,04,14))
        def filters = new HashMap()
        filters.put(CultureCalendarClientService.Filter.START_DATE_BETWEEN,dateRange)
        when:
        expected+="?fecha_inicio+IN+%5Bdate%2720220413%27..date%2720220510%27%5D"
        def uri = service.buildQueryServicesSpecialConditions(uriBuilder.build(),filters)
        then:
        uri.toString()==expected

        when:'añadimos una fecha con condición de igualdad'
        uriBuilder = service.buildURIPath("eventos-de-la-agenda-cultural-categorizados-y-geolocalizados")
        filters.put(CultureCalendarClientService.Filter.START_DATE,LocalDate.of(2022,04,13))
        expected+="?fecha_inicio+IN+%5Bdate%2720220413%27..date%2720220510%27%5D"+"fecha_inicio%3D20220413"
        uri = service.buildQueryServicesSpecialConditions(uriBuilder.build(),filters)
        then:
        uri.toString()==expected


    }*/

}

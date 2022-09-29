package com.jtristan.enjoycyl

import com.jtristan.enjoycyl.client.helper.DateRange
import com.jtristan.enjoycyl.network.UriService
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import io.micronaut.http.uri.UriBuilder
import spock.lang.Specification

import java.time.LocalDate

@Integration
@Rollback
class UriServiceIntSpec extends Specification{

    UriService uriService

    def setup() {
    }

    def cleanup() {
    }

    def "dependiendo del tipo de filtro construye los query params"(){
        def expected = "/api/v2/catalog/datasets/eventos-de-la-agenda-cultural-categorizados-y-geolocalizados/records?where="
        given:
        UriBuilder uriBuilder = uriService.buildURIPath("eventos-de-la-agenda-cultural-categorizados-y-geolocalizados")
        DateRange dateRange = new DateRange(LocalDate.of(2022,03,14), LocalDate.of(2022,04,10))
        def filters = new LinkedHashMap()
        filters.put(CultureCalendarClientService.Filter.START_DATE_BETWEEN,dateRange)
        when:
        expected+="fecha_inicio+IN+%5Bdate%2720220314%27..date%2720220410%27%5D"
        def uri = uriService.buildQueryParam(uriBuilder.build(),filters)
        then:
        uri.toString()==expected

        when:'añadimos una fecha con condición de igualdad'
        filters.put(CultureCalendarClientService.Filter.START_DATE,LocalDate.of(2022,04,13))
        expected+="+and+fecha_inicio%3D20220413"
        uri = uriService.buildQueryParam(uriBuilder.build(),filters)
        then:
        uri.toString()==expected

        when:'añadimos filtramos también por localidad'
        filters.put(CultureCalendarClientService.Filter.LOCALITY, Activity.Locality.PALENCIA.name)
        expected+='+and+search%28nombre_provincia%2C%22Palencia%22%29'
        uri = uriService.buildQueryParam(uriBuilder.build(),filters)
        then:
        uri.toString()==expected

        when: 'añadimos el id de un evento'
        filters.put(CultureCalendarClientService.Filter.EVENT_ID, "12345678")
        expected+='+and+"12345678"'
        uri = uriService.buildQueryParam(uriBuilder.build(),filters)
        then:
        uri.toString()==expected

    }

}

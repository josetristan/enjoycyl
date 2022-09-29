package com.jtristan.enjoycyl

import com.jtristan.enjoycyl.client.helper.DateRange
import com.jtristan.enjoycyl.helpers.Check
import com.jtristan.enjoycyl.network.UriService
import com.jtristan.enjoycyl.pojo.ActivityPojo
import com.jtristan.enjoycyl.pojo.Record
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import io.micronaut.http.uri.UriBuilder
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification

import com.jtristan.enjoycyl.Activity.Category
import com.jtristan.enjoycyl.Activity.Locality

import java.nio.charset.StandardCharsets
import java.time.LocalDate

@Integration
@Rollback
class CultureCalendarClientServiceSpec extends Specification{

    @Shared
    CultureCalendarClientService cultureCalendarClientService


    int maximunRows = 10

    void "la llamada a la API sin filtros devuelve datos 10 registros"(){
        given:
            def filters = [:]
        when:
            ActivityPojo activity = cultureCalendarClientService.GET(filters, maximunRows)
        then: 'El API devuelve por defecto un máximo de 10 registros'
            activity.records.size()==10
            !activity.records.get(0).field.description.isEmpty()
    }

    void "llamar a la API filtrando por CATEGORIA devuelve registros"(){
        given:
        def filters = [:]
        def category = Category.SHOWS.name
        filters.put(CultureCalendarClientService.Filter.CATEGORY, category)

        when:'filtra por categoría: Espectáculos'
        ActivityPojo activity = cultureCalendarClientService.GET(filters, maximunRows)

        then: 'todos los registros tienen la categoría Espectáculos'
        containsRecords(activity)==true
        allRecordsHasCategoryFiltered(activity.records, category)==true
    }

    void "llamar a la API filtrando por PROVINCIA devuelve registros"(){
        given:
        def filters = [:]
        def locality = Locality.VALLADOLID.name
        filters.put(CultureCalendarClientService.Filter.LOCALITY, locality)

        when:'filtra por provincia: Valladolid'
        ActivityPojo activity = cultureCalendarClientService.GET(filters, maximunRows)

        then: 'todos los registros tienen la provincia Valladolid'
        containsRecords(activity)==true
        Check.allRecordsHasLocalityFiltered(activity.records, locality)==true
    }

    void "aplica el encode a la hora de pasar parámetros"(){
        given:
        def filters = [:]
        def locality = Locality.LEON.name
        filters.put(CultureCalendarClientService.Filter.LOCALITY, locality)

        when:'tiene que codificar el acento de León'
        ActivityPojo activity = cultureCalendarClientService.GET(filters, maximunRows)

        then:
        containsRecords(activity)==true
        Check.allRecordsHasLocalityFiltered(activity.records, locality)==true
    }

    void "llamar a la API filtrando por FECHA devuelve registros"(){
        given: "fecha en formato: yyyy/MM/dd"
        def filters = [:]
        def date = LocalDate.now().plusDays(5)
        filters.put(CultureCalendarClientService.Filter.START_DATE, date)

        when:
        ActivityPojo activity = cultureCalendarClientService.GET(filters, maximunRows)

        then: 'todos los registros tienen la provincia Valladolid'
        containsRecords(activity)==true
        allRecordsHasEqualOrBiggerDate(activity.records, date)==true
    }


    /*void "llama tantas veces por fecha según el rango que se ha seleccionado"(){
        given: "fecha en formato: yyyy/MM/dd"
        def filters = [:]
        def startDate = LocalDate.now().plusDays(5)
        def endDate = startDate.plusDays(3)
        DateRange dateRange = new DateRange(startDate, endDate)
        filters.put(CultureCalendarClientService.Filter.START_DATE_BETWEEN, dateRange)

        when:'tiene que llamar tres veces a la API'
        ActivityPojo activity = cultureCalendarClientService.GET(filters, maximunRows)

        then: ''
        containsRecords(activity)==true
        allRecordsHasDateInRange(activity.records, startDate, endDate)==true

        when:
        int records = getRecordsDaily(dateRange.getRangeOfDates())


        then:'comprueba que la suma de los registros diarios sea igual al del rango'
        activity.records.size()==records
    }*/
    @Ignore("El API está rota y no filtra bien por fechas")
    void "llama a la api filtrando por un rango de fechas"(){
        given: "fecha en formato: yyyy/MM/dd"
        def filters = [:]
        def startDate = LocalDate.now().plusDays(5)
        def endDate = startDate.plusDays(3)
        DateRange dateRange = new DateRange(startDate, endDate)
        filters.put(CultureCalendarClientService.Filter.START_DATE_BETWEEN, dateRange)

        when:'tiene que llamar tres veces a la API'
        ActivityPojo activity = cultureCalendarClientService.GET(filters, maximunRows)

        then: ''
        containsRecords(activity)==true
        allRecordsHasDateInRange(activity.records, startDate, endDate)==true

        when:
        int records = getRecordsDaily(dateRange.getRangeOfDates())


        then:'comprueba que la suma de los registros diarios sea igual al del rango'
        activity.records.size()==records
    }


    void "llamar a la API filtrando por el id del evento devuelve un registro"(){
        given:'buscamos el evento id de cualquier actividad'
        def filters = [:]
        def filterEventId = [:]
        def locality = Locality.LEON.name
        filters.put(CultureCalendarClientService.Filter.LOCALITY, locality)
        ActivityPojo activity = cultureCalendarClientService.GET(filters, maximunRows)
        def eventId = activity.records[0].field.eventId

        when:
        filters.put(CultureCalendarClientService.Filter.EVENT_ID, eventId)
        activity = cultureCalendarClientService.GET(filters, maximunRows)

        then:'Sólo devuelve un registro que tiene que coincidir con el eventId'
        activity.records.size()==1
        activity.records[0].field.eventId==eventId
    }

    void "llamar a la API limitando el número de elementos"(){
        given:'comprobamos que por sin filtrar se reciban más de 1 elemento'
        def filters = [:]
        def filterEventId = [:]
        def locality = Locality.LEON.name
        filters.put(CultureCalendarClientService.Filter.LOCALITY, locality)
        when:
        int rowsDefault = cultureCalendarClientService.GET(filters, maximunRows).records.size()
        int rows = cultureCalendarClientService.GET(filters,1).records.size()

        then:
        rowsDefault>=1
        rows==1
    }

    def boolean allRecordsHasCategoryFiltered(records, category) {
        return records.every{Record record-> record.field.category.equals(category)}
    }



    def boolean allRecordsHasEqualOrBiggerDate(List<Record> records, LocalDate localDate) {
        return records.every{Record record-> record.field.startDate.compareTo(localDate)>=0}
    }

    def boolean containsRecords(ActivityPojo activity) {
        return activity.records.size()>0?true:false
    }

    def boolean allRecordsHasDateInRange(List<Record> records, startDate, endDate) {

        boolean biggerThanStartDate = records.every{Record record-> record.field.startDate.compareTo(startDate)>=0}
        boolean lessThanStartDate = records.every{Record record-> record.field.startDate.compareTo(endDate)<=0}

        return biggerThanStartDate && lessThanStartDate ? true : false
    }

    def int getRecordsDaily(List<LocalDate> ranges) {
        int records = 0
        def filter = [:]
        ranges.each {it->
            //Map<CultureCalendarClientService.Filter, String> filter = HashMap.of(CultureCalendarClientService.Filter.START_DATE, it)
            filter.clear()
            filter.put(CultureCalendarClientService.Filter.START_DATE, it)

            records += cultureCalendarClientService.GET(filter,maximunRows).records.size()
        }
        return records
    }


    def "test"(){
        given:

            UriBuilder uriBuilder = UriBuilder.of('/api/records/1.0/search')
            uriBuilder.queryParam("dataset", "eventos-de-la-agenda-cultural-categorizados-y-geolocalizados")
            uriBuilder.queryParam("facet","localidad")
            uriBuilder.queryParam("refine.localidad", "Palencia en")

            def uri = uriBuilder.build()

        when:
            def value = uri.toString()
            def date = "fecha>=2022-15-27 H24"


            try {
                date = URLEncoder.encode(date, StandardCharsets.UTF_8.name());
            } catch (UnsupportedEncodingException e) {
                throw new IllegalStateException("No available charset: " + e.getMessage());
            }

            value+="&"+date
            URI uri1 = URI.create(value)

            ExpandoUriBuilder uriBuilder1 = ExpandoUriBuilder.of('/api/records/1.0/search')
            uriBuilder1.queryParam("dataset", "eventos-de-la-agenda-cultural-categorizados-y-geolocalizados")
            var a = uriBuilder1.build()

        then:
            1==1

    }
}

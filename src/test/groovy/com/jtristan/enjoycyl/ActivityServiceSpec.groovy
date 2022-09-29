package com.jtristan.enjoycyl

import com.jtristan.enjoycyl.client.helper.DateRange
import com.jtristan.enjoycyl.command.ActivityCommand
import com.jtristan.enjoycyl.pojo.ActivityPojo
import com.jtristan.enjoycyl.pojo.Field
import com.jtristan.enjoycyl.pojo.Record
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

import java.time.LocalDate

class ActivityServiceSpec extends Specification implements ServiceUnitTest<ActivityService>{

    private final static LocalDate PRESENT_DATE = LocalDate.now()
    private final static LocalDate FUTURE_DATE = LocalDate.now().plusMonths(1)
    private final static LocalDate PAST_DATE = LocalDate.now().minusMonths(1)

    def "construye el filtro por categoria al API"(){
        given:
        ActivityCommand command = new ActivityCommand()
        command.category = Activity.Category.EXHIBITIONS
        when:
        def filter = service.buildFilter(command)
        then:
        filter.get(CultureCalendarClientService.Filter.CATEGORY)==Activity.Category.EXHIBITIONS.name
    }

    def "construye el filtro por provincia al API"(){
        given:
        ActivityCommand command = new ActivityCommand()
        command.locality = Activity.Locality.PALENCIA
        when:
        def filter = service.buildFilter(command)
        then:
        filter.get(CultureCalendarClientService.Filter.LOCALITY)==Activity.Locality.PALENCIA.name
    }

    def "construye el filtro por fecha con el formato yyyy/MM/dd para la API"(){
        given:
        ActivityCommand command = new ActivityCommand()
        command.startDate = LocalDate.parse("2022-02-23")
        when:
        def filter = service.buildFilter(command)
        then:
        filter.get(CultureCalendarClientService.Filter.START_DATE).toString()=="2022-02-23"
    }

    def "construye el filtro por rango de fecha con el formato yyyy/MM/dd para la API"(){
        given:
        ActivityCommand command = new ActivityCommand()
        command.startDate = LocalDate.parse("2022-02-23")
        command.endDate = LocalDate.parse("2022-02-24")
        when:
        def filter = service.buildFilter(command)
        then:
        filter.get(CultureCalendarClientService.Filter.START_DATE_BETWEEN) instanceof DateRange
        filter.get(CultureCalendarClientService.Filter.START_DATE_BETWEEN).startDate.toString()=="2022-02-23"
        filter.get(CultureCalendarClientService.Filter.START_DATE_BETWEEN).endDate.toString()=="2022-02-24"

    }

    def "se la fecha de inicio está en el pasado y no tiene fecha final se elimina"(){
        expect:
            service.removeActivitiesOutOfDateRange(mockResponse(PAST_DATE, null),new ActivityCommand()).records.size()==0
        and:'aplicando filtro en la fecha'
        service.removeActivitiesOutOfDateRange(mockResponse(PAST_DATE, null),new ActivityCommand(startDate: PRESENT_DATE)).records.size()==0
    }

    def "si la fecha de inicio y la de final están en el pasado se elimina"(){
        expect:
        service.removeActivitiesOutOfDateRange(mockResponse(PAST_DATE, PAST_DATE),new ActivityCommand()).records.size()==0
        and:'aplicando filtro en la fecha'
        service.removeActivitiesOutOfDateRange(mockResponse(PAST_DATE, PAST_DATE),new ActivityCommand(startDate: PRESENT_DATE)).records.size()==0

    }

    def "si la fecha de inicio está en el pasado y la final en el futuro no se elimina"(){
        expect:
        service.removeActivitiesOutOfDateRange(mockResponse(PAST_DATE, FUTURE_DATE),new ActivityCommand()).records.size()==1
        and:'aplicando filtro en la fecha'
        service.removeActivitiesOutOfDateRange(mockResponse(PAST_DATE, FUTURE_DATE),new ActivityCommand(startDate: PRESENT_DATE)).records.size()==1

    }

    def "si la fecha de inicio está en el futuro y no hay fecha final no se elimina"(){
        expect:
        service.removeActivitiesOutOfDateRange(mockResponse(FUTURE_DATE, null),new ActivityCommand()).records.size()==1
        and:'aplicando filtro en la fecha'
        service.removeActivitiesOutOfDateRange(mockResponse(FUTURE_DATE, null),new ActivityCommand(startDate: PRESENT_DATE)).records.size()==1

    }
    def "si la fecha de inicio y la de final están en el futuro no se elimina"() {
        expect:
        service.removeActivitiesOutOfDateRange(mockResponse(FUTURE_DATE, FUTURE_DATE),new ActivityCommand()).records.size()==1
        and:'aplicando filtro en la fecha'
        service.removeActivitiesOutOfDateRange(mockResponse(FUTURE_DATE, FUTURE_DATE),new ActivityCommand(startDate: PRESENT_DATE)).records.size()==1
        and:"Si la fecha de inicio y la final son el día actual"
        service.removeActivitiesOutOfDateRange(mockResponse(PRESENT_DATE, PRESENT_DATE),new ActivityCommand()).records.size()==1

    }

    def mockResponse(startDate, endDate) {
        def records = []
        def field = new Field(startDate:startDate, endDate:endDate)
        records << new Record(field:field)
        return new ActivityPojo(records:records)
    }
}




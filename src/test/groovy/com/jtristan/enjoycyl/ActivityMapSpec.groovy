package com.jtristan.enjoycyl

import com.jtristan.enjoycyl.convert.ActivityMapService
import com.jtristan.enjoycyl.pojo.ActivityPojo
import com.jtristan.enjoycyl.pojo.Field
import com.jtristan.enjoycyl.pojo.Record
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

import java.time.LocalDate

class ActivityMapSpec extends Specification implements ServiceUnitTest<ActivityMapService>{

    def "convierte el response del API al dominio"(){
        given:
        ActivityPojo pojo = buildPojoResponseFromAPI()

        when:
        List<Activity> activities = service.mapToActivities(pojo)

        then:
        with(activities.get(0)){
            celebrationPlace == "Biblioteca Pública de Ávila"
            region == "Ávila"
            category == Activity.Category.EXHIBITIONS
            address == "Plaza de la Catedral, 3"
            isLibraryEvent == true
            endDate == LocalDate.of(2022,3,31)
            urlImage == "https://datosabiertos.jcyl.es/web/jcyl/binarios/825/20/concuros%20fotografico%20cee%20agenda.jpg?blobheader=image%2Fjpeg&blobheadername1=Cache-Control&blobheadername2=Expires&blobheadername3=Site&blobheadervalue1=no-store%2Cno-cache%2Cmust-revalidate&blobheadervalue2=0&blobnocache=true"
            title == "Exposición: 'La belleza de lo cotidiano'"
            urlContent =="https://bibliotecas.jcyl.es/web/jcyl/BibliotecaAvila/es/Plantilla100Detalle/1284347540032/Evento/1285140679027/Comunicacion"
            startDate == LocalDate.of(2022,3,3)
            locality == Activity.Locality.AVILA
            description == "<h3>Desde el 3 hasta el 31 de marzo. Segunda exposición de fotografía "
        }


    }

    def ActivityPojo buildPojoResponseFromAPI(){
        ActivityPojo pojo = new ActivityPojo()
        def records = []
        def field = new Field()

        field.celebrationPlace = "Biblioteca Pública de Ávila"
        field.region = "Ávila"
        field.category = "Exposiciones"
        field.address = "Plaza de la Catedral, 3"
        field.isLibraryEvent = "SI"
        field.endDate = LocalDate.parse("2022-03-31")
        field.urlImage = "https://datosabiertos.jcyl.es/web/jcyl/binarios/825/20/concuros%20fotografico%20cee%20agenda.jpg?blobheader=image%2Fjpeg&blobheadername1=Cache-Control&blobheadername2=Expires&blobheadername3=Site&blobheadervalue1=no-store%2Cno-cache%2Cmust-revalidate&blobheadervalue2=0&blobnocache=true"
        field.title = "Exposición: 'La belleza de lo cotidiano'"
        field.urlContent ="https://bibliotecas.jcyl.es/web/jcyl/BibliotecaAvila/es/Plantilla100Detalle/1284347540032/Evento/1285140679027/Comunicacion"
        field.startDate = LocalDate.parse("2022-03-03")
        field.locality = "Ávila"
        field.description = "<h3>Desde el 3 hasta el 31 de marzo. Segunda exposición de fotografía "

        records.add(new Record(field:field))
        pojo.records = records

        return pojo
    }
}

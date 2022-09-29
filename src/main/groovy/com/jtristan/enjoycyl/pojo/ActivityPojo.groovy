package com.jtristan.enjoycyl.pojo

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.CompileStatic

import java.time.LocalDate

@CompileStatic
class ActivityPojo {



    @JsonProperty("total_count")
    String totalCount

    @JsonProperty("links")
    List<Link> links

    @JsonProperty("records")
    List<RecordFather> records

}
//Se utiliza para poder paginar. Siempre tendremos un rel: self que es la propia búsqueda, un first, last, previous y
//el next
class Link {
    //String format
    //String timezone
    String rel
    String href
}

class RecordFather{
    List<Link> links
    Record record
}

class Record{

    @JsonProperty("id")
    String dataSetId
    String timestamp
    String size

    /*@JsonProperty("datasetid")
    String dataSetId
    @JsonProperty("recordid")
    String recordId*/
    @JsonProperty("fields")
    Field field

}

class Field{
    @JsonProperty("id_evento")
    String eventId
    @JsonProperty("titulo")
    String title
    @JsonProperty("lugar_celebracion")
    String celebrationPlace
    @JsonProperty("nombre_localidad")
    String region
    @JsonProperty("categoria")
    String category
    @JsonProperty("calle")
    String address
    @JsonProperty("evento_biblioteca")
    String isLibraryEvent
    @JsonProperty("fecha_inicio")
    LocalDate startDate
    @JsonProperty("fecha_fin")
    LocalDate endDate
    @JsonProperty("imagen_evento")
    String urlImage
    @JsonProperty("nombre_provincia")
    String locality
    @JsonProperty("descripcion")
    String description
    @JsonProperty("enlace_contenido")
    String urlContent


}

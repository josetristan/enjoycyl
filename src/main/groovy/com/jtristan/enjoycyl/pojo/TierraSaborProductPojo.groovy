package com.jtristan.enjoycyl.pojo

import com.fasterxml.jackson.annotation.JsonProperty

class TierraSaborProductPojo {
    String nhits

    @JsonProperty("parameters")
    Link parameter

    @JsonProperty("records")
    List<TSProductRecord> records

}

class TSProductRecord{

    @JsonProperty("datasetid")
    String dataSetId
    @JsonProperty("recordid")
    String recordId
    @JsonProperty("fields")
    TSProductField field

}

class TSProductField{

    @JsonProperty("producto")
    String product
    @JsonProperty("idempresa")
    String idCompany
    @JsonProperty("seccion")
    String section
    @JsonProperty("figurascalidad")
    String qualityType
    @JsonProperty("marca")
    String brand
    @JsonProperty("variedad")
    String variety
    @JsonProperty("categoria")
    String category

}


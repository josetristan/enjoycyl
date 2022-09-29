package com.jtristan.enjoycyl.pojo

import com.fasterxml.jackson.annotation.JsonProperty

class TierraSaborCompanyPojo {
    String nhits

    @JsonProperty("parameters")
    Link parameter

    @JsonProperty("records")
    List<TSCompanyRecord> records

}

class TSCompanyRecord{

    @JsonProperty("datasetid")
    String dataSetId
    @JsonProperty("recordid")
    String recordId
    @JsonProperty("fields")
    TSCompanyField field

    List<TierraSaborProductPojo> products

    public addProducts(List<TierraSaborProductPojo> products){

        products.each {TierraSaborProductPojo it->
            Iterator<TSProductRecord> ite = it.records.iterator()
            while(ite.hasNext()){
                if (ite.field.idCompany!=this.field.idCompany){
                    ite.remove()
                }
            }
        }

        Iterator<TierraSaborProductPojo> ite = products.iterator()
        while(ite.hasNext()){
            ite.records.each{

            }
        }
        this.field.idCompany

    }

}

class TSCompanyField{

    @JsonProperty("idempresa")
    String idCompany
    @JsonProperty("provincia")
    String locality
    @JsonProperty("web")
    String web
    @JsonProperty("nombre_comercial")
    String name
    @JsonProperty("localidad")
    String region
    @JsonProperty("direccion")
    String address

    /**
     * It doesn't use the standard code so it contains accents
     */
    public setLocality(String locality){
        if (locality.equals("LEON"))
            this.locality="LEÓN"
        else if (locality.equals("AVILA"))
            this.locality="ÁVILA"
        else
            this.locality=locality


    }

}


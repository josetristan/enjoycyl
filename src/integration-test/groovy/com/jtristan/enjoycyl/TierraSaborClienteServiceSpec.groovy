package com.jtristan.enjoycyl

import com.jtristan.enjoycyl.helpers.Check
import com.jtristan.enjoycyl.pojo.ActivityPojo
import com.jtristan.enjoycyl.pojo.Record
import com.jtristan.enjoycyl.pojo.TierraSaborCompanyPojo
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import spock.lang.Specification
import com.jtristan.enjoycyl.TierraSaborClientService.TypeTS

@Integration
@Rollback
class TierraSaborClienteServiceSpec extends Specification{

    TierraSaborClientService tierraSaborClientService

    int maximunRows = 10

    void "llamar a la API filtrando por provincia devuelve registros"(){
        given:
        def filters = [:]
        filters.put(TierraSaborClientService.Filter.LOCALITY, Activity.Locality.PALENCIA)
        when:
        def companies = tierraSaborClientService.GET(TypeTS.COMPANY,filters,maximunRows)
        then: 'El API devuelve los registros indicados'
        companies.records.size()==10
        Check.allRecordsHasLocalityFiltered(companies.records,Activity.Locality.PALENCIA.name.toUpperCase())==true
    }

    void "llamar a la API filtrando por localidad devuelve registros"(){
        given:
        String region = "AGUILAR DE CAMPOO"
        def filters = [:]
        filters.put(TierraSaborClientService.Filter.REGION, region)
        when:
        def companies = tierraSaborClientService.GET(TypeTS.COMPANY,filters,maximunRows)
        then: 'El API devuelve los registros indicados'
        companies.records.size()>=1
        allRecordsHasRegionFiltered(companies.records ,region)==true

    }

    void "llamar a la API de producto filtrando id Empresa devuelve sus productos"(){
        given:
        String idCompany = "1469"
        def filters = [:]
        filters.put(TierraSaborClientService.Filter.COMPANY_ID, idCompany)
        when:
        def products = tierraSaborClientService.GET(TypeTS.PRODUCT,filters,maximunRows)
        then: 'El API devuelve sólo productos de la empresa filtrada'
        products.records.size()>=1
        allRecordsHasCompanyFiltered(products.records ,idCompany)==true

    }

    def  allRecordsHasRegionFiltered(records, String region) {
        return records.every{record-> record.field.region.equals(region)}
    }
    //TODO: refactorizar todos los filtros
    def allRecordsHasCompanyFiltered(records, String idCompany){
        return records.every{record-> record.field.idCompany.equals(idCompany)}
    }

}

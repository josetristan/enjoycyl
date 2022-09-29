package com.jtristan

import com.jtristan.enjoycyl.Activity
import com.jtristan.enjoycyl.TierraSaborClientService
import com.jtristan.enjoycyl.TierraSaborCompany
import com.jtristan.enjoycyl.command.ActivityCommand
import com.jtristan.enjoycyl.convert.TierraSaborMapService
import com.jtristan.enjoycyl.helpers.Check
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import grails.testing.services.ServiceUnitTest
import org.junit.Ignore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class TierraSaborServiceSpec extends Specification{

    TierraSaborService tierraSaborService

    def setup() {
    }

    def cleanup() {
    }

    void "devuelve el número de productos para completar las propuestas filtrando por provincia"() {
        given:
        ActivityCommand command = new ActivityCommand(locality: Activity.Locality.PALENCIA)
        when:
             def companies = tierraSaborService.buildRamdon(command, 3)
        then:
            companies.size()==3
            allRecordsHasLocalityFiltered(companies, Activity.Locality.PALENCIA)==true
            everyCompanyContainsOneProduct(companies)==true
    }
    @Ignore
    void "devuelve el número de productos para completar las propuestas si no se especifica provincia"() {
        given:
        ActivityCommand command = new ActivityCommand()
        when:
        def companies = tierraSaborService.buildRamdon(command, 3)
        then:
        companies.size()==3
        everyCompanyContainsOneProduct(companies)==true
    }


    def allRecordsHasLocalityFiltered(companies, locality){
        companies.every{company->company.locality==locality}
    }
    def everyCompanyContainsOneProduct(companies){
        for(TierraSaborCompany company:companies){
            if (company.products.size()!=1){
                return false
            }
        }
        return true
    }

}

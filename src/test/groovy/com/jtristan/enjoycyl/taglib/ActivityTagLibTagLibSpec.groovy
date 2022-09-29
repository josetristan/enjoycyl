package com.jtristan.enjoycyl.taglib

import com.jtristan.enjoycyl.Activity
import com.jtristan.enjoycyl.ActivityTabLibTagLib
import com.jtristan.enjoycyl.Statistic
import com.jtristan.enjoycyl.TierraSaborCompany
import com.jtristan.enjoycyl.pojo.Proposal
import com.jtristan.enjoycyl.pojo.StatisticPojo
import com.jtristan.enjoycyl.ui.GridService
import grails.testing.web.taglib.TagLibUnitTest
import spock.lang.IgnoreRest
import spock.lang.Specification

class ActivityTagLibTagLibSpec extends Specification implements TagLibUnitTest<ActivityTabLibTagLib> {

    def setup() {
        GridService gridServiceStub = Stub()
        gridServiceStub.generateGrid(_)>>new String[4][3];
        tagLib.gridService = gridServiceStub
    }

    def cleanup() {
    }

    def "si el API no devuelve eventos se muestra un mensaje de error"(){
        given:'una propuesta con tres actividades'
        def activities= []
        def statistics = createStatisticsPojo(4)
        def companies = createCompanies(8)

        def proposal = new Proposal(activities:activities, staticPojos: statistics, companies: companies)
        when:
        def expected = "<div class=\"alert alert-danger\">No existen eventos que cumplan los criterios de búsqueda.</div>"
        def output = applyTemplate('<cyl:card proposals="${proposals}"/>',[proposals:proposal])
        then:
        output.contains(expected)


        when: "Si contiene actividades no se muestra el mensaje de error"
        proposal.activities = createActivities(3)
        output = applyTemplate('<cyl:card proposals="${proposals}"/>',[proposals:proposal])
        then:
        !output.contains(expected)

    }

    //TODO: refactorizar con GridServiceSpec
    def createActivities(int number){
        def activities=[]
        0.step(number,1){
            activities << new Activity(title:"title-${it}", category: Activity.Category.BOOKS, locality:Activity.Locality.PALENCIA)
        }
        return activities
    }

    def createStatisticsPojo(int number){
        def statistics=[]
        0.step(number,1){
            statistics << new StatisticPojo(category: Activity.Category.BOOKS, locality:Activity.Locality.PALENCIA, number: it)
        }
        return statistics
    }

    def createCompanies(int number) {
        def companies=[]
        0.step(number,1){
            companies << new TierraSaborCompany(idCompany:"${it}")
        }
        return companies
    }



}

package com.jtristan.enjoycyl.ui

import com.jtristan.enjoycyl.Activity
import com.jtristan.enjoycyl.ActivityTabLibTagLib
import com.jtristan.enjoycyl.TierraSaborCompany
import com.jtristan.enjoycyl.pojo.Proposal
import com.jtristan.enjoycyl.pojo.StatisticPojo
import com.jtristan.enjoycyl.statistic.Period
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class GridServiceSpec extends Specification implements ServiceUnitTest<GridService>{

    def setup() {
    }

    def cleanup() {
    }

    
    void "si existen al menos tres actividades ocupan la primera fila"(){
        given:'una propuesta con tres actividades'
        def activities= creteActivities(3)
        def statistics = []
        def companies = []

        def proposal = new Proposal(activities:activities, staticPojos: statistics, companies: companies)
        when:
        def grid = service.generateGrid(proposal)
        then:
        grid[0][0] instanceof Activity
        grid[0][1] instanceof Activity
        grid[0][2] instanceof Activity
    }

    void "si no existen tres actividades se completa el grid con el resto de propuestas"(){
        given:'una propuesta con dos actividades'
        def activities= creteActivities(2)
        def statistics =  createStatisticsPojo(1)
        def companies = []

        def proposal = new Proposal(activities:activities, staticPojos: statistics, companies: companies)

        when:
        def grid = service.generateGrid(proposal)
        then:
        grid[0][0] instanceof Activity
        grid[0][1] instanceof Activity
        grid[0][2] instanceof StatisticPojo
    }

    void "a partir de la segunda fila se muestran dos actividades en cualquier orden si existen"(){
        given:'una propuesta con dos actividades'
        def activities= creteActivities(4)
        def statistics = createStatisticsPojo(4)
        def companies = createCompanies(4)

        def proposal = new Proposal(activities:activities, staticPojos: statistics, companies: companies)

        when:
        def grid = service.generateGrid(proposal)
        then:
        allCardsContainsValue(grid)==true
        getNumberObjects(grid, GridService.ACTIVITY)==4
        getNumberObjects(grid, GridService.STATISTIC)==4
        getNumberObjects(grid, GridService.TIERRASABOR)==4
    }

    void "si no se reciben 12 propuestas se cargan las posibles"(){
        given: "menos de 12 propuestas"
        def activities= creteActivities(2)
        def statistics = createStatisticsPojo(1)
        def companies = []

        def proposal = new Proposal(activities:activities, staticPojos: statistics, companies: companies)

        when:
        def grid = service.generateGrid(proposal)
        then:
        grid[0][0] instanceof Activity
        grid[0][1] instanceof Activity
        grid[0][2] instanceof StatisticPojo
        grid[1][0] ==null
    }


    void "si el API no devuelve actividades se completa la primera fila con el resto de propuestas"(){
        given: ""
        def activities= []
        def statistics = createStatisticsPojo(4)
        def companies = createCompanies(8)

        def proposal = new Proposal(activities:activities, staticPojos: statistics, companies: companies)

        when:
        def grid = service.generateGrid(proposal)
        then: 'la primera fila contiene propuestas'
        grid[0][0] !=null
        grid[0][1] !=null
        grid[0][2] !=null
    }


    def creteActivities(int number){
        def activities=[]
        0.step(number,1){
            activities << new Activity(title:"title-${it}", category: Activity.Category.BOOKS, locality:Activity.Locality.PALENCIA)
        }
        return activities
    }

    def createStatisticsPojo(int number){
        def statistics=[]
        def period = new Period(Period.TypePeriod.CURRENT_MONTH, "042020")
        0.step(number,1){
            statistics << new StatisticPojo(category: Activity.Category.BOOKS, locality:Activity.Locality.PALENCIA, number: it, period:period)
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

    def boolean allCardsContainsValue(Object[][] grid) {
        for(int row=0;row<GridService.GRID_ROWS;row++){
            for(int column=0;column<GridService.GRID_COLUMNS;column++){
                if (grid[row][column]==null)return false
            }
        }
        return true

    }

    def int getNumberObjects(Object[][] grid, int type) {
        int number = 0
        def instance
        if (type==GridService.ACTIVITY){
            instance = Activity.class
        }else if (type==GridService.STATISTIC){
            instance = StatisticPojo.class
        }else if(type==GridService.TIERRASABOR) {
            instance = TierraSaborCompany.class
        }

        for(int row=0;row<ActivityTabLibTagLib.GRID_ROWS;row++){
            for(int column=0; column< ActivityTabLibTagLib.GRID_COLUMNS; column++){
                if ((grid[row][column]).class==instance) number++
            }
        }
        return number
    }

}

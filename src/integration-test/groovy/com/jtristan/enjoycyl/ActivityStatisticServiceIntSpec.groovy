package com.jtristan.enjoycyl

import com.jtristan.enjoycyl.command.ActivityCommand
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

import java.time.LocalDate

@Integration
@Rollback
class ActivityStatisticServiceIntSpec extends Specification{

    def categoryBooks = Activity.Category.BOOKS
    def localityPalencia = Activity.Locality.PALENCIA

    @Autowired
    ActivityStatisticService service

    void setup(){

    }

    void "el número total de estadísticas es una tercera parte de los elementos pendientes: categoria"(){
        given:
        createStatistics()
        def command = new ActivityCommand(category: categoryBooks)
        int numberActivitiesLoaded = 6
        when:
        def list = service.buildRamdonStatistics(command, numberActivitiesLoaded)
        then:'Al menos una estadística está relacionado con el filtro: categoría Book'
        list.size()==2
        atLeastOneHasCategoryBook(list)==true
    }

    void "el número total de estadísticas es una tercera parte de los elementos pendientes: provincia"(){
        given:
        createStatistics()
        def command = new ActivityCommand(locality: localityPalencia)
        int numberActivitiesLoaded = 6
        when:
        def list = service.buildRamdonStatistics(command, numberActivitiesLoaded)
        then:'Al menos una estadística está relacionado con el filtro: provincia Palencia'
        list.size()==2
        atLeastOneHasLocalityPalencia(list)==true
    }

    def atLeastOneHasCategoryBook(list){
        return list.any(it->it.category==categoryBooks)
    }

    def atLeastOneHasLocalityPalencia(list){
        return list.any(it->it.locality==localityPalencia)
    }

    def void createStatistics() {
        new Statistic(category:categoryBooks, locality: Activity.Locality.PALENCIA,startDate: LocalDate.now()).save(flush:true)
        new Statistic(category:categoryBooks, locality: Activity.Locality.ZAMORA,startDate: LocalDate.now().plusMonths(1)).save(flush:true)
        new Statistic(category:categoryBooks, locality: Activity.Locality.PALENCIA,startDate: LocalDate.now().plusMonths(1)).save(flush:true)

        new Statistic(category:categoryBooks, locality: Activity.Locality.PALENCIA,startDate: LocalDate.now().minusMonths(3)).save(flush:true)
    }
}

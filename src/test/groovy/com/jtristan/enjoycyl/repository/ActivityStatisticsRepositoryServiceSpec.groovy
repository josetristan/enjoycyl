package com.jtristan.enjoycyl.repository

import com.jtristan.enjoycyl.Activity
import com.jtristan.enjoycyl.Statistic
import grails.testing.gorm.DomainUnitTest
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

import static com.jtristan.enjoycyl.Activity.*

class ActivityStatisticsRepositoryServiceSpec extends Specification implements ServiceUnitTest<ActivityStatisticsRepositoryService>,
                                                                        DomainUnitTest<Statistic>{

  /*  def setup() {
        saveStatistics()
    }

    def cleanup() {
    }

    void "obtiene número de eventos para una provincia por categorías"() {
        when:
        def statistics = service.getActivitiesByCategories(Locality.PALENCIA)
        then:
        getNumberActivities(Activity.Category.BOOKS)==2
        getNumberActivities(Activity.Category.CONFERENCES)==1
    }

    void "obtiene el número de eventos para una categoría por provincias"(){

    }

    void "obtiene el número de eventos por provincia y categoría"(){

    }

    void "obtiene el número de eventos por categoría y provincia"(){

    }

    def Object saveStatistics() {
        new Statistic(region:"Dueñas", period:"032022",locality: Locality.PALENCIA, category: Activity.Category.BOOKS,eventId:123456).save(flush:true)
        new Statistic(region:"Villamuriel", period:"032022",locality: Locality.PALENCIA, category: Activity.Category.BOOKS,eventId:123457).save(flush:true)
        new Statistic(region:"Dueñas", period:"052022",locality: Locality.PALENCIA, category: Activity.Category.CONFERENCES,eventId:123458).save(flush:true)
        new Statistic(region:"Laguna", period:"042022",locality: Locality.VALLADOLID, category: Activity.Category.CONFERENCES,eventId:123459).save(flush:true)

    }

    def int getNumberActivities(list, Activity.Category cate) {
        return list.find{category==cate}.number
    }

   */
}

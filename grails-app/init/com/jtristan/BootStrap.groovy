package com.jtristan

import com.jtristan.enjoycyl.Activity
import com.jtristan.enjoycyl.Statistic
import com.jtristan.enjoycyl.security.Role
import com.jtristan.enjoycyl.security.User
import com.jtristan.enjoycyl.security.UserRole
import grails.gorm.transactions.Transactional
import grails.util.Environment

import java.time.LocalDate

class BootStrap {

    def init = { servletContext ->

        if (Environment.current == Environment.DEVELOPMENT){
            addTestUser()
        }


        /*def categoryBooks = Activity.Category.BOOKS
        def categoryShows = Activity.Category.SHOWS


        new Statistic(eventId: "1", category:categoryBooks, locality: Activity.Locality.PALENCIA,startDate: LocalDate.now()).save()
        new Statistic(eventId: "2", category:categoryBooks, locality: Activity.Locality.ZAMORA,startDate: LocalDate.now().plusMonths(1)).save()
        new Statistic(eventId: "3", category:categoryBooks, locality: Activity.Locality.PALENCIA,startDate: LocalDate.now().plusMonths(1)).save()
        new Statistic(eventId: "4", category:categoryShows, locality: Activity.Locality.PALENCIA,startDate: LocalDate.now().plusMonths(2)).save()
        new Statistic(eventId: "5", category:categoryShows, locality: Activity.Locality.PALENCIA,startDate: LocalDate.now()).save()
        new Statistic(eventId: "6", category:categoryShows, locality: Activity.Locality.PALENCIA,startDate: LocalDate.now().minusMonths(1)).save()
        new Statistic(eventId: "7", category:categoryBooks, locality: Activity.Locality.PALENCIA,startDate: LocalDate.now().minusMonths(3)).save()

        new Statistic(eventId: "21", category:categoryBooks, locality: Activity.Locality.PALENCIA,startDate: LocalDate.now()).save()

        */

    }
    def destroy = {

        /*def sta = Statistic.list()
        sta.each{
            it.delete()
        }*/
    }

    @Transactional
    void addTestUser() {

        def adminRole = new Role(authority: 'ROLE_ADMIN').save()

        def testUser = new User(username: 'admin', password: 'password9874*').save()

        UserRole.create testUser, adminRole

        UserRole.withSession {
            it.flush()
            it.clear()
        }

    }
}

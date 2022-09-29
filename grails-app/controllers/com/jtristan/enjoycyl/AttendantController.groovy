package com.jtristan.enjoycyl

import com.jtristan.enjoycyl.command.ActivityCommand
import com.jtristan.enjoycyl.command.ReportCommand
import com.jtristan.enjoycyl.repository.ActivityStatisticsRepositoryService
import com.jtristan.enjoycyl.repository.AttendantRepositoryService
import com.jtristan.enjoycyl.security.User
import com.jtristan.enjoycyl.security.UserService
import com.jtristan.enjoycyl.util.FormatDateCommandService
import grails.plugin.springsecurity.annotation.Secured

import java.time.LocalDate

class AttendantController {

    ActivityStatisticsRepositoryService activityStatisticsRepositoryService
    AttendantRepositoryService attendantRepositoryService
    FormatDateCommandService formatDateCommandService
    UserService userService

    @Secured('ROLE_MANAGEMENT_USER')
    def index() {
        String region
        Activity.Locality locality
        LocalDate startDate = LocalDate.now()
        LocalDate endDate = LocalDate.now().plusMonths(3)

        if (isLoggedIn()) {
            String username = getPrincipal().username
            User user = userService.findByUsername(username)
            if (user.region) region = user.region
            if (user.locality) locality = user.locality
        }

        def command = new ReportCommand(startDate:startDate,endDate:endDate, region:region, locality:locality)

        def statistics = activityStatisticsRepositoryService.findStatistics(command)

        render view:"search", model:[ statistics:statistics, command:command]
    }

    @Secured('ROLE_MANAGEMENT_USER')
    def search() {
        def command = new ReportCommand()
        if (params?.locality) command.locality = params.locality
        if (params?.category) command.category = params.category
        if (params?.region) command.region = params.region
        if (params?.title) command.title = params.title
        if (params?.startDate){
            command.startDate = formatDateCommandService.formatToDate(params.startDate)
        }
        if (params?.endDate){
            command.endDate = formatDateCommandService.formatToDate(params.endDate)
        }

        def statistics = activityStatisticsRepositoryService.findStatistics(command)

        render view:"search", model:[ statistics:statistics, command:command]
    }



    @Secured('ROLE_MANAGEMENT_USER')
    def create() {
        def activityId = params.id
        def title = params?.title

        [activityId: activityId, title:title]
    }

    @Secured('ROLE_MANAGEMENT_USER')
    def save(Attendant attendantInstance){
        attendantInstance = new Attendant(params)
        if (attendantInstance){
            if (attendantInstance.validate()){
                attendantRepositoryService.save(attendantInstance)
            }else{
                attendantInstance.hasErrors().each {
                    log.error(it)
                }
            }
        }
        redirect controller: 'activity', action: 'detail', params:[id: attendantInstance.eventId]
    }
}

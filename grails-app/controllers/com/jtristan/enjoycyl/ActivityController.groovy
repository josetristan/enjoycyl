package com.jtristan.enjoycyl

import com.jtristan.enjoycyl.command.ActivityCommand
import com.jtristan.enjoycyl.job.ActivityStatisticJobService
import com.jtristan.enjoycyl.repository.ActivityStatisticsRepositoryService
import com.jtristan.enjoycyl.util.FormatDateCommandService
import grails.plugin.springsecurity.annotation.Secured
import grails.util.Environment
import groovy.sql.Sql

import java.time.LocalDate

class ActivityController {

    ActivityService activityService
    ProposalService proposalService
    ActivityStatisticsRepositoryService activityStatisticsRepositoryService
    ActivityStatisticJobService activityStatisticJobService
    FormatDateCommandService formatDateCommandService

    private final static int MAX_ACTIVITIES = 12

    def index(){

    }

    def listProposal(ActivityCommand command){
        //TODO: eliminar
        if (command.locality==null && command.category==null && Environment.current==Environment.DEVELOPMENT) command.locality=Activity.Locality.PALENCIA

        //Filter the date to show the most recent events
        command.startDate = LocalDate.now()
        command.endDate = LocalDate.now().plusMonths(6)

        def proposals = proposalService.getProposal(command)
        render view:"index", model:[proposals:proposals]
    }

    def accurateSearch(ActivityCommand command){
        //TODO: eliminar
        if (command.locality==null && command.category==null && Environment.current==Environment.DEVELOPMENT) command.locality=Activity.Locality.PALENCIA
        if (params?.startDate){
            command.startDate = formatDateCommandService.formatToDate(params.startDate)
        }else{
            command.startDate = LocalDate.now()
        }
        if (params?.endDate){
            command.endDate = formatDateCommandService.formatToDate(params.endDate)
        }else{
            command.endDate = LocalDate.now().plusMonths(6)
        }
        def activities = activityService.getActivities(command, MAX_ACTIVITIES)

        boolean isGeneric=false
        //In case the search contains values in both fields it could be a region for a different locality so the locality is showed in the card
        if (command.locality && command.region) isGeneric=true

        render view:"listAccurate", model:[activities:activities, command:command, isGeneric: isGeneric]
    }

    def accurateIndex(){
        def command = new ActivityCommand()
        command.startDate = LocalDate.now()
        command.endDate = LocalDate.now().plusMonths(3)

        def activities = activityService.getActivities(command, MAX_ACTIVITIES)
        render view:"listAccurate", model:[activities:activities, command:command, isGeneric:true]
    }

    def detail(){
        def eventId = params.id

        def activity = activityService.getDetailedActivity(eventId)

        if (activity)
            return [activity:activity]
        else
            render view: "/error", model:[error:"El catálogo de datos abiertos de la Junca de Castilla y León no dispone ya de información sobre este evento"]
    }

    /**
     * Save the like in the statistic event
     * @return
     */
    def like(){
        if (params?.eventId){
            activityStatisticsRepositoryService.saveLike(params?.eventId)
        }
    }
    @Secured('ROLE_ADMIN')
    def executeJob(){
        activityStatisticJobService.executeJob()
        redirect action:'listProposal'
    }

}

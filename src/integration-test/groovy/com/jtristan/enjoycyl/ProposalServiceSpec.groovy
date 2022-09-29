package com.jtristan.enjoycyl

import com.jtristan.enjoycyl.command.ActivityCommand
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

import java.time.LocalDate

@Integration
@Rollback
class ProposalServiceSpec extends Specification{
    @Autowired
    ProposalService proposalService

    def "si el API no devuelve ninguna actividad se completa el grid con estadísticas y datos de tierra de sabor"(){
        ActivityService activityServiceStub = Stub()
        activityServiceStub.getActivities(_)>>new ArrayList()
        proposalService.activityService = activityServiceStub
        given:
            createActivityStatistics()

        when:
            def proposal = proposalService.getProposal(new ActivityCommand(category: Activity.Category.BOOKS))
        then:
            proposal.getStaticPojos().size()+proposal.companies.size()==12
    }

    public createActivityStatistics(){
        new Statistic(eventId: "1", category: Activity.Category.BOOKS, locality: Activity.Locality.PALENCIA,startDate: LocalDate.now()).save(validate:false)
        new Statistic(eventId: "2", category: Activity.Category.BOOKS, locality: Activity.Locality.ZAMORA,startDate: LocalDate.now().plusMonths(1)).save(validate:false)
        new Statistic(eventId: "3", category: Activity.Category.BOOKS, locality: Activity.Locality.PALENCIA,startDate: LocalDate.now().minusMonths(1)).save(validate:false)
    }


}

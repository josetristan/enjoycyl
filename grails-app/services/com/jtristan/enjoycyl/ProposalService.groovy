package com.jtristan.enjoycyl

import com.jtristan.TierraSaborService
import com.jtristan.enjoycyl.command.ActivityCommand
import com.jtristan.enjoycyl.pojo.Proposal
import com.jtristan.enjoycyl.pojo.StatisticPojo
import com.jtristan.enjoycyl.pojo.TierraSaborCompanyPojo
import com.jtristan.enjoycyl.repository.ActivityStatisticsRepositoryService
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic

@CompileStatic
class ProposalService {

    ActivityService activityService
    ActivityStatisticService activityStatisticService
    TierraSaborService tierraSaborService

    public final static int MAX_ACTIVITIES = 9
    public final static int MAXIMUN_ELEMENTS = 12

    /**
     * Return a list of activity matching with the requirements together some ramdom proposal and statistic information
     * It allows a maximun of 9 activities by requested and a maximun of 12 elements (activities + proposals + statistics)
     * @param activityCommand includes the filter for activities API
     * @return List return activities + other kind of ramdon proposals + statistics
     */
    public Proposal getProposal(ActivityCommand activityCommand) {

        List<Activity> activities = activityService.getActivities(activityCommand)
        List<List<StatisticPojo>> statistics = activityStatisticService.buildRamdonStatistics(activityCommand, activities.size())
        int tsProducts = MAXIMUN_ELEMENTS - activities.size() - statistics.size()
        List< TierraSaborCompany> companies  = tierraSaborService.buildRamdon(activityCommand, tsProducts)
        return new Proposal(activities: activities, staticPojos: statistics,companies: companies)

    }
}

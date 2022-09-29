package com.jtristan.enjoycyl.repository

import com.jtristan.enjoycyl.Statistic
import com.jtristan.enjoycyl.Rated
import com.jtristan.enjoycyl.command.ReportCommand
import com.jtristan.enjoycyl.pojo.StatisticPojo
import grails.gorm.transactions.Transactional

@Transactional
class RatedRepositoryService {

    def save(Rated userOpinion) {
        userOpinion.statistic = Statistic.findByEventId(userOpinion.eventId)
        userOpinion.save()
    }

    public List<Statistic> ratesBy(ReportCommand command, StatisticPojo.GroupedBy groupedBy) {

        return Rated.createCriteria().list {

            projections {
                sum("eventRated")
                statistic {
                    if (groupedBy == StatisticPojo.GroupedBy.LOCALITY) groupProperty('locality')
                    if (groupedBy == StatisticPojo.GroupedBy.CATEGORY) groupProperty('category')
                    if (groupedBy == StatisticPojo.GroupedBy.MONTH) groupProperty('period')
                }
            }

            statistic {
                if (command.startDate){
                    between("startDate",command.startDate,command.endDate)
                }
                if (command.locality){
                    eq("locality",command.locality)
                }
                if(command.category){
                    eq("category",command.category)
                }

                if (groupedBy== StatisticPojo.GroupedBy.LOCALITY)  order("locality", "asc")
                if (groupedBy== StatisticPojo.GroupedBy.CATEGORY)  order("category", "asc")
                if (groupedBy== StatisticPojo.GroupedBy.MONTH)  order("period", "asc")
            }
        }
    }

    /**
     * Return the events with most rated (eventRated).
     * @param command
     * @param limit
     * @return: a list of []
     */
    public List<Object> mostRated(ReportCommand command, int limit) {

        def e = Rated.createCriteria().list {

            projections {
                avg("eventRated")
                statistic {
                    groupProperty('locality')
                    groupProperty('region')
                    groupProperty('category')
                    groupProperty('title')
                }
            }

            statistic {
                if (command.startDate){
                    between("startDate",command.startDate,command.endDate)
                }
                if (command.locality){
                    eq("locality",command.locality)
                }
                if(command.category){
                    eq("category",command.category)
                }
                maxResults(limit)
                order("locality", "asc")
            }

        }

    }


}

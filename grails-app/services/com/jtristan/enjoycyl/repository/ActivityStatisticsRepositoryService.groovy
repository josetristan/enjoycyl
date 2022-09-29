package com.jtristan.enjoycyl.repository

import com.jtristan.enjoycyl.Activity
import com.jtristan.enjoycyl.CultureCalendarClientService
import com.jtristan.enjoycyl.Statistic
import com.jtristan.enjoycyl.client.helper.DateRange
import com.jtristan.enjoycyl.command.ActivityCommand
import com.jtristan.enjoycyl.command.ReportCommand
import com.jtristan.enjoycyl.pojo.ActivityPojo
import com.jtristan.enjoycyl.pojo.Link
import com.jtristan.enjoycyl.pojo.RecordFather
import com.jtristan.enjoycyl.pojo.StatisticPojo
import com.jtristan.enjoycyl.statistic.Period
import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic

import javax.validation.constraints.NotNull
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import static com.jtristan.enjoycyl.pojo.StatisticPojo.*

import com.jtristan.enjoycyl.CultureCalendarClientService

@Transactional
class ActivityStatisticsRepositoryService {

    CultureCalendarClientService cultureCalendarClientService

    public enum StatisticField{
        CATEGORY("category"),
        REGION("region"),
        LOCALITY("locality")

        private String field
        StatisticField(field){ this.field = field }
        public String getField(){ return this.field }

    }

    /**
     * If the Event is new save it
     * @param statisticList
     * @return
     */
    def save(List<Statistic>statisticList) {

        statisticList.each{Statistic statistic->
            if (exists(statistic)==false){
                if (statistic.validate()){
                    statistic.save()
                }else{
                    //TODO: send mail to the admin with the error?
                    log.error(statistic.errors.toString())
                }
            }
        }
    }

    def saveLike(String eventId){
        Statistic statistic = Statistic.findByEventId(eventId)
        if (statistic) {
            statistic.likes++
            if (statistic.validate()) {
                statistic.save()
            } else {
                log.error(statistic.errors.toString())
            }
        }else{
            log.error("Event id ${eventId} was not saved in db for statistics")
        }
    }

    /**
     * Check if the event already exists in the db
     * @param statistic
     * @return
     */
    private boolean exists(Statistic statistic) {
        return Statistic.findByEventId(statistic.eventId)
    }

    /**
     *
     * @param locality
     * @param period
     * @return
     */
    @ReadOnly
    def List<StatisticPojo> getStatistics(@NotNull Activity.Locality locality, Period period) {
        def pojos = []

        //TODO: pendiente de solucionar si existen resultados como gestionar el mostrar las tarjetas.
        def results = Statistic.createCriteria().list {
            projections {
                count "id"
                groupProperty('category')
            }
            eq("locality", locality)
            if (period.startPeriod!=period.endPeriod){
                between("startDate",convertStartPeriodToDate(period.startPeriod),convertEndPeriodToDate(period.endPeriod))
            } else{
                between("startDate",convertStartPeriodToDate(period.startPeriod),convertEndPeriodToDate(period.startPeriod))
            }
        }

        results.each{
            pojos<<buildStatisticPojo(locality, it, period)
        }
        return pojos
    }

    private StatisticPojo buildStatisticPojo(Activity.Locality locality, it, Period period) {
        StatisticPojo pojo = new StatisticPojo(locality: locality, category: it[1], number: it[0], groupedBy: GroupedBy.CATEGORY, period: period)
        pojo
    }

    private StatisticPojo buildStatisticPojo(Activity.Category category, it, Period period) {
        StatisticPojo pojo = new StatisticPojo(locality: it[1], category: category, number: it[0], groupedBy: GroupedBy.CATEGORY, period: period)
        pojo
    }

    /**
     *
     * @param category
     * @param period
     * @return
     */
    @ReadOnly
    def List<StatisticPojo> getStatistics(@NotNull Activity.Category category, Period period) {
        def pojos = []

        def results = Statistic.createCriteria().list {
            projections {
                count "id"
                groupProperty('locality')
            }
            eq("category", category)

            if (period.startPeriod!=period.endPeriod){
                between("startDate",convertStartPeriodToDate(period.startPeriod),convertEndPeriodToDate(period.endPeriod))
            } else{
                between("startDate",convertStartPeriodToDate(period.startPeriod),convertEndPeriodToDate(period.startPeriod))
            }
        }

        results.each{
            pojos<<buildStatisticPojo(category, it, period)
        }
        return pojos
    }

    @ReadOnly
    def List<Statistic>findStatistics(ReportCommand command){
        return Statistic.createCriteria().list {
            if (command.category)
                eq("category", command.category)
            if (command.locality)
                eq("locality", command.locality)
            if (command.region)
                eq("region", command.region)
            if (command.startDate)
                between("startDate",command.startDate, command.endDate)
            if (command.title)
                like('title',"%${command.title}%")

            order("title","desc")

        }
    }

    /**
     *
     * @param period
     * @return
     */
    @ReadOnly
    def List<StatisticPojo> getStatistics(Period period) {
        def pojos = []

        def results = Statistic.createCriteria().list {
            projections {
                count "id"
                groupProperty('locality')
                groupProperty('category')
            }
            if (period.startPeriod!=period.endPeriod){
                between("startDate",convertStartPeriodToDate(period.startPeriod),convertEndPeriodToDate(period.endPeriod))
            } else{
                between("startDate",convertStartPeriodToDate(period.startPeriod),convertEndPeriodToDate(period.startPeriod))
            }
        }

        results.each{
            pojos<<buildStatisticPojo(locality, it, period)
        }
        return pojos
    }

    /**
     * Add the first day to the period to get the date
     * @param period
     * @return LocalDate
     */
    private LocalDate convertStartPeriodToDate(String period){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy")
        return LocalDate.parse("01"+period,formatter)
    }

    /**
     * Add the last day of the month to the period to get the date
     * @param period
     * @return LocalDate
     */
    private LocalDate convertEndPeriodToDate(String period){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy")

        return LocalDate.parse(LocalDate.of(Integer.parseInt(period.substring(2,6)) ,Integer.parseInt(period.substring(0,2)),1).lengthOfMonth()+period,formatter)
    }


    /**
     *
     * @param command
     * @param limit
     * @return
     */
    public List<Statistic> mostLiked(ReportCommand command, int limit) {

        return Statistic.createCriteria().list {

            gt("likes",0)

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
            order("likes", "desc")
        }

    }


    public List<Statistic> mostLikedBy(ReportCommand command,  GroupedBy groupedBy) {

        return Statistic.createCriteria().list {

            projections {
                sum("likes")
                if (groupedBy==GroupedBy.LOCALITY)  groupProperty('locality')
                if (groupedBy==GroupedBy.CATEGORY)  groupProperty('category')
                if (groupedBy==GroupedBy.MONTH) groupProperty('period')
            }

            if (command.startDate){
                between("startDate",command.startDate,command.endDate)
            }
            if (command.locality){
                eq("locality",command.locality)
            }
            if(command.category){
                eq("category",command.category)
            }

            if (groupedBy==GroupedBy.LOCALITY)  order("locality", "asc")
            if (groupedBy==GroupedBy.CATEGORY)  order("category", "asc")
            if (groupedBy==GroupedBy.MONTH)  order("period", "asc")

        }
    }


    public List<Statistic> activitiesBy(ReportCommand command,  GroupedBy groupedBy) {

        return Statistic.createCriteria().list {

            projections {
                count("id")
                if (groupedBy==GroupedBy.LOCALITY)  groupProperty('locality')
                if (groupedBy==GroupedBy.CATEGORY)  groupProperty('category')
                if (groupedBy==GroupedBy.MONTH) groupProperty('period')
            }

            if (command.startDate){
                between("startDate",command.startDate,command.endDate)
            }
            if (command.locality){
                eq("locality",command.locality)
            }
            if(command.category){
                eq("category",command.category)
            }

            if (groupedBy==GroupedBy.LOCALITY)  order("locality", "asc")
            if (groupedBy==GroupedBy.CATEGORY)  order("category", "asc")
            if (groupedBy==GroupedBy.MONTH)  order("period", "asc")

        }
    }

    /**
     *
     * @param command
     * @return
     */
    public List<Statistic> getActivitiesByTitle(ReportCommand command) {

        return Statistic.createCriteria().list {

            if (command.title){
                like('title',"%${command.title}%")
            }
            if (command.startDate){
                between("startDate",command.startDate,command.endDate)
            }
            if (command.locality){
                eq("locality",command.locality)
            }
            if(command.category){
                eq("category",command.category)
            }
            order("title", "desc")
        }

    }

    public String getTitle(String eventId){
        def list = Statistic.createCriteria().listDistinct {
            projections{
                property("title")
            }
            eq("eventId",eventId)
        }

        return list[0]
    }

}

package com.jtristan.enjoycyl.report

import com.jtristan.enjoycyl.Statistic
import com.jtristan.enjoycyl.command.ReportCommand
import com.jtristan.enjoycyl.pojo.StatisticPojo
import com.jtristan.enjoycyl.repository.ActivityStatisticsRepositoryService
import com.jtristan.enjoycyl.repository.RatedRepositoryService

import java.time.LocalDate



class ReportService {

    private static int MAX_RECORDS = 10

    ActivityStatisticsRepositoryService activityStatisticsRepositoryService
    RatedRepositoryService ratedRepositoryService

    /**
     * Return the activities with more likes
     * @param reportCommand
     * @return
     */
    private List<Statistic> mostLiked(ReportCommand reportCommand) {
        def list = new ArrayList<Statistic>()
        list = activityStatisticsRepositoryService.mostLiked(reportCommand, MAX_RECORDS)
        return list
    }

    private List<Object> likesByLocality(ReportCommand reportCommand) {
        return activityStatisticsRepositoryService.mostLikedBy(reportCommand,  StatisticPojo.GroupedBy.LOCALITY)
    }

    private List<Object> likesByCategory(ReportCommand reportCommand) {
        return activityStatisticsRepositoryService.mostLikedBy(reportCommand, StatisticPojo.GroupedBy.CATEGORY)
    }

    private List<Object> likesByMonth(ReportCommand command) {
        command = setDateRange(command)
        def groupedByMonth = activityStatisticsRepositoryService.mostLikedBy(command, StatisticPojo.GroupedBy.MONTH)
        return setMonthByName(groupedByMonth)
    }

    /**
     * Si no se ha filtrado por fechas el periodo será el año actual. Si se filtra por
     * fechas sólo se tiene la de comienzo y el periodo será el año completo
     * @param reportCommand
     * @return
     */
    private ReportCommand setDateRange(ReportCommand command) {
        if (!command.startDate){
            command.startDate = LocalDate.of(LocalDate.now().year,1,1)
            command.endDate = LocalDate.of(LocalDate.now().year,12,31)
        }else{
            command.startDate = LocalDate.of(command.startDate.year,1,1)
            command.endDate = LocalDate.of(command.startDate.year,12,31)
        }
        return command

    }

    private List<Object> setMonthByName(List<Statistic> statistics) {
        def month
        def monthName
        statistics.each {
            month = Integer.parseInt(it[1].substring(0,2))
             switch (month){
                case 1: monthName = "Enero";break;
                case 2: monthName = "Febrero";break;
                case 3: monthName = "Marzo";break;
                case 4: monthName = "Abril";break;
                case 5: monthName = "Mayo";break;
                case 6: monthName = "Junio";break;
                case 7: monthName = "Julio";break;
                case 8: monthName = "Agosto";break;
                case 9: monthName = "Septiembre";break;
                case 10: monthName = "Octubre";break;
                case 11: monthName = "Noviembre";break;
                case 12: monthName = "Diciembre";break;
            }
            it[1]=monthName
        }
        return statistics
    }

    private List<Object> activitiesByLocality(ReportCommand reportCommand) {
        return activityStatisticsRepositoryService.activitiesBy(reportCommand, StatisticPojo.GroupedBy.LOCALITY)
    }

    private List<Object> activitiesByCategory(ReportCommand reportCommand) {
        return activityStatisticsRepositoryService.activitiesBy(reportCommand, StatisticPojo.GroupedBy.CATEGORY)
    }

    private List<Object> activitiesByMonth(ReportCommand command) {
        command = setDateRange(command)
        def groupedByMonth = activityStatisticsRepositoryService.activitiesBy(command, StatisticPojo.GroupedBy.MONTH)
        return setMonthByName(groupedByMonth)
    }

    private List<Statistic> activitiesByTitle(ReportCommand reportCommand) {
        def activities = new ArrayList<Statistic>()
        if (reportCommand.title){
            activities = activityStatisticsRepositoryService.getActivitiesByTitle(reportCommand)
        }
        return activities
    }

    private List<Object> ratesByLocality(ReportCommand reportCommand) {
        return ratedRepositoryService.ratesBy(reportCommand, StatisticPojo.GroupedBy.LOCALITY)
    }

    private List<Object> ratesByCategory(ReportCommand reportCommand) {
        return ratedRepositoryService.ratesBy(reportCommand, StatisticPojo.GroupedBy.CATEGORY)
    }

    private List<Object> ratesByMonth(ReportCommand command) {
        command = setDateRange(command)
        def groupedByMonth = ratedRepositoryService.ratesBy(command, StatisticPojo.GroupedBy.MONTH)
        return setMonthByName(groupedByMonth)
    }

    private List<Statistic> mostRated(ReportCommand reportCommand) {
        def list = new ArrayList<Statistic>()
        list = ratedRepositoryService.mostRated(reportCommand, MAX_RECORDS)
        return list
    }

    public Report analyze(ReportCommand command) {

        Report report = new Report()
        report.mostLiked = mostLiked(command)
        report.likesByLocality = likesByLocality(command)
        report.likesByCategory = likesByCategory(command)
        report.likesByMonth = likesByMonth(command)
        report.activitiesByLocality = activitiesByLocality(command)
        report.activitiesByCategory = activitiesByCategory(command)
        report.activitiesByMonth = activitiesByMonth(command)
        report.individualInfo = activitiesByTitle(command)

        report.ratedByLocality = ratesByLocality(command)
        report.ratedByCategory = ratesByCategory(command)
        report.ratedByMonth = ratesByMonth(command)
        report.mostRated = mostRated(command)

        return report

    }
}

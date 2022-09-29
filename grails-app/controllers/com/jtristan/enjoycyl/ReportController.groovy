package com.jtristan.enjoycyl

import com.jtristan.enjoycyl.command.ReportCommand
import com.jtristan.enjoycyl.report.Report
import com.jtristan.enjoycyl.report.ReportService
import com.jtristan.enjoycyl.util.FormatDateCommandService

import java.time.LocalDate

class ReportController {

    FormatDateCommandService formatDateCommandService
    ReportService reportService

    def index() {
        LocalDate startDate = LocalDate.now()
        LocalDate endDate = LocalDate.now().plusMonths(3)

        def command = new ReportCommand(startDate:startDate,endDate:endDate)

        Report report = reportService.analyze(command)

        [mostLiked           : report.mostLiked, command:command, likesByLocality:report.likesByLocality,
         likesByCategory     :report.likesByCategory, likesByMonth:report.likesByMonth, activitiesByLocality:report.activitiesByLocality,
         activitiesByCategory:report.activitiesByCategory, activitiesByMonth: report.activitiesByMonth, individualInfo: report.individualInfo,
         ratedByLocality     :report.ratedByCategory, ratedByCategory:report.ratedByCategory, ratedByMonth:report.ratedByMonth, mostRated: report.mostRated]

    }

    def search(ReportCommand command){
        //TODO: crear una página de errores para tener la opción de contactar
        if (params?.startDate){
            command.startDate = formatDateCommandService.formatToDate(params.startDate)
        }
        if (params?.endDate){
            command.endDate = formatDateCommandService.formatToDate(params.endDate)
        }

        Report report = reportService.analyze(command)

        //TODO: pendiente de asignar a los combos el valor
        render view:"index", model:[mostLiked           : report.mostLiked, command:command, likesByLocality:report.likesByLocality,
                                    likesByCategory     :report.likesByCategory, likesByMonth:report.likesByMonth, activitiesByLocality:report.activitiesByLocality,
                                    activitiesByCategory:report.activitiesByCategory, activitiesByMonth: report.activitiesByMonth, individualInfo: report.individualInfo,
                                    ratesByLocality     :report.ratedByCategory, ratesByCategory:report.ratedByCategory, ratesByMonth:report.ratedByMonth, mostRated: report.mostRated]

    }
}

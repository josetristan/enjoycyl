package com.jtristan.enjoycyl.command

import com.jtristan.enjoycyl.Activity
import grails.validation.Validateable

import java.time.LocalDate

class ReportCommand implements Validateable {

    LocalDate startDate
    LocalDate endDate
    Activity.Category category
    Activity.Locality locality
    String region
    String title

}

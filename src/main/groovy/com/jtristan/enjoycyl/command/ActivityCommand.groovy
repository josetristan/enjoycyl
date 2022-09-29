package com.jtristan.enjoycyl.command

import com.jtristan.enjoycyl.Activity
import grails.validation.Validateable

import java.time.LocalDate

class ActivityCommand implements Validateable {

    LocalDate startDate
    LocalDate endDate
    String region
    Activity.Category category
    Activity.Locality locality

}

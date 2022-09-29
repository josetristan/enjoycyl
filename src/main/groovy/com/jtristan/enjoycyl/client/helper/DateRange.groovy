package com.jtristan.enjoycyl.client.helper

import org.apache.tomcat.jni.Local

import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors
import java.util.stream.Stream

/**
 * Calculate the dates between two dates
 */
class DateRange {

    public final LocalDate startDate
    public final LocalDate endDate
    //private List<LocalDate> dates = new ArrayList<>()

    public DateRange(startDate, endDate){
        this.startDate =startDate
        this.endDate = endDate

        //datesInRange()
    }
    //TODO: remove
    private List<LocalDate> datesInRange(){

        final int days = (int) this.startDate.until(this.endDate, ChronoUnit.DAYS);

        dates = Stream.iterate(this.startDate, d -> d.plusDays(1))
                .limit(days)
                .collect(Collectors.toList());
    }

    public getRangeOfDates() {return this.dates}


}

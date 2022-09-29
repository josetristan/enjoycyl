package com.jtristan.enjoycyl

import com.jtristan.enjoycyl.statistic.Period

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.jtristan.enjoycyl.Activity

class Statistic {

    //To avoid issues with custom id mantain the id and eventId
    String eventId
    String title
    String celebrationPlace
    String region
    Activity.Category category
    Boolean isLibraryEvent
    LocalDate startDate
    LocalDate endDate
    //MMyyyy
    String period
    Activity.Locality locality
    int likes
    int attends

    static mapping = {

        category column: 'category', index: 'Category_Idx'
        locality column: 'locality', index: 'Locality_Idx'
        period column: 'period', index: 'Period_Idx'

        title column:'title', length: 255
        celebrationPlace column: 'celebration_place', length: 255
        region column:'region', length: 255
        locality column:'locality', length: 255

    }

    static constraints = {
        title maxSize: 255
        celebrationPlace maxSize: 255
        region maxSize: 255
        locality maxSize: 255
    }

    public Statistic(){

    }

    public setLibraryEvent(String value){
        this.isLibraryEvent = value.toUpperCase().equals("SI")?true:false
    }


    //TODO: He intenntado que se llamase desde el set del startdate pero al declarar el método
    //no añade la propiedad en el save.
    /**
     * Work with period instead of date to increase the speed
     * @param date
     * @return
     */
    public buildPeriod(){
        if (this.startDate){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMyyyy");
            this.period = formatter.format(this.startDate)
        }
    }
}

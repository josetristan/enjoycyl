package com.jtristan.enjoycyl

class Rated {

    Statistic statistic
    Long eventId
    int adults
    int children

    Activity.Locality locality
    String region

    /**
     * Pick up the cuantitative opinion about the several data related with the activity.
     */
    int placeRated
    int timeRated
    int eventRated

    static mapping = {

    }

    static constraints = {
        placeRated range: 0..5
        timeRated range: 0..5
        eventRated range: 0..5
    }
}

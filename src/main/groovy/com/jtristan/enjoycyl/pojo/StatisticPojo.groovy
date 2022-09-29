package com.jtristan.enjoycyl.pojo

import com.jtristan.enjoycyl.Activity
import com.jtristan.enjoycyl.statistic.Period

/**
 * Class used to show the statistic
 */
class StatisticPojo {

    Activity.Category category
    Activity.Locality locality
    //Necessary to set the message: Number of Shows in Palencia in  the last year
    Period period
    int number
    GroupedBy groupedBy

    public enum GroupedBy{
        //for a locality all categories
        CATEGORY,
        LOCALITY,
        CATEGORY_LOCALITY,

        MONTH
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        StatisticPojo that = (StatisticPojo) o

        if (category != that.category) return false
        if (locality != that.locality) return false
        if (period != that.period) return false

        return true
    }

    int hashCode() {
        int result
        result = (category != null ? category.hashCode() : 0)
        result = 31 * result + (locality != null ? locality.hashCode() : 0)
        result = 31 * result + period.hashCode()
        return result
    }
}

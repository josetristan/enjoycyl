package com.jtristan.enjoycyl.statistic

import javax.validation.constraints.NotNull
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Contains de period to apply to the statistics
 */
class Period {

    public final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMyyyy")
    //Number of options in enum without taking into account CUSTOM. Used to calculate randoms periods
    public final static int NUMBER = 3

    TypePeriod typePeriod
    String startPeriod
    String endPeriod

    public Period(@NotNull typePeriod, @NotNull startPeriod){
        //TODO: check the format is ok
        this.typePeriod = typePeriod
        this.startPeriod = startPeriod
        this.endPeriod = startPeriod
    }

    public Period(@NotNull typePeriod, @NotNull startPeriod, @NotNull endPeriod){
        //TODO: check start period is less than end
        this.typePeriod = typePeriod
        this.startPeriod = startPeriod
        this.endPeriod = endPeriod
    }

    public Period(@NotNull TypePeriod typePeriod){
        this.typePeriod = typePeriod
        calculatePeriod()
    }

    enum TypePeriod{
        CURRENT_MONTH("durante este mes"),
        LAST_YEAR("en el último año"),
        TWO_NEXT_MONTHS("durante los próximos dos meses"),
        CUSTOM(" ")

        private String messsage

        public TypePeriod(message){ this.messsage = message }
        public getMessage(){return this.messsage}
    }

    private String calculatePeriod(){
        switch (typePeriod){
            case TypePeriod.CURRENT_MONTH:
                return getCurrentMonthPeriod()
                break;
            case TypePeriod.LAST_YEAR:
                return getLasYearMonthPeriod()
                break;
            case TypePeriod.TWO_NEXT_MONTHS:
                return getTwoNextMonthsPeriod()
                break;
        }
    }

    private void getCurrentMonthPeriod() {
        this.startPeriod = LocalDate.now().format(FORMATTER)
        this.endPeriod = this.startPeriod
    }

    private void getLasYearMonthPeriod() {
        this.endPeriod = LocalDate.now().format(FORMATTER)
        this.startPeriod = LocalDate.now().minusMonths(12).format(FORMATTER)
    }

    private void getTwoNextMonthsPeriod() {
        this.startPeriod = LocalDate.now().format(FORMATTER)
        this.endPeriod = LocalDate.now().plusMonths(2).format(FORMATTER)
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Period period = (Period) o

        if (endPeriod != period.endPeriod) return false
        if (startPeriod != period.startPeriod) return false
        if (typePeriod != period.typePeriod) return false

        return true
    }

    int hashCode() {
        int result
        result = typePeriod.hashCode()
        result = 31 * result + startPeriod.hashCode()
        result = 31 * result + (endPeriod != null ? endPeriod.hashCode() : 0)
        return result
    }
}

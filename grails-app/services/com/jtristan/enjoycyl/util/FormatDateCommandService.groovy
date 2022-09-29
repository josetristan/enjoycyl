package com.jtristan.enjoycyl.util

import java.text.SimpleDateFormat
import java.time.LocalDate


class FormatDateCommandService {

    public LocalDate formatToDate(String date) {
        int day = Integer.parseInt(date.substring(0,2))
        int month = Integer.parseInt(date.substring(3,5))
        int year = Integer.parseInt(date.substring(6,10))
        return LocalDate.of(year,month,day)

    }
}

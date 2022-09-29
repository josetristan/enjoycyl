package com.jtristan.enjoycyl.helpers

import com.jtristan.enjoycyl.pojo.Record

class Check {

    public static boolean allRecordsHasLocalityFiltered(records, String locality) {
        return records.every{ record-> record.field.locality.equals(locality)}
    }

}

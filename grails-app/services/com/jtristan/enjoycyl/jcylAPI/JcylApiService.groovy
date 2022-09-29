package com.jtristan.enjoycyl.jcylAPI

import com.jtristan.enjoycyl.CultureCalendarClientService
import com.jtristan.enjoycyl.client.helper.DateRange
import groovy.transform.CompileStatic

import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@CompileStatic
class JcylApiService {

    /**
     * Return a format date according the https://help.opendatasoft.com/apis/ods-search-v1/#query-language
     * yyyy/MM/dd
     * Date formats can be specified in different formats: simple (YYYY[[/mm]/dd]) or ISO 8601 (YYYY-mm-DDTHH:MM:SS)
     */
    private String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return date.format(formatter)
    }

    /**
     * Build the query param to acomplish a between date.
     * https://analisis.datosabiertos.jcyl.es/api/v2/catalog/datasets/eventos-de-la-agenda-cultural-categorizados-y-geolocalizados/records?where=fecha_inicio+IN+%5Bdate%2720220413%27..date%2720220510%27%5D
     * https://help.opendatasoft.com/apis/ods-search-v2/#where-clause
     * date_field IN [date'2017'..date'2018']: query records whose date is between start_date and end_date
     */
    public String buildQueryParamBetweenDates(String field, DateRange dateRange,String and) {
        return this.encode("${and}${field} IN [date'${this.formatDate(dateRange.startDate)}'..date'${this.formatDate(dateRange.endDate)}']")
    }

    /**
     * Build the query param for a condition: start date equals
     * https://analisis.datosabiertos.jcyl.es/api/v2/catalog/datasets/eventos-de-la-agenda-cultural-categorizados-y-geolocalizados/records?where=fecha_inicio=date'2022/04/09'
     * @param field
     * @param localDate
     */
    public String buildQueryParamDate(String field, LocalDate startDate,String and) {
        return this.encode("${and}${field}=date'${this.formatDate(startDate)}'")
    }

    /**
     * Build the query param for a condition: start date equals or greater
     * https://analisis.datosabiertos.jcyl.es/api/v2/catalog/datasets/eventos-de-la-agenda-cultural-categorizados-y-geolocalizados/records?where=fecha_inicio>=date'2022/04/09'
     * @param field
     * @param localDate
     */
    public String buildQueryParamDateGreater(String field, LocalDate startDate,String and) {
        return this.encode("${and}${field}>=date'${this.formatDate(startDate)}'")
    }

    /**
     * Build the query param for a string condition: key=value
     * https://analisis.datosabiertos.jcyl.es/api/v2/catalog/datasets/eventos-de-la-agenda-cultural-categorizados-y-geolocalizados/records?refine=nombre_localidad:Burgos
     * @param filter:field name
     * @param value
     * @param and value to apply if there are several conditions
     */
    public String buildQueryParamFacetString(String field, String value, String and) {
        return this.encode("${and}search(${field},\"${value}\")")
    }

    /**
     * Filter searching in whatever visible value.
     *https://analisis.datosabiertos.jcyl.es/api/v2/catalog/datasets/eventos-de-la-agenda-cultural-categorizados-y-geolocalizados/records?where=%221285147082163%22
     * Filter search queries are queries that don’t refer to fields. They only contain quoted strings and
     * boolean operators. Filter search queries perform full-text searches on all visible fields of each
     * record and return matching rows.
     If the string contains more than one word, the query will be an AND query on each tokenized word.
     It is possible to perform a greedy query by adding a wildcard * at the end of a word.
     * @param s1
     * @param s2
     * @param s3
     */
    public String buildQueryParamFilterSearchQuery(String value, String and) {
        return this.encode("${and}\"${value}\"")
    }

    /**
     * Make a search for an specific field (For equal comparasion)
     * https://analisis.datosabiertos.jcyl.es/api/v2/catalog/datasets/eventos-de-la-agenda-cultural-categorizados-y-geolocalizados/records?where=id_evento=1284638392640
     * @param field
     * @param value
     * @param and
     * @return
     */
    public String buildQueryParamFilterWhereQueryEqual(String field, String value, String and){
        return this.encode("${and}${field}=${value}")
    }

    public String encode(String userInfo) {
        try {
            return URLEncoder.encode(userInfo, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("No available charset: " + e.getMessage());
        }
    }
}

package com.jtristan.enjoycyl


import com.jtristan.enjoycyl.network.UriService
import com.jtristan.enjoycyl.pojo.ActivityPojo
import groovy.transform.CompileStatic
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.uri.UriBuilder

import javax.validation.constraints.NotNull

/**
 * Access to the API "eventos de la agenda cultural categorizados y geolocalizados"
 */
@CompileStatic
class CultureCalendarClientService {

    UriService uriService

    public final static String BASE_URL = "https://analisis.datosabiertos.jcyl.es"
    //API contains events in the past so we need to pick up more in order to remove the ones in the past
    public final static int ROWS = 9

    public enum Filter{
        CATEGORY('categoria'),
        START_DATE('fecha_inicio'),
        START_DATE_BETWEEN('fecha_inicio'),
        LOCALITY('nombre_provincia'),
        REGION('nombre_localidad'),
        EVENT_ID('id_evento'),
        START_DATE_GREATER_THAN('fecha_inicio')

        @Override
        String toString() {
            return super.toString()
        }
        String name
        public Filter(name){ this.name = name }
        String getName() { return name }
        void setName(String name) { this.name = name }
    }


    /**
     * Call sevaral times the API in order to get the records from a range of days
     * @param filter
     * @params rows: máximun number of rows to return. 10 is the default for API.
     * @return
     * @throws HttpClientResponseException
     */
    /*public ActivityPojo GET(@NotNull Map<Filter,Object> filter, int rows) throws HttpClientResponseException{
        ActivityPojo activity
        List<ActivityPojo> activities = new ArrayList<>()

        DateRange range = filter.get(Filter.START_DATE_BETWEEN) as DateRange
        if (range){

            filter.remove(Filter.START_DATE_BETWEEN)

            range.getRangeOfDates().each {
                filter.remove(Filter.START_DATE)
                filter.put(Filter.START_DATE, it)

                ActivityPojo activityDaily = (requestGET(filter,rows))
                if (!activity){
                    activity = activityDaily
                } else{
                    activity.records.addAll(activityDaily.records)
                }

            }
        }else{
            activity = requestGET(filter,rows)
        }
        return activity

    }*/
    /**
     * Return some activities accourding with the filter and limits parameters.
     * @param filter
     * @param @params rows: máximun number of rows to return. 10 is the default for API.
     * @return ActivityPojo: pojo with the API structure
     * @throws HttpClientResponseException
     */
    public ActivityPojo GET(@NotNull Map<Filter,Object> filter, int rows) throws HttpClientResponseException{

        HttpClient client
        try {

            client = HttpClient.create(BASE_URL.toURL())


            UriBuilder uriBuilder = uriService.buildURIPath("eventos-de-la-agenda-cultural-categorizados-y-geolocalizados")
            /*UriBuilder uriBuilder = UriBuilder.of('/api/records/1.0/search')
            uriBuilder.path("dataset")
            uriBuilder.path("eventos-de-la-agenda-cultural-categorizados-y-geolocalizados")
            uriBuilder.path("records")
             */
            //uriBuilder.queryParam("dataset", "eventos-de-la-agenda-cultural-categorizados-y-geolocalizados")
            uriBuilder = addFilter(uriBuilder, filter)
            uriBuilder = limits(uriBuilder,  rows)

            URI uri = uriBuilder.build()
            //uri = addSpecialConditionsToQueryParams(uri,filter)

            HttpRequest request = HttpRequest.GET(uri)

            HttpResponse response = client.toBlocking().exchange(request, Argument.of(ActivityPojo.class))

            if (response.status().getCode() > 202) throw new HttpClientResponseException(response.status().getReason(), response)

            return response.body()

        }finally{
            client.close()
        }

    }

    /**
     * Return all activities without applying filters
     * @param href: URI already created
     * @return ActivityPojo: pojo with the API structure
     * @throws HttpClientResponseException
     */
    public ActivityPojo GET(String href) throws HttpClientResponseException{

        HttpClient client
        try {

            client = HttpClient.create(href.toURL())

            URI uri = URI.create(href)

            HttpRequest request = HttpRequest.GET(uri)

            HttpResponse response = client.toBlocking().exchange(request, Argument.of(ActivityPojo.class))

            if (response.status().getCode() > 202) throw new HttpClientResponseException(response.status().getReason(), response)

            return response.body()

        }finally{
            client.close()
        }

    }

    private UriBuilder addFilter(UriBuilder uriBuilder, Map<Filter,Object> filters){

        if (filters)
            return  UriBuilder.of(uriService.buildQueryParam(uriBuilder.build(), filters))
        else
            return uriBuilder
    }

    /**
     * It is a query param with the template key=value
     * @return
     */
    private boolean isFilterWithConditionEqual(key){
        return (!key==Filter.START_DATE_BETWEEN)?true:false
    }

    private UriBuilder limits(UriBuilder uriBuilder, int rows) {
        return (rows!=10)?uriBuilder.queryParam("rows",rows):uriBuilder
    }

}



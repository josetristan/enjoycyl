package com.jtristan.enjoycyl.network


import com.jtristan.enjoycyl.client.helper.DateRange
import com.jtristan.enjoycyl.jcylAPI.JcylApiService
import groovy.transform.CompileStatic
import io.micronaut.http.uri.UriBuilder

import javax.validation.constraints.NotNull
import java.time.LocalDate

import static com.jtristan.enjoycyl.CultureCalendarClientService.*

/**
 * TODO: check what is better: extends the DefaultUriBuilder (ExpandoUriBuilder) or add dinamic method with metaClass
 *
 */
@CompileStatic
class UriService {

    JcylApiService jcylApiService

    /**
     * Add to the URI build by DefaultUriBuilder (Micronaut http client) the conditions
     * which use a conditional value different of "=".
     * The API allows to use for example for dates ">="
     * @param uri
     * @param map<Filter,Object>
     * @return
     */
    public URI buildQueryParam(@NotNull URI uri, @NotNull Map<Filter,Object> filter){
        StringBuilder builder = new StringBuilder()
        String and=""
        boolean existQueryParameter=false
        if (filter.size()>0){
            builder.append(uri.getPath()).append("?where=")

            filter.each {key,value->
                if (key==Filter.START_DATE_BETWEEN){
                    if (uri.getQuery() || existQueryParameter) and=" and "// builder.append(' and ')
                    builder.append(jcylApiService.buildQueryParamBetweenDates(Filter.START_DATE_BETWEEN.name, (DateRange)value, and))
                    existQueryParameter=true
                }

                if (key==Filter.START_DATE){
                    if (uri.getQuery() || existQueryParameter) and=" and "
                    builder.append(jcylApiService.buildQueryParamDate(Filter.START_DATE.name, (LocalDate)value, and))
                    existQueryParameter=true
                }

                if (key==Filter.START_DATE_GREATER_THAN){
                    if (uri.getQuery() || existQueryParameter) and=" and "
                    builder.append(jcylApiService.buildQueryParamDateGreater(Filter.START_DATE.name, (LocalDate)value, and))
                    existQueryParameter=true
                }

                if (key==Filter.CATEGORY || key==Filter.LOCALITY || key==Filter.REGION){
                    if (uri.getQuery() || existQueryParameter) and=" and "
                    builder.append(jcylApiService.buildQueryParamFacetString(key.name, (String)value, and))
                    existQueryParameter=true
                }

                if (key==Filter.EVENT_ID){
                    if (uri.getQuery() || existQueryParameter) and=" and "
                    builder.append(jcylApiService.buildQueryParamFilterWhereQueryEqual(key.name, (String)value, and))
                    existQueryParameter=true
                }
            }
        }
        return URI.create(builder.toString())
    }

    public UriBuilder buildURIPath(String dataset){
        UriBuilder uriBuilder = UriBuilder.of('/api/v2/catalog')
        uriBuilder.path("datasets")
        uriBuilder.path(dataset)
        uriBuilder.path("records")
        return uriBuilder
    }

}

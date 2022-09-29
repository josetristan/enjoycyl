package com.jtristan.enjoycyl


import com.jtristan.enjoycyl.pojo.TierraSaborCompanyPojo
import com.jtristan.enjoycyl.pojo.TierraSaborProductPojo
import groovy.transform.CompileStatic
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.uri.UriBuilder

import javax.validation.constraints.NotNull

@CompileStatic
class TierraSaborClientService {

    public enum Filter{
        LOCALITY('provincia'),
        REGION('localidad'),
        COMPANY_ID('idEmpresa')

        @Override
        String toString() {
            return super.toString()
        }
        String name
        public Filter(name){ this.name = name }
        String getName() { return name }
        void setName(String name) { this.name = name }
    }


    public enum TypeTS{
        COMPANY("empresas-acogidas-a-la-marca-tierra-de-sabor"), // TierraSaborCompanyPojo.class),
        PRODUCT("productos-de-la-marca-tierra-de-sabor"), //,TierraSaborCompanyProdutPojo.class)

        //TODO: see how to pass an object.class
        //private Class<?> aClass
        private String dataset

        //public TypeTS(String dateset,aClass){ this.dataset=dataset; this.aClass=aClass }
        public TypeTS(String dataset){ this.dataset=dataset }

        public getDataset(){return this.dataset}
        //public getAClass(){return this.aClass}
    }
    /**
     *
     * @param filter
     * @param @params rows: máximun number of rows to return. 10 is the default for API.
     * @return ActivityPojo: pojo with the API structure
     * @throws HttpClientResponseException
     */
    def GET(@NotNull TypeTS typeTS, @NotNull Map<Filter,Object> filter, int rows) throws HttpClientResponseException{

        HttpClient client
        try {

            client = HttpClient.create(ClientConstant.BASE_URL.toURL())


            UriBuilder uriBuilder = UriBuilder.of('/api/records/1.0/search')
            uriBuilder.queryParam("dataset", typeTS.dataset )
            uriBuilder = addFilter(uriBuilder, filter)
            uriBuilder = limits(uriBuilder, filter, rows)

            HttpRequest request = HttpRequest.GET(uriBuilder.build())

            HttpResponse response
            if (typeTS==TypeTS.COMPANY)
                response = client.toBlocking().exchange(request, Argument.of(TierraSaborCompanyPojo.class))
            else if (typeTS==TypeTS.PRODUCT)
                response = client.toBlocking().exchange(request, Argument.of(TierraSaborProductPojo.class))

            if (response.status().getCode() > 202) throw new HttpClientResponseException(response.status().getReason(), response)

            return response.body()

        }finally{
            client.close()
        }

    }

    private UriBuilder addFilter(UriBuilder uriBuilder, Map<Filter,Object> filters){
        filters.each {key, value ->
            //TODO: refactorize to split the differents FILTERS in a class
            if (key==Filter.COMPANY_ID){
                uriBuilder.queryParam("q",value)
            }else{
                uriBuilder.queryParam("facet",key.name)
                uriBuilder.queryParam("refine.${key.name}", value)
            }
        }
        return uriBuilder
    }

    private UriBuilder limits(UriBuilder uriBuilder, Map<Filter, Object> filter, int rows) {
        return (rows!=10)?uriBuilder.queryParam("rows",rows):uriBuilder
    }

}

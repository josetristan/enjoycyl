package com.jtristan.enjoycyl.network

import com.jtristan.enjoycyl.CultureCalendarClientService
import groovy.transform.CompileStatic
import static com.jtristan.enjoycyl.CultureCalendarClientService.Filter

import javax.validation.constraints.NotNull

@CompileStatic
class UrlService {

    public String addProtocol(String web){
        if (web) {
            web = web.strip()
            if (web.contains("http") == false && web.contains("//www") == false) {
                web = "//" + web
            }
        }else{
            web=""
        }
        return web

    }

   

}

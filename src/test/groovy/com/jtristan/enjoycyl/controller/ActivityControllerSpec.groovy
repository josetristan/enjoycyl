package com.jtristan.enjoycyl.controller

import spock.lang.PendingFeature
import spock.lang.Specification

class ActivityControllerSpec extends Specification{

    @PendingFeature
    def "si la API no devuelve valores se muestra un mensaje"(){
        expect:
            1==2
    }
    @PendingFeature
    def "si la API no devuelve valores se generan propuestas"(){
        expect:
        1==2

    }

}

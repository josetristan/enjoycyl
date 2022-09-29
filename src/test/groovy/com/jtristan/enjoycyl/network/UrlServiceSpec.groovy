package com.jtristan.enjoycyl.network

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class UrlServiceSpec extends Specification implements ServiceUnitTest<UrlService>{

    def setup() {
    }

    def cleanup() {
    }

    void "si la web #web no contine el protocolo añade //"() {
        expect:""
            service.addProtocol(web)==result
        where:
        web                         |   result
        ""                          |  ""
        null                            |  ""
        "www.pagina.com"            |  "//www.pagina.com"
        "//www.pagina.com"          |  "//www.pagina.com"
        "http://www.pagina.com"     |  "http://www.pagina.com"
        "https://www.pagina.com"    |  "https://www.pagina.com"
        "  www.pagina.com"          |  "//www.pagina.com"

    }
}

package com.jtristan

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller:'activity', action:'listProposal')
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}

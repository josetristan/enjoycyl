package com.jtristan.enjoycyl.ui

import com.jtristan.enjoycyl.TierraSaborCompany
import com.jtristan.enjoycyl.network.UrlService
import groovy.transform.CompileStatic



//@CompileStatic
class TierraSaborCardService {

    private final static int MAXIMUN_NUMBER_PRODUCTS = 3

    UrlService urlService
    def grailsApplication

    public String tierraSaborCard(TierraSaborCompany company) {

        def html=""

        def g = grailsApplication.mainContext.getBean('org.grails.plugins.web.taglib.ApplicationTagLib')
        html="<span class=\"product-price tierra-sabor-icon\">${g.img(uri:"/assets/logo-vector-tierra-de-sabor_128_t.jpg")}</span>"

        html+='''<div class="product-desc card-title">
            Disfruta en la zona de estos productos de Tierra de Sabor
            </div>
        '''

        html+=loadProducts(company, MAXIMUN_NUMBER_PRODUCTS)

        html+='''                    
            <div class="product-desc">                         
        '''
        //def g = grailsApplication.mainContext.getBean('org.grails.plugins.web.taglib.ApplicationTagLib')
        //html+="<span class=\"product-price tierra-sabor-icon\">${g.img(uri:"/assets/logo-vector-tierra-de-sabor_128_t.jpg")}</span>"

        html+="<a href=\"#\" class=\"product-name\"> ${company.name}</a>"
        html+='''
                <small class="text-muted">Dirección</small>
                <div class="small m-t-xs">
            '''
        html+="${company?.address}. ${company?.region}. ${company?.locality?.name}"
        html+='''        
                </div>
                <div class="m-t text-righ">
            '''

        if (company.web){
            html+="<a href=\"${urlService.addProtocol(company.web)}\" class=\"btn btn-xs btn-outline btn-primary\">${company.web}<i class=\"fa fa-long-arrow-right\"></i> </a>"
        }
        html+="</div> </div>"

        return html

    }


    private String loadProducts(TierraSaborCompany company, int numberProducts) {
        def html=""
        company.products.eachWithIndex {it, index->
            if (index<numberProducts-1){
                html+="<div>${it.product} </div>"
                html+="<small class=\"text-muted\">${it.brand}</small></br>"
                html+="<small class=\"text-muted\">Variedad:${it.variety}</small>"
            }
        }
        return html
    }
}

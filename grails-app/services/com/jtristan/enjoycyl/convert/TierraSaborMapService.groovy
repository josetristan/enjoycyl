package com.jtristan.enjoycyl.convert

import com.jtristan.enjoycyl.Activity
import com.jtristan.enjoycyl.TierraSaborCompany
import com.jtristan.enjoycyl.TierraSaborProduct
import com.jtristan.enjoycyl.pojo.ActivityPojo
import com.jtristan.enjoycyl.pojo.Record
import com.jtristan.enjoycyl.pojo.TSCompanyRecord
import com.jtristan.enjoycyl.pojo.TSProductRecord
import com.jtristan.enjoycyl.pojo.TierraSaborCompanyPojo
import com.jtristan.enjoycyl.pojo.TierraSaborProductPojo
import groovy.transform.CompileStatic

@CompileStatic
class TierraSaborMapService {

    /**
     * Convert the pojo return by the API to the domain Company
     * @param companyRecord
     * @param productsRecord
     * @return Company
     */
    public TierraSaborCompany mapToCompany(TSCompanyRecord companyRecord, List<TSProductRecord> productsRecord){
        TierraSaborCompany company = new TierraSaborCompany()

        def field = companyRecord.field

        company.idCompany = field.idCompany
        company.locality = Activity.Locality.findByKey(field.locality.toUpperCase())
        company.web = field.web
        company.name = field.name
        company.region = field.region
        company.address = field.address

        productsRecord.each { TSProductRecord it->
            TierraSaborProduct product = new TierraSaborProduct()
            product.product =  it.field.product
            product.section =  it.field.section
            product.qualityType =  it.field.qualityType
            product.brand =  it.field.brand
            product.variety =  it.field.variety
            product.category =  it.field.category
            company.products.add(product)
        }

        return company
    }
}

package com.jtristan.enjoycyl

class TierraSaborCompany {

    static mapWith = 'none'

    String idCompany
    Activity.Locality locality
    String web
    String name
    String region
    String address

    List<TierraSaborProduct> products = new ArrayList<>()

    static constraints = {
    }
}

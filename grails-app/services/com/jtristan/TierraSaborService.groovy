package com.jtristan


import com.jtristan.enjoycyl.RandomHelper
import com.jtristan.enjoycyl.TierraSaborClientService
import com.jtristan.enjoycyl.TierraSaborCompany
import com.jtristan.enjoycyl.command.ActivityCommand
import com.jtristan.enjoycyl.convert.TierraSaborMapService
import com.jtristan.enjoycyl.pojo.TSCompanyRecord
import com.jtristan.enjoycyl.pojo.TSProductRecord
import com.jtristan.enjoycyl.pojo.TierraSaborCompanyPojo
import com.jtristan.enjoycyl.TierraSaborClientService.Filter
import com.jtristan.enjoycyl.pojo.TierraSaborProductPojo
import groovy.transform.CompileStatic

class TierraSaborService {

    TierraSaborClientService tierraSaborClientService
    TierraSaborMapService tierraSaborMapService
    //Get all companies by locality to don't show always the same records
    private final static int MAXIMUN_ROWS = 100
    /**
     * Return a random list of TS products
     * @param activityCommand: use to filter by locality if exists
     * @param rows: maximun number or products to return
     * @return
     */
    def List<TierraSaborCompany> buildRamdon(ActivityCommand activityCommand, int rows) {
        Map<TierraSaborClientService.Filter, Object> filters = buildFilter(activityCommand)

        TierraSaborCompanyPojo pojo = (TierraSaborCompanyPojo)tierraSaborClientService.GET(TierraSaborClientService.TypeTS.COMPANY, filters, MAXIMUN_ROWS)

        List<TierraSaborCompany> companies = selectToAccomplishWithRows(pojo,rows)

        return companies
    }

    def Map<TierraSaborClientService.Filter, Object> buildFilter(ActivityCommand activityCommand) {
        Map<TierraSaborClientService.Filter, Object> filters = new HashMap<>()
        if (activityCommand.locality){
            filters.put(TierraSaborClientService.Filter.LOCALITY, activityCommand.locality)
        }else{
            filters.put(TierraSaborClientService.Filter.LOCALITY, RandomHelper.randomLocality())
        }
        return filters
    }

    /**
     * remove companies and products until get the maximun number of rows
     * @param tierraSaborCompanyPojos
     * @param rows: maximun number of products
     * @return
     */
    def List<TierraSaborCompany> selectToAccomplishWithRows(TierraSaborCompanyPojo companyPojo, int rows) {
        List<TierraSaborCompany> companies = new ArrayList<>()
        //Check that random doesn't repeat the value
        LinkedHashSet<Integer> randoms = new LinkedHashSet<>()
        while(randoms.size()<rows){
             randoms.add(RandomHelper.random(companyPojo.records.size()-1,0))
        }

        if (companyPojo.records.size()>=rows){
            //Select a product for every random company
            randoms.each {Integer it->
                TSCompanyRecord companyRecord = companyPojo.records.get(it)
                TierraSaborProductPojo products = getProducts(companyRecord.field.idCompany)
                int randomProduct = RandomHelper.random(products.records.size()-1,0)
                TSProductRecord productRecord = products.records.get(randomProduct)
                companies.add(tierraSaborMapService.mapToCompany(companyRecord, ArrayList.of(productRecord)))
            }
        }else{
            log.error("There isn't enough product to cover the rows ${rows}")
            companyPojo.records.each { TSCompanyRecord record->
                log.error("Companies: ${record.field.idCompany}")
            }
        }
        /*else{
            //Select several products for a company
            randoms.eachWithIndex {int it, index->
                TSCompanyRecord companyRecord = companyPojo.records.get(it)
                TierraSaborProductPojo products = getProducts(companyRecord.field.idCompany)

                List<TSProductRecord> productsRecord = new ArrayList<>()
                int pendingRows = rows - randoms.size()
                while(pendingRows!=0){
                    if (products.records.size()==pendingRows){
                        productsRecord = products.records
                        pendingRows = 0
                    }else if (products.records.size()>pendingRows){
                        for(int i=0;i<pendingRows-1;i++){
                            productsRecord.add(products.records.get(i))
                        }
                        pendingRows = 0
                    }else{
                        productsRecord = products.records
                        pendingRows = pendingRows - products.records.size()
                    }
                }

                companies.add(tierraSaborMapService.mapToCompany(companyRecord, productsRecord))
            }

        }*/
        return companies

    }



    def TierraSaborProductPojo getProducts(String idCompanay) {
        Map<Filter, Object> filter = HashMap.of(Filter.COMPANY_ID, idCompanay)
        return tierraSaborClientService.GET(TierraSaborClientService.TypeTS.PRODUCT, filter, MAXIMUN_ROWS)
    }
}

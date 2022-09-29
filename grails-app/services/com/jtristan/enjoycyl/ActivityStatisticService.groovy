package com.jtristan.enjoycyl

import com.jtristan.enjoycyl.command.ActivityCommand
import com.jtristan.enjoycyl.pojo.StatisticPojo
import com.jtristan.enjoycyl.repository.ActivityStatisticsRepositoryService
import com.jtristan.enjoycyl.statistic.Period
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import com.jtristan.enjoycyl.CultureCalendarClientService.Filter
import com.jtristan.enjoycyl.Activity.Category

@CompileStatic
//@Transactional(readOnly = true)
class ActivityStatisticService {

    private final static int NUMBER_STATISTIC_RELATED  =1

    ActivityStatisticsRepositoryService activityStatisticsRepositoryService

    /**
     * Generate ramdon statistics related mainly with the filter (locality, category) filtered.
     * Always returns at least one  statistic related with the filter. *
     * Generates a third part of the pending elements. statistics = (Elements = max_elements - numberActivities)/3
     * For example,
     *  activities 4
     *  statistics 3
     *      Related with filter 1
     *      ramdom  2
     *  proposal 5
     * @param activityCommand
     * @return
     */

    public List<List<StatisticPojo>> buildRamdonStatistics(ActivityCommand activityCommand, int numberActivities) {
        int i=1;
        List<List<StatisticPojo>> list = new ArrayList<>()
        int numberStatistics =  (ProposalService.MAXIMUN_ELEMENTS - numberActivities)/3 as int
        numberStatistics =  numberStatistics - NUMBER_STATISTIC_RELATED
        list.add(getRelated(activityCommand))
        while(i<=numberStatistics){
            List<StatisticPojo> statistics = getRamdom()
            //It could be the random selection doesn't return any item so we continue calling db
            if (statistics){
                list.add(getRamdom())
                i++
            }
        }
        return list

    }

    private List<StatisticPojo> getRelated(ActivityCommand command) {
        List<StatisticPojo> list
        def period = randomPeriod()
        if (command.category){
            list = activityStatisticsRepositoryService.getStatistics(command.category, period)
        }else if (command.locality){
            list = activityStatisticsRepositoryService.getStatistics(command.locality, period)
        }else{
            //in case of not request any filter select a random locality
            list = activityStatisticsRepositoryService.getStatistics(RandomHelper.randomLocality(), period)
        }

        if (list)list.get(0).period = period

        return list
    }

    private Period randomPeriod(){
        //(int) (rnd.nextDouble() * cantidad_números_rango + término_inicial_rango)
        Random rnd = new Random();
        def random = (int) (rnd.nextDouble() *  Period.NUMBER + 1)
        switch (random){
            case 1:
                return new Period(Period.TypePeriod.CURRENT_MONTH)
            case 2:
                return new Period(Period.TypePeriod.LAST_YEAR)
            case 3:
                return new Period(Period.TypePeriod.TWO_NEXT_MONTHS)
        }
    }

    /**
     * Return statistics based in whatever criteria and period
     * @param number
     * @return
     */
    def List<StatisticPojo> getRamdom() {
        List<StatisticPojo> list
        ActivityCommand command = new ActivityCommand()
        Filter filter = randomFilter()
        def period = randomPeriod()

        if (filter==Filter.CATEGORY){
            command.category = randomCategory()
            list = activityStatisticsRepositoryService.getStatistics(command.category, period)
        }else if (filter==Filter.LOCALITY){
            command.locality = RandomHelper.randomLocality()
            list = activityStatisticsRepositoryService.getStatistics(command.locality, period)
        }


        /*StatisticPojo pojo
        if (list){
            pojo = list.get(0)
            pojo.period = period
        }

        return pojo
        */
        return list
    }

    private Filter randomFilter(){
        Random rnd = new Random();
        def random = (int) (rnd.nextDouble() *  2 + 1)
        switch (random){
            case 1:
                return Filter.CATEGORY
            case 2:
                return Filter.LOCALITY
            default:
                return Filter.CATEGORY
        }
    }

    private Activity.Category randomCategory(){
        Random rnd = new Random();
        def random = (int) (rnd.nextDouble() *  Activity.Category.values().size() + 1)

        switch (random){
            case 1:
                return Category.SHOWS
            case 2:
                return Category.BOOKS
            case 3:
                return Category.OTHERS
            case 4:
                return Category.CONCERTS
            case 5:
                return Category.EXHIBITIONS
            case 6:
                return Category.CONFERENCES
            default:
                return Category.BOOKS
        }
    }


}

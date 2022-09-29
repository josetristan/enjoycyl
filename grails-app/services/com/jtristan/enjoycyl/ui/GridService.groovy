package com.jtristan.enjoycyl.ui

import com.jtristan.enjoycyl.Activity
import com.jtristan.enjoycyl.RandomHelper
import com.jtristan.enjoycyl.exceptions.ExceptionProposalEmpty
import com.jtristan.enjoycyl.pojo.Proposal
import groovy.transform.CompileStatic

class GridService {

    public final static int STATISTIC = 1
    public final static int TIERRASABOR = 2
    public final static int ACTIVITY = 3

    public final static int GRID_ROWS = 4
    public final static int GRID_COLUMNS = 3

    /**
     * First row: activity activity activity (if exists)
     * @param proposal
     * @return
     */
    @CompileStatic
    def  generateGrid(Proposal proposal) {
        int row = 0
        int column = 0
        Object[][] grid = new Object[GRID_ROWS][GRID_COLUMNS]

        println "PROPOSAL: "

        proposal.activities.each {activity->println activity.class}
        proposal.staticPojos.each {stati-> println stati.class.name}
        proposal.companies.each {company->println  company.class.name}

        //Fill first row
        if (proposal.activities.size()>=3){
            grid = fillAllRowWithActivities(grid,proposal.activities)

        }else{
            if (proposal.activities.size()>0){
                grid[0][0] = addToGrid(grid,proposal,ACTIVITY)
                grid[0][1] = addToGrid(grid,proposal,ACTIVITY)
                grid[0][2] = addToGrid(grid,proposal, randomWithoutActivity())
            }else{
                //If API doesn't return events
                grid[0][0] = addToGrid(grid,proposal,randomWithoutActivity())
                grid[0][1] = addToGrid(grid,proposal,randomWithoutActivity())
                grid[0][2] = addToGrid(grid,proposal, randomWithoutActivity())
            }
        }
        row=1
        column=0
        //Fill rest of rows with random positions but if activities exists at least select one
        def selected
        boolean activityAssigned
        for(row;row<GRID_ROWS;row++){
            activityAssigned=false
            for(column=0;column<GRID_COLUMNS;column++){
                println "row ${row} column ${column}"
                try{
                    if (activityAssigned==false&&proposal.activities.size()>0){
                        println "ACTIVITY ASSIGNED false"
                        grid[row][column]=addToGrid(grid,proposal,ACTIVITY)
                        activityAssigned=true
                    }else{
                        if (proposal.activities.size()>0){
                            selected = addToGrid(grid,proposal,randomWithActitity())
                        }else{
                            selected = addToGrid(grid,proposal,randomWithoutActivity())
                        }
                        println "ACTIVITY ASSIGNED false " + selected
                        if (selected instanceof Activity) activityAssigned=true
                        grid[row][column]=selected
                        //println "Grid result: " + (grid[row][column]).class.name
                    }
                }catch(ExceptionProposalEmpty e){
                    log.error("It doesn't recived 12 proposals")
                    return grid
                }
            }
        }

        return grid
    }

    def Object[][] fillAllRowWithActivities(Object[][] grid, List<Activity> activities) {
        0.step(3,1){
            grid[0][it] = activities.get(0)
            activities.remove(0)
        }

        return grid
    }
    @CompileStatic
    private int randomWithActitity(){
        def random = RandomHelper.random(3)
        if (random==1)return STATISTIC
        if (random==2)return TIERRASABOR
        if (random==3)return ACTIVITY
    }
    def int randomWithoutActivity(){
        def random = RandomHelper.random(2)
        if (random==1)return STATISTIC
        if (random==2)return TIERRASABOR
    }

    /**
     * Search the object of the type proposal to complete the column in the grid and remove it. If the type proposal
     * doesn't exist search a different one until find a new one.
     * @param objects: grid
     * @param proposal
     * @param type: type of proposal; statistic, tierrasabor, activity
     * @return grid
     */
    def addToGrid(Object[][] grid, Proposal proposal, int type) {

        println "type: ${type}"
        def prop

        if (type == ACTIVITY) {
            prop = getProposal(proposal.activities)
            println "prop activity:  ${prop?.class?.name}"
            if (prop == null) type = randomType(ACTIVITY)
        }
        if (type == STATISTIC) {
            prop = getProposal(proposal.staticPojos)
            println "prop statistic:  ${prop?.class?.name}"
            if (prop == null) type = randomType(STATISTIC)
        }
        if (type == TIERRASABOR) {
            prop = getProposal(proposal.companies)
            println "prop tierra sabor:  ${prop?.class?.name}"
            if (prop == null) type = randomType(TIERRASABOR)
        }

        println "size activities: ${proposal.activities.size()} statistics: ${proposal.staticPojos.size()} companies: ${proposal.companies.size()}"

        if (prop == null && proposal.activities.size() == 0 && proposal.staticPojos.size() == 0 && proposal.companies.size() == 0) {
            println "exception}"
            throw new ExceptionProposalEmpty()
        }

        if (prop == null && hasOneElement(proposal) == true) {
            println "hasOneElement y prop null"
            prop = getUniquePendingProposal(proposal)
        }

        println "Prop: ${prop} -  type:${type}"

        if (prop == null && type != 0) {
            println "entro"
            addToGrid(grid, proposal, type)
        }

        println "Prop2: ${prop} type ${type}"


        println "------------------------------------"
        return prop
    }


    def  getProposal(proposals) {
        def prop=null
        println "antes remove ${proposals.size()}"
        if (proposals.size()>0){
            //prop = proposals.get(0)
            prop = proposals.remove(0)
        }
        println "después remove ${proposals.size()}"
        println "PROPO ${prop}"
        return prop
    }

    /**
     * get a random type excluding the
     * @param activityClass
     */
    def randomType(int excluded) {
        def random=excluded
        while (random==excluded){
            random = RandomHelper.random(3)
        }
        return random
    }
    def boolean hasOneElement(Proposal proposal) {
        println "hasOneElement : companies ${proposal.companies?.size()} statistic: ${proposal.staticPojos?.size()}"
        int elements = proposal.activities?.size() +proposal.staticPojos?.size() +proposal.companies?.size()

        return (elements==1)?true:false

    }
    def getUniquePendingProposal(Proposal proposal) {
        println "uniquePendingProposal : companies ${proposal.companies?.size()} statistic: ${proposal.staticPojos?.size()}"
        if (proposal.activities?.size()==1) return proposal.activities.get(0)
        if (proposal.getStaticPojos()?.size()==1) return proposal.getStaticPojos().get(0)
        if (proposal.companies?.size()==1) return proposal.companies.get(0)
    }


}

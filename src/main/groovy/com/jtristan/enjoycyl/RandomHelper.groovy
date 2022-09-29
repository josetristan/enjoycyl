package com.jtristan.enjoycyl

class RandomHelper {

    /**
     * return a random locality
     * @return
     */
    public static Activity.Locality randomLocality(){
        Random rnd = new Random();
        def random = (int) (rnd.nextDouble() *  9 + 1)

        switch (random){
            case 1:
                return Activity.Locality.LEON
            case 2:
                return Activity.Locality.PALENCIA
            case 3:
                return Activity.Locality.BURGOS
            case 4:
                return Activity.Locality.SORIA
            case 5:
                return Activity.Locality.AVILA
            case 6:
                return Activity.Locality.SEGOVIA
            case 7:
                return Activity.Locality.SALAMANCA
            case 8:
                return Activity.Locality.ZAMORA
            case 9:
                return Activity.Locality.VALLADOLID
            default:
                return Activity.Locality.VALLADOLID
        }
    }


    /**
     * Generate a random number
     * @param size: maximun number to get
     * @param minimunValue: minimun number to get. Default 1
     * @return
     */
    public static Integer random(int size, int minimunValue=1){
        Random rnd = new Random();
        return  (Integer)  (rnd.nextDouble() *  size + minimunValue)
    }





}

package com.jtristan.enjoycyl

/**
 * Count the number of attendand to an event and the rated for the event
 */
class Attendant {

    Statistic statistic
    Long eventId
    int adults
    int children

    SpanishLocality locality
    String region

    /**
     * Pick up the cuantitative opinion about the several data related with the activity.
     */
    int placeRated
    int timeRated
    int eventRated

    static mapping = {

    }

    static constraints = {
        placeRated range: 0..5
        timeRated range: 0..5
        eventRated range: 0..5
    }

    public enum SpanishLocality{
        ALB(2,	'Albacete'),
        ALI(3,'Alicante/Alacant'),
        ALM(4,'Almería',),
        ALA(1,'Araba/Álava'),
        AST(33,'Asturias'),
        AVI(5,'Ávila'),
        BAD(6,'Badajoz'),
        BAL(7,'Balears, Illes'),
        BAR(8,'Barcelona'),
        BIZ(48,'Bizkaia'),
        BUR(9,'Burgos'),
        CAC(10,'Cáceres'),
        CAD(11,'Cádiz'),
        CAN(39,'Cantabria'),
        CAS(12,'Castellón/Castelló'),
        CIU(13,'Ciudad Real'),
        COR(14,'Córdoba'),
        CORU(15,'Coruña, A'),
        CUE(16,'Cuenca'),
        GIP(20,'Gipuzkoa'),
        GIR(17,'Girona'),
        GRA(18,'Granada'),
        GUA(19,'Guadalajara'),
        HUE(21,'Huelva'),
        HUES(22,'Huesca'),
        JAE(23,'Jaén'),
        LEO(24,'León'),
        LLE(25,'Lleida'),
        LUG(27,'Lugo'),
        MAD(28,'Madrid'),
        MAL(29,'Málaga'),
        MUR(30,'Murcia'),
        NAV(31,'Navarra'),
        OUR(32,'Ourense'),
        PAL(34,'Palencia'),
        PALM(35,'Palmas, Las'),
        PON(36,'Pontevedra'),
        RIO(26,'Rioja, La'),
        SAL(37,'Salamanca'),
        SANT(38,'Santa Cruz de Tenerife'),
        SEG(40,'Segovia'),
        SEV(41,'Sevilla'),
        SOR(42,'Soria'),
        TAR(43,'Tarragona'),
        TER(44,'Teruel'),
        TOL(45,'Toledo'),
        VALE(46,'Valencia/València'),
        VAL(47,'Valladolid'),
        ZAM(49,'Zamora'),
        ZAR(50,'Zaragoza'),
        CEU(51,'Ceuta'),
        MEL(52,'Melilla')

        private int code
        private String description

        public SpanishLocality(int code, String description){
            this.code = code
            this.description = description
        }

        public getCode(){return this.code}
        public getDescription(){return  this.description}

        String getKey() { name() }
    }
}

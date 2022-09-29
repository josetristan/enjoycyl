package com.jtristan.enjoycyl
import java.time.LocalDate

class Activity implements Comparable<Activity>{

    static mapWith = 'none'

    String eventId
    String title
    String celebrationPlace
    String region
    Category category
    String address
    Boolean isLibraryEvent
    LocalDate startDate
    LocalDate endDate
    String urlImage
    Locality locality
    String description
    String urlContent

    @Override
    int compareTo(Activity o) {
        if (this.startDate == null || o.startDate == null) {
            return 0;
        }
        return this.startDate.compareTo(o.startDate)
    }

    public enum Category{
        SHOWS('Espectáculos'),
        BOOKS('Libros y Lectura'),
        OTHERS('Otros'),
        CONCERTS('Conciertos'),
        EXHIBITIONS('Exposiciones'),
        CONFERENCES('Conferencias y Cursos'),
        CINEMA('Cine')

        String name
        public Category(name){ this.name = name }
        String getName() { return name }
        void setName(String name) { this.name = name }

        private static final Map<String,Category> map;
        static {
            map = new HashMap<String,Category>();
            for (Category v : Category.values()) {
                map.put(v.name, v);
            }
        }
        public static Category findByKey(String name) {
            return map.get(name);
        }
    }



    public enum Locality{
        AVILA('Ávila'),
        BURGOS('Burgos'),
        LEON('León'),
        PALENCIA('Palencia'),
        SALAMANCA('Salamanca'),
        SEGOVIA('Segovia'),
        SORIA('Soria'),
        VALLADOLID('Valladolid'),
        ZAMORA('Zamora')

        public name
        public Locality(name){ this.name = name }
        String getName() { return name }
        void setName(String name) { this.name = name }

        private static final Map<String,Category> map;
        static {
            map = new HashMap<String,Locality>();
            for (Locality v : Locality.values()) {
                map.put(v.name.toUpperCase(), v);
            }
        }
        public static Locality findByKey(String name) {
            return map.get(name);
        }
    }

    public setLibraryEvent(String value){
        this.isLibraryEvent = value.toUpperCase().equals("SI")?true:false
    }

}

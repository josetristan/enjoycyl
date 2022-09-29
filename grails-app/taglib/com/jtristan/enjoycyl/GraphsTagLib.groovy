package com.jtristan.enjoycyl

class GraphsTagLib {
    static namespace = "cyl"

    public enum Type{
        LOCALITY,
        CATEGORY,
        MONTH
    }

    /**
     *  id: chart id
     *  data: ArrayList<object>:
     *      index 0: sum of likes
     *      index 1: label data:
     *  type: label type; locality, category, month
     *  labelTitle: etiqueta label
     */
    def barChart = { attrs ->
        int i = 0


        if (!attrs?.id) throwTagError('The id are necessary to draw the graph')
        //if (!attrs?.data) throwTagError('The data are necessary to draw the graph')
        if (!attrs?.type) throwTagError('The type are necessary to draw the graph')

        String id = attrs?.id
        def data = attrs?.data
        Type type  =attrs.type
        def labelTitle = attrs?.labelTitle

        if (data){

            def script = """
    
                <script>
                    const ctx${id} = document.getElementById('${id}').getContext('2d');
                    const myChart${id} = new Chart(ctx${id}, {
                        type: 'bar',                    
                        data: {        
                        labels: [${getLabel(data, type)}],
                        datasets: [{
                            label: '# ${labelTitle}',            
                            data: [${getValue(data)}],
                            backgroundColor: [                
                                'rgba(255, 99, 132, 0.2)',
                                'rgba(54, 162, 235, 0.2)',
                                'rgba(255, 206, 86, 0.2)',
                                'rgba(75, 192, 192, 0.2)',
                                'rgba(153, 102, 255, 0.2)',
                                'rgba(255, 159, 64, 0.2)'
                            ],
                            borderColor: [
                                'rgba(255, 99, 132, 1)',
                                'rgba(54, 162, 235, 1)',
                                'rgba(255, 206, 86, 1)',
                                'rgba(75, 192, 192, 1)',
                                'rgba(153, 102, 255, 1)',
                                'rgba(255, 159, 64, 1)'
                            ],
                            borderWidth: 1
                        }]
                    },
                    options: {
                        scales: {
                            y: {
                                beginAtZero: true
                            }
                        }
                    }
                });
                </script>
    
    
            """
        out<<script

        }
        return out

    }


    /**
     *  id: chart id
     *  data: ArrayList<object>:
     *      index 0: sum of likes
     *      index 1: label data:
     *  type: label type; locality, category, month
     *  labelTitle: etiqueta label
     */
    def lineChart = { attrs ->
        int i = 0


        if (!attrs?.id) throwTagError('The id are necessary to draw the graph')
        //if (!attrs?.data) throwTagError('The data are necessary to draw the graph')
        if (!attrs?.type) throwTagError('The type are necessary to draw the graph')

        String id = attrs?.id
        def data = attrs?.data
        Type type  =attrs.type
        def labelTitle = attrs?.labelTitle

        if (data){

            def script = """
    
                <script>
                    const ctx${id} = document.getElementById('${id}').getContext('2d');
                    const myChart${id} = new Chart(ctx${id}, {
                        type: 'line',                    
                        data: {        
                        labels: [${getLabel(data, type)}],
                        datasets: [{
                            label: '# ${labelTitle}',            
                            data: [${getValue(data)}],                            
                            borderColor: 'rgba(255, 99, 132, 1)',
                            fill:false,
                            tension: 0.1                            
                        }]
                    }
                });
                </script>
    
    
            """
            out<<script

        }
        return out

    }

    /**
     *  id: chart id
     *  data: ArrayList<object>:
     *      index 0: sum of likes
     *      index 1: label data:
     *  type: label type; locality, category, month
     *  labelTitle: etiqueta label
     */
    def pieChart = { attrs ->
        int i = 0


        if (!attrs?.id) throwTagError('The id are necessary to draw the graph')
        //if (!attrs?.data) throwTagError('The data are necessary to draw the graph')
        if (!attrs?.type) throwTagError('The type are necessary to draw the graph')

        String id = attrs?.id
        def data = attrs?.data
        Type type  =attrs.type
        def labelTitle = attrs?.labelTitle

        if (data){

            def script = """
    
                <script>
                    const ctx${id} = document.getElementById('${id}').getContext('2d');
                    const myChart${id} = new Chart(ctx${id}, {
                        type: 'pie',                    
                        data: {        
                        labels: [${getLabel(data, type)}],
                        datasets: [{
                            label: '# ${labelTitle}',            
                            data: [${getValue(data)}],
                            backgroundColor: [                
                                'rgba(255, 99, 132, 0.2)',
                                'rgba(54, 162, 235, 0.2)',
                                'rgba(255, 206, 86, 0.2)',
                                'rgba(75, 192, 192, 0.2)',
                                'rgba(153, 102, 255, 0.2)',
                                'rgba(255, 159, 64, 0.2)'
                            ],
                            hoverOffset: 4
                        }]
                    }
                });
                </script>
    
    
            """
            out<<script

        }
        return out

    }

    /**
     * labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
     * @param data
     * @param type
     * @return
     */
    private String getLabel(List<Object> data, Type type){
        StringBuilder sb = new StringBuilder()
        data.each{
            if (it[1]){
                if (sb.size()>0)sb.append(",")
                if (type==Type.LOCALITY || type==Type.CATEGORY){
                    sb.append("'").append(it[1].name).append("'")
                }else if (type==Type.MONTH){
                    sb.append("'").append(it[1]).append("'")
                }
            }
        }
        return sb.toString()
    }

    /**
     * data: [${getLabel(data, type)}][12, 19, 3, 5, 2, 3],
     * @param data
     * @return
     */
    private String getValue(List<Object> data){
        StringBuilder sb = new StringBuilder()
        data.each{
            if (it[1]) {
                if (sb.size() > 0) sb.append(",")
                sb.append(it[0].toString())
            }
        }
        return sb.toString()
    }
}

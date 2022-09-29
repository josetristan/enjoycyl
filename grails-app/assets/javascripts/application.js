// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better
// to create separate JavaScript files as needed.
//
//= require jquery-3.5.1.min
//= require popper.min
//= require bootstrap
//= require_tree plugins
//= require_self

//$( document ).ready(function(){

   // removeOldLikes();
    //markEventsAsLike();
    $(".like").click(function(){
        if ($( this ).css("color")=='rgb(103, 106, 108)'){ //white
            setColor(this,"green");
            saveLike(this);
            postLike(this.dataset.eventid);
        }else{
            setColor(this,"inherit");
            //$( this ).css("color","inherit");
        }
    })

    function setColor(element,color){
        $( element ).css("color",color);
    }

    function saveLike(like){
        let index = 0;
        let eventIdsLike = [];
        if (localStorage.eventIdsLike!==undefined){
            eventIdsLike = JSON.parse(localStorage.eventIdsLike);
            index = eventIdsLike.length;
        }

        eventIdsLike[index]=like.dataset.eventid;

        localStorage.setItem("eventIdsLike", JSON.stringify(eventIdsLike));
    }

    function saveRated(eventId){
        let index = 0;
        let eventIdsLike = [];
        if (localStorage.eventIdsUserOpinion!==undefined){
            eventIdsLike = JSON.parse(localStorage.eventIdsUserOpinion);
            index = eventIdsLike.length;
        }

        eventIdsLike[index]=eventId.dataset.eventid;

        localStorage.setItem("eventIdsUserOpinion", JSON.stringify(eventIdsLike));
    }

    function markEventsAsLike(){

        let eventIdsLike = [];
        if (localStorage.eventIdsLike!==undefined){
            eventIdsLike = JSON.parse(localStorage.eventIdsLike);
        }

        eventIdsLike.forEach(function(eventId){
            let element =document.querySelectorAll('[data-eventid~="'+eventId+'"]');
            if (element){
                setColor(element,"green");
            }
        });
    }

    function markEventsAsRated(){

        let eventIdsLike = [];
        if (localStorage.eventIdsUserOpinion!==undefined){
            eventIdsLike = JSON.parse(localStorage.eventIdsUserOpinion);
        }

        eventIdsLike.forEach(function(eventId){
            let element =document.querySelectorAll('[data-eventid~="'+eventId+'"]');
            if (element){
                element[0].style.display="none"
            }
        });
    }

    /**
     * We only keep a maximun of 200 likes
     */
    function removeOldLikes(){
        let eventIdsLike = [];
        if (localStorage.eventIdsLike!==undefined){
            eventIdsLike = JSON.parse(localStorage.eventIdsLike);
        }

        if (eventIdsLike.length>200){
            let indexToRemove = 200 - eventIdsLike.length;
            eventIdsLike.splice(0,indexToRemove);
        }
        localStorage.setItem("eventIdsLike", JSON.stringify(eventIdsLike));
    }

    /**
     * We only keep a maximun of 200 user opinions
     */
    function removeOldUserOpinions(){
        let eventIdsLike = [];
        if (localStorage.eventIdsUserOpinion!==undefined){
            eventIdsLike = JSON.parse(localStorage.eventIdsUserOpinion);
        }

        if (eventIdsLike.length>200){
            let indexToRemove = 200 - eventIdsLike.length;
            eventIdsLike.splice(0,indexToRemove);
        }
        localStorage.setItem("eventIdsUserOpinion", JSON.stringify(eventIdsLike));
    }

    function postLike(eventId, contextPath){
        window.appContext = contextPath;
        let url = '/activity/like';
        if (contextPath){
            url = '/'+contextPath+'/activity/like';
        }
        $.ajax({
            type: "POST",
            url: url,
            data: {eventId:eventId}
        });
    }

    function saveLocation(locality, region){
        localStorage.setItem("locality", locality);
        localStorage.setItem("region", region);
    }

    function readLocation(){
        let locality =  localStorage.getItem("locality")
        let region =  localStorage.getItem("region")
        return {
            locality: locality,
            region: region
        }
    }




//})

/*
 * Funcionalidades javascript na visão do paciente
 */


$("document").ready(function(){
	chargeEvents();
	chargeServices();
	//chargeScheduleDay(id);


});

function initCalendarPatient(json){

	console.log(json);

	$("#calendar-patient").fullCalendar({

		header: {
			left: 'prev',
			center: 'title',
			right: 'next'
		},

		businessHours: true,
		editable: false,
		dayClick: null,
		eventClick: null,

		dayClick: function(date, jsEvent, view, event) {

			$("#modal-day").modal('show');
			$(".modal-title").html(date.format('DD/MM/YYYY'));  

		},


		events: json,
		eventClick: function(event, jsEvent, view ){
			chargeScheduleDay(event.id);
			$("#modal-day").modal('show');
		}

	});


}

function chargeScheduleDay(id){

	ajaxCall("/siac/search/patient/scheduleday?id="+id, function(json){

		var hour;
		var state;

		console.log(JSON.stringify(json));

		$.each(json, function(name, value){

			if(name=="hour"){
				hour = value;
			}
			if(name=="status"){
				state = value;
			}					


		});

		$("#table-schedule").append("<tr> <td>" +hour+ " </td> <td>" +state+ "</td> </tr>");
	});
}


function chargeEvents(){
	var params = new Object();
	params["pat"] = 123; 
	var j;
	ajaxCall("/siac/search/patient/consultations?cpf="+params["pat"], function(json){
		j = json;
		initCalendarPatient(json);
	})
}


function chargeServices(){

	ajaxCall("/siac/getServices", function(json){

		var serviceName;
		var serviceActive;
		var serviceId;

		json.sort(function (obj1, obj2) {
			return obj1.name < obj2.name ? -1 :
				(obj1.name > obj2.name ? 1 : 0);
		});

		$.each(json, function(key, obj){

			$.each(obj, function(name, value){
				if(name=="name"){
					serviceName = value;
				}
				if(name=="active"){
					serviceActive = value;
				}
				if(name=="id"){
					serviceId = value;
				}
			})


			$("#ul-services").append("<li class='service'><a class='link-service' id='"+serviceId+"'>"+serviceName+"</a></li>");

		});


	});

}


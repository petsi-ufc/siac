/*
 * Funcionalidades javascript na visão do paciente
 */


$("document").ready(function(){
	chargeEvents();
	chargeServices();
	myConsultations();
	myCalendar();
	addRating();
	onServiceClick()
});

function initCalendarPatient(json){


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
	$(".tr-horary").remove();

	ajaxCall("/siac/search/patient/scheduleday?id="+id, function(json){

		var hour;
		var state;

		$.each(json, function(name, value){

			if(name=="hour"){
				hour = value;
			}
			if(name=="status"){
				state = value;
			}					

		});

		if(state == "Agendado"){
			$("#table-schedule").append("<tr class='tr-horary'> <td>" +hour+ " </td> <td style= 'background-color: #4682B4; color: white'>" +state+ "</td> </tr>");
		}
		if(state == "Cancelado"){
			$("#table-schedule").append("<tr class='tr-horary'> <td>" +hour+ " </td> <td style= 'background-color: #FF0000; color: white'>" +state+ "</td> </tr>");
		}
		if(state == "Realizado"){
			$("#table-schedule").append("<tr class='tr-horary'> <td>" +hour+ " </td> <td style= 'background-color: grey; color: white'>" +state+ "</td> </tr>");
		}
		if(state == "Reservado"){
			$("#table-schedule").append("<tr class='tr-horary'> <td>" +hour+ " </td> <td style= 'background-color: #D9D919; color: black'>" +state+ "</td> </tr>");
		}

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


			$("#ul-services").append("<li class='service'><a class='link-service social-service' id='"+serviceId+"'>"+serviceName+"</a></li>");

		});


	});

}


function myConsultations(){
	$("#my-consults").click(function() {
		$("#calendar-patient").css("display", "none");
		$("#my-consultations").css("display", "block");
	});

	var params = new Object();
	params["pat"] = 123; 
	var j;
	ajaxCall("/siac/search/patient/consultations?cpf="+params["pat"], function(json){
	

		var service;
		var date;
		var horary;
		var state;
		var id_cons;

		$.each(json, function(key, obj){

			$.each(obj, function(name, value){
				if(name=="start"){
					date = value;
				}
				if(name=="title"){
					service = value;
				}
				if(name=="hour"){
					horary = value;
				}
				if(name=="state"){
					state = value;
				}
				if(name=="id"){
					id_cons = value;
				}
			})
			
			

			$("#my-consultations-table").append("<tr>" +
					"<td>"+service+"</td><td>"+date+"</td><td>"+horary+"</td><td>"+state+"</td>" +
					"<td> <div class='btn-group'> <button type='button' class='btn btn-warning rating-button-id' id='"+id_cons+"' data-target='#modal-rating' data-toggle='modal'>Avaliar</button></td>"
			+" <td><button type='button' class='btn btn-danger'> Cancelar </button> </td></tr>");

		});
		
		addRating();
	})
}

function myCalendar(){
	$("#my-calend").click(function() {
		$("#calendar-patient").css("display", "block");
		$("#my-consultations").css("display", "none");
	});
}


function addRating(){
	
//	$("#cancel-rating").click(function(){
//		$("#my-calend").modal('fade');
//	});

	$(".rating-button-id").click(function(){
		$("#input-rating-id").val($(this).attr("id"));
			console.log("oi");
	});

	$("#save-rating").click(function(){		
		var params = new Object();
		params["rating.rating"] = $("#rating-grade").val();
		params["rating.comment"] = $("#rating-comment").val();
		params["id"] =  $("#input-rating-id").val();
		
		ajaxCall("/siac/updateConsultationRating", params);
		$("#my-calend").modal('fade');

	});
}

function onServiceClick(){
	
	$(document).on("click", ".link-service", function(){
		$(".service").removeClass("active");
		$(this).parent().addClass("active");
		
		if($(this).hasClass("social-service")){
			var idSocialService = $(this).attr("id");
			
			ajaxCall("/siac//getConsultationBySocialService?id="+idSocialService, function(json){
				
				$(".calendar").remove();
				$(".content-calendar").append($("<div class='calendar' id='calendar-patient'></div>"));
				
				initCalendarPatient(json);
				
			});
			
		}
	});
	
}
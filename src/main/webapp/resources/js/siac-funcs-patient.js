/*
 * Funcionalidades javascript na visão do paciente
 */


$("document").ready(function(){
	chargeEvents();
	chargeServices();
	myConsultations();
	myCalendar();
	addRating();
	onServiceClick();
	scheduleConsultation();
	showRating();
	cancelConsultation();
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
			$("#modal-event").modal('show');
		}

	});


}

function chargeScheduleDay(id){
	$(".tr-horary").remove();
	

	ajaxCall("/siac/getConsultationById?id="+id, function(json){
		
		var hour;
		var state;
		var  is_rating_null;

		$.each(json, function(name, value){

			if(name=="hour"){
				hour = value;
			}
			if(name=="state"){
				state = value;
			}
			if(name=="isRatingNull"){
				is_rating_null = value;
			}

		});

		if(state == "Agendado"){

			var newRow = $("<tr class='tr-horary info'> </tr>");
			newRow.append($("<td>" +hour+ "</td>"));
			newRow.append($("<td>" +state+ "</td>"));
			newRow.append($("<td><button type='button' class='btn btn-danger btn-sm' data-id='"+id+"' id='cancel-consultation'>Cancelar</button></td>"));
			$("#body-table-event").append(newRow);
			
		}else if(state == "Cancelado"){

			var newRow = $("<tr class='tr-horary danger'> </tr>");
			newRow.append($("<td>" +hour+ "</td>"));
			newRow.append($("<td>" +state+ "</td>"));
			newRow.append($("<td> - </td>"));
			$("#body-table-event").append(newRow);

		}else if(state == "Realizado"){

			var newRow = $("<tr class='tr-horary active'> </tr>");
			newRow.append($("<td>" +hour+ "</td>"));
			newRow.append($("<td>" +state+ "</td>"));
			if(is_rating_null){
				newRow.append($("<td><button type='button' class='btn btn-success btn-sm rating-button' id='"+id+"' data-target='#modal-rating' data-toggle='modal'>Avaliar</button></td>"));
			}else{
				newRow.append($("<td><button type='button' class='btn btn-success show-rating' data-id='"+id+"'>Ver avaliação</button></td>"));
			}
			
			$("#body-table-event").append(newRow);

		}else if(state == "Reservado"){
			$("#body-table-event").append("<tr class='tr-horary warning'> <td>" +hour+ " </td> <td>" +state+ "</td> </tr>");
		}else if(state == "Disponivel"){
			var newRow = $("<tr class='tr-horary success'></tr>");
			newRow.append($("<td>" +hour+ " </td>"));
			newRow.append($("<td>Disponível</td>"));
			newRow.append($("<td><button type='button' class='btn btn-primary btn-sm' data-id='"+id+"' id='schedule-consultation'>Agendar</button></td>"));

			$("#body-table-event").append(newRow);

		}else if(state == "Ocupado"){
			var newRow = $("<tr class='tr-horary warning'> </tr>");
			newRow.append($("<td>" +hour+ "</td>"));
			newRow.append($("<td>" +state+ "</td>"));
			newRow.append($("<td><button type='button' class='btn btn-info btn-sm' data-id='"+id+"' id='reserve-consultation'>Reservar</button></td>"));
			$("#body-table-event").append(newRow);
		}


	});
}

function chargeEvents(){

	ajaxCall("/siac/getMyConsultations", function(json){
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


			$("#ul-services").append("<li class='service'><a class='link-service social-service service-item' id='"+serviceId+"'>"+serviceName+"</a></li>");

		});


	});

}


function myConsultations(){
	$("#my-consults").click(function() {
		$(".content-calendar").css("display", "none");
		$("#my-consultations").css("display", "block");

		$(".tr-my-consultations").remove();
		
		ajaxCall("/siac/getMyConsultations", function(json){

			var service;
			var date;
			var horary;
			var state;
			var id_cons;
			var is_rating_null;

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
					if(name=="isRatingNull"){
						is_rating_null = value;
					}
				});

				var newRow = $("<tr class='tr-my-consultations'></tr>");
				newRow.append($("<td>"+service+"</td>"));
				newRow.append($("<td>"+date+"</td>"));
				newRow.append($("<td>"+horary+"</td>"));
				newRow.append($("<td>"+state+"</td>"));

				if(state == "Realizado" && !is_rating_null){
					newRow.append($("<td><button type='button' class='btn btn-success show-rating' data-id='"+id_cons+"'>Ver avaliação</button></td>"));
				}else if(state == "Realizado"){
					newRow.append($("<td><button type='button' class='btn btn-warning rating-button' id='"+id_cons+"' data-target='#modal-rating' data-toggle='modal'>Avaliar</button></td>"));
				}

				if(state == "Agendado"){
					newRow.append($("<td><button type='button' class='btn btn-danger' id='cancel-consultation' data-id='"+id_cons+"'> Cancelar </button> </td>"));
				}else{
					newRow.append($("<td><button disabled='disabled' type='button' class='btn btn-danger'> Cancelar </button> </td>"));
				}

				$("#my-consultations-table").append(newRow);

			});
		});
	});
}

function myCalendar(){
	$("#my-calend").click(function() {
		$(".content-calendar").css("display", "block");
		$("#my-consultations").css("display", "none");

		$(".calendar").remove();
		$(".content-calendar").append($("<div class='calendar' id='calendar-patient'></div>"));

		chargeEvents();
	});
}


function addRating(){

	$(document).on("click", ".rating-button",function(){
		$("#input-rating-id").val($(this).attr("id"));
	});

	$("#save-rating").click(function(){		
		var params = new Object();
		params["rating.rating"] = $("#rating-grade").val();
		params["rating.comment"] = $("#rating-comment").val();
		params["id"] =  $("#input-rating-id").val();

		ajaxCall("/siac/updateConsultationRating", params);
		$("#modal-rating").modal('hide');
		
		location.reload();

	});
}

function onServiceClick(){

	$(document).on("click", ".link-service", function(){
		$(".service").removeClass("active");
		$(this).parent().addClass("active");

		if($(this).hasClass("social-service")){
			$("#my-consultations").css("display", "none");
			var idSocialService = $(this).attr("id");

			ajaxCall("/siac/getConsultationBySocialService?id="+idSocialService, function(json){
				$(".content-calendar").css("display", "block");
				$(".calendar").remove();
				$(".content-calendar").append($("<div class='calendar' id='calendar-patient'></div>"));

				initCalendarPatient(json);

			});

		}
	});

}

function scheduleConsultation(){
	$(document).on("click", "#schedule-consultation", function(){

		var id = $(this).attr("data-id");

		ajaxCall("/siac/scheduleConsultation?id="+id, function(json){

			$.each(json, function(name, value){

				if(name=="id"){
					hour = value;
				}

				$("#id-service-temp").val(value);

			});

		});

		location.reload();

//		var idService = $("#id-service-temp").val();

//		ajaxCall("/siac/getConsultationBySocialService?id="+idService, function(json){

//		$(".calendar").remove();
//		$(".content-calendar").append($("<div class='calendar' id='calendar-patient'></div>"));

//		initCalendarPatient(json);

//		});

		$('#modal-event').modal('hide');
	});
}


function showRating(){

	$(document).on("click",".show-rating", function(){
		
		$("#content-rating").remove();
		$(".modal-body-rating").append($("<div id='content-rating'></div>"));
		
		var id_consultation = $(this).attr("data-id");

		var comment;
		var rating;

		ajaxCall("/siac/showRating?id="+id_consultation, function(json) {

			$.each(json, function(name, value){

				if(name=="comment"){
					comment = value;
				}

				if(name="rating"){
					rating = value;
				}
			});

			$("#content-rating").append("<h4><strong>Comentário:</strong></h4>");
			$("#content-rating").append(comment);
			$("#content-rating").append("<h4><strong>Nota</strong>: " + rating+"</h4>");

		});

		$("#modal-read-rating").modal('show');
	});
}

function cancelConsultation(){

	$(document).on("click","#cancel-consultation", function(){
		var id = $(this).attr("data-id");
		ajaxCall("/siac/cancelConsultation?id="+id, function(){

		});
		location.reload();
	});
}
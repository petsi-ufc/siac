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
	reserveConsultation();
	cancelReserve();
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
			$("#my-calendar-title").html("Meu Calendário");
			$("#modal-event").modal('show');
		},

		eventMouseover: function (event, jsEvent, view){

			$("#aaaa").tooltip();	
		}

	});

}

function chargeScheduleDay(id){
	$(".tr-horary").remove();

	ajaxCall("/siac/getConsultationById?id="+id, function(json){

		var hour;
		var state;
		var  is_rating_null;
		var idReserve;

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
			if(name=="idReserve"){
				idReserve = value;
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
			var newRow = $("<tr class='tr-horary warning'> </tr>");
			newRow.append("<td>" +hour+ " </td> <td>" +state+ "</td>");
			newRow.append($("<td><button type='button' class='btn btn-danger btn-sm cancel-reserve' data-id-reserve='"+idReserve+"' id='cancel-reserve'>Cancelar</button></td>"));
			$("#body-table-event").append(newRow);

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
			newRow.append($("<td><button type='button' class='btn btn-info btn-sm reserve-button' data-id='"+id+"' id='reserve-consultation'>Reservar</button></td>"));
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


			$("#ul-services").append("<li class='service'><a class='link-service social-service service-item' id='"+serviceId+"' data-name='"+serviceName+"'>"+serviceName+"</a></li>");

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
			var idReserve;

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
					if(name=="idReserve"){
						idReserve = value;
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
					newRow.append($("<td><button type='button' class='btn btn-warning' disabled='disabled'> Avaliar </button> </td>"));
					newRow.append($("<td><button type='button' class='btn btn-danger' id='cancel-consultation' data-id='"+id_cons+"'> Cancelar </button> </td>"));
				}else if(state == "Realizado"){
					newRow.append($("<td><button disabled='disabled' type='button' class='btn btn-danger'> Cancelar </button> </td>"));
				}
				else if(state == "Reservado"){
					newRow.append($("<td><button type='button' class='btn btn-warning' disabled='disabled'> Avaliar </button> </td>"));
					newRow.append($("<td><button type='button' class='btn btn-danger cancel-reserve' data-id-reserve='"+idReserve+"'> Cancelar </button> </td>"));
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

	$('#rating-grade').barrating({
		theme: 'bootstrap-stars'
	});

	$(document).on("click", ".rating-button",function(){
		$("#input-rating-id").val($(this).attr("id"));
	});

	$("#save-rating").click(function(){		
		var params = new Object();
		params["rating.rating"] = $("#rating-grade option:selected").val();
		params["rating.comment"] = $("#rating-comment").val();
		params["id"] =  $("#input-rating-id").val();

		console.log(JSON.stringify(params));

		ajaxCall("/siac/updateConsultationRating", params, function (response) {

			if(response.code == RESPONSE_SUCCESS)
				alertMessage(response.message, null, ALERT_SUCCESS);
			else
				alertMessage(response.message, null, ALERT_ERROR);

		}, function () {
			alertMessage("ERRO", null, ALERT_ERROR);
		});

		$("#modal-rating").modal('hide');
		$("#modal-event").modal('hide');


	});
}

function onServiceClick(){

	$(document).on("click", ".link-service", function(){

		$(".service").removeClass("active");
		$(this).parent().addClass("active");

		if($(this).hasClass("social-service")){
			$("#my-consultations").css("display", "none");
			var idSocialService = $(this).attr("id");
			$("#my-calendar-title").html("Calendário " + $(this).attr("data-name"));

			ajaxCall("/siac/getConsultationBySocialService?id="+idSocialService, function(json){
				$(".content-calendar").css("display", "block");
				$(".calendar").remove();
				$(".content-calendar").append($("<div class='calendar' id='calendar-patient'></div>"));

				initCalendarPatient(json);

			});

		}else $("#my-calendar-title").html("Meu Calendário");
	});

}

function scheduleConsultation(){
	$(document).on("click", "#schedule-consultation", function(){

		var id = $(this).attr("data-id");
		
		ajaxCall("/siac/scheduleConsultation", {"id": id }, function(response){		

			if(response.code == RESPONSE_SUCCESS){				
				changeEvent(id, "#4682B4");
				alertMessage(response.message, null, ALERT_SUCCESS);
			}	
			else
				alertMessage(response.message, null, ALERT_ERROR);

		}, function () {
			alertMessage("ERRO", null, ALERT_ERROR);
		});

		$("#modal-event").modal('hide');
		chargeEvents();
		
		
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

		ajaxCallNoJSON("/siac/cancelConsultationPatient", {"id":id }, function() {
			alertMessage("Consulta cancelada com sucesso", null, ALERT_SUCCESS);
			if($("#my-calendar-title").text() === "Meu Calendário"){
				$("#calendar-patient").fullCalendar('removeEvents', id);
			} else
				changeEvent(id, "#32CD32");
				
		}, function() {
			alertMessage("Desculpe, a operação falhou", 5000, ALERT_ERROR);

		});
		
		$("#modal-event").modal('hide');

	});
}

function reserveConsultation(){

	$(document).on("click", ".reserve-button", function(){
		var id = $(this).attr("data-id");

		ajaxCallNoJSON("/siac/reserveConsultation", {"id":id }, function() {
			changeEvent(id, "#D9D919");
			alertMessage("Consulta reservada com sucesso", 5000, ALERT_SUCCESS);
		}, function() {
			alertMessage("Desculpe, a operação falhou", 5000, ALERT_ERROR);
		});

		$("#modal-event").modal('hide');

	});

}

function cancelReserve(){
	$(document).on("click", ".cancel-reserve", function(){
		var idReserve = $(this).attr("data-id-reserve");
		ajaxCallNoJSON("/siac/cancelReserve", {"id":idReserve}, function() {
			if($("#my-calendar-title").text() === "Meu Calendário"){
				$("#calendar-patient").fullCalendar('removeEvents', id);
			} else
				changeEvent(id, "#FF7F00");
			alertMessage("Reserva cancelada com sucesso", 5000, ALERT_SUCCESS);
		}, function() {
			alertMessage("Desculpe, a operação falhou", 5000, ALERT_ERROR);

		});

		$("#modal-event").modal('hide');

	});

}


function changeEvent(idEvent, color){
	var evento = $("#calendar-patient").fullCalendar('clientEvents', idEvent)[0];
	console.log(evento);
	evento.color = color;
	$("#calendar-patient").fullCalendar('updateEvent', evento);
}









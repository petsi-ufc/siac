/**
 *	Todas as funcionalidades javascript do siac. 
 */

const MY_CALENDAR = "0";

//Essa variável é utilizada para saber qual o serviço que o usuário clicou.
var serviceId = "0";

$("document").ready(function(){

	onClickModalConfig();
	initCalendarPatient();
	onServiceClick();

});

function postAjaxCall(url, params){
	$.post(url, params).done(function(data, textStatus){
		alert("Schedules: "+data);
	}).fail(function(textStatus, errorThrown){
		alert("Error: "+errorThrown);
	});
}

function openScheduleModal(schedules){
	if(schedules == null || schedules == undefined){
		alert("Serviço sem horário disponível!");
		return;
	}
	
	var newRow = $("<tr></tr>");
	var cols = "";
	$.each(schedules, function(value){
		cols += '<td>'+value+'</td>';
	});
	
	newRow.append(cols);
	$("#table-schedule").append(newRow);
	
	$("#modal-schedules").modal('show'); 
	$("#modal-title-schedule").html("Horários dia <strong>"+date.format()+"</strong>");
	
}

function onServiceClick(){

	$(".link-service").click(function(){
		serviceId = $(this).attr('id');
		
		if(serviceId == MY_CALENDAR)
			$("#my-calendar").text("Meu Calendário");
		else{
			$("#my-calendar").text("Calendário "+$(this).text());
			
			var params = new Object();
			params["serviceId"] = serviceId;
			
			postAjaxCall("/siac/getAgenda", params);
		}
		
		$(".service").removeClass("active");
		$(this).parent().addClass("active");
	});
}

function onClickModalConfig(){
	//Quando o usuário clicar na imagem o modal aparece...
	$("#avatar-img").click(function(){
		$("#modal-config").modal('show');
	});
}

function initCalendarPatient(){
	$("#calendar_patient").fullCalendar({
		header: {
			left: 'prev',
			center: 'title',
			right: 'next'
		},
		events: [
		         {
		        	 title: 'Consulta Psicologia',
		        	 start: '2015-09-21',
		        	 end: '2015-09-21',
		        	 color: 'red'
		         },
		         {
		        	 title: 'Consulta Nutrição',
		        	 start: '2015-09-25',
		        	 end: '2015-09-25',
		        	 color: 'blue'
		         },
		         {
		        	 title: 'Consulta Psiquiatria',
		        	 start: '2015-09-29',
		        	 end: '2015-09-29',
		        	 color: 'yellow'
		         }

		         ],
		         businessHours: true,
		         editable: false,
		         dayClick: clickFunction
	});
	
}

function clickFunction(date, jsEvent, view){
	alert("TODO");
}
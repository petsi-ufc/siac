/**
 *	Todas as funcionalidades javascript do siac. 
 */

const MY_CALENDAR = "service_0";

$("document").ready(function(){

	onClickModalConfig();
	initCalendar();
	onServiceClick();

});

function postAjaxCall(url, params){
	$.post(url,null ,function(data, status){
		if(status == "success"){
			openScheduleModal(data);
		}
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
	$(".service").click(function(){
		$("#my-calendar").text("Calendário "+$(this).text());
	});
}

function onClickModalConfig(){
	//Quando o usuário clicar na imagem o modal aparece...
	$("#avatar-img").click(function(){
		$("#modal-config").modal('show');
	});
}

function initCalendar(){
	$("#calendar").fullCalendar({
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
		         dayClick: function(date, jsEvent, view){
		        	 postAjaxCall("/siac/", null);
		         }
	});
}




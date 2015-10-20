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

//Função que fax uma chamada ajax contendo a url e os parametros devidos.
//O terceiro parâmetro é uma função de callback, ela é chamada quando a requisição é retornada.
function ajaxCall(url, params, func){
	$.getJSON(url, params, func);
}


function openScheduleModal(schedules){
	
	$.each(schedules, function(schedule, status){
		var newRow = $("<tr></tr>");
		var tableData = "";
		tableData += '<td>'+schedule+'</td>';
		tableData += '<td>'+status+'</td>';
		newRow.append(tableData);
		$("#table-schedule").append(newRow);
	});
	
	$("#modal-schedules").modal('show'); 
	$("#modal-title-schedule").html("Horários dia <strong>"+date.format()+"</strong>");
	
}


function onServiceClick(){
	$(".link-service").click(function(){
		$(".service").removeClass("active");
		$(this).parent().addClass("active");
		
		//No caso do calendário ter sido oculto pelo "Gerar Relatórios"
		$(".calendar").css("display", "block");
		$("#my-calendar").css("display", "block");
		$("#alert-schedules").css("display", "block");
		$(".action").removeClass("active");
		$("#generate-report").css("display", "none");
		$("#set-professional").css("display", "none");
		$("#add-service").css("display", "none");

		serviceId = $(this).attr('id');
		
		var params = new Object();
		var url;
		
		if(serviceId == MY_CALENDAR){
			$("#my-calendar").text("Meu Calendário");
			params["userId"] = serviceId;
			url = "/siac/getUserAgenda"
		}else{
			$("#my-calendar").text("Calendário "+$(this).text());
			params["serviceId"] = serviceId;
			url = "/siac/getServiceAgenda";
		}
		ajaxCall(url, params, function(json){
			setCalendarSchedules("#calendar_patient", json);
		});
	});
}


function onClickModalConfig(){
	//Quando o usuário clicar na imagem o modal aparece...
	$("#avatar-img").click(function(){
		$("#modal-config").modal('show');
	});
}

//Função que inicia o calendário.
function initCalendarPatient(){
	$("#calendar_patient").fullCalendar({
		header: {
			left: 'prev',
			center: 'title',
			right: 'next'
		},
				 businessHours: true,
		         editable: false,
		         dayClick: clickFunction,
		         eventClick: clickEvent
	});
	
}

//Quando o usuário clica no evento essa função é chamada.
function clickEvent(event, jsEvent, view){
	alert("Event: "+event.title+"\nID: "+event.id);
}

function openModalSchedules(idService){
	
}


/*Essa função pega todos os dados vindos da requisição
  AJAX e preche o calendário com elas.
  Params: Id do calendário e json vindo do ajax.
  */
function setCalendarSchedules(idCalendar, json){
	//Escondendo a mensagem de erro.
	$("#alert-schedules").css({"display":"none"});
	
	/*Removendo todos os eventos para não haver duplicações. A função passada como argumento quando retorna
	 * true remove o evento passado por parametro. Logo essa função irá remover todos os eventos.
	*/
	$(idCalendar).fullCalendar('removeEvents', function(event){
		return true;
	});
	if(JSON.stringify(json) == "{}" || json.consultations.length == 0){
		$("#alert-schedules").css({"display":"block"});
		return;
	}
	
	$.each(json.consultations, function(key, obj){
		var serviceName = obj.service.name;
		
		var serviceId = 0;
		$.each(obj, function(name, value){
			if(name == "service"){
				serviceId = value.id;
			}
			if(name == "schedule"){
				dateInit = new Date(value.dateInit);
				dateEnd = new Date(value.dateEnd);
				_dateInit = formatDate(dateInit);
				_dateEnd = formatDate(dateEnd);
				renderCalendarEvent(idCalendar, serviceId, serviceName, _dateInit, _dateEnd);
			}
		});
	});
	
}


function formatDate(date){
	var dd = date.getDate();
    var mm = date.getMonth()+1; //Janeiro é 0!

    var yyyy = date.getFullYear();
    if(dd<10){
        dd='0'+dd
    } 
    if(mm<10){
        mm='0'+mm
    } 
    res = yyyy+'-'+mm+'-'+dd;
    return res;
}

//Essa função é responsável por adicionar um evento no calendário.
function renderCalendarEvent(idCalendar, idService ,serviceName, dayStart, dayEnd){
	$(idCalendar).fullCalendar('renderEvent',{
		id: idService,
		title: serviceName,
		start: dayStart,
		end: dayEnd
	}, true);
}


function clickFunction(date, jsEvent, view){
	
}

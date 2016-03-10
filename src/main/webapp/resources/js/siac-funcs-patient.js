/**
 * 
 */

/**
 *	Todas as funcionalidades do paciente javascript do siac. 
 */


const MY_CALENDAR = "0";

const NO_CONSULTATIONS_MESSAGE = "Não há nenhum horário cadastrado para este serviço!";

//Essa variável é utilizada para saber qual o serviço que o usuário clicou.
var serviceId = "0";

$("document").ready(function(){
	onClickModalConfig();
	initCalendarPatient();
	loadActiveServices();
	
});



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


//Preenche a lista de serviços disponíveis para o paciente.
function loadActiveServices(){
	ajaxCall("/siac/getActiveServices", null, function(json){
		$.each(json, function(key, obj){
			fillPatientTableServices(obj["id"], obj["name"]);
		});
	});
}

function fillPatientTableServices(serviceId, serviceName){
	var tableService = $("#ul-services");
	tableService.append("<li class='service'><a class='link-service' id="+serviceId+">"+serviceName+"</a></li>" +
	"<li class='nav-divider'></li>");
	
	//Carrega a ação de click nos links dos serviços sociais.
	onServiceClick();
}

function onServiceClick(){
	$(".link-service").click(function(){
		$(".service").removeClass("active");
		$(this).parent().addClass("active");

		serviceId = $(this).attr('id');
		
		var params = new Object();
		var url;
		
		if(serviceId == MY_CALENDAR){
			$("#my-calendar").text("Meu Calendário");
			//params["cpf"] = cpf;
			//url = "/siac/getConsultationsByPatient"
			alert("FALTA FAZER ESSA PARTE!\n COLOCAR TODAS AS CONSULTAS DO PACIENTE!!");
		}else{
			$("#my-calendar").text("Calendário "+$(this).text());
			params["socialServiceId"] = serviceId;
			url = "/siac/getConsultationsBySocialService";
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
	$("#calendar-patient").fullCalendar({
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
	if(Object.keys(json).length == 0){
		$("#alert-schedules").css({"display":"block"});
		$("#alert-schedules").text(NO_CONSULTATIONS_MESSAGE);
		hideElement("#alert-schedules", 4000);
		return;
	}
	
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

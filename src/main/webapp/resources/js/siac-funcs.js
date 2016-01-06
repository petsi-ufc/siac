/**
 *	Todas as funcionalidades javascript do siac. 
 *	Author: Daniel Filho
 */

const MY_CALENDAR = "0";

//Essa variável é utilizada para saber qual o serviço que o usuário clicou.
var serviceId = "0";

$("document").ready(function(){

	onClickModalConfig();
	initCalendarPatient();
	initServices();	
	initMainNavBar();
});

/* Essa função pega todos os serviços ativos e mostra na
 * barra de serviços disponíveis para o paciente.
*/
function initServices(){
	ajaxCall("/siac/getActiveServices", null, function(json){
		var li = $("#list-services");
		
		$.each(json, function(key, obj){
			li.append("<li class='nav-divider'></li>");
			
			var newLine = $("<li class='service'></li>");
			newLine.append("<a class='link-service'></a>");
			
			newLine.children("a").attr("id", obj.id);
			newLine.children("a").text(obj.name);
			li.append(newLine);
		})
		onServiceClick();
	});
}

//Função que fax uma chamada ajax contendo a url e os parametros devidos.
//O terceiro parâmetro é uma função de callback, ela é chamada quando a requisição é retornada.
function ajaxCall(url, params, func){
	$.getJSON(url, params, func).error(function(textStatus, error){
		$("#alert-schedules").text("Não foi possível realizar essa ação!");
		$("#alert-schedules").css({"display":"block"});
	});
}

/* Essa função é chamada quando o paciente clica no serviço no calendário.
 * Ela abre o modal com todos os horários do serviço selecionado naquele dia.
*/
function openScheduleModal(event, schedules){
	
	$("#table-schedule tbody").remove();
	
	$("#modal-title-schedule").html("Horários dia <strong>"+event.start.format()+"</strong>");
	
	var dateInit;
	var dateEnd;
	
	$.each(schedules, function(key, obj){
		var newRow = $("<tr class='row-schedule'></tr>");
		var tableData = "";
		
		dateInit = new Date(obj['dateInit']);
		dateEnd = new Date(obj['dateEnd']);
		
		
		var timeInit = dateInit.getHours()+':'+addZero(dateInit);
		var timeEnd = dateEnd.getHours()+':'+addZero(dateEnd);
		
		var disabled = "";
		if(obj['available'])
			disabled = "";
		else
			disabled = "disabled";
		
			tableData += '<td><div class="checkbox">'+
				  			' <label><input type="checkbox" value="" '+disabled+'></label>'+
				  		 '</div></td>';
		
		tableData += '<td>'+timeInit+' - '+timeEnd+'</td>';
		
		if(obj['available'])
			tableData += '<td class="schedule-disponivel"><strong>Disponível</strong></td>';
		else
			tableData += '<td class="schedule-indisponivel"><strong>Indisponível</strong></td>';

		newRow.append(tableData);
		$("#table-schedule").append(newRow);
	});
	
	$("#btn-confirm-schedule").addClass("disabled");
	$(".schedule-disponivel").css({"color":"blue"});
	$(".schedule-indisponivel").css({"color":"red"});
	$("#modal-schedules").modal('show'); 
	
	//Ativa o click nas linhas da tabela.
	onScheduleTableClick();
	
}

/* Essa função é chamada quando o paciente clica em algum horário
 * apresentado no modal de horários.
*/
function onScheduleTableClick(){	
	$(".row-schedule").click(function(){
		var row = $(this);
		var btnConfirm = $("#btn-confirm-schedule");
		
		var status = row.find("td").next().text();
		
		if(row.hasClass("success")){
			row.removeClass("success");
			btnConfirm.addClass("disabled");
		}else if(status == "Disponível"){
			btnConfirm.removeClass("disabled");
			row.addClass("success");
		}
	});
}



//Essa função serve para adicionar os zeros aos minutos da data.
function addZero(date){
	 return date.getMinutes() < 10 ? '0'+date.getMinutes() : date.getMinutes();
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
			params["userCpf"] = serviceId;
			url = "/siac/getUserAgenda"
		}else{
			$("#my-calendar").text("Calendário "+$(this).text());
			params["serviceId"] = serviceId;
			url = "/siac/getServiceAgenda";
		}
		ajaxCall(url, params, function(json){
			setCalendarSchedules("#calendar-patient", json);
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
	getModalSchedules(event);
}

function getModalSchedules(event){
	var params = new Object();
	
	dateInit = new Date(event.start);
	dateEnd = new Date(event.end);
	params["serviceId"] = event.id;
	params["startDay"] = formatDate(dateInit, '/');
	params["endDay"] = formatDate(dateEnd, '/');
	
	ajaxCall("/siac/getConsultationsByDate", params, function(json){
		if(!(Object.keys(json).length === 0)){
			openScheduleModal(event, json );
		}
	});
	
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
	if(Object.keys(json).length === 0){
		$("#alert-schedules").text("Não há nenhum horário cadastrado!");
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
				_dateInit = formatDate(dateInit, '-');
				renderCalendarEvent(idCalendar, serviceId, serviceName, _dateInit);
			}
		});
	});
	
}

//Essa função recebe uma data e um separador, esse separador é o que separa o dia, mes e ano. ( / ou -) 
function formatDate(date, separator){
	var dd = date.getDate();
    var mm = date.getMonth()+1; //Janeiro é 0!

    var yyyy = date.getFullYear();
    if(dd<10){
        dd='0'+dd
    } 
    if(mm<10){
        mm='0'+mm
    } 
    res = yyyy + separator + mm + separator + dd;
    return res;
}

//Essa função é responsável por adicionar um evento no calendário.
function renderCalendarEvent(idCalendar, idService ,serviceName, dayStart){
	$(idCalendar).fullCalendar('renderEvent',{
		id: idService,
		title: serviceName,
		start: dayStart,
	}, true);
}

function clickFunction(date, jsEvent, view){
	
}

/* Essa função é inicia a barra principal.
*/
function initMainNavBar(){
	$("#main-navbar").append(
		'<li class="active" id="menu-main"><a href="#">Pincipal</a></li>'+
	    '<li id="menu-consultations"><a href="#">Consultas</a></li>'+
	    '<li id="menu-reservations"><a href="#">Reservas</a></li>'+
	    '<li id="menu-help"><a href="#">Ajuda</a></li>');
	
	$("#menu-consultations").click(function(){
		$("#main-navbar").children().removeClass("active");
		$(this).addClass("active");
		
		$("#calendar-patient").css({"display":"none"});
		$("#my-calendar").css({"display":"none"});
		
		$("#my-consultations").css({"display":"block"});
	});
	
	$("#menu-main").click(function(){
		$("#main-navbar").children().removeClass("active");
		$(this).addClass("active");
		
		$("#calendar-patient").css({"display":"block"});
		$("#my-calendar").css({"display":"block"});
		
		$("#my-consultations").css({"display":"none"});
		
	});
		
}




/**
 * Funcionalidades javascript na visão do profissional
 */

var scheduleManager = new ScheduleManager();

var REGISTER_ACTION = "Cadastrar";
var EDIT_ACTION = "Editar";

var MY_CALENDAR = 0;
var REGISTER_SCHEDULE = 1;
var MY_CONSULTATIONS = 2;
var GENERATE_REPORT = 3;

var CALENDAR_ID = "#calendar_professional";
var SELECT_REPEAT_SCHEDULE_ID = "#select-repeat-schedule";
var MAP_SELECTED_DAYS = "map_selected_days";
var INPUT_FREQUENCI = "input_frequenci";
var TABLE_DAYS = "table_days";

var INPUT_COUNT_TIME = "input_count_time";
var INPUT_VACANCY = "input_vacancy";

var MAP_DAYS = "map_days"; 
var MAP_WEEK_DAYS = "map_week_days";

//Constantes usadas para indentificar qual a
//frequencia de repetição dos horários.
var MONTHLY_FREQUENCI = "monthly";
var WEEKLY_FREQUENCI = "weekly";

//Variável usada para saber a frequencia das repetições
//dos horários.
var frequenci = "w";

var idTimepickersInit = 0;
var idTimepickersEnd = 0;

// Mapa com todas as variáveis
var mapVars = new Map();


var colors = new Map();
colors.set("SC", {text: "Agendada", hex : "#4682B4", css: "color-blue"});
colors.set("FR", {text: "Disponível", hex : "#32CD32", css: "color-green"});
colors.set("RD", {text: "Realizada", hex : "grey", css: "color-grey"});
colors.set("RV", {text: "Reservada", hex : "#D9D919", css: "color-yellow"});
colors.set("CD", {text: "Cancelada", hex : "#FF0000", css: "color-red"});
colors.set("GS", {hex : "#FA6900"});



$("document").ready(function(){
	
	//Adicionando elementos DOM no mapa de variaveis
	mapVars.set(CALENDAR_ID, $("#calendar_professional"));
	mapVars.set(SELECT_REPEAT_SCHEDULE_ID, $("#select-repeat-schedule"));
	mapVars.set(INPUT_FREQUENCI, $("#input-frequenci"));
	mapVars.set(TABLE_DAYS, $("#tbody-table-days"));
	mapVars.set(INPUT_VACANCY, $("#input-count-vacancy"));
	mapVars.set(INPUT_COUNT_TIME, $("#input-count-time"));
	
	
	initCalendarProfessional();	
	onButtonAddScheduleClick();
	onSelectScheduleRepeatClick();
	onButtonRepeatScheduleDayClick();
	onInputFrequenciChange();
	onLiItemServiceClick();
	
	onButtonGenerateSchedules();
	
	onButtonConfirmSchedulesClick();
	
	// Mapa que conterá todos os dias da semana
	// que se repetirão, Mon, Tue...
	// CHAVE: dia da semana
	// VALOR: booleano, indicando se o dia foi marcado ou não
	var mapSelectedDays = new Map();
	mapSelectedDays.set("Monday", false);
	mapSelectedDays.set("Sunday", false);
	mapSelectedDays.set("Tuesday", false);
	mapSelectedDays.set("Wednesday", false);
	mapSelectedDays.set("Thursday", false);
	mapSelectedDays.set("Friday", false);
	mapSelectedDays.set("Saturday", false);
	
	mapVars.set(MAP_SELECTED_DAYS, mapSelectedDays);
	
	//Mapa usado para preencher a tabela de 
	//datas com dias da semana em português 
	var mapWeekDays = new Map();
	mapWeekDays.set("Monday", "Segunda-Feira");
	mapWeekDays.set("Tuesday", "Terça-Feira");
	mapWeekDays.set("Wednesday", "Quarta-Feira");
	mapWeekDays.set("Thursday", "Quinta-Feira");
	mapWeekDays.set("Friday", "Sexta-Feira");
	mapWeekDays.set("Saturday", "Sábado");
	mapWeekDays.set("Sunday", "Domingo");
	
	mapVars.set(MAP_WEEK_DAYS, mapWeekDays);
	
	//Carregando todas as consultas cadastradas no calendário do profissional.
	getProfessionalConsultations(fillProfessionalCalendar);
	
	onBtnCancelConsultationClick();
	
	onButtonGoToDateClick();
	
	//Função de submissão do formulário de reagendamento de consulta.
	onBtnConfirmReschedule();
	
	$("#input-dtpckr-start-report").datepicker({
		 format: 'dd/mm/yyyy',                
		 language: 'pt-BR'
	});
	
	$("#input-dtpckr-end-report").datepicker({
		 format: 'dd/mm/yyyy',                
		 language: 'pt-BR'
	});
	
});

function onButtonGoToDateClick(){
	calendar = mapVars.get(CALENDAR_ID);
	$("#btn-goto-date").click(function(){
		var value = $("#input-goto-date").val();
		var date = new Date(value);
		calendar.fullCalendar("day", date);
		calendar.fullCalendar('gotoDate', date);
	});
}

function onLiItemServiceClick(){
	var calendar = mapVars.get(CALENDAR_ID);
	$(".service-item").click(function(){
		
		$(".service-item").removeClass("active");
		$(this).addClass("active");
		id = $(this).attr("id");
		
		if(id == REGISTER_SCHEDULE){
			getProfessionalConsultations();
			$("#calendar-container").addClass("hidden");
			$("#panel-register-schedules").removeClass("hidden");
			$("#panel-generate-report").addClass("hidden");
			$("#panel-my-consultations").addClass("hidden");
		}else if(id == MY_CALENDAR){
			getProfessionalConsultations(fillProfessionalCalendar);
			$("#calendar-container").removeClass("hidden");
			$("#panel-register-schedules").addClass("hidden");
			$("#panel-my-consultations").addClass("hidden");
			$("#panel-generate-report").addClass("hidden");
		}else if(id == MY_CONSULTATIONS){
			getProfessionalConsultations(fillMyConsultationsCollapses);
			$("#my-calendar").addClass("hidden");
			$("#panel-register-schedules").addClass("hidden");
			$("#panel-my-consultations").removeClass("hidden");
			$("#panel-generate-report").addClass("hidden");
		}else if(id == GENERATE_REPORT){
			$("#calendar-container").addClass("hidden");
			$("#panel-register-schedules").addClass("hidden");
			$("#panel-my-consultations").addClass("hidden");
			$("#panel-generate-report").removeClass("hidden");
		}
	});
}

// Função que inicia o calendário.
function initCalendarProfessional(){
	calendar = mapVars.get(CALENDAR_ID);
	
	calendar.fullCalendar({
		header: {
			left: 'prev',
			center: 'title',
			right: 'next'
		},
		
		businessHours: true,
	    
		editable: false,
		
		eventRender: function (event, element) {
		    element.find(".fc-time").text(event.id);  
		},
		dayClick: function(mDate, jsEvent, view){
			var date = mDate.format("DD/MM/YYYY");
			var scheduleDay = scheduleManager.getSchedulesMap().get(date);
			
			if(scheduleDay){
				var listSchedules = scheduleDay.getListSchedules();
				if(listSchedules.length > 0){
					setEditModalSchedule(date, listSchedules);
				}
			}else{
				//Só pode cadastrar horários se o dia for maior que o dia de hoje.
				today = moment();
				if(!mDate.isBefore(today)){
					showModalSchedules(date, REGISTER_ACTION);
				}else{
					alertMessage("Ops, não é possível cadastrar horários de um dia que já passou", null, ALERT_ERROR);
				}
			}
		},
	    eventClick: function(date, jsEvent, view, event) {
	    	var date = moment(new Date(date.start._d)).format("DD/MM/YYYY");
	    	var modal = $("#modal-schedules-description");
	    	fillDescriptionSchedulesTable(scheduleManager.getScheduleDay(date));
	    	modal.modal("show");
	    } 		         		         
	});
	
}

function fillDescriptionSchedulesTable(scheduleDay){
	
	if(!scheduleDay) return;
	
	fillDetailsConsultationTable("tbody-schedules-description", scheduleDay.getDate(), scheduleDay.getListSchedules());

}

function fillDetailsConsultationTable(tbodyId, date, scheduleList){
	var tbody = $("#"+tbodyId);
	tbody.find("tr").remove();
	
	var dateSchedule = getFormatedDate(date);
	
	scheduleList.forEach(function(sday){
		var row = $("<tr>");
		var tdata = "";
		
		tdata += "<td>"+sday.getTimeInit()+"</td>";
		tdata += "<td>"+sday.getTimeEnd()+"</td>";
		tdata += "<td>"+colors.get(sday.getState()).text+"</td>";
		
		var arrayTime = sday.getTimeEnd().split(":");
		var hourEnd = arrayTime[0];
		var minuteEnd = arrayTime[1];
		
		var today = new Date();
		
		dateSchedule.setHours(hourEnd);
		dateSchedule.setMinutes(minuteEnd);
		
		var disabled = "";
		
		if((sday.getState() == "RD") || (sday.getState() == "CD") || dateSchedule.getTime() < today.getTime()){
			disabled = "disabled='disabled'";
		}
		
		var patient = sday.getPatient();
		patient = patient ? patient : {name: "Sem Paciente", email: null};
		
		tdata += '<td>'+patient.name+'</td>';
		tdata += '<td>'+
		'<button type="button" value='+sday.getId()+' class="btn btn btn-success action-register-consultation">Registrar <span class="glyphicon glyphicon-ok"></span></button>'+
			'<button type="button" value='+sday.getId()+' class="btn btn btn-danger action-cancel-consultation" '+disabled+' ">Cancelar <span class="glyphicon glyphicon-remove-circle"></span></button>'+
			'<button type="button" value='+sday.getId()+' class="btn btn btn-warning action-reschedule-consultation margin-left" '+disabled+' ">Reagendar <span class="glyphicon glyphicon-time"></span></button>'
			+'</td>';
			
		row.append(tdata);
		tbody.append(row);
		
	});
	
	$(".action-cancel-consultation").off("click").click(function(){
		var scheduleId = $(this).attr("value");
		
		var schedule = scheduleManager.getScheduleTimeById(scheduleId);
		
		var modalCancel = $("#modal-cancel-consultation");
		
		var $divEmail= $("#div-send-email");
		$divEmail.removeClass("hidden");
		$("#text-area-email").text("Desculpe, sua consulta do dia "+schedule.toEmail()+" foi cancelada.");
		
		if(!schedule.getPatient()){
			$divEmail.addClass("hidden");
		}
		
		modalCancel.find("#btn-cancel-consultation").attr("value", scheduleId);
		modalCancel.modal("show");
	});
	
	$(".action-register-consultation").off("click").click(function(){
		var scheduleId = $(this).attr("value"); 
		
		ajaxCall("/siac/registerConsultation", {"id": scheduleId}, function(response){
			if(response.code == RESPONSE_SUCCESS){
				alertMessage(response.message, null, ALERT_SUCCESS);
				getProfessionalConsultations(fillProfessionalCalendar);
			}
			else
				alertMessage(response.message, null, ALERT_ERROR);
			
		}, function(a,b){
			alertMessage("Não foi possível registar a consulta!", null, ALERT_ERROR);
		});
	});
	
	$(".action-reschedule-consultation").off("click").click(function(){
		var scheduleId = $(this).attr("value");
		var schedule = scheduleManager.getScheduleTimeById(scheduleId);
		
		fillReScheduleModal(schedule);
		
		//Conferindo se existe algum paciente vinculado a consulta
		var $divEmail = $("#div-email");
		$divEmail.addClass("hidden");
		
		if(schedule.getPatient()){
			var $textEmail = $("#textArea-email-rsch");
			$divEmail.removeClass("hidden");
		}
		
		var $modalReSch = $("#modal-reschedule");
		$modalReSch.modal('show');
		
	});
	
	
}

function onBtnConfirmReschedule(){
	$("#btn-confirm-resch").click(function(event){
		
		var newDateInit = $("#input-dtpckr-reschedule").data('datepicker').date;
		var timeInit = $('#rch-timeinit').data('timepicker');
		
		//Clonando o objeto newDateInit...
		var newDateEnd= new Date(newDateInit.getTime());
		var timeEnd = $('#rch-timeend').data('timepicker');
		
		//Setando o horário nas datas...
		newDateInit.setHours(timeInit.hour);
		newDateInit.setMinutes(timeInit.minute);
		
		newDateEnd.setHours(timeEnd.hour);
		newDateEnd.setMinutes(timeEnd.minute);
		
		$("#input-dtpckr-reschedule").removeClass("has-error");
		$('#rch-timeinit').removeClass("has-error");
		$('#rch-timeend').removeClass("has-error");
		
		//Conferindo se a hora dos timepickers estão válidas.
		if(!isValidTimePickers(timeInit, timeEnd)){
			$('#rch-timeinit').addClass("has-error");
			$('#rch-timeend').addClass("has-error");
			
			alertMessage("O horário de início não pode ser maior ou igual que o de fim!", null, ALERT_SUCCESS, "alert-reschedule");
			event.preventDefault();
			return;
		}
		var now = new Date();
		
		if(newDateInit.getTime() < now.getTime()){
			$("#input-dtpckr-reschedule").addClass("has-error");
			alertMessage("A nova data não pode ser anterior ao dia de hoje!", null, ALERT_SUCCESS, "alert-reschedule");
			event.preventDefault();
			return;
		}
		
		var idConsultation = $(".action-reschedule-consultation").attr("value");
		
		var $textEmail = $("#textArea-email-rsch");
		
		var email = $textEmail.val() ? $textEmail.val() : "";
		
		var params = {"idConsultation": idConsultation, "dateInit": newDateInit, "dateEnd": newDateEnd, "email": email};
		
		ajaxCall("/siac/rescheduleConsultation", params, function(response){
			if(response.code == RESPONSE_SUCCESS){
				//Carregando todas as consultas cadastradas no calendário do profissional.
				getProfessionalConsultations(fillProfessionalCalendar);
				alertMessage(response.message, null, ALERT_SUCCESS);
			}else{
				alertMessage(response.message, null, ALERT_ERROR);
			}
		}, function(){
			alertMessage("Ops, não foi possível reagendar a consulta!", null, ALERT_ERROR);
		}, "POST");
		
		var $modalReSch = $("#modal-reschedule");
		$modalReSch.modal('hide');
		
		$("#modal-schedules-description").modal('hide');
		
	});
}

function fillReScheduleModal(schedule){
	var $atualDate = $("#rsch-atualdate");
	var $atualTimeInit = $("#rsch-atual-timeinit");
	var $atualTimeEnd = $("#rsch-atual-timeend");
		
	//Preenchendo os campos de data e hora atual da consulta 
	$atualDate.val(schedule.getDateInit());
	$atualTimeInit.val(schedule.getTimeInit());
	$atualTimeEnd.val(schedule.getTimeEnd());
	
	//Iniciando o date picker para pegar a nova data do reagendamento
	var datePickerNewDate = $("#input-dtpckr-reschedule");
	datePickerNewDate.datepicker({
		 format: 'dd/mm/yyyy',                
		 language: 'pt-BR'
	});
	
	//Iniciando os timepickers para pegar o novo horário.
	initTimepicker("rch-timeinit", null);
	initTimepicker("rch-timeend", null);
	
}

function onBtnCancelConsultationClick(){
	$("#btn-cancel-consultation").click(function(){
		var modalCancel = $("#modal-cancel-consultation");
		modalCancel.modal("hide");
		
		var scheduleId = $(this).attr("value"); 
		var message = $("#text-area-email").val();
		
		ajaxCall("/siac/cancelConsultation", {"id":scheduleId, "message": message}, function(response){
			var type = ALERT_ERROR;
			if(response.code == RESPONSE_SUCCESS){
				var cancelButton = $(".action-cancel-consultation[value="+scheduleId+"]");
				cancelButton.addClass("disabled");
				cancelButton.siblings(".action-register-consultation").addClass("disabled");
				
				alertMessage(response.message, null, ALERT_SUCCESS);
				getProfessionalConsultations(fillProfessionalCalendar);
				
			}else
				alertMessage(response.message, null, ALERT_ERROR);
		}, function(){
			alertMessage("Ops, algo de errado aconteceu!", null, ALERT_ERROR);
		});
	});
}


function onCalendarDayCLicked(date){
	//Iniciando os dois times picker iniciais
	showModalSchedules(date, REGISTER_ACTION);
}


/*Essa função abre o modal de cadastro e edição de horários.
  Parametros: date: Data para cadastro ou edição de horários.
  			  action: Se a ação é de cadastro ou de edição.
*/
function showModalSchedules(date, action){
	
	var inputVacancy = mapVars.get(INPUT_VACANCY);
	var inputTimePerConsult = mapVars.get(INPUT_COUNT_TIME);
	
	inputVacancy.val("");
	inputTimePerConsult.val("");
	
	var modal =	$("#modal-day");
	var modalDescription = $("#modal-description-body");
	var labelDay = $("#label-date-clicked");
	
	//Removendo todos os timepickers clonados.
	modal.find(".cloned-timepicker").remove();
	
	//Resetando os ids dos timepickers;
	idTimepickersInit = 1;
	idTimepickersEnd = 1;
	
	initTimepicker("tmp-init-0", null);
	initTimepicker("tmp-init-1", null);
	initTimepicker("tmp-end-1", null);
	
	$("#tmp-init-1").removeAttr("disabled");
	$("#tmp-end-1").removeAttr("disabled");
	
	today = moment(date,"DD/MM/YYYY").format("dddd");
	
	//Mostrando o modal
	$("#modal-schedule-title").text(action+" Horários");
	modalDescription.html(action+" consultas para o dia <strong>"+date+" ("+today+")</strong>");
	labelDay.text(date);
	
	modal.modal('show');
		
}


function onButtonConfirmSchedulesClick(){
	/*
	 * Quando o profissional clicar para confirmar 
	 * o cadastro de horários, todos as horas dos timepickers
	 * devem ser pegues.
	 */
	$("#btn-confirm-schedules").click(function(event){
		
		var modal = $("#modal-day");
		var rowsSchedules = modal.find("#panel-schedules").find(".row-schedule-id");
		var timepickersObject = rowsSchedules.find(".timepicker").find("input");
		

		var timepickersIdArray = []
		var tempArray = $.makeArray(timepickersObject);
		
		for(var i = 0, j=0; i < tempArray.length-1; i+=2, j++){
			var idSchedule = rowsSchedules[j].id;
			
			if(tempArray[i].id && tempArray[i+1].id){
				timepickersIdArray.push({"idSchedule":idSchedule, "timePickInit":tempArray[i].id, "timePickEnd":tempArray[i+1].id});
			}
		}
		
		
		var labelDay = $("#label-date-clicked");
		var date = labelDay.text();
		
		if(timepickersIdArray.length > 0){
			
			//Removendo a cor vermelha dos timepickers inválidos.
			$(".row-schedule-id").removeClass("has-error");
			//Salvando os horários no objeto schedule manager
			if(!saveSchedules(timepickersIdArray, date)){
				return;
			}
			 
			//Quando o profissional cadastrar os horários
			//o botão do de cadastrar será alterado para o de editar
			var panel = $("#panel-register-schedules");
			
			var buttonAddSchedules = panel.find("button[value='"+date+"']").filter(".add-schedule");
			var buttonEditSchedules = buttonAddSchedules.siblings(".edit-schedule");
			var buttonRemoveDay = panel.find("button[value='"+date+"']").filter(".remove-day");
			
			buttonRemoveDay.addClass("disabled");
			buttonEditSchedules.removeClass("hidden");
			buttonAddSchedules.addClass("hidden");
			
		}
		
		var params = JSON.stringify(scheduleManager.getScheduleDayAsJSON(date));
		
		ajaxCall("/siac/saveConsultation", {"json": params}, function(response){
			var type = ALERT_SUCCESS;
			if(response.code == RESPONSE_ERROR)
				type = ALERT_ERROR;
			
			alertMessage(response.message, null, type);
			
			
			//Carregando todas as consultas cadastradas no calendário do profissional.
			getProfessionalConsultations(fillProfessionalCalendar);
			
		}, null, "POST");
		
		modal.modal("hide");
		
	});

}
	


function getTimePickerHourAndMinutes(timepickerId){
	hour = $("#"+timepickerId).data("timepicker").hour;
	minute = $("#"+timepickerId).data("timepicker").minute;
	return {"hour":hour, "minute":minute};
}


/* Função que recebe uma data todos os timepickers referentes aos horários
 * e salva no gerenciador de horários(scheduleManager).
 * 
 * Caso a data passada já esteja cadastrada no schedule manager
 * então a operação será de edição, logo todos os horários daquela 
 * data serão atualizados com os novos horários dos timepickers.
*/
function saveSchedules(timepickersId, date){
	var scheduleDay = scheduleManager.getSchedulesMap().get(date);
	/*Caso esse dia (date) já exista, a lista de horários antiga 
	 *é apagada e os novos horários são cadastrados. 
	*/
	if(scheduleDay){
		scheduleDay.clearListSchedules();
	}else{
		scheduleDay = new ScheduleDay();
		scheduleDay.setDate(date);
	}

	var listScheduleTime = scheduleDay.getListSchedules();
	
	for(var i = 0; i < timepickersId.length; i++){
		
		var timeInit = getTimePickerHourAndMinutes(timepickersId[i].timePickInit);
		var timeEnd = getTimePickerHourAndMinutes(timepickersId[i].timePickEnd);
		var scheduleId = timepickersId[i].idSchedule;
		
		//Se o horáriod e inicio for maior que o de fim, os timepickers ficam vermelho.
		if(!isValidTimePickers(timeInit, timeEnd)){
			$("#"+timepickersId[i].timePickInit).parents(".row-schedule-id").addClass("has-error");
			$("#"+timepickersId[i].timePickEnd).parents(".row-schedule-id").addClass("has-error");
			
			return false;
		}
		
		scheduleDay.addSchedule(getFormatedDate(date), timeInit["hour"], timeInit["minute"], timeEnd["hour"], timeEnd["minute"], null, null, null, scheduleId, null);
		
	}
	scheduleManager.addScheduleDay(date, scheduleDay);
	return true;
}

//Função que verifica se os horários informados nos timepickers são válidos.
//Se o horário do timepicker de inicio for menor que o timepicker de fim é retornado true.
function isValidTimePickers(objTimeInit, objTimeEnd){
	var dateInit = new Date();
	dateInit.setHours(objTimeInit.hour);
	dateInit.setMinutes(objTimeInit.minute);
	
	var dateEnd = new Date();
	dateEnd.setHours(objTimeEnd.hour);
	dateEnd.setMinutes(objTimeEnd.minute);
	
	console.log("INI: "+dateInit+" - End: "+dateEnd)
	
	if( dateInit.getTime() >= dateEnd.getTime() )
		return false;
	return true;
}

// Função que inicia um timepicker passado por parâmetro
function initTimepicker(idPicker, time){
	idPicker = "#"+idPicker;
	$(idPicker).timepicker({showMeridian: false});
	if(time != null){
		$(idPicker).timepicker('setTime', time);
	}else{
		$(idPicker).timepicker('setTime', '08:00');
	}
}

// Função que inicializa a ação de click no botão de adicionar horário 
function onButtonAddScheduleClick(){
	$(".add-schedule").click(function(){
		
		//Pegando o último timepicker para obter o último horário.
		var listTimers = $(".timepicker-end input");
		var idLastTimePicker = listTimers[listTimers.length - 1];
		
		hour = $("#"+idLastTimePicker.id).data("timepicker").hour;
		minute = $("#"+idLastTimePicker.id).data("timepicker").minute;
		
		momentTime = moment();
		momentTime.hours(hour);
		
		var timePerconsult = $("#input-count-time").val() ? $("#input-count-time").val() : 15;
		
		momentTime.minutes(minute);
		momentTime.add('m', timePerconsult);
		tempInit = momentTime.format("HH:mm");
		
		momentTime.add('m', timePerconsult);
		tempEnd = momentTime.format("HH:mm")
		
		addSchedules({"timeInit":tempInit, "timeEnd":tempEnd, "idSchedule":null});
	});
}

function addSchedules(obj){
	
	
	var timeInit = obj.timeInit;
	var timeEnd = obj.timeEnd;
	var scheduleId = obj.idSchedule;
	
	var newRow = $("#row-add-schedules").clone();
	
	newRow.find(".row-schedule-id").attr("id",scheduleId);
	
	timeInitId = "tmp-init-"+getNewInitTimePickerId();
	timeEndId = "tmp-end-"+getNewEndTimePickerId();
	
	var timepickerInit = newRow.find(".timepicker-init"); 
	var timepickerEnd = newRow.find(".timepicker-end");
	
	var inputInit = timepickerInit.find("input"); 
	var inputEnd = timepickerEnd.find("input");
	if(!obj.disabled){
		inputInit.removeAttr("disabled");
		inputEnd.removeAttr("disabled");
	}else{
		inputInit.attr("disabled", obj.disabled);
		inputEnd.attr("disabled", obj.disabled);
	}
	
	inputInit.attr("id", timeInitId);
	inputEnd.attr("id", timeEndId);
	
	//Adicionando a classe que informa que o timepicker é clonado.
	newRow.addClass("cloned-timepicker");
	
	var button = newRow.find(".add-schedule");
	button.removeClass("add-schedule");
	button.addClass("remove-schedule");
	button.removeClass("btn-primary");
	button.addClass("btn-danger");
	
	if(obj.disabled){
		button.attr("disabled","disabled");
	}
	
	var span = button.find("span");
	span.removeClass("glyphicon-plus");
	span.addClass("glyphicon-minus");
	
	$("#panel-schedules").append(newRow);

	//Iniciando os timepickers
	initTimepicker(timeInitId, timeInit);
	initTimepicker(timeEndId, timeEnd);		
	
	//Iniciando o metodo de remover os horários caso o usuário clique no botar de remover horário.
	onButtonRemoveScheduleClick();

	
}

function onButtonRemoveScheduleClick(){
	$(".remove-schedule").click(function(){
		$(this).parents(".row").remove();
	});
}

function getNewInitTimePickerId(){
	idTimepickersInit = idTimepickersInit+1;
	return idTimepickersInit;
}

function getNewEndTimePickerId(){
	idTimepickersEnd = idTimepickersEnd+1;
	return idTimepickersEnd;
}

function onSelectScheduleRepeatClick(){
	var select = mapVars.get(SELECT_REPEAT_SCHEDULE_ID);
	select.change(function(){
		var inputFrequenci = mapVars.get(INPUT_FREQUENCI);
		
		var selectVal = $(this).find(":selected").val();
			
		/*
		 * Se a frequencia selecionada for semanalmente, a variável global
		 * frequenci será "w" (Informa ao moment que o salto entre as datas será por semana)
		 * caso contrário o valor será "M" (Informa ao moment que o salto entre as datas será por mês)
		*/
		if(selectVal == WEEKLY_FREQUENCI){
			frequenci = "week";
			inputFrequenci.attr("placeholder","Quantidade de Semanas");
		}else{
			frequenci = "months";
			inputFrequenci.attr("placeholder","Quantidade de Meses");
		}
		setFrequenciDays();
		
	});
}

function onButtonRepeatScheduleDayClick(){
	$(".btn-day").click(function(){
		
		var btn = $(this);
		
		var mapDays = mapVars.get(MAP_SELECTED_DAYS);
		//Pegando o nome do dia do botão clicado
		var dayName = btn.val();
		
		if(btn.hasClass("btn-success")){
			btn.removeClass("btn-success");
			
			mapDays.set(dayName, false);
			
		}else{
			//Setando o dia clicado como true na lista de dias selecionados
			mapDays.set(dayName, true);
			
			btn.addClass("btn-success");
		}
		setFrequenciDays();
	});
}

function setFrequenciDays(){
	var inputFrequenci = mapVars.get(INPUT_FREQUENCI);
	val = inputFrequenci.val() <= 48 ? inputFrequenci.val() : 48;
	if(val == "")
		return;
	
	var mapDays = mapVars.get(MAP_SELECTED_DAYS);	
	
	if(val != ""){
		var resultMap = getDateByDays(mapDays, val);
		mapVars.set(MAP_DAYS, resultMap);
		
		scheduleManager.updateSchedules(resultMap);
		
		//Preenchendo a tabela de dias;
		fillTableDays(resultMap);
	}
}

function onButtonGenerateSchedules(){  
	$("#btn-generate-schedules").bind("click", function(event){
		
		//Removendo todos os timepickers clonados 
		$(".cloned-timepicker").remove();
		//Removendo a classe de input inválido de todos os timepickers.
		$(".row-schedule-id").removeClass("has-error");
		
		var inputVacancy = mapVars.get(INPUT_VACANCY);
		var inputTimePerConsult = mapVars.get(INPUT_COUNT_TIME);
		
		var vacancy = parseInt(inputVacancy.val());
		var timePerConsult = parseInt(inputTimePerConsult.val());
		var timeInit = getTimePickerHourAndMinutes("tmp-init-0");
		
		hour = timeInit["hour"];
		minutes = timeInit["minute"];
		
		momentTime = moment();
		momentTime.hours(hour);
		momentTime.minutes(minutes);
		
		tempInit = momentTime.format("HH:mm");
		momentTime.add('m', timePerConsult);
		tempEnd = momentTime.format("HH:mm");
		
		//Iniciando os 2 timepickers fixos do modal
		$("#tmp-init-1").timepicker('setTime', tempInit);
		$("#tmp-end-1").timepicker('setTime', tempEnd);
		
		//Esse for adiciona novos timepickers em relação a quantidade
		//de vagas e o tempo para cada consulta. Como já existe 2 timepickers fixos
		//a quantidade informada pelo usuário deve ser diminuida em 1
		for(var i = 0; i < vacancy-1; i++){
			tempInit = momentTime.format("HH:mm");
			momentTime.add('m', timePerConsult);
			tempEnd = momentTime.format("HH:mm");
			
			addSchedules({"timeInit":tempInit, "timeEnd":tempEnd, "idSchedule":0});
		}
	});
}


//Função que atualiza a tabela quando o usuário 
//insere a quantidade de meses ou semanas no input
function onInputFrequenciChange(){
	var inputFrequenci = mapVars.get(INPUT_FREQUENCI);
	
	inputFrequenci.bind("change keyup paste", function(){
		setFrequenciDays();
	});
}

function getDateByDays(mapDays, repetition){
	
	var date = moment();
	date.locale('en');
	
	//Lista de dias selecionados
	listDays = [];

	//Variável usada para informar a salto entre a quantidade de semanas ou meses
	var salt = 1;
	
	//Adicionando os dias selecionados na lista listDays
	mapDays.forEach(function(value, key, mapDays){
		if(value){
			listDays.push(key);
		}
	});
	
	/*Variável usada para retorno da função.
	 * Mapa contendo nome do dia da semana e sua respectiva data.
	 * Chave -> data
	 * Value -> dia da semana
	*/
	var resultMap = new Map();
	
	listDays.forEach(function(el, index, listDays){
		var tempDate = moment();
		tempDate.locale('en');
		
		//Procurando a primeira ocorrência do dia selecionado.
		for(var i = 0; i < 7; i++){
			//Pegando o nome do dia.
			day = tempDate.format('dddd');
			if(day == el){
				
				//Depois de encontrado, todas as datas com base na frequencia (mensal ou semanal) são obtidas
				for(var j = 0; j < repetition; j++){
					//Pegando o dia
					newDate = tempDate.format("DD/MM/YYYY");
					
					//Adicionando o dia no mapa.
					resultMap.set(newDate, day);
					
					//Pulando para a data especificada passando a quantidade e a frequencia(meses ou dias)
					tempDate.add(salt, frequenci);
					
					if(frequenci == "months"){
						tempDate.startOf("month");
						
						for(var z = 0; z < 7; z++){
							//Pegando o nome do dia.
							_day = tempDate.format('dddd');
							
							if(_day == el){
								break;
							}
							tempDate.add(1, "day");
						}
					}
				}	
				break;
			}
			//Passando pro dia seguinte
			tempDate.add(1, "day");
		}
		
	});	
	return resultMap;
}

function fillTableDays(mapDays){
	var table = mapVars.get(TABLE_DAYS);
	
	var mapPtBrDays = mapVars.get(MAP_WEEK_DAYS);
	//Removendo as linhas antigas
	table.find("td").remove();
	
	
	mapDays.forEach(function(value, key, mapDays){
		row = $("<tr>");
		data = '"<td class="day-name">'+mapPtBrDays.get(value)+'</td>"';
		data += '"<td>'+key+'</td>"';
		
		var isScheduleRegistered = scheduleManager.isScheduleRegistered(key);
		
		var hiddenAddSchedule = "";
		var hiddenEditSchedule = "hidden";
		var disableRemoveDay = "";
		
		if(isScheduleRegistered){
			hiddenAddSchedule = "hidden";
			hiddenEditSchedule = "";
			disableRemoveDay = "disabled"
		}
		
		
		var trashData = '<td><button value="'+key+'" class="'+disableRemoveDay+' btn btn-danger action-day remove-day"><i class="glyphicon glyphicon-trash"></button></i></td>';
		
		data += '<td>'+
			'<button type="button" value="'+key+'" class="'+hiddenAddSchedule+' action-day add-schedule btn btn-primary">Cadastrar <i class="glyphicon glyphicon-time"></i></button>'
			+'<button type="button" value="'+key+'" class="'+hiddenEditSchedule+' action-day edit-schedule btn btn-warning">Editar <i class="glyphicon glyphicon-pencil"></i></button>'
			+'</td>';
		data += trashData;
		
		row.append(data);
		table.append(row);
	});
	
	onActionTableClick();
	
	
}

/*
 * Função que inicia as funções de cadastrar horário ou remove em um dia selecionado no mapa.
 */
function onActionTableClick(){
	map = mapVars.get(MAP_DAYS);
	mapButtonDays = mapVars.get(MAP_SELECTED_DAYS);
	$(".action-day").click(function(){
		action = $(this);
		var key = action.attr("value");
		
		//Removendo a cor vermelha dos timepickers inválidos.
		$(".row-schedule-id").removeClass("has-error");
		
		
		if(action.hasClass("add-schedule")){
			showModalSchedules(key, REGISTER_ACTION);
		}else if(action.hasClass("remove-day") && !action.hasClass("disabled")){
			var dayName = map.get(key);
			
			//Removendo a data selecionada e preenchendo a tabela novamente
			map.delete(key);
			fillTableDays(map);
			
			//Removendo todos os horários cadastrados do dia removido.
			scheduleManager.removeScheduleDay(key);
			
			//Conferindo se ainda existe uma ocorrencia do dia da semana exluído
			if(!hasValue(dayName, map)){
				//Removendo a classe que deixa o botão verde, já que não existe mais ocorrência do dia
				//vinculado a ele.
				$("#days-buttons").find("button[value="+dayName+"]").removeClass("btn-success");
				
				//Informando que o dia não está mais selecionado
				mapButtonDays.set(dayName, false);
			}
		}else if(action.hasClass("edit-schedule")){
			var scheduleDay = scheduleManager.getSchedulesMap().get(key);
			var listSchedules = scheduleDay.getListSchedules();
			setEditModalSchedule(key, listSchedules);
		}
	});
}


function setEditModalSchedule(date, listSchedules){
	
	if(listSchedules && listSchedules.length){
		showModalSchedules(date, EDIT_ACTION);
		
		var rowsTimepickers =  $("#row-add-schedules").find(".row-schedule-id");
		rowsTimepickers.attr("id",listSchedules[0].getId());
		
		initTimepicker("tmp-init-1", listSchedules[0].getTimeInit());
		initTimepicker("tmp-end-1", listSchedules[0].getTimeEnd());
		
		if(isOldSchedule(date, listSchedules[0])){
			$("#tmp-init-1").attr("disabled","disabled");
			$("#tmp-end-1").attr("disabled","disabled");
		}
		
		for(var i = 1; i < listSchedules.length; i++){
			var tempSch = listSchedules[i];
			var disabled = "";
			if(isOldSchedule(date, tempSch)){
				disabled = "disabled";
			}
			addSchedules({"timeInit":tempSch.getTimeInit(), "timeEnd":tempSch.getTimeEnd(), "idSchedule":tempSch.getId(), "disabled" : disabled});
		}
	}else{
		console.log("O dia "+key+" não possui horários cadastrados!");
	}
}

function isOldSchedule(date, sch){
	var timeNow = new Date();
	var timeSchedule = getFormatedDate(date);
	var arrayTime = sch.getTimeInit().split(":");
	var hour = arrayTime[0];
	var minute = arrayTime[1];
	
	timeSchedule.setHours(hour);
	timeSchedule.setMinutes(minute);
	
	return ( timeSchedule.getTime() < timeNow.getTime() )
	
}


function getProfessionalConsultations(func){
	ajaxCall("/siac/getConsutationsByProfessionalJSON", null, function(json){
		updateScheduleManagerList(json);
		if(func)
			func();
	}, function(){
		alertMessage("Ops, não foi possível preencher seu calendário!");
	});
	
}

function updateScheduleManagerList(json){
	console.log(json);
	var length = Object.keys(json).length;
	if(length == 0){
		alertMessage("Ops, você ainda não possui nenhum horário de consulta cadastrado!");
	}else{
		//Removendo todos os horários em memória.
		scheduleManager.clearSchedules();
		for(var i = 0; i < length; i++){
			var obj = json[i]; 
			
			date = new Date(obj.dateInit);
			
			var timeInit = moment(obj.dateInit);
			var timeEnd = moment(obj.dateEnd);
			console.log(timeInit);
			
			var rating = null;
			var comment = null;
			var patientName = null;
			
			if(obj.rating){
				rating = obj.rating.rating;
				comment = obj.rating.comment;
			}
			
			scheduleManager.addNewScheduleTime(date, timeInit.hours(), timeInit.minutes(), timeEnd.hours(), timeEnd.minutes(), obj.state, rating, comment, obj.id, obj.patient);
		}	
	}
		
}

function fillProfessionalCalendar(){
	var scheduleMap = scheduleManager.getSchedulesMap();
	var events = [];
	scheduleMap.forEach(function(value, key, scheduleMap){
		
		var sch = value;
		
		
		//Criando uma data no formato YYYY-DD-MM
		var eventDate = moment(getFormatedDate( sch.getDate()));
		
		
		var event = new Object();
		event.id = sch.getListSchedules().length;
		
		if(sch.getListSchedules().length > 1){
			event.color = colors.get("GS").hex;
			event.title = "Consultas";
		}else{
			event.color = colors.get(sch.getListSchedules()[0].getState()).hex;
			event.title = "Consulta";
		} 
		
		event.start = eventDate;
		event.allDay = false;
		events.push(event);
	});
		
	var calendar = mapVars.get(CALENDAR_ID);
	
	renderCalendarEvents(events, calendar);
	
}


/*
 Essa função renderiza um evento no calendário passado por parâmetro.
*/
function renderCalendarEvents(events, calendar){
	calendar.fullCalendar('removeEvents');
	events.forEach(function(value, index, events){
		calendar.fullCalendar('renderEvent', value, true);
	});
}




/*
   Função que recebe um valor e um mapa
   e retorna true se o mapa tiver esse valor
   e false caso contrário
*/
function hasValue(value, map){
	var res = false;
	map.forEach(function(val, key, map){
		if(val == value){
			res = true;
		}
	});
	return res;
}

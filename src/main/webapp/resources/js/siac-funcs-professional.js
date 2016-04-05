/**
 * Funcionalidades javascript na visão do profissional
 */

var scheduleManager = new ScheduleManager();

var REGISTER_ACTION = "Cadastrar";
var EDIT_ACTION = "Editar";

var MY_CALENDAR = 0;
var REGISTER_SCHEDULE = 1;
var MY_CONSULTATIONS = 2;

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
colors.set("GS", {hex : "#000000"});



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
	
	onButtonRegisterSchedulesClick();
	
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
	
});

function onLiItemServiceClick(){
	var calendar = mapVars.get(CALENDAR_ID);
	$(".service-item").click(function(){
		
		$(".service-item").removeClass("active");
		$(this).addClass("active");
		id = $(this).attr("id");
		
		$("#calendar-legend").addClass("hidden");
		
		if(id == REGISTER_SCHEDULE){
			calendar.addClass("hidden");
			$("#my-calendar").addClass("hidden");
			$("#panel-register-schedules").removeClass("hidden");
			$("#panel-my-consultations").addClass("hidden");
		}else if(id == MY_CALENDAR){
			getProfessionalConsultations(fillProfessionalCalendar);
			calendar.removeClass("hidden");
			$("#my-calendar").removeClass("hidden");
			$("#panel-register-schedules").addClass("hidden");
			$("#panel-my-consultations").addClass("hidden");
			$("#calendar-legend").removeClass("hidden");
		}else if(id == MY_CONSULTATIONS){
			getProfessionalConsultations(fillMyConsultationsCollapses);
			calendar.addClass("hidden");
			$("#my-calendar").addClass("hidden");
			$("#panel-register-schedules").addClass("hidden");
			$("#panel-my-consultations").removeClass("hidden");
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
	             
	    eventClick: function(date, jsEvent, view, event) {
	    	var date = moment(new Date(date.start._d)).format("DD/MM/YYYY");
//	    	setEditModalSchedule(date, scheduleManager.getSchedulesMap().get(date).getListSchedules());
	    	$("#modal-schedules-description").modal("show");
	    	fillDescriptionSchedulesTable(scheduleManager.getScheduleDay(date));
	    }  		         		         
	});
	
}

function fillDescriptionSchedulesTable(scheduleDay){
	
	if(!scheduleDay) return;
	
	var tbody = $("#tbody-schedules-description");

	tbody.find("tr").remove();
	
	var listSchedules = scheduleDay.getListSchedules();
	
	listSchedules.forEach(function(sch, index, listSchedules){
		
		var css = colors.get(sch.getState()).css;
		
		var row = $("<tr>");
		var data = "";
		data += "<td>"+sch.getTimeInit()+"</td>";
		data += "<td>"+sch.getTimeEnd()+"</td>";
		data += "<td>"+colors.get(sch.getState()).text+"</td>";
		data += "<td>"+sch.getRating()+"</td>";
		row.append(data);
		tbody.append(row);
	});
}


//Preenche o painel de minhas consutlas e seus respectivos horários
function fillMyConsultationsCollapses(){
	var map = scheduleManager.getSchedulesMap();
	$("#message-no-cosultations").addClass("hidden");
	if(map.size == 0){
		$("#message-no-cosultations").removeClass("hidden");
		alertMessage("Ops, Você não possui consultas cadastradas!", null, ALERT_ERROR);
	}
	var panelGroup = $("#collapse-panel-group");
	var fixedPanel = $("#fixed-panel-collapse");
	
	//Removendo todos os collapses clonados...
	$(".cloned-collapse").remove();
	
	map.forEach(function(scheduleDay, key){
		var clonedCollapse = fixedPanel.clone();
		clonedCollapse.removeClass("hidden");
		var collapseId = "collapse-id-"+key.replace(/\//g,"-");
		
		clonedCollapse.attr("id","cloned-panel-collapse-"+collapseId);
		clonedCollapse.addClass("cloned-collapse");
		
		var clonedCollapseHeader = clonedCollapse.find("a");
		clonedCollapseHeader.html("Consultas do dia <strong>"+key+"</strong>");
		clonedCollapseHeader.attr("href", "#"+collapseId);
		
		var panelCollapse = clonedCollapse.find(".panel-collapse");
		panelCollapse.attr("id", collapseId);
		
		var tbody = panelCollapse.find(".collapse-tbody");
		tbody.attr("id", "tbody-"+collapseId);
		
		panelGroup.append(clonedCollapse);
		
		fillMyConsultationTable("tbody-"+collapseId, map.get(key).getListSchedules());
	});
	
	$(".collapse-header").click(function(){
		$(".my-collapse-panel").removeClass("panel-primary").addClass("panel-default");
		$(this).parents(".panel-default").removeClass("panel-default").addClass("panel-primary");
		$(this).siblings(".collapse-icon").removeClass("glyphicon-plus").addClass("glyphicon-minus");
	});
	
	$('.collapse').on('hidden.bs.collapse', function () {
		$(".my-collapse-panel").removeClass("panel-primary").addClass("panel-default");
		$(".my-collapse-panel").find(".collapse-icon").removeClass("glyphicon-minus").addClass("glyphicon-plus");
	});
	
}

function fillMyConsultationTable(tbodyId, scheduleList){
	var tbody = $("#"+tbodyId);
	scheduleList.forEach(function(sday){
		var row = $("<tr>");
		var tdata = "";
		
		tdata += "<td>"+sday.getTimeInit()+"</td>";
		tdata += "<td>"+sday.getTimeEnd()+"</td>";
		tdata += "<td>"+colors.get(sday.getState()).text+"</td>";
		
		var disabled = "";
		if((sday.getState() == "RD") || (sday.getState() == "CD")){
			disabled = "disabled='disabled'";
		}
		tdata += '<td><button type="button" value='+sday.getId()+' class="action-info-consultation btn btn btn-primary"><span class="glyphicon glyphicon-info-sign"></span></button></td>'
		tdata += '<td><button type="button" value='+sday.getId()+' class="btn btn btn-danger action-cancel-consultation" '+disabled+' ">Cancelar Horário <span class="glyphicon glyphicon-remove-circle"></span></button></td>'
			
		row.append(tdata);
		tbody.append(row);
		
	});
	
	$(".action-cancel-consultation").click(function(){
		var scheduleId = $(this).attr("value"); 
		var modalCancel = $("#modal-cancel-consultation");
		modalCancel.find("#btn-cancel-consultation").attr("value", scheduleId);
		modalCancel.modal("show");
	});
	
	$(".action-info-consultation").click(function(){
		var scheduleId = $(this).attr("value");
		var res = scheduleList.filter(function(scheduleDay){
			if(scheduleDay.getId() == scheduleId)
				return scheduleDay;
		});
		var schDay = res[0];
		$("#modal-consultation-details").modal("show");
		$("#textarea-comment").text(schDay.getComment());
		$("#div-rating").html(schDay.getRating());
	});
	
	
}

function onBtnCancelConsultationClick(){
	$("#btn-cancel-consultation").click(function(){
		var modalCancel = $("#modal-cancel-consultation");
		modalCancel.modal("hide");
		var scheduleId = $(this).attr("value"); 
		ajaxCallNoJSON("/siac/cancelConsultation", {"id":scheduleId}, function(){
			alertMessage("Consulta cancelada com sucesso!", null, ALERT_SUCCESS);
			$(".action-cancel-consultation[value="+scheduleId+"]").addClass("disabled");
		}, function(){
			alertMessage("Ops, não foi possível cancelar essa consulta", null, ALERT_ERROR);
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
		var timepickersObject = modal.find("#panel-schedules").find(".timepicker").find("input");
		

		var timepickersIdArray = []
		var tempArray = $.makeArray(timepickersObject);
		
		for(var i = 0; i < tempArray.length-1; i+=2){
			if(tempArray[i].id && tempArray[i+1].id){
				timepickersIdArray.push([tempArray[i].id, tempArray[i+1].id]);
			}
		}
		
		
		var labelDay = $("#label-date-clicked");
		var date = labelDay.text();
		
		if(timepickersIdArray.length > 0){
			//Salvando os horários no objeto schedule manager
			saveSchedules(timepickersIdArray, date);
			 
			//Quando o profissional cadastrar os horários
			//o botão do de cadastrar será alterado para o de editar
			var panel = $("#panel-register-schedules");
			
			var buttonAddSchedules = panel.find("button[value='"+date+"']").filter(".add-schedule");
			var buttonEditSchedules = buttonAddSchedules.siblings(".edit-schedule");
			
			buttonEditSchedules.removeClass("hidden");
			buttonAddSchedules.addClass("hidden");
			
			
			
		}
		
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
	
	for(var i = 0; i < timepickersId.length; i++){
		
		var timeInit = getTimePickerHourAndMinutes(timepickersId[i][0]);
		var timeEnd = getTimePickerHourAndMinutes(timepickersId[i][1]);
		
		scheduleDay.addSchedule(timeInit["hour"], timeInit["minute"], timeEnd["hour"], timeEnd["minute"]);
	}
	scheduleManager.addScheduleDay(date, scheduleDay);
	
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
		addSchedules(null, null);
	});
}

function addSchedules(timeInit, timeEnd){
	
	var newRow = $("#row-add-schedules").clone();
	newRow.attr("id","");
	
	timeInitId = "tmp-init-"+getNewInitTimePickerId();
	timeEndId = "tmp-end-"+getNewEndTimePickerId();
	
	var timepickerInit = newRow.find(".timepicker-init"); 
	var timepickerEnd = newRow.find(".timepicker-end"); 
	
	timepickerInit.find("input").attr("id", timeInitId);
	timepickerEnd.find("input").attr("id", timeEndId);
	
	//Adicionando a classe que informa que o timepicker é clonado.
	newRow.addClass("cloned-timepicker");
	
	var button = newRow.find(".add-schedule");
	button.removeClass("add-schedule");
	button.addClass("remove-schedule");
	button.removeClass("btn-primary");
	button.addClass("btn-danger");
	
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
			frequenci = "w";
			inputFrequenci.attr("placeholder","Quantidade de Semanas");
		}else{
			frequenci = "M";
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
	val = inputFrequenci.val();
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
			
			addSchedules(tempInit, tempEnd);
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
				for(var i = 0; i < repetition; i++){
					//Pegando o dia
					newDate = tempDate.format("DD/MM/YYYY");
					//Adicionando o dia no mapa.
					resultMap.set(newDate, day);
					//Pulando para a data especificada passando a quantidade e a frequencia(meses ou dias)
					tempDate.add(salt, frequenci);
				}	
				break;
			}
			//Passando pro dia seguinte
			tempDate.add("d", 1);
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
		
		console.log("Key: "+key+" - "+isScheduleRegistered);
		
		if(isScheduleRegistered){
			hiddenAddSchedule = "hidden";
			hiddenEditSchedule = "";
		}
		
		data += '<td>'+
			'<button type="button" value="'+key+'" class="'+hiddenAddSchedule+' action-day add-schedule btn btn-primary">Cadastrar <i class="glyphicon glyphicon-time"></i></button>'
			+'<button type="button" value="'+key+'" class="'+hiddenEditSchedule+' action-day edit-schedule btn btn-warning">Editar <i class="glyphicon glyphicon-pencil"></i></button>'
			+'</td>';
		data += '<td class="action-day remove-day" value="'+key+'"><i class="glyphicon glyphicon-trash"></i></td>';
		
		row.append(data);
		table.append(row);
	});
	
	onActionTableClick();
	
	//Desabilitando o botão de cadastrar horários caso não exista horários cadastrados.
	mapDays.size > 0 ? $("#btn-register-schedules").removeClass("disabled") : $("#btn-register-schedules").addClass("disabled");
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
		
		if(action.hasClass("add-schedule")){
			showModalSchedules(key, REGISTER_ACTION);
		}else if(action.hasClass("remove-day")){
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
		initTimepicker("tmp-init-1", listSchedules[0].getTimeInit());
		initTimepicker("tmp-end-1", listSchedules[0].getTimeEnd());
		
		for(var i = 1; i < listSchedules.length; i++){
			addSchedules(listSchedules[i].getTimeInit(), listSchedules[i].getTimeEnd());
		}
	}else{
		console.log("O dia "+key+" não possui horários cadastrados!");
	}
}

function onButtonRegisterSchedulesClick(){
	$("#btn-register-schedules").click(function(){
		var json = JSON.stringify(scheduleManager);
		var params = {"json" : json};
		ajaxCallNoJSON("/siac/saveConsultation", params, function(){
			alertMessage("Horários cadastrados com sucesso!", null, ALERT_SUCCESS);
		}, function(){
			alertMessage("Ops, não foi possível cadastrar os horários!");
			console.log("Erro at register schedules!");
		});
	});
}

function getProfessionalConsultations(func){
	ajaxCall("/siac/getConsutationsByProfessionalJSON", null, function(json){
		updateScheduleManagerList(json);
		func();
	}, function(){
		alertMessage("Ops, não foi possível preencher seu calendário!");
	});
	
}

function updateScheduleManagerList(json){
	var length = Object.keys(json).length;
	if(length == 0){
		alertMessage("Ops, você ainda não possui nenhum horário de consulta cadastrado!");
	}else{
		//Removendo todos os horários em memória.
		scheduleManager.clearSchedules();
		for(var i = 0; i < length; i++){
			var obj = json[i]; 
			
			date = moment(new Date(obj.dateInit)).format("DD/MM/YYYY");
			var timeInit = moment(obj.dateInit);
			var timeEnd = moment(obj.dateEnd);
			
			var rating = null;
			var comment = null;
			
			if(obj.rating){
				rating = obj.rating.rating;
				comment = obj.rating.comment;
			}
			
			scheduleManager.addNewScheduleTime(date, timeInit.hours(), timeInit.minutes(), timeEnd.hours(), timeEnd.minutes(), obj.state, rating, comment, obj.id);
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
		event.id = 10;
		
		if(sch.getListSchedules().length > 1){
			event.color = colors.get("GS").hex;
		}else{
			console.log(sch.getListSchedules());
			event.color = colors.get(sch.getListSchedules()[0].getState()).hex;
		} 
		event.title = "Consulta";
		event.start = eventDate;
		event.allDay = false;
		events.push(event);
	});
		
	var calendar = mapVars.get(CALENDAR_ID);
		
	renderCalendarEvents(events, calendar);
	
}

function getFormatedDate(stringDate){
	//Formatando a data para YYYY-DD-MM
	//sch.getDate() retorna a data em DD/MM/YYYY
	//Logo é feito um split que é usado para criar um objeto date no formato abaixo.
	var from = stringDate.split("/");
	return new Date(from[2], from[1] - 1, from[0]);
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

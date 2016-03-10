/**
 * Funcionalidades javascript na visão do profissional
 */

var MY_CALENDAR = 0;
var REGISTER_SCHEDULE = 1;

var CALENDAR_ID = "#calendar_professional";
var SELECT_REPEAT_SCHEDULE_ID = "#select-repeat-schedule";
var MAP_SELECTED_DAYS = "map_selected_days";
var INPUT_FREQUENCI = "input_frequenci";
var TABLE_DAYS = "table_days";

var MAṔ_DAYS = "map_days"; 
var MAP_WEEK_DAYS = "map_week_days";

//Constantes usadas para indentificar qual a
//frequencia de repetição dos horários.
var MONTHLY_FREQUENCI = "monthly";
var WEEKLY_FREQUENCI = "weekly";

//Variável usada para saber a frequencia das repetições
//dos horários.
var frequenci = "w";

var idTimepickersInit = 1;
var idTimepickersEnd = 1;

// Mapa com todas as variáveis
var mapVars = new Map();


$("document").ready(function(){
	
	//Adicionando elementos DOM no mapa de variaveis
	mapVars.set(CALENDAR_ID, $("#calendar_professional"));
	mapVars.set(SELECT_REPEAT_SCHEDULE_ID, $("#select-repeat-schedule"));
	mapVars.set(INPUT_FREQUENCI, $("#input-frequenci"));
	mapVars.set(TABLE_DAYS, $("#tbody-table-days"));
	
	initCalendarProfessional();	
	onButtonAddScheduleClick();
	onSelectScheduleRepeatClick();
	onButtonRepeatScheduleDayClick();
	onInputFrequenciChange();
	onLiItemServiceClick();
	
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
	
});

function onLiItemServiceClick(){
	var calendar = mapVars.get(CALENDAR_ID);
	$(".service-item").click(function(){
		id = $(this).attr("id");
		if(id == REGISTER_SCHEDULE){
			calendar.addClass("hidden");
			$("#my-calendar").addClass("hidden");
			$("#panel-register-schedules").removeClass("hidden");
		}else if(id == MY_CALENDAR){
			calendar.removeClass("hidden");
			$("#my-calendar").removeClass("hidden");
			$("#panel-register-schedules").addClass("hidden");
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
	    dayClick: clickFunction,
	    eventClick: clickEvent,
		         
	    dayClick: function(date, jsEvent, view, event) {
	    	//Função que abre o modal para cadastrar horário
	    	showModalRegisterSchedule(date.format("DD/MM/YYYY"));
	    }  		         		         
	});
	
}

function showModalRegisterSchedule(date){
	var modal =	$("#modal-day");
	var modalDescription = $("#modal-description-body");
	
	//Removendo todos os timepickers clonados.
	modal.find(".cloned-timepicker").remove();
	
	//Resetando os ids dos timepickers;
	idTimepickersInit = 1;
	idTimepickersEnd = 1;
	
	//Mostrando o modal
	modalDescription.html("Cadastrar consultas para o dia <strong>"+date+"</strong>");
	modal.modal('show');
    
	//Iniciando os dois times picker iniciais
	initTimepicker("tmp-init-1");
	initTimepicker("tmp-end-1");
}

// Função que inicia um timepicker passado por parâmetro
function initTimepicker(idPicker){
	idPicker = "#"+idPicker;
	$(idPicker).timepicker({showMeridian: false});
	$(idPicker).timepicker('setTime', '08:00');
}

// Função que inicializa a ação de click no botão de adicionar horário 
function onButtonAddScheduleClick(){
	$(".add-schedule").click(function(){
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
		initTimepicker(timeInitId);
		initTimepicker(timeEndId);		
		
		//Iniciando o metodo de remover os horários caso o usuário clique no botar de remover horário.
		onButtonRemoveScheduleClick();
	});
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
		mapVars.set(MAṔ_DAYS, resultMap);
		fillTableDays(resultMap);
	}
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
		data += '<td class="action-day add-schedule" value="'+key+'"><button type="button" class="btn btn-primary">Cadastrar <i class="glyphicon glyphicon-time"></i></button></td>';
		data += '<td class="action-day remove-day" value="'+key+'"><i class="glyphicon glyphicon-trash"></i></td>';
		row.append(data);
		table.append(row);
	});
	
	onActionTableClick();
}

/*
 * Função que inicia as funções de cadastrar horário ou remove em um dia selecionado no mapa.
 */
function onActionTableClick(){
	map = mapVars.get(MAṔ_DAYS);
	mapButtonDays = mapVars.get(MAP_SELECTED_DAYS);
	$(".action-day").click(function(){
		action = $(this);
		var key = action.attr("value");
		
		if(action.hasClass("add-schedule")){
			showModalRegisterSchedule(key);
		}else if(action.hasClass("remove-day")){
			var dayName = map.get(key);
			
			//Removendo a data selecionada e preenchendo a tabela novamente
			map.delete(key);
			fillTableDays(map);
			
			//Conferindo se ainda existe uma ocorrencia do dia da semana exluído
			if(!hasValue(dayName, map)){
				//Removendo a classe que deixa o botão verde, já que não existe mais ocorrência do dia
				//vinculado a ele.
				$("#days-buttons").find("button[value="+dayName+"]").removeClass("btn-success");
				
				//Informando que o dia não está mais selecionado
				mapButtonDays.set(dayName, false);
			}
		}
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

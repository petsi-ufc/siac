var scheduleManager = new ScheduleManager();

var MAP_WEEK_DAYS = "map_week_days";
var TABLE_DAYS = "table_days";
var MAP_SELECTED_DAYS = "map_selected_days";
var REGISTER_ACTION = "Cadastrar";


var CALENDAR_ID = "#calendar_professional";
var SELECT_REPEAT_SCHEDULE_ID = "#select-repeat-schedule";
var MAP_SELECTED_DAYS = "map_selected_days";
var INPUT_FREQUENCI = "input_frequenci";
var TABLE_DAYS = "table_days";


var INPUT_VACANCY = "input_vacancy";
var INPUT_COUNT_TIME = "input_count_time";

var MAP_DAYS = "map_days"; 
var MAP_WEEK_DAYS = "map_week_days";

//Constantes usadas para indentificar qual a
//frequencia de repetição dos horários.
var MONTHLY_FREQUENCI = "monthly";
var WEEKLY_FREQUENCI = "weekly";

//Variável usada para saber a frequencia das repetições
//dos horários.
var frequenci = "w";

//Mapa com todas as variáveis
var mapVars = new Map();

//Adicionando elementos DOM no mapa de variaveis
mapVars.set(CALENDAR_ID, $("#calendar_professional"));
mapVars.set(SELECT_REPEAT_SCHEDULE_ID, $("#select-repeat-schedule"));
mapVars.set(INPUT_FREQUENCI, $("#input-frequenci"));
mapVars.set(TABLE_DAYS, $("#tbody-table-days"));
mapVars.set(INPUT_VACANCY, $("#input-count-vacancy"));
mapVars.set(INPUT_COUNT_TIME, $("#input-count-time"));

var worker = null;
if(typeof(Worker) !== "undefined"){
	worker = new Worker('resources/js/doWork.js');
}else{
	alert("não dá suporte ao worker");
}

(function(){
	
	initTimepicker("tmp-init-1",null);
	initTimepicker("tmp-end-1",null);
	
	
	angular.module("siacApp")
	
	/*.run(function($rootScope, professionalService){
		$rootScope.events = [];
		/*function runConsultations(datas){
			datas.forEach(function (value, key){
				var title = "";
				if(value.state == "FR")	title = "Livre";
				else if (value.state == "CD" &&  value.group == null &&  value.patient == null) title = "Cancelada";
				else title =  value.group == null ? value.patient.name : value.group.title;
				var e = {
					title: title,
					color: colors.get(value.state).hex,
					state: value.state,
					start:value.dateInit,
					end: value.dateEnd,
					date: value.date,
					id: value.id,
					isGroup: value.group != null,
					comment: value.comment
				};
				if(value.group != null)
					e.group = value.group;
				$rootScope.events.push(e);
			});
		}
	})*/
	
	.controller("professionalController", function($scope,$rootScope, $compile, $sce,$timeout ,uiCalendarConfig, professionalService){
		
		//Variables
		$scope.menuIndex = 0;
		$scope.generetedSchedules = [];
		$scope.events = [];
		$scope.eventSources = [$scope.events];
		$scope.buscarPaciente = "";
		$scope.groups = [];
		$scope.patients = [];
		$scope.eventsForDay = [];
		$scope.frequencyList = [];
		$scope.allConsToCancel = [];
		$scope.chGroupNowConsultation = false;
		$scope.chPatientNowConsultation = false;
		$scope.modelCheck = false;
		$scope.showReport = true;
		$scope.initGroup = true;
		
		//Colors
		var colors = new Map();
		colors.set("SC", {text: "Agendada", hex : "#4682B4", css: "color-blue"});
		colors.set("RS", {text: "Agendada", hex : "#4682B4", css: "color-blue"});
		colors.set("FR", {text: "Disponível", hex : "#32CD32", css: "color-green"});
		colors.set("RD", {text: "Realizada", hex : "grey", css: "color-grey"});
		colors.set("RV", {text: "Reservada", hex : "#D9D919", css: "color-yellow"});
		colors.set("CD", {text: "Cancelada", hex : "#FF0000", css: "color-red"});
		colors.set("NO", {text: "Não Agendado", hex : "#000000", css: "color-black"});
		colors.set("GS", {hex : "#FA6900"});
		
		//Group
		$scope.grupo = {};
		$scope.grupo.patients = [];
		
		var selectedDay = {};
		
		$scope.isPacientConsultation = false;
		$scope.isFreeConsultation = false;
		$scope.isGroupConsultation = false;
		$scope.showConsultationButtons = true;
		
		
		//Functions
		$scope.canShow = _canShow;
		$scope.setMenuIndex = _setMenuIndex;
		$scope.generateSchedules = _generateSchedules;
		$scope.addTempSchedule = _addTempSchedule;
		$scope.removeSchedule = _removeSchedule;
		$scope.saveFreeConsultations = _saveFreeConsultations;
        $scope.saveConsultations = _saveConsultations;
        $scope.saveGroupConsultation = _saveGroupConsultation;
		$scope.setPacientConsultation = _setPacientConsultation;
		$scope.setFreeConsultation = _setFreeConsultation;
		$scope.setGroupConsultation = _setGroupConsultation;
		$scope.addPatient = _addPatient;
		$scope.removePatient = _removePatient;
		$scope.createGroup = _createGroup;
		$scope.updateGroup = _updateGroup;
		$scope.saveUpdateGroup = _saveUpdateGroup;
		$scope.addGroup = _addGroup;
		$scope.removeGroup = _removeGroup;
		$scope.addConPatient = _addConPatient;
		$scope.removeConPatient = _removeConPatient;
		$scope.cancelAllConsultation = _cancelAllConsultation;
		$scope.registerConsultation = _registerConsultation;
		$scope.saveRegister = _saveRegister;
		$scope.cancelConsultation = _cancelConsultation; 
		$scope.reschedulingConsultation = _reschedulingConsultation;
		$scope.registerCancelConsultation = _registerCancelConsultation;
		$scope.registerAllCancelConsultation = _registerAllCancelConsultation;
		$scope.registerReschedulingConsultation = _registerReschedulingConsultation;
		$scope.addFrequencyList = _addFrequencyList;
		$scope.addFrequencyListAll = _addFrequencyListAll;
		$scope.registerFrequencyList = _registerFrequencyList;
		$scope.showComment = _showComment;
		$scope.registerComment = _registerComment;
		$scope.showConsultationOfGroup = _showConsultationOfGroup;
		$scope.getMyGroups = _getMyGroups;
		$scope.getFrequencyList = _getFrequencyList;
		$scope.reload = _reload;
		$scope.viewComment = _viewComment;
		$scope.MouseOver = _MouseOver;
		
		//Array destinado ao gerenciamento da agenda de horários
		$scope.scheduler = [];
		//funções destinadas ao gerenciamento da agenda de horários
		$scope.addScheduler = _addScheduler;
		$scope.onShowSchedulers = _onShowSchedulers;
		$scope.removeScheduler = _removeScheduler; 
		$scope.plusScheculer = _plusScheculer;
		$scope.confirmeSchduler = _confirmeSchduler;
		$scope.removeDate = _removeDate;
		$scope.consolidateAgenda = _consolidateAgenda;
		$scope.setFrequenciDays = _setFrequenciDays;
		$scope.fillTableDays = _fillTableDays;
		$scope.getDateByDays = _getDateByDays;
		
		
		configureModal();
		loadPatients();
		//getMyGroups();
		
		
		//Configuração do calendário.
		$scope.uiConfig = {
			calendar : {
				header:{
					left:'prev',
					center:'title',
					right:'next'
				},
				businessHours: true,
				editable : false,
				displayEventTime : true,
				eventClick : function(data, jsevent, view, event){
					
					var d = new Date(data.start._d);
					var e = [];
					
					$scope.events.forEach(function (value, key){
						var dvs = new Date(Date.parse(value.start));
						var dve = new Date(Date.parse(value.end));
						if(dvs.getDate() == d.getDate() && dvs.getMonth() == d.getMonth() && dvs.getYear() == d.getYear()){
							var v = angular.copy(value);
							v.date = dvs.getDate()+1+"/"+(dvs.getMonth()+1)+"/"+dvs.getFullYear();
							v.color = returnState(value.color);
							v.start = dvs.getHours() +":"+dvs.getMinutes();
							v.end = dve.getHours()+":"+dve.getMinutes();
							
							e.push(v);
						}
					});
					$scope.eventsForDay = e;
					$("#modal-schedules-description").modal("show");
				},
				dayClick : _dayClick,
				eventRender: _MouseOver,
				events: getConsultations,
				monthNames: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
			    monthNamesShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
			    dayNames: ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sabado'],
			    dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab']
			}
		};
		

		var DataConsultation;
		function getConsultations(a,b,c){
			var init = a._d;
			var end = b._d;
			init = (init.getFullYear())+"-"+(init.getMonth()+1)+"-"+(init.getDate()+1);
			end = (end.getFullYear())+"-"+(end.getMonth()+1)+"-"+(end.getDate()+1);
			
			showSnack("Carregando as consultas, aguarde...");
			var start = new Date().getTime();
			professionalService.getProfessionalConsultations(init, end, function(data){
				var end = new Date().getTime();
				$scope.events.length = 0;
				DataConsultation = data;
				//worker.postMessage({datas: data.data});
				data.data.forEach(function (value, key){
					var title = "";
					if(value.state == "FR")	title = "Livre";
					else if (value.state == "CD" &&  value.group == null &&  value.patient == null) title = "Cancelada";
					else title =  value.group == null ? value.patient.name : value.group.title;
					var e = {
						title: title,
						color: colors.get(value.state).hex,
						state: value.state,
						start:value.dateInit,
						end: value.dateEnd,
						date: value.date,
						id: value.id,
						isGroup: value.group != null,
						comment: value.comment
					};
					if(value.group != null)
						e.group = value.group;
					$scope.events.push(e);
					hideSnack();
				});
				console.log($scope.events);
				/*worker.addEventListener('message', function(e) {
					console.log(e.data);
					//console.log($scope.uiConfig)
					//for(var i=0;i<e.data.length;i++)
						//$scope.events.push(e.data[i]);
					e.data.forEach(function(value, key){
						$scope.events.push(value);
					});																																																																																									
					//$scope.uiConfig.fullCalendar('refetchEvents');
					$('#calendar').fullCalendar('refetchEvents')
					console.log($scope.events);
				}, false);*/
			});
		}
		
		function _MouseOver(event, element, view){
							
			if(event.state == "SC"){
				var title;
				if(event.isGroup){
					
					title = event.title;
				}else{
					var nome;
					professionalService.getReserveByidConsultation(event.id,function(data){
						data.data.forEach(function(value,key){
							var config_tooltip =
							{
							    'title':  value.patient.name,
							    'tooltip-append-to-body': true
							};
							$timeout(function(){
								 element.attr(config_tooltip);
							});	
							
						});
					});
					
										
					
				}
				var config_tooltip =
				{
				    'title': title,
				    'tooltip-append-to-body': true
				};
				$timeout(function(){
					 element.attr(config_tooltip);
				});	
				
				
			}
			if(event.state == "CD"){
				var title = "Consulta Cancelada!";
				var config_tooltip =
				{
				    'title': title,
				    'tooltip-append-to-body': true
				};
				$timeout(function(){
					 element.attr(config_tooltip);
				});	
				
			}
		if(event.state == "FR"){
			var title = "Consulta Livre!";
			var config_tooltip =
			{
			    'title': title,
			    'tooltip-append-to-body': true
			};
			$timeout(function(){
				 element.attr(config_tooltip);
			});	
		}
	
		}
		
		function _addFrequencyList(index){
			$scope.frequencyList[index].presence = !$scope.frequencyList[index].presence;
			
		}
		
		function _addFrequencyListAll(){
			for (var i = 0; i < $scope.frequencyList.length; i++) {
				$scope.addFrequencyList(i);
			}
		}

		
		function _registerFrequencyList(){
			console.log($scope.frequencyList);
			professionalService.registerFrequency({frequencyList:$scope.frequencyList}, function(response){
				var message = response.data.message;
				if(response.data.code == 200){
					alertMessage(message,null,ALERT_SUCCESS);
					location.reload();
				}else{
					console.log(response.data);
					alertMessage(message,null,ALERT_ERROR);
				}
			});
		}
		
		function _showConsultationOfGroup(group){
			$("#modal-consultation-group").modal("show");
			showSnack("Carregando as consultas...");
			professionalService.getConsultationsByGroup({id:group.id}, function(response){
				var consultations = response.data.message;
				if(response.data.code == 200){
					group.listConsultations =  JSON.parse(consultations);
				}else{
					alert(consultations,null,ALERT_ERROR);
				}
			});
			$scope.groupVisibleConsultation = group;
		}
		
		function viewFrequencyListOfGroup(frequency){
			$scope.frequencyListOfGroup = frequency;
			$("#modal-view-frequency-group").modal("show");
		}
		
		function _getFrequencyList(idConsultation){
			professionalService.getFrequencyList({id:idConsultation}, function(response){
				var message = response.data.message;
				console.log(message);
				if(response.data.code == 200){
					viewFrequencyListOfGroup(JSON.parse(message));
				}else{
					alertMessage(message,null,ALERT_ERROR);
				}
			});
		}
		
		function _showComment(id){
			$("#modal-comment").modal("show");
			$scope.idConsultationToComment = id; 
		}
		
		function _registerComment(comment){
			professionalService.registerComment({id:$scope.idConsultationToComment, comment: comment}, function(response){
				var message = response.data.message;
				if(response.data.code == 200){
					alertMessage(message,null,ALERT_SUCCESS);
				}else{
					alertMessage(message,null,ALERT_ERROR);
				}
			});
		}
		
		function _reload(){
			location.reload();
		}
		
		function _viewComment(comment, id){
			$("#modal-comment").modal("show");
			$scope.idConsultationToComment = id;
			$scope.comentario = comment;
		}
				
		function _dayClick(date){
			$("#modal-day").modal("show");
			$scope.selectedDay = date;
		}
	
		
		function _canShow(index){
			return $scope.menuIndex == index;
		}
		
		function _setMenuIndex(index){
			$scope.menuIndex = index;
			if($scope.initGroup){
				$scope.getMyGroups(); 
				$scope.initGroup = false;
			}
		}
		
		function _generateSchedules(vacancyAmount, timePerConsult, timeInit){
			
		}
		
		// ESSE CÓDIGO A GENTE NÃO ESTÁ MAIS UTILIZANDO, CASO O DE BAIXO NÃO FUNCIONE, VOLTE PARA ESSE E SEJA FELIZ
		/*
		function _addTempSchedule(){
			var dateInit = $('#livreInicio').val();
        	var dateEnd = $('#livreFim').val();
        	
        	var dataInit = new Date(date);
        	dataInit.setUTCHours(parseInt(intHour.split(":")[0]));
        	dataInit.setUTCMinutes(parseInt(intHour.split(":")[1]));

        	var dataEnd = new Date(date);
        	dataEnd.setUTCHours(parseInt(endHour.split(":")[0]));
        	dataEnd.setUTCMinutes(parseInt(endHour.split(":")[1]));
        	
			var dateInit = angular.copy($scope.selectedDay);
			var dateEnd = angular.copy($scope.selectedDay);
			
			dateInit.set("hours",$("#tmp-init-1").data("timepicker").hour);
			dateInit.set("minute", $("#tmp-init-1").data("timepicker").minute);
			
			dateEnd.set("hours", $("#tmp-end-1").data("timepicker").hour);
			dateEnd.set("minute", $("#tmp-end-1").data("timepicker").minute);
			
			$scope.generetedSchedules.push({schedule:{dateInit: dateInit, dateEnd : dateEnd, state: "FR"}});
		}
		
		
		function initTimePickers(){
			$("#tmp-init-1").timepicker({showMeridian: false, defaultTime:"8:00"});
			$("#tmp-end-1").timepicker({showMeridian: false, defaultTime:"8:15"});
			$("#tmp-init-hour").timepicker();
		}
		*/
		
		
		function _addTempSchedule(){
			var dInittemp = $('#livreInicio').val();
			var dEndtemp = $('#livreFim').val();
		
			if(typeof dInittemp === undefined || dInittemp == "__:__"){
				dInittemp = '08:00';
			}
			
			if(typeof dEndtemp === undefined || dEndtemp == "__:__"){
				dEndtemp = '08:15';
			}
			
			var dateInit = angular.copy($scope.selectedDay);
			var dateEnd = angular.copy($scope.selectedDay);
	
			dateInit.set("hours", dInittemp.substring(0,2));
			dateInit.set("minute", dInittemp.substring(3,5));
			
	
			dateEnd.set("hours", dEndtemp.substring(0,2));
			dateEnd.set("minute",  dEndtemp.substring(3,5));
			
			
			
			$scope.generetedSchedules.push({schedule:{dateInit: dateInit, dateEnd : dateEnd, state: "FR"}});
	
		}
	
		function initTimePickers(){
			$("#tmp-init-1").timepicker({showMeridian: false, defaultTime:"8:00"});
			$("#tmp-end-1").timepicker({showMeridian: false, defaultTime:"8:15"});
			$("#tmp-init-hour").timepicker();
		}
		
		function _removeSchedule(index){
			$scope.generetedSchedules.splice(index, 1);
		}
		
		
        function _saveFreeConsultations(arraySchedules,modal){
        	console.log("MODAL" + modal);
        	var array = [];
        	for ( var i in arraySchedules) {
        		var s = arraySchedules[i].schedule;
        		array.push({dateInit: s.dateInit._d.getTime(), dateEnd : s.dateEnd._d.getTime(), state: "FR"});
			}
			var json = {json:{schedule:array}};
			
			professionalService.saveConsultation(json, function(response){
				//console.log(response.data);
				var message = response.data.message;
				if(response.data.code == 200){
					alertMessage(message,null,ALERT_SUCCESS);
					location.reload(); 
				}else{
					/*
					console.log(response.data);
					if(modal!= null){
					var div = $("#modal-horario");
					div.addClass("alert");
					div.addClass("alert-danger");
					div.find("#erro-msg").text(message);
					div.show();
					div.fadeOut(3000);
					}else{
					alertMessage(message,null,ALERT_ERROR);
					}*/
					
					alertMessage(message,null,ALERT_ERROR,null,modal);
				}
			});
		}
        
        function _saveConsultations(patient, date,modal){
        	var intHour = $('#pacienteInicio').val();
        	var endHour = $('#pacienteFim').val();
        	
        	var dataInit = new Date(date);
        	dataInit.setUTCHours(parseInt(intHour.split(":")[0]));
        	dataInit.setUTCMinutes(parseInt(intHour.split(":")[1]));

        	var dataEnd = new Date(date);
        	dataEnd.setUTCHours(parseInt(endHour.split(":")[0]));
        	dataEnd.setUTCMinutes(parseInt(endHour.split(":")[1]));
            
        	var con = {schedule:[{"patient":patient, "dateInit":dataInit.getTime(),"dateEnd":dataEnd.getTime(), state:"SC"}]};
            console.log(con);
            
            professionalService.saveConsultation({json:con}, function(response){
				var message = response.data.message;
				if(response.data.code == 200){
					alertMessage(message,null,ALERT_SUCCESS);
					location.reload(); 
				}else{
					console.log(response.data);
					alertMessage(message,null,ALERT_ERROR,null,modal);
				}
			});
            
		}
        
        function _saveGroupConsultation(group, date){
        	var intHour = $('#grupoInicio').val();
        	var endHour = $('#grupoFim').val();

        	var dataInit = new Date(date);
        	dataInit.setUTCHours(parseInt(intHour.split(":")[0]));
        	dataInit.setUTCMinutes(parseInt(intHour.split(":")[1]));
        	
        	var dataEnd = new Date(date);
        	dataEnd.setUTCHours(parseInt(endHour.split(":")[0]));
        	dataEnd.setUTCMinutes(parseInt(endHour.split(":")[1]));
        	
        	var g = {"id":group.id};
        	var con = {schedule:[{"group":g, "dateInit":dataInit.getTime(),"dateEnd":dataEnd.getTime(), state:"SC"}]};
            
            console.log(con);
            professionalService.saveConsultation({json:con}, function(response){
            	//console.log(response.data);
				var message = response.data.message;
				if(response.data.code == 200){
					alertMessage(message,null,ALERT_SUCCESS);
					location.reload(); 
				}else{
					console.log(response.data);
					alertMessage(message,null,ALERT_ERROR,null,modal);
				}
			});
        }
        
		function _setPacientConsultation(){
			$scope.isPacientConsultation = true;
			$scope.isFreeConsultation = false;
			$scope.isGroupConsultation = false;
			$scope.showConsultationButtons = false;
		}
		
		function _setGroupConsultation(){
			$scope.isPacientConsultation = false;
			$scope.isGroupConsultation = true;
			$scope.isFreeConsultation = false;
			$scope.showConsultationButtons = false;
		}
		
		function _setFreeConsultation(){
			$scope.isPacientConsultation = false;
			$scope.isFreeConsultation = true;
			$scope.isGroupConsultation = false;
			$scope.showConsultationButtons = false;
		}
		
		function resetConsultationConfiguration(){
			$scope.isPacientConsultation = false;
			$scope.isFreeConsultation = false;
			$scope.isGroupConsultation = false;
			$scope.showConsultationButtons = true;
		}
		
		function configureModal(){
			$('#modal-day').on('show.bs.modal', function (e) {
				 resetConsultationConfiguration();
			});
		}
		
		function _registerConsultation(index, date){
			
			//Localizar o grupo
			for(var i in $scope.events){
				
				if($scope.events[i].id == index){
					
					//Caso se uma consulta com um grupo
					if($scope.events[i].isGroup){
						
						$("#modal-schedules-description").modal("hide");
						$scope.setMenuIndex(4);
						
						//Trazer as informações do grupo
						$scope.tempConsultation = {};
						$scope.tempConsultation.group = findGroup($scope.events[i].group.id,$scope.groups);
						
						//consulta os membros do grupo
						showSnack("Consultando os membros do grupo, aguarde...");
						professionalService.getPatientsOfGroup({id:$scope.events[i].group.id}, function(response){
							var message = JSON.parse(response.data.message);
							if(response.data.code == 200){
								$scope.tempConsultation.group.patients = message;
								$scope.frequencyList = [];//reset na lista de frequencia
								for (var i in $scope.tempConsultation.group.patients){
									$scope.frequencyList.push({
										group:{id:$scope.tempConsultation.group.id},
										patient:{id:$scope.tempConsultation.group.patients[i].id},
										presence: false,
										consultation: {id:index}
									});
								}
								console.log($scope.tempConsultation.group.patients);
							}else{
								alertMessage(message,null,ALERT_ERROR);
							}
						});
						
						$scope.tempConsultation.date = date;
						$scope.tempConsultation.id = index;
					
					}else{
						//Caso seja uma consulta com uma única pessoa
						$scope.comentario = "";
						$scope.showComment(index);
					}
				}
			}
			
		}
		
		function _saveRegister(){
			console.log($scope.frequencyList);
			console.log($scope.comment);
			if($scope.comment != '' && $scope.comment != undefined){
				professionalService.registerConsultationAndFrequency({"id": $scope.tempConsultation.id, "comment":$scope.comment, "frequencyList": $scope.frequencyList},function(response){
					console.log(response);
					if(response.data.code == RESPONSE_SUCCESS){
						alertMessage(response.data.message, null, ALERT_SUCCESS);
						$scope.registerComment($scope.comment);
						location.reload();
					}else{
						alertMessage(response.data.message, null, ALERT_ERROR);
					}
					
				}, function(a,b){
					alertMessage("Não foi possível registar a consulta!", null, ALERT_ERROR);
				});
			}else{
				//alertMessage("Insira um comentário!", null, ALERT_ERROR);
				alert("Insira um comentário!");
			}
		}
		
		function _cancelConsultation(index){
			$scope.conToCancel = index;
			$("#modal-cancel-consultation").modal("show");
		}
		
		function _cancelAllConsultation(date){
			var datef = new Date(date); // Angular não está reconhecendo os métodos getDate..., então foi necessário usar o new Date
			
			//Adicionando a var as datas para comparar cada uma e evitar problemas futuramente
			dayFormat = datef.getDate() + 1; //0 a 30
			monthFormat = datef.getMonth();
			yearFormat = datef.getFullYear();
			
			dayconsultations = [];
			
			//console.log($scope.events.length);
			
			for(var i = 0; i < $scope.events.length; i++){
				datei = new Date($scope.events[i].start); //Pegando a data Start(atributo) da consulta (objeto)
				dayi = datei.getDate();
				monthi = datei.getMonth();
				yeari = datei.getFullYear();
				
				if((yearFormat == yeari) && (monthFormat == monthi) && (dayFormat == dayi)){ //Eventos do dia selecionando para serem cancelados
					dayconsultations.push($scope.events[i].id);
				}
			}
			i = 0;
			
			$scope.allConsToCancel = dayconsultations;
			$("#modal-all-cancel-consultation").modal("show");
		}
		
		function _registerCancelConsultation(index, message){
			ajaxCall("/siac/cancelConsultation", {"id":index, "message": message}, function(response){
				var type = ALERT_ERROR;
				if(response.code == RESPONSE_SUCCESS){
					alertMessage(response.message, null, ALERT_SUCCESS);
					location.reload(); 
				}else{
					alertMessage(response.message, null, ALERT_ERROR);
				}
			}, function(){
				alertMessage("Ops, algo de errado aconteceu!", null, ALERT_ERROR);
			});
		}
		
		function _registerAllCancelConsultation(index, message){
			consultations = [];
			for(var i=0; i < index.length; i++){
				consultations.push({id:index[i], comment:message});
			}
			professionalService.cancelAllConsultation({consultations}, function(response){
				var type = ALERT_ERROR;
				if(response.data.code == RESPONSE_SUCCESS){
					alertMessage(response.data.message, null, ALERT_SUCCESS);
					location.reload(); 
				}else{
					console.log(response);
					alertMessage(response.data.message, null, ALERT_ERROR);
				}
			});
		}
		
		function _reschedulingConsultation(consultation){
			$scope.consultation = consultation;
			$("#modal-reschedule").modal("show");
		}
		
		function _registerReschedulingConsultation(id, reason){
			
			var dateTemp = $('#input-dtpckr-reschedule').val();
			var intHour = $('#rch-timeinit').val();
        	var endHour = $('#rch-timeend').val();
			
			var d = dateTemp.split("/");
			var dateStart = new Date(d[2]+"-"+d[1]+"-"+d[0]+"T"+intHour);
			var dateEnd = new Date(d[2]+"-"+d[1]+"-"+d[0]+"T"+endHour);
			
			var params = {"idConsultation": id, "dateInit": dateStart, "dateEnd": dateEnd, "email": reason};
			
			ajaxCall("/siac/rescheduleConsultation", params, function(response){
				if(response.code == RESPONSE_SUCCESS){
					alertMessage(response.message, null, ALERT_SUCCESS);
					location.reload();
				}else{
					alertMessage(response.message, null, ALERT_ERROR);
				}
			}, function(){
				alertMessage("Ops, não foi possível reagendar a consulta!", null, ALERT_ERROR);
			}, "POST");
		}
		
		function _getMyGroups(){
			//$scope.groups = [{"id":70, "openGroup":true, "title":"Aprimore", "patientLimit":10, "patients":[{"id":0,"cpf":"00104294337","name":"Mariana Souza Araujo","email":"marianasouza@siaf.com"},{"id":0,"cpf":"00104294447","name":"Mariana Souza Araujo","email":"marianasouza@siaf.com"}]},
			//                 {"id":71, "openGroup":false, "title":"Um grupo qualquer", "patientLimit":15, "patients":[{"id":0,"cpf":"00104294447","name":"Mariana Souza Araujo","email":"marianasouza@siaf.com"},{"id":0,"cpf":"45631697300","name":"Mariana Carvalho Cunha","email":"marianac@gmail.com"}]}];

			//O back-end não tá retornando os grupos
			showSnack("Carregando meu Grupos...");
			professionalService.getMyGroups(function(response){
				var message = response.data.message;
				if(response.data.code == 200){
					$scope.groups = JSON.parse(response.data.message);
				}else{
					console.log(response.data);
					alertMessage(message,null,ALERT_ERROR);
				}
			});
		}
		
		function loadPatients(){
			//$scope.patients = [{"id":0,"cpf":"00104294337","name":"Mariana Souza Araujo","email":"marianasouza@siaf.com"},{"id":0,"cpf":"00104294447","name":"Mariana Souza Araujo","email":"marianasouza@siaf.com"},{"id":0,"cpf":"45631697300","name":"Mariana Carvalho Cunha","email":"marianac@gmail.com"}]
			showSnack("Carregando Pacientes...");
			professionalService.getUsers(function(response){
				if(response.data.code == 200){
					$scope.patients = JSON.parse(response.data.message);
					console.log($scope.patients);
				}else{
					alertMessage(response.data.message,null,ALERT_ERROR);
				}
				
			});
		}
		
		function _addPatient(patient){
			if($scope.update){
				var grp = {"id":$scope.grupo.id, "patients":[patient]};
				professionalService.addAndRemovePatient(grp, true, function(response){
					var message = response.data.message;
					if(response.data.code == 200){
						$scope.grupo.patients.push(angular.copy(patient));
						delete patient;
					}else{
						alertMessage(message,null,ALERT_ERROR);
					}
				});
			}else{
				if($scope.grupo.patients.length < $scope.grupo.patientLimit ){
					$scope.grupo.patients.push(angular.copy(patient));
					delete patient;
				}else{
					alertMessage("A quantidade máxima já foi atingida!",null,ALERT_ERROR);
				}
			}
		}
		
		function _removePatient(index){
			if($scope.update){
				var grp = {"id":$scope.grupo.id, "patients":[$scope.grupo.patients[index]]};
				professionalService.addAndRemovePatient(grp, false, function(response){
					var message = response.data.message;
					if(response.data.code == 200){
						$scope.grupo.patients.splice(index, 1);
					}else{
						alertMessage(message,null,ALERT_ERROR);
					}
				});
			}else{
				$scope.grupo.patients.splice(index, 1);
			}
		}
		
		function _createGroup(){
			showSnack("Criando Grupo");
			professionalService.createGroup($scope.grupo, function(response){
				var message = response.data.message;
				if(response.data.code == 200){
					alertMessage(message,null,ALERT_SUCCESS);
					cleanGrupo();
					$scope.getMyGroups();
				}else{
					alertMessage(message,null,ALERT_ERROR);
				}
			});
		}
		
		function _updateGroup(group){
			$scope.group = 1;
			$scope.update = true;
			$scope.grupo = group;
			showSnack("Consultando os membros do grupo, aguarde...");
			professionalService.getPatientsOfGroup({id:group.id}, function(response){
				var message = JSON.parse(response.data.message);
				//console.log(message);
				if(response.data.code == 200){
					$scope.grupo.patients = message;
				}else{
					alertMessage(message,null,ALERT_ERROR);
				}
			});
			
			if(group.openGroup){
				$("#inlineRadio1").prop("checked", true);
			}else{
				$("#inlineRadio2").prop("checked", true);
			}
		}
		
		function _saveUpdateGroup(){
			showSnack("Atualizando Grupo");
			professionalService.updateGroup($scope.grupo, function(response){
				var message = response.data.message;
				if(response.data.code == 200){
					$scope.getMyGroups();
					alertMessage(message,null,ALERT_SUCCESS);
					$scope.grupo = {};
					$scope.grupo.patients = [];
					$scope.group = 0; 
					$scope.update=false;
				}else{
					console.log(response);
					alertMessage(message,null,ALERT_ERROR);
				}
			});
		}
		
		function _addGroup(group){
			$scope.grp = group;
			delete group;
		}
		
		function _removeGroup(){
			$scope.grp = null;
		}
		
		function _addConPatient(patient){
			$scope.ptt = patient;
			delete patient;
		}
		
		function _removeConPatient(){
			$scope.ptt = null;
		}
		
		function returnState(color){
			for (var [key, value] of colors)
				if(value.hex == color)
					return value.text;
		}
		
		function cleanGrupo(){
			$scope.grupo = {};
			$scope.grupo.patients = [];
			$scope.group = 0;
		}
		
		
		//Mapa que conterá todos os dias da semana
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
		
		
		function _setFrequenciDays(){
			var inputFrequenci = $("#input-frequenci");
			var inputNumberVancancy = $("#input-number-vacancy").val();
			var inputTimeConsultation = $("#input-time-consultation").val();
			
			var inputInitHour = $("#tmp-init-hour").val();
			val = inputFrequenci.val() <= 48 ? inputFrequenci.val() : 48;
			
			if(val == "")
				return;
			
			var mapDays = mapVars.get(MAP_SELECTED_DAYS);	
			
			if(val != ""){
				var resultMap = _getDateByDays(mapDays, val);
				mapVars.set(MAP_DAYS, resultMap);
				
				scheduleManager.updateSchedules(resultMap);
				
				try{
					//Preenchendo a tabela de dias, a partir dos valores padrão
					_fillTableDays(resultMap, parseInt(inputNumberVancancy), parseInt(inputTimeConsultation), inputInitHour);
				}catch(err){
					alert("Verifique os valores especificados!");
				}
			}
		}
		
		function _addScheduler($event){
			
			var btnClass = $event.target.className;
			
			var mapDays = mapVars.get(MAP_SELECTED_DAYS);
			//Pegando o nome do dia do botão clicado
			var dayName = $event.target.value;
			
			if(btnClass === "btn btn-default btn-day btn-success"){
				$event.target.className = "btn btn-default btn-day";
				mapDays.set(dayName, false);
			}else{
				//Setando o dia clicado como true na lista de dias selecionados
				mapDays.set(dayName, true);
				$event.target.className = "btn btn-default btn-day btn-success";
			}
			_setFrequenciDays();
		} 
		
		function _removeScheduler(scheduler){
			for(var i = 0; i < $scope.schedulerOfDay.schedules.length;i++){
				if($scope.schedulerOfDay.schedules[i].idSchedule === scheduler.idSchedule){
					$scope.schedulerOfDay.schedules.splice(i,1);
				}
			}
		}
		
		function _removeDate(date){
			for(var i = 0; i < $scope.scheduler.length;i++){
				if($scope.scheduler[i].date === date.date){
					$scope.scheduler.splice(i,1);
				}
			}
		}
		
		function _onShowSchedulers(day){
			$("#modal-day-scheduler").modal("show");
			$scope.schedulerOfDay = day;
		}
		
		function _plusScheculer(){
			var hour = {timeInit:$("#tmp-init-plus-1").val(), timeEnd:$("#tmp-end-plus-1").val(), idSchedule:$scope.schedulerOfDay.schedules.length};
			$scope.schedulerOfDay.schedules.push(hour);
		}
		
		function _fillTableDays(mapDays, inputVacancy, inputTime, inputHour){
			var table = $("#tbody-table-days");
			
			var mapPtBrDays = mapVars.get(MAP_WEEK_DAYS);
			//Removendo as linhas antigas
			table.find("td").remove();
			
			$scope.scheduler = [];
			mapDays.forEach(function(value, key, mapDays){
				
				today = moment(date,"DD/MM/YYYY").format("dddd");
				var objDay = {
					day:mapPtBrDays.get(value),
					date: key,
					schedules:[]
				}
				
				//Preenchendo os horários com base nos valores padrão
				if(inputVacancy != null && inputTime != null && inputHour != null){
					
					var hour = inputHour.split(":")[0];
					var minutes = inputHour.split(":")[1];
					
					var momentTime = moment();
					momentTime.hours(hour);
					momentTime.minutes(minutes);
					
					var tempInit = momentTime.format("HH:mm");
					var tempEnd = momentTime.format("HH:mm");
					
					//Esse for adiciona novos timepickers em relação a quantidade
					//de vagas e o tempo para cada consulta. Como já existe 2 timepickers fixos
					//a quantidade informada pelo usuário deve ser diminuida em 1
					for(var i = 0; i < parseInt(inputVacancy); i++){
						tempInit = momentTime.format("HH:mm");
						momentTime.add('m', parseInt(inputTime));
						tempEnd = momentTime.format("HH:mm");
						
						var time = {"timeInit":tempInit, "timeEnd":tempEnd, "idSchedule":i};
						objDay.schedules.push(time);
					}
				}
				
				$scope.scheduler.push(objDay);
			});
			
		}

		function _getDateByDays(mapDays, repetition){
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
		
		function _confirmeSchduler(){
			$("#modal-day-scheduler").modal("hide");
		}
		
		function _consolidateAgenda(){
			console.log($scope.scheduler);
			var agenda = {schedule:[]};
			for(var i=0; i<$scope.scheduler.length; i++){
				var d = $scope.scheduler[i].date;
				var date = new Date(d.split("/")[1]+"/"+d.split("/")[0]+"/"+d.split("/")[2]);
				for(var j=0;j<$scope.scheduler[i].schedules.length;j++){
		        	
		        	var dataInit = new Date(date);
		        	dataInit.setUTCHours(parseInt($scope.scheduler[i].schedules[j].timeInit.split(":")[0]));
		        	dataInit.setUTCMinutes(parseInt($scope.scheduler[i].schedules[j].timeInit.split(":")[1]));
		        	
		        	var dataEnd = new Date(date);
		        	dataEnd.setUTCHours(parseInt($scope.scheduler[i].schedules[j].timeEnd.split(":")[0]));
		        	dataEnd.setUTCMinutes(parseInt($scope.scheduler[i].schedules[j].timeEnd.split(":")[1]));
		        	
					agenda.schedule.push({dateInit: dataInit.getTime(), dateEnd : dataEnd.getTime(), state: "FR"});
				}
			}
			console.log(agenda);
			
			professionalService.saveConsultation({json:agenda}, function(response){
				var message = response.data.message;
				if(response.data.code == 200){
					alertMessage(message,null,ALERT_SUCCESS);
					location.reload(); 
				}else{
					console.log(response.data);
					alertMessage(message,null,ALERT_ERROR);
				}
			});
		}
		
	});
	
	
//;
	
})();

//Função que inicia um timepicker passado por parâmetro
function initTimepicker(idPicker, time){
	idPicker = "#"+idPicker;
	$(idPicker).timepicker({showMeridian: false});
	if(time != null){
		$(idPicker).timepicker('setTime', time);
	}else{
		$(idPicker).timepicker('setTime', '08:00');
	}
}

function showSnack(text) {
    var x = document.getElementById("snackbar")
    x.textContent= text;
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}

function hideSnack(){
	var x = document.getElementById("snackbar")
	x.className = x.className.replace("show", "");
}

function format(date){
	return (date.getMonth()+1)+"/"+date.getDate()+"/"+date.getUTCFullYear()+" "+date.getUTCHours()+":"+date.getUTCMinutes();
}

function findGroup(id, groups){
	for (var i in groups) {
		if(groups[i].id == id){
			return groups[i];
		}
	}
	return null;
}

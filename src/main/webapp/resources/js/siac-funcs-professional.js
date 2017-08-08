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

(function(){
	
	initTimepicker("tmp-init-1",null);
	initTimepicker("tmp-end-1",null);
	
	
	angular.module("siacApp")
	.controller("professionalController", function($scope, $compile, $sce, uiCalendarConfig, professionalService){
		
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
		
		//Colors
		var colors = new Map();
		colors.set("SC", {text: "Agendada", hex : "#4682B4", css: "color-blue"});
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
		$scope.getFrequencyList = _getFrequencyList;
		$scope.reload = _reload;
		$scope.viewComment = _viewComment;

		$("#input-frequenci").keypress(function(e){
			setFrequenciDays();
		});
		
		onButtonAddScheduleClick();
		onSelectScheduleRepeatClick();
		onButtonRepeatScheduleDayClick();
		onButtonGenerateSchedules();

		onButtonConfirmSchedulesClick();
		
		initTimePickers();
		configureModal();
		loadPatients();
		getMyGroups();
		
		
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
				events: getConsultations,
				monthNames: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
			    monthNamesShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
			    dayNames: ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sabado'],
			    dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab']
			}
		};

		
		function getConsultations(a,b,c){
			
			showSnack("Carregando as consultas...");
			professionalService.getProfessionalConsultations(function(data){
				
				data.data.forEach(function (value, key){
					console.log(value);
					console.log("value Patient => " + value.patient);
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
					
				});
				
			});
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
			$scope.groupVisibleConsultation = group;
		}
		
		function viewFrequencyListOfGroup(frequency){
			$scope.frequencyListOfGroup = frequency;
			console.log($scope.frequencyListOfGroup);
			$("#modal-view-frequency-group").modal("show");
		}
		
		function _getFrequencyList(idConsultation){
			professionalService.getFrequencyList({id:idConsultation}, function(response){
				var message = response.data.message;
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
		}
		
		function _generateSchedules(vacancyAmount, timePerConsult, timeInit){
			
		}
		
		function _addTempSchedule(){
//			var dateInit = $('#livreInicio').val();
//        	var dateEnd = $('#livreFim').val();
//        	
//        	var dataInit = new Date(date);
//        	dataInit.setUTCHours(parseInt(intHour.split(":")[0]));
//        	dataInit.setUTCMinutes(parseInt(intHour.split(":")[1]));
//
//        	var dataEnd = new Date(date);
//        	dataEnd.setUTCHours(parseInt(endHour.split(":")[0]));
//        	dataEnd.setUTCMinutes(parseInt(endHour.split(":")[1]));
        	
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
		
		function _removeSchedule(index){
			$scope.generetedSchedules.splice(index, 1);
		}
		
		
        function _saveFreeConsultations(arraySchedules){
        	var array = [];
        	for ( var i in arraySchedules) {
        		var s = arraySchedules[i].schedule;
        		console.log(s.dateInit);
        		console.log(s.dateEnd);
				array.push({dateInit: format(s.dateInit._d), dateEnd : format(s.dateEnd._d), state: "FR"});
			}
			var json = {json:{schedule:array}};
			
			professionalService.saveConsultation(json, function(response){
				var message = response.data.message;
				if(response.data.code == 200){
					alertMessage(message,null,ALERT_SUCCESS);
					//location.reload(); 
				}else{
					console.log(response.data);
					alertMessage(message,null,ALERT_ERROR);
				}
			});
		}
        
        function _saveConsultations(patient, date){
        	var intHour = $('#pacienteInicio').val();
        	var endHour = $('#pacienteFim').val();
        	
        	var dataInit = new Date(date);
        	dataInit.setUTCHours(parseInt(intHour.split(":")[0]));
        	dataInit.setUTCMinutes(parseInt(intHour.split(":")[1]));

        	var dataEnd = new Date(date);
        	dataEnd.setUTCHours(parseInt(endHour.split(":")[0]));
        	dataEnd.setUTCMinutes(parseInt(endHour.split(":")[1]));
            
        	
            var con = {schedule:[{"patient":patient, "dateInit":format(dataInit),"dateEnd":format(dataEnd), state:"SC"}]};
            if($scope.chPatientNowConsultation){
            	con.schedule[0].state="NO";
            }
            console.log(con);
            
            professionalService.saveConsultation({json:con}, function(response){
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
        
        function _saveGroupConsultation(group, date){
        	var intHour = $('#grupoInicio').val();
        	var endHour = $('#grupoFim').val();

        	var dataInit = new Date(date);
        	
        	dataInit.setUTCHours(parseInt(intHour.split(":")[0]));
        	dataInit.setUTCMinutes(parseInt(intHour.split(":")[1]));
        	
        	var dataEnd = new Date(date);
        	dataEnd.setUTCHours(parseInt(endHour.split(":")[0]));
        	dataEnd.setUTCMinutes(parseInt(endHour.split(":")[1]));
        	
            var con = {schedule:[{"group":group, "dateInit":format(dataInit),"dateEnd":format(dataEnd), state:"SC"}]};
            if($scope.chGroupNowConsultation){
            	con.schedule[0].state="NO";
            }
//            console.log(con);
            professionalService.saveConsultation({json:con}, function(response){
            	
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
						$scope.frequencyList = [];//reset na lista de frequencia
						for (var i in $scope.tempConsultation.group.patients){
							$scope.frequencyList.push({
								group:{id:$scope.tempConsultation.group.id},
								patient:{id:$scope.tempConsultation.group.patients[i].id},
								presence: false,
								consultation: {id:index}
							});
						}
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
				ajaxCall("/siac/registerConsultation", {"id": $scope.tempConsultation.id}, function(response){
					if(response.data.code == RESPONSE_SUCCESS){
						alertMessage(response.message, null, ALERT_SUCCESS);
						$scope.idConsultationToComment = $scope.tempConsultation.id;
						$scope.registerComment($scope.comment);
						$scope.registerFrequencyList();
					}else{
						alertMessage(response.message, null, ALERT_ERROR);
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
			console.log(index);
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
					console.log(response);
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
					alertMessage(response.message, null, ALERT_ERROR);
				}
			});
		}
		
		function _reschedulingConsultation(consultation){
			$scope.consultation = consultation;
			$("#modal-reschedule").modal("show");
		}
		
		function _registerReschedulingConsultation(id, newDate, newStarHour, newEndHour, reason){
			
			var d = newDate.split("/");
			var dateStart = new Date(d[2]+"-"+d[1]+"-"+d[0]+"T"+newStarHour);
			var dateEnd = new Date(d[2]+"-"+d[1]+"-"+d[0]+"T"+newEndHour);
			
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
		
		function getMyGroups(){
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
					getMyGroups();
				}else{
					alertMessage(message,null,ALERT_ERROR);
				}
			});
		}
		
		function _updateGroup(group){
			$scope.group = 1;
			$scope.update = true;
			$scope.grupo = group;
			
			professionalService.getPatientsOfGroup({id:group.id}, function(response){
				var message = response.data.message;
				console.log(response);
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
					getMyGroups();
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

//TODO: Testando a geração de horários automática

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

function inicializateScheduler(){
	$("#input-frequenci")
}

function setFrequenciDays(){
	var inputFrequenci = $("#input-frequenci");
	var inputNumberVancancy = $("#input-number-vacancy").val();
	var inputTimeConsultation = $("#input-time-consultation").val();
	var inputInitHour = $("#tmp-init-hour").val();
	val = inputFrequenci.val() <= 48 ? inputFrequenci.val() : 48;
	
	if(val == "")
		return;
	
	var mapDays = mapVars.get(MAP_SELECTED_DAYS);	
	
	if(val != ""){
		var resultMap = getDateByDays(mapDays, val);
		mapVars.set(MAP_DAYS, resultMap);
		
		scheduleManager.updateSchedules(resultMap);
		
		try{
			//Preenchendo a tabela de dias, a partir dos valores padrão
			fillTableDays(resultMap, parseInt(inputNumberVancancy), parseInt(inputTimeConsultation), inputInitHour);
		}catch(err){
			alert("Verifique os valores especificados!");
		}
	}
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

function onSelectScheduleRepeatClick(){
	//var select = mapVars.get(SELECT_REPEAT_SCHEDULE_ID);
	var select = $(SELECT_REPEAT_SCHEDULE_ID); 
	select.change(function(){
		var inputFrequenci = $("#input-frequenci");
		
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

function fillTableDays(mapDays, inputVacancy, inputTime, inputHour){
	var table = $("#tbody-table-days");
	
	var mapPtBrDays = mapVars.get(MAP_WEEK_DAYS);
	console.log(table);
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
	
	if(inputVacancy > 0 && inputTime > 0 && inputHour != "")
		onActionTableClick(inputVacancy, inputTime, inputHour);
	else
		onActionTableClick(null, null, null);
}

function onActionTableClick(inputVacancy, inputTime, inputHour){
	map = mapVars.get(MAP_DAYS);
	mapButtonDays = mapVars.get(MAP_SELECTED_DAYS);
	$(".action-day").click(function(){
		action = $(this);
		var key = action.attr("value");
		
		//Removendo a cor vermelha dos timepickers inválidos.
		$(".row-schedule-id").removeClass("has-error");
		
		
		if(action.hasClass("add-schedule")){
			showModalSchedules(key, REGISTER_ACTION, inputVacancy, inputTime, inputHour);
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
			//setEditModalSchedule(key, listSchedules);
		}
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


function showModalSchedules(date, action, inputVacancyPattern, inputTime, inputHour){
	
	var inputVacancy = mapVars.get(INPUT_VACANCY);
	var inputTimePerConsult = mapVars.get(INPUT_COUNT_TIME);
	
	inputVacancy.val("");
	inputTimePerConsult.val("");
	
	var modal =	$("#modal-day-scheduler");
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
	
	//Preenchendo os horários com base nos valores padrão
	if(inputVacancyPattern != null && inputTime != null && inputHour != null){
		var vacancy = inputVacancyPattern;
		var timePerConsult = inputTime;
		var timeInit = getTimePickerHourAndMinutes("tmp-init-hour");
		
		console.log(inputVacancy);
		console.log(inputTime);
		console.log(inputHour);
		
		hour = timeInit["hour"];
		minutes = timeInit["minute"];
		
		momentTime = moment();
		momentTime.hours(hour);
		momentTime.minutes(minutes);
		
		tempInit = momentTime.format("HH:mm");
		momentTime.add('m', timePerConsult);
		tempEnd = momentTime.format("HH:mm");
		
		console.log(tempInit);
		console.log(tempEnd);
		
		//Iniciando os 2 timepickers fixos do modal
		$("#tmp-init-1").timepicker('setTime', tempInit);
		$("#tmp-end-1").timepicker('setTime', tempEnd);
		
		//Esse for adiciona novos timepickers em relação a quantidade
		//de vagas e o tempo para cada consulta. Como já existe 2 timepickers fixos
		//a quantidade informada pelo usuário deve ser diminuida em 1
		for(var i = 0; i < vacancy; i++){
			tempInit = momentTime.format("HH:mm");
			momentTime.add('m', timePerConsult);
			tempEnd = momentTime.format("HH:mm");
			
			var time = {"timeInit":tempInit, "timeEnd":tempEnd, "idSchedule":0};
			console.log(time);
			addSchedules(time);
		}
	}
	
	
	//Mostrando o modal
	$("#modal-schedule-title").text(action+" Horários");
	modalDescription.html(action+" consultas para o dia <strong>"+date+" ("+today+")</strong>");
	labelDay.text(date);
	
	modal.modal('show');

}

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
		
		var time = {"timeInit":tempInit, "timeEnd":tempEnd, "idSchedule":null};
		addSchedules(time);
	});
}

function onButtonGenerateSchedules(){  
	$("#btn-generate-schedules").bind("click", function(event){
		
		//Removendo todos os timepickers clonados 
		$(".cloned-timepicker").remove();
		//Removendo a classe de input inválido de todos os timepickers.
		$(".row-schedule-id").removeClass("has-error");
		
		var inputVacancy = $("#input-count-vacancy");
		var inputTimePerConsult = $("#input-count-time");
		
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
			
			var time = {"timeInit":tempInit, "timeEnd":tempEnd, "idSchedule":0};
			addSchedules(time);
		}
	});
}

function addSchedules(obj){
	
	
	var timeInit = obj.timeInit;
	var timeEnd = obj.timeEnd;
	var scheduleId = obj.idSchedule;
	
	var newRow = $("#row-add-schedules-hours").clone();
	
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
	
	$("#panel-schedules-hours").append(newRow);

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

function onButtonConfirmSchedulesClick(){
	/*
	 * Quando o profissional clicar para confirmar 
	 * o cadastro de horários, todos as horas dos timepickers
	 * devem ser pegues.
	 */
	$("#btn-confirm-schedules-hours").click(function(event){
		
		var modal = $("#modal-day-scheduler");
		var rowsSchedules = modal.find("#panel-schedules-hours").find(".row-schedule-id");
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
		
		//var params = JSON.stringify(scheduleManager.getScheduleDayAsJSON(date));
		var data = scheduleManager.getScheduleDayAsJSON(date);
		console.log(data.data[0].listSchedules[0].timeInit);
		console.log(data.data[0].listSchedules[0].timeEnd);
		
		console.log(data.data[0].listSchedules[1].timeInit);
		console.log(data.data[0].listSchedules[1].timeEnd);
		console.log(data.data[0].listSchedules[0].timeInit);
		
		console.log(JSON.stringify(scheduleManager.getScheduleDayAsJSON(date)));
		/*ajaxCall("/siac/saveConsultation", {"json": params}, function(response){
			var type = ALERT_SUCCESS;
			if(response.code == RESPONSE_ERROR)
				type = ALERT_ERROR;
			
			alertMessage(response.message, null, type);
			
			
			//Carregando todas as consultas cadastradas no calendário do profissional.
			getProfessionalConsultations(fillProfessionalCalendar);
			
		}, null, "POST");*/
		
		modal.modal("hide");
		
	});

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
		
		//Se o horário de inicio for maior que o de fim, os timepickers ficam vermelho.
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

function getNewInitTimePickerId(){
	idTimepickersInit = idTimepickersInit+1;
	return idTimepickersInit;
}

function getNewEndTimePickerId(){
	idTimepickersEnd = idTimepickersEnd+1;
	return idTimepickersEnd;
}

function getTimePickerHourAndMinutes(timepickerId){
	hour = $("#"+timepickerId).data("timepicker").hour;
	minute = $("#"+timepickerId).data("timepicker").minute;
	return {"hour":hour, "minute":minute};
}
(function(){
	
	initTimepicker("tmp-init-1",null);
	initTimepicker("tmp-end-1",null);
	
	
	angular.module("siacApp").controller("professionalController", function($scope, $compile, $sce, uiCalendarConfig, professionalService){
		
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
		$scope.chGroupNowConsultation = false;
		$scope.chPatientNowConsultation = false;
		
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
		$scope.openGroup = _openGroup;
		$scope.closeGroup = _closeGroup;
		$scope.addGroup = _addGroup;
		$scope.removeGroup = _removeGroup;
		$scope.addConPatient = _addConPatient;
		$scope.removeConPatient = _removeConPatient;
		$scope.registerConsultation = _registerConsultation; 
		$scope.cancelConsultation = _cancelConsultation; 
		$scope.reschedulingConsultation = _reschedulingConsultation;
		$scope.registerCancelConsultation = _registerCancelConsultation;
		$scope.registerReschedulingConsultation = _registerReschedulingConsultation;
		$scope.showFrequencyList = _showFrequencyList;
		$scope.addFrequencyList = _addFrequencyList;
		$scope.registerFrequencyList = _registerFrequencyList;
		$scope.showComment = _showComment;
		$scope.registerComment = _registerComment;

		
		initTimePickers();
		configureModal();
		loadPatients();
		getMyGroups();
		
		
		//Configuração do calendário.
		$scope.uiConfig = {
			calendar : {
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
				events: getConsultations
			}
		};

		
		function getConsultations(a,b,c){
			showSnack("Carregando as consultas...");
			professionalService.getProfessionalConsultations(function(data){
				
				data.data.forEach(function (value, key){
					
					var title = "";
					if(value.state == "FR")	title = "Livre";
					else title = value.group == null? value.patient.name:value.group.title;
					
					var e = {
						title: title,
						color: colors.get(value.state).hex,
						state: value.state,
						start:value.dateInit,
						end: value.dateEnd,
						date: value.date,
						id: value.id,
						isGroup: value.group != null
					};
					if(value.group != null)
						e.group = value.group;
					$scope.events.push(e);
					
				});
				
			});
		}
		
		function _showFrequencyList(id, date){
			$("#modal-frequency-list").modal("show");
			$scope.tempConsultation = {};
			$scope.tempConsultation.group = findGroup(id,$scope.groups);
			for (var i in $scope.tempConsultation.group.patients){
				$scope.frequencyList.push({
					group:{id:$scope.tempConsultation.group.id},
					patient:{id:$scope.tempConsultation.group.patients[i].id},
					presence: false,
					consultation: {id:$scope.tempConsultation.id}
				});
			}
			$scope.tempConsultation.date = date;
		}
		
		function _addFrequencyList(index){
			$scope.frequencyList[index].presence = !$scope.frequencyList[index].presence;
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
		
		function _showComment(id){
			$("#modal-comment").modal("show");
			$scope.idConsultationToComment = id; 
		}
		
		function _registerComment(comment){
			professionalService.registerComment({id:$scope.idConsultationToComment, comment: comment}, function(response){
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
					location.reload(); 
				}else{
					console.log(response.data);
					alertMessage(message,null,ALERT_ERROR);
				}
			});
		}
        
        function _saveConsultations(patient, date, intHour, endHour){
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
        
        function _saveGroupConsultation(group, date, intHour, endHour){
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
			$scope.showConsultationButtons = true;;
		}
		
		function configureModal(){
			$('#modal-day').on('show.bs.modal', function (e) {
				 resetConsultationConfiguration();
			});
		}
		
		function _registerConsultation(index){
			ajaxCall("/siac/registerConsultation", {"id": index}, function(response){
				if(response.code == RESPONSE_SUCCESS){
					alertMessage(response.message, null, ALERT_SUCCESS);
					location.reload(); 
				}else{
					alertMessage(response.message, null, ALERT_ERROR);
				}
				
			}, function(a,b){
				alertMessage("Não foi possível registar a consulta!", null, ALERT_ERROR);
			});
		}
		
		function _cancelConsultation(index){
			$scope.conToCancel = index;
			$("#modal-cancel-consultation").modal("show");
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
					alertMessage(message,null,ALERT_ERROR);
				}
			});
		}
		
		function _openGroup(group){
			professionalService.modifyTypeGroup(group, true, function(response){
				var message = response.data.message;
				if(response.data.code == 200){
					alertMessage(message,null,ALERT_SUCCESS);
					getMyGroups();
				}else{
					alertMessage(message,null,ALERT_ERROR);
				}
			});
		}
		
		function _closeGroup(group){
			professionalService.modifyTypeGroup(group, false, function(response){
				var message = response.data.message;
				if(response.data.code == 200){
					alertMessage(message,null,ALERT_SUCCESS);
					getMyGroups();
				}else{
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
	console.log(groups);
	var i = 0;
	for (var i in groups) {
		if(groups[i].id == id){
			return groups[i];
		}
	}
}
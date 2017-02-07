(function(){
	
	angular.module("siacApp").controller("professionalController", function($scope, uiCalendarConfig, professionalService){
		
		//Variables
		$scope.menuIndex = 0;
		$scope.generetedSchedules = [];
		$scope.events = [];
		$scope.eventSources = [$scope.events];
		
		var selectedDay = {};
		
		$scope.isPacientConsultation = false;
		$scope.isFreeConsultation = false;
		$scope.showConsultationButtons = true;
		
		//Functions
		$scope.eventClick = _eventClick;
		$scope.addEvent = _addEvent;
		$scope.canShow = _canShow;
		$scope.setMenuIndex = _setMenuIndex;
		$scope.generateSchedules = _generateSchedules;
		$scope.addTempSchedule = _addTempSchedule;
		$scope.removeSchedule = _removeSchedule;
		$scope.saveFreeConsultations = _saveFreeConsultations;
		$scope.setPacientConsultation = _setPacientConsultation;
		$scope.setFreeConsultation = _setFreeConsultation;
		
		
		initTimePickers();
		configureModal();
		
		professionalService.getProfessionalConsultations(function(data){
			console.log("TODO - Get professional consultations");
		});
		
		//Configuração do calendário.
		$scope.uiConfig = {
			calendar : {
				editable : true,
				displayEventTime : true,
				eventClick : function(event){
					alert(event);
				},
				dayClick : _dayClick
			}
		};
		
		function _dayClick(date){
			$("#modal-day").modal("show");
			$scope.selectedDay = date;
		}
		
		function _eventClick(){
			alert("clicked");
		}
		
		function _addEvent(){
			$scope.events.push({
				title : 'Teste',
				start : new Date(),
				end : new Date()
			});
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
			
			$scope.generetedSchedules.push({schedule:{dateInit: dateInit, dateEnd : dateEnd}});
		}
		
		function initTimePickers(){
			$("#tmp-init-1").timepicker({showMeridian: false, defaultTime:"8:00"});
			$("#tmp-end-1").timepicker({showMeridian: false, defaultTime:"8:15"});
		}
		
		function _removeSchedule(index){
			$scope.generetedSchedules.splice(index, 1);
		}
		
		function _saveFreeConsultations(arraySchedules){
			var json = {json:JSON.stringify(arraySchedules)};
			console.log(json);
		}
		
		function _setPacientConsultation(){
			$scope.isPacientConsultation = true;
			$scope.isFreeConsultation = false;
			$scope.showConsultationButtons = false;
		}
		
		function _setFreeConsultation(){
			$scope.isPacientConsultation = false;
			$scope.isFreeConsultation = true;
			$scope.showConsultationButtons = false;
		}
		
		function resetConsultationConfiguration(){
			$scope.isPacientConsultation = false;
			$scope.isFreeConsultation = false;
			$scope.showConsultationButtons = true;
		}
		
		function configureModal(){
			$('#modal-day').on('show.bs.modal', function (e) {
				 resetConsultationConfiguration();
			});
		}
		
		
	});
	
})();
(function(){
	
	angular.module("siacApp").controller("professionalController", function($scope, uiCalendarConfig, professionalService){
		
		//Variables
		$scope.menuIndex = 0;
		$scope.generetedSchedules = [];
		$scope.events = [];
		$scope.eventSources = [$scope.events];
		$scope.initSchTemp = "8:00";
		$scope.endSchTemp = "8:15";
		
		var selectedDay = {};
		
		professionalService.getProfessionalConsultations(function(data){
			console.log("TODO - Get professional consultations");
		});
		
		//Functions
		$scope.eventClick = _eventClick;
		$scope.addEvent = _addEvent;
		$scope.canShow = _canShow;
		$scope.setMenuIndex = _setMenuIndex;
		$scope.generateSchedules = _generateSchedules;
		$scope.addTempSchedule = _addTempSchedule;
		$scope.removeSchedule = _removeSchedule;
		$scope.saveConsultations = _saveConsultations;
		
		initTimePickers();
		
		
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
		
		function _addTempSchedule(initSchTemp, endSchTemp){
			console.log($("#tmp-init-1").data("timepicker").hour)
			console.log($("#tmp-init-1").data("timepicker").minute);
			
			var dateInit = angular.copy($scope.selectedDay);
			var dateEnd = angular.copy($scope.selectedDay);
			
			dateInit.minutes($("#tmp-init-1").data("timepicker").hour);
			dateInit.minutes($("#tmp-init-1").data("timepicker").minute);
			
			dateEnd.minutes($("#tmp-end-1").data("timepicker").hour);
			dateEnd.minutes($("#tmp-end-1").data("timepicker").minute);
			
			
//			console.log(dateInit.format("x"));
//			console.log(dateEnd.format("x"));
//			
			$scope.generetedSchedules.push({initTime: initSchTemp, endTime : endSchTemp});
			
			dateInit.add(15, "minutes");
			dateEnd.add(15, "minutes");
			
			$scope.initSchTemp = dateInit.format("hh:mm");
			$scope.endSchTemp = dateEnd.format("hh:mm");
			
		}
		
		function initTimePickers(){
			$("#tmp-init-1, #tmp-end-1").timepicker({showMeridian: false});
		}
		
		function _removeSchedule(index){
			$scope.generetedSchedules.splice(index, 1);
		}
		
		function _saveConsultations(arraySchedules){
			console.log(arraySchedules);
		}
		
	});
	
})();
(function(){
	
	angular.module("siacApp").controller("professionalController", function($scope, uiCalendarConfig, professionalService){
		
		//Variables
		$scope.menuIndex = 0;
		$scope.generetedSchedules = [];
		$scope.events = [];
		$scope.eventSources = [$scope.events];
		
		professionalService.getProfessionalConsultations(function(data){
			console.log("Service Professional");
		});
		
		//Functions
		$scope.eventClick = _eventClick;
		$scope.addEvent = _addEvent;
		$scope.canShow = _canShow;
		$scope.setMenuIndex = _setMenuIndex;
		$scope.generateSchedules = _generateSchedules; 
		
		
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
		
		function _dayClick(){
			$("#modal-day").modal("show");
		}
		
		function _eventClick(){
			alert("clicked");
		}
		
		function _addEvent(){
			$scope.events.push({
				title : 'Power',
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
			console.log(vacancyAmount);
			console.log(timePerConsult);
			console.log(timeInit);
		}
		
	});
	
})();
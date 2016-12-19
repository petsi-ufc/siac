(function(){
	
	angular.module("siacApp").controller("professionalController", function($scope, uiCalendarConfig, professionalService){
		
		//Variables
		$scope.events = [];
		$scope.eventSources = [$scope.events];
		
		professionalService.getProfessionalConsultations(function(data){
			console.log(data);
		});
		
		//Functions
		$scope.eventClick = _eventClick;
		$scope.addEvent = _addEvent; 
		
		//Configuração do calendário.
		$scope.uiConfig = {
			calendar : {
				editable : true,
				displayEventTime : true,
				eventClick : function(event){
					alert(event);
				}
			}
		};
		
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
		
		
		
	});
	
})();
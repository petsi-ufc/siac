(function(){
	
	angular.module("siacApp").controller("professionalController", function($scope){
		
		
		$scope.eventSource = {
			currentTimezone : 'America/Brazil'	
		};
		
//		Configuração do calendário.
		$scope.uiConfig = {
				calendar : {
					lang:'pt-br'
				},
				eventClick: $scope.eventClick
		};
		
		
		$scope.eventClick = function(){
			alert("clicked");
		}
		
	});
	
})();
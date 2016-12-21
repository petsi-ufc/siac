/**
 * Serviços HTTP para o Profissional 
 */

(function(){
	
	angular.module("siacApp").service("professionalService", function($http){
		
		function _getProfessionalConsultations(callback){
			$http.get("/siac/getConsutationsByProfessionalJSON").then(callback, function(err){
				console.log("Error at get professional consultations");
				console.log(err);
			});
		}
		
		function _saveConsultation(params, callback){
			$http.post("/siac/saveConsultation", {"json":params}).then(callback, function(err){
				console.log("Error at save professional consultations");
				console.log(err);
			});
		}
		
		return {
			getProfessionalConsultations : _getProfessionalConsultations,
			saveConsultation : _saveConsultation
		}
	});
})();
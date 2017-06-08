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
			$http.post("/siac/saveConsultation?json="+JSON.stringify(params)).then(callback, function(err){
				console.log("Error at save professional consultations");
				console.log(err);
			});
		}
		
		function _saveConsultationNow(params, callback){
			$http.post("/siac/saveConsultationNow?json="+JSON.stringify(params), {"json":params}).then(callback, function(err){
				console.log("Error at save professional consultations");
				console.log(err);
			});
		}
		
		function _createGroup(params, callback){
			console.log(params);
			$http.post("/siac/createGroup?json="+JSON.stringify(params), {'json':params}).then(callback, function(err){
				console.log("Error at create new group");
				console.log(err);
			});
		}
		
		function _updateGroup(params, callback){
			$http.post("/siac/updateGroup?json="+JSON.stringify(params), {"json":params}).then(callback, function(err){
				console.log("Error at update group");
				console.log(err);
			});
		}
		
		function _modifyTypeGroup(params, type, callback){
			var obj = {
				id:params.id
			};
			if(type){
				url = "/siac/openGroup?json="+JSON.stringify(obj);
			}else{
				url = "/siac/closeGroup?json="+JSON.stringify(obj);
			}
			$http.post(url, {"json":obj}).then(callback, function(err){
				console.log("Error at modify type group");
				console.log(err);
			});
		}
		
		function _addAndRemovePatient(params, add, callback){
			var url = "";
			if(add){
				url = "/siac/addPatient?json="+JSON.stringify(params);
			}else{
				url = "/siac/removePatient?json="+JSON.stringify(params);
			}
			
			
			$http.post(url, {"json":params}).then(callback, function(err){
				console.log("Error at add and remove patient");
				console.log(err);
			});
			
		}
		
		function _getUsers(callback){
			$http.get("/siac/getAllUsers").then(callback, function(err){
				console.log("Error at getAllUsers");
				console.log(err);
			});
		}
		
		function _getMyGroups(callback){
			$http.get("/siac/getAllGroups").then(callback, function(err){
				console.log("Error at get my groups");
				console.log(err);
			});
		}
		
		function _registerFrequency(params, callback){
			$http.post('/siac/registerFrequency?json='+JSON.stringify(params), {"json":params}).then(callback, function(err){
				console.log("Error at register frequency");
				console.log(err);
			});
		}
		
		function _registerComment(params, callback){
			$http.post('/siac/registerComment?json='+JSON.stringify(params), {"json":params}).then(callback, function(err){
				console.log("Error at register comment");
				console.log(err);
			});
		}
		
		return {
			getProfessionalConsultations : _getProfessionalConsultations,
			saveConsultation : _saveConsultation,
			saveConsultationNow : _saveConsultationNow,
			createGroup : _createGroup,
			getUsers : _getUsers,
			getMyGroups: _getMyGroups,
			updateGroup : _updateGroup,
			modifyTypeGroup : _modifyTypeGroup,
			addAndRemovePatient : _addAndRemovePatient,
			registerFrequency: _registerFrequency,
			registerComment: _registerComment
		}
	});
})();
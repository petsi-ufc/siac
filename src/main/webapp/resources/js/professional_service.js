/**
 * Serviços HTTP para o Profissional 
 */

(function(){
	
	angular.module("siacApp").service("professionalService", function($http){
		
		function _getProfessionalConsultations(dateInit, dateEnd, callback){
			$http.post("/siac/getConsutationsByProfessionalJSON?dateInit="+dateInit+"&dateEnd="+dateEnd).then(callback, function(err){
				console.log("Error at get professional consultations");
				console.log(err);
			});
		}
		
		function _getConsultationsByGroup(params,callback){
			$http.post("/siac/getConsultationsByGroup?json="+JSON.stringify(params)).then(callback, function(err){
				console.log("Error at get group consultations");
				console.log(err);
			});
		}
		
		function _saveConsultation(params, callback){
			console.log(params);
			$http.post("/siac/checkSchedules?json="+JSON.stringify(params)).then(function(response){
				if(response.data.code == 200){
					//console.log("Os horários passaram!");
					$http.post("/siac/saveConsultation?json="+JSON.stringify(params)).then(callback, function(err){
						console.log("Error at save professional consultations");
						console.log(err);
					});
				}else{
					callback(response);
				}
			},function(err){
				console.log("Error at check schedule");
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
		
		function _getPatientsOfGroup(params, callback){
			$http.get("/siac/getPatients?json="+JSON.stringify(params)).then(callback, function(err){
				console.log("Error at get patients of groups");
				console.log(err);
			});
		}
		
		function _registerFrequency(params, callback){
			$http.post('/siac/registerFrequency?json='+JSON.stringify(params), {"json":params}).then(callback, function(err){
				console.log("Error at register frequency");
				console.log(err);
			});
		}
		
		function _getFrequencyList(params, callback){
			$http.post('/siac/getFrequency?json='+JSON.stringify(params), {"json":params}).then(callback, function(err){
				console.log("Error at get frequency list");
				console.log(err);
			});
		}
		
		function _registerComment(params, callback){
			$http.post('/siac/registerComment?json='+JSON.stringify(params), {"json":params}).then(callback, function(err){
				console.log("Error at register comment");
				console.log(err);
			});
		}
		
		function _cancelAllConsultation(params, callback){
			$http.post('/siac/cancelAllConsultation?json='+JSON.stringify(params), {"json":params}).then(callback, function(err){
				console.log("Error at cancel all consultations");
				console.log(err);
			});
		}
		function _getReserveByidConsultation(params,callback){
			$http.get('/siac/getReserveByidConsultation?id='+params,{'id':params}).then(callback,function(error){
				console.log("ID: " + params);
				console.log("erro at get All Reserve ):");
				console.log(error);
			});
		}
		
		function _registerConsultationAndFrequency(params, callback){
			$http.post('/siac/registerConsultationAndFrequency?json='+JSON.stringify(params), {"json":params}).then(callback, function(err){
				console.log("error at register consultation and frequency");
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
			registerComment: _registerComment,
			getFrequencyList: _getFrequencyList,
			getPatientsOfGroup :_getPatientsOfGroup,
			cancelAllConsultation: _cancelAllConsultation,
			getReserveByidConsultation: _getReserveByidConsultation,
			getConsultationsByGroup: _getConsultationsByGroup,
			registerConsultationAndFrequency: _registerConsultationAndFrequency
		}
	});
})();
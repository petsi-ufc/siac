
/**
 *	Todas as principais funcionalidades javascript do siac. 
 */

var RESPONSE_ERROR = 500;
var RESPONSE_SUCCESS = 200;

//Função que fax uma chamada ajax contendo a url e os parametros devidos.
//O terceiro parâmetro é uma função de callback, ela é chamada quando a requisição é retornada.
function ajaxCall(_url, params, func, method){
	$.ajax({
		method : method ? method : "GET",
		url: url,
		dataType: "json",
		data: params
	}).done(func).error(function(textStatus, error){
		alertMessage("Ops! Aconteceu algo de errado.");
		console.log(textStatus+" - "+error);
	});
	
}

function ajaxCallNoJSON(_url, params, func, fail){
	$.ajax({
			method: "GET",
			url: _url,
			data: params
		}
	).done(func).fail(fail);
}

function getFormatedDate(stringDate){
	//Formatando a data para YYYY-DD-MM
	//sch.getDate() retorna a data em DD/MM/YYYY
	//Logo é feito um split que é usado para criar um objeto date no formato abaixo.
	var from = stringDate.split("/");
	return new Date(from[2], from[1] - 1, from[0]);
}


const ALERT_SUCCESS = "alert-success";
const ALERT_ERROR = "alert-warning";
//Função que mostra a mensagem de alerta em cima do calendário.
//Type: SUCCESS ou ERROR
//Time: tempo para que a mensagem desapareça
function alertMessage(message, time, type){
	//Se o tempo para esconder a mensagem não for passado por paramentro
	//o valor do tempo será 5 segundos.
	time = !time ? 5000 : time;
	var alertMessage = $(".alert-message");
	
	alertMessage.removeClass(ALERT_SUCCESS);
	alertMessage.removeClass(ALERT_ERROR);
	
	var icon = "glyphicon glyphicon-exclamation-sign";

	if( type == ALERT_SUCCESS ){
		icon = "glyphicon glyphicon-ok-sign";
	}else{
		type = ALERT_ERROR;
	}
	
	
	
	alertMessage.addClass(type);
	
	alertMessage.find("#alert-icon").addClass(icon);
	
	alertMessage.find("#alert-text").text(message);
	alertMessage.removeClass("hidden");
	alertMessage.show();
	hideElement(alertMessage, time);
}

function hideElement(element, time){
	setTimeout(
			function(){
				$(element).hide(1500);
			}, time
	);
}
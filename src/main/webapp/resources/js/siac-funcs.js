
/**
 *	Todas as principais funcionalidades javascript do siac. 
 */

//Função que fax uma chamada ajax contendo a url e os parametros devidos.
//O terceiro parâmetro é uma função de callback, ela é chamada quando a requisição é retornada.
function ajaxCall(_url, params, func){
	$.getJSON(_url, params).done(func).error(function(textStatus, error){
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


const ALERT_SUCCESS = "alert-success";
const ALERT_ERROR = "alert-danger";
//Função que mostra a mensagem de alerta em cima do calendário.
//Type: SUCCESS ou ERROR
//Time: tempo para que a mensagem desapareça
function alertMessage(message, time, type){
	//Se o tempo para esconder a mensagem não for passado por paramentro
	//o valor do tempo será 5 segundos.
	time = !time ? 5000 : time;
	var alertMessage = $(".alert-message");
	
	alertMessage.removeClass("alert-success");
	alertMessage.removeClass("alert-danger");
	
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
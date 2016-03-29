
/**
 *	Todas as principais funcionalidades javascript do siac. 
 */

//Função que fax uma chamada ajax contendo a url e os parametros devidos.
//O terceiro parâmetro é uma função de callback, ela é chamada quando a requisição é retornada.
function ajaxCall(_url, params, func, error){
	$.getJSON(_url, params, func).error(function(textStatus, error){
		$("#alert-schedules").text("Ops! Aconteceu algo de errado.");
	});
}

function alertMessage(message, time){
	//Se o tempo para esconder a mensagem não for passado por paramentro
	//o valor do tempo será 5 segundos.
	time = !time ? 5000 : time;
	var alert = $(".alert-message");
	alert.text(message);
	alert.removeClass("hidden");
	alert.show();
	hideElement(alert, time);
}

function hideElement(element, time){
	setTimeout(
			function(){
				$(element).hide(1500);
			}, time
	);
}
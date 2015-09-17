/**
 *	Todas as funcionalidades javascript do siac. 
 */

$("document").ready(function(){
	
	//Quando o usuário clicar na imagem o modal aparece...
	$("#avatar-img").click(function(){
		$("#modal-config").modal('show');
	});
	
	$("#calendar").fullCalendar({
		dayClick: function(date, jsEvent, view){
			$(this).css('background-color', '#2887ED');
		}
	});
	
});



/**
 *	Todas as funcionalidades javascript do siac. 
 */

$("document").ready(function(){

	//Quando o usuário clicar na imagem o modal aparece...
	$("#avatar-img").click(function(){
		$("#modal-config").modal('show');
	});

	$("#calendar").fullCalendar({
		header: {
			left: 'prev',
			center: 'title',
			right: 'next'
		},
		events: [
		         {
		        	 title: 'Consulta Psicologia',
		        	 start: '2015-09-21',
		        	 end: '2015-09-21',
		        	 color: 'red'
		         },
		         {
		        	 title: 'Consulta Nutrição',
		        	 start: '2015-09-25',
		        	 end: '2015-09-25',
		        	 color: 'blue'
		         },
		         {
		        	 title: 'Consulta Psiquiatria',
		        	 start: '2015-09-29',
		        	 end: '2015-09-29',
		        	 color: 'yellow'
		         }

		         ],
		         businessHours: true,
		         editable: false,
		         dayClick: function(date, jsEvent, view){
		        	 $(this).css('background-color', '#2887ED');
		         }
	});

});



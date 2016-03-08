/**
 * Funcionalidades javascript na visão do profissional
 */


$("document").ready(function(){
	initCalendarProfessional();	
	initCalendarSchedule();
	
	
});


//Função que inicia o calendário.
function initCalendarProfessional(){
	$("#calendar_professional").fullCalendar({
		header: {
			left: 'prev',
			center: 'title',
			right: 'next'
		},
		
		businessHours: true,
	    editable: false,
	    dayClick: clickFunction,
	    eventClick: clickEvent,
		         
	    dayClick: function(date, jsEvent, view, event) {
	    	
	    	$("#modal-day").modal('show');
	        $(".modal-title").html(date.format('DD/MM/YYYY'));     	
	    }  		         		         
	});
	
}


// Função que inicial o calendario-agenda
function initCalendarSchedule() {
	$('#calendar_schedule').fullCalendar({
		header: {
			left: 'prev,next today',
			center: 'title',
			right: 'month,agendaWeek,agendaDay'
		},
		defaultDate: '2015-02-12',
		editable: true,
		eventLimit: true, 
		events: [
			{
				title: 'All Day Event',
				start: '2015-02-01'
			},
			{
				title: 'Long Event',
				start: '2015-02-07',
				end: '2015-02-10'
			},
			{
				id: 999,
				title: 'Repeating Event',
				start: '2015-02-09T16:00:00'
			},
			{
				id: 999,
				title: 'Repeating Event',
				start: '2015-02-16T16:00:00'
			},
			{
				title: 'Conference',
				start: '2015-02-11',
				end: '2015-02-13'
			},
			{
				title: 'Meeting',
				start: '2015-02-12T10:30:00',
				end: '2015-02-12T12:30:00'
			},
			{
				title: 'Lunch',
				start: '2015-02-12T12:00:00'
			},
			{
				title: 'Meeting',
				start: '2015-02-12T14:30:00'
			},
			{
				title: 'Happy Hour',
				start: '2015-02-12T17:30:00'
			},
			{
				title: 'Dinner',
				start: '2015-02-12T20:00:00'
			},
			{
				title: 'Birthday Party',
				start: '2015-02-13T07:00:00'
			},
			{
				title: 'Click for Google',
				url: 'http://google.com/',
				start: '2015-02-28'
			}
		],
		
		 dayClick: function(date, jsEvent, view, event) {
		    	
		    	$("#modal-schedule").modal('show');
		    	$("#input-calendar").attr('value', date.format('DD/MM/YYYY'));
		    	$("#dp3").attr('data-date', date.format('DD/MM/YYYY'));
		    	 
		    	$('#timepicker1').click(function (){
		    		$('#timepicker1').timepicker('show');
		    	});
		    	
		    	$('#timepicker2').click(function (){
		    		$('#timepicker2').timepicker('show');
		    	});
		    	
		    	$("#dp3").click(function () {
		    		$("#dp3").datepicker('show');
				});
		    	
		        $(".modal-title").html("<h4> Novos horários livres </h4>");   

		       
 
		    }  		         		         
	});
}

function addTuple() {
	
	var newRow = $("<tr>");
	var cols = "";
	var div1 = "";
	var div2 = "";
	
	var id1 = createID();
	var id2 = createID();
	
	cols += '<td>';
	
	div1 += '<div class="timepicker-schedule">';
	div1 +='<div class="input-group bootstrap-timepicker timepicker">';
	div1 += '<input id="id1" type="text" class="form-control input-small">';
	div1 += '<span class="input-group-addon">';
	div1 += '<i class="glyphicon glyphicon-time"></i>';
	div1 += '</span>';
	div1 +='</div>';
	div1 +='</div>';
	
	cols += div1;
	
	cols += '</td>';
	
	$("#id1").attr('id', id1);
	
	cols += '<td>';
	div2 += '<div class="timepicker-schedule">';
	div2 +='<div class="input-group bootstrap-timepicker timepicker">';
	div2 += '<input id="id2" type="text" class="form-control input-small">';
	div2 += '<span class="input-group-addon">';
	div2 += '<i class="glyphicon glyphicon-time"></i>';
	div2 += '</span>';
	div2 +='</div>';
	div2 +='</div>';
	
	cols += div2;
	
	cols += '</td>';
	
	$("#id2").attr('id', id2);
	
	newRow.append(cols);
    $("#table-new-time").append(newRow);
    
    $('#id1').click(function (){
		$('#id1').timepicker('show');
	});
    
    $('#id2').click(function (){
		$('#id2').timepicker('show');
	});
    
	
}
	

function createID() {
	id = Math.floor((Math.random() * 200) + 1);
	return id;
}
$.datetimepicker.setLocale('pt');

$('.Ntimepicker').datetimepicker({
	datepicker:false,
	format:'H:i',
	step:15
});

$('.Ndatepicker').datetimepicker({
	lang:'pt',
	timepicker:false,
	format:'d/m/Y',
	formatDate:'Y/m/d',
	mask:'39/19/2999'
});
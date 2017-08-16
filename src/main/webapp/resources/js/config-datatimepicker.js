$(function () {
	$('.datetimepicker').datetimepicker({
		format: 'HH:mm',
	});
});

$.datetimepicker.setLocale('pt');

$('.datetimepicker1').datetimepicker({
	datepicker:false,
	format:'H:i',
	step:15,
	mask:'29:59',
	use24hours: true
});

$('.datetimepicker2').datetimepicker({
	lang:'pt',
	timepicker:false,
	format:'d/m/Y',
	formatDate:'Y/m/d',
	mask:'39/19/2999'
});
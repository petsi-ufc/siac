$("document").ready(function(){
	
	onActionClick();
	onOptionReportClick();
	
});

function onActionClick(){
	$(".link-action").click(function(){
		$(".calendar").css("display", "none");
		$("#my-calendar").css("display", "none");
		$("#alert-schedules").css("display", "none");
		$("#generate-report").css("display", "none");
		$("#set-professional").css("display", "none");
		$("#add-service").css("display", "none");
		
		$(".action").removeClass("active");
		$(".service").removeClass("active");
		$(this).parent().addClass("active");
		
		if($(this).attr("id") == 0){ //Gerar Relatórios
			$("#generate-report").css("display", "block");
		}else if($(this).attr("id") == 1){
			$("#set-professional").css("display", "block");
		}else if($(this).attr("id") == 2){
			$("#add-service").css("display", "block");
		}
	});
}

function onOptionReportClick(){
	$(".type-report").change(function(){
		if($(".type-report option:selected").text() == "Geral"){
			$(".select-service").attr("disabled", "disabled");
		}else if($(".type-report option:selected").text() == "Por serviço"){
			$(".select-service").removeAttr("disabled");
		}
	});
}
$("document").ready(function(){
	
	onActionClick();
	onOptionReportClick();
	
});

function onActionClick(){
	$(".link-action").click(function(){
		
		$(".line-service").remove();
		
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
			ajaxCall("/siac/getServices", function(json){
				var serviceName;
				var serviceActive;
				$.each(json, function(key, obj){
					$.each(obj, function(name, value){
						if(name=="name"){
							serviceName = value;
						}
						if(name=="active"){
							if(value == true){
								serviceActive = "Ativo";
							}else if(value == false){
								serviceActive = "Inativo";
							}
						}
					})
					
					var newRow = $("<tr class='line-service'></tr>");
					newRow.append("<td>"+serviceName+"</td>");
					newRow.append("<td>"+serviceActive+"</td>");
					newRow.append("<td><button class='btn btn-primary'>Editar</button></td>");
					newRow.append("<td><button class='btn btn-primary'>Desativar</button></td>");
					$("#table-services").append(newRow);
				});
			})
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
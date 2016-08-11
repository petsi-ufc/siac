$("document").ready(function(){
	
	onActionClick();
	onOptionReportClick();
	onServiceActiveButtonClick();
	onServiceInactiveButtonClick();
	onServiceEditButtonClick();
	onServiceEditSaveButtonClick();
	onServiceAddButtonClick();
	onFieldSearchProfessionalChange();
	onGenerateReportButtonClicked();
	
	$("#input-dtpckr-start-report").datepicker({
		 format: 'dd/mm/yyyy',                
		 language: 'pt-BR'
	});
	
	$("#input-dtpckr-end-report").datepicker({
		 format: 'dd/mm/yyyy',                
		 language: 'pt-BR'
	});
	
	$("#select-servico").find('select').prop('disabled',true);
	$("#select-professional").find('select').prop('disabled',true);
	$("#select-report-type").change(function(){
		onSelectReportChaged()
	});

});

function onSelectReportChaged(){
	var value = $("#select-report-type").val();
	if(value === "by-type"){
		$("#select-servico").show().find('select').prop('disabled', false);
		$("#select-professional").show().find('select').prop('disabled', false);
		
		ajaxCall("/getActiveServices", null, function(json){
			
		});
		
	} else {
		if(value === "general") {
			$("#select-servico").hide().find('select').prop('disabled', true);
			$("#select-professional").hide().find('select').prop('disabled', true);
		} else {
			$("#select-servico").show().find('select').prop('disabled', true);
			$("#select-professional").show().find('select').prop('disabled', true);
		}
	}
}



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
			ajaxCall("/siac/getServices", null ,function(json){
				
				fillTableServices(json);
				
			});
		}
	});
}

function onOptionReportClick(){
	
	$(".type-report").change(function(){
		if($(".type-report option:selected").text() == "Geral"){
			$(".select-service").attr("disabled", "disabled");
			
		}else if($(".type-report option:selected").text() == "Por serviço"){
			
			$(".service-option").remove();
			
			ajaxCall("/siac/getServices", function(json){
				
				var serviceName;
				var serviceActive;
				var serviceId;
				
				json.sort(function (obj1, obj2) {
					return obj1.name < obj2.name ? -1 :
					(obj1.name > obj2.name ? 1 : 0);
					});
				
				$.each(json, function(key, obj){
					
					$.each(obj, function(name, value){
						if(name=="name"){
							serviceName = value;
						}
						if(name=="active"){
							serviceActive = value;
						}
						if(name=="id"){
							serviceId = value;
						}
					})
					
				
					$(".select-service").append("<option class='service-option' value='"+serviceId+"'>"+serviceName+"</option>");
					
				});
				
				$(".select-service").removeAttr("disabled");
				
			});
		}
	});
}

function onServiceActiveButtonClick(){
	
	$(document).on("click", ".service-active", function(){
		
		var params = new Object();
		params["serviceId"] = $(this).attr("id");
		
		ajaxCall("/siac/setInactiveService?id="+params["serviceId"],function(json){
				
			fillTableServices(json);
				
		});
		
	});
	
}

function onServiceInactiveButtonClick(){
	
	$(document).on("click", ".service-inactive", function(){
		
		var params = new Object();
		params["serviceId"] = $(this).attr("id");
		
		ajaxCall("/siac/setActiveService?id="+params["serviceId"],function(json){
			
			fillTableServices(json);
			
		});
		
	});
	
}

function onServiceEditButtonClick(){
	$(document).on("click", ".edit-service", function(){
		$("#save-edit-service").val("");
		$("#name-edit-service").val($(this).attr("data-name")); // Colocando o nome atual do serviço no campo de edição
		$(".save-edit-service").removeAttr("data-id"); //removendo, caso exista, o atributo "data-id"
		$(".save-edit-service").attr("data-id", $(this).attr("data-id"));
	});
}

function onServiceEditSaveButtonClick(){
	$(".save-edit-service").on("click", function(){
		var params = new Object();
		params["serviceName"] = $("#name-edit-service").val();
		params["serviceId"] = $(this).attr("data-id");
		
		ajaxCall("/siac/editService?id="+params["serviceId"]+"&name="+params["serviceName"], function(json){
			fillTableServices(json);
		});
		
		$('#modal-edit-service').modal('hide');
	});
}

function onServiceAddButtonClick(){
	
	$(".save-register-service").on("click", function(){
		
		var params = new Object();
		params["serviceName"] = $("#name-register-service").val();
		
		$("#name-register-service").val("");
		
		ajaxCall("/siac/registerService?name="+params["serviceName"], function(json){
			fillTableServices(json);
		});
		
		$('#modal-add-service').modal('hide');
	});
	
}

function fillTableServices(json){
	var serviceName;
	var serviceActive;
	var serviceId;
	
	$(".line-service").remove();
	
	json.sort(function (obj1, obj2) {
		return obj1.name < obj2.name ? -1 :
		(obj1.name > obj2.name ? 1 : 0);
		});
	
	$.each(json, function(key, obj){
		$.each(obj, function(name, value){
			if(name=="name"){
				serviceName = value;
			}
			if(name=="active"){
				serviceActive = value;
			}
			if(name=="id"){
				serviceId = value;
			}
		})
		
		if(serviceActive){
			var newRow = $("<tr class='line-service success'></tr>");
		}else{
			var newRow = $("<tr class='line-service active'></tr>");
		}
		
		newRow.append("<td>"+serviceName+"</td>");
		
		if(serviceActive){
			newRow.append("<td>Ativo</td>");
		}else{
			newRow.append("<td>Inativo</td>");
		}
		
		newRow.append("<td><button class='btn btn-sm btn-warning edit-service' role='button' data-toggle='modal' data-target='#modal-edit-service' data-name='"+serviceName+"' data-id='"+serviceId+"'>Editar</button></td>");
		
		if(serviceActive){
			newRow.append("<td><button class='btn btn-danger btn-sm service-active' id='"+serviceId+"'>Desativar</button></td>");
		}else{
			newRow.append("<td><button class='btn btn-primary btn-sm service-inactive' id='"+serviceId+"'>Ativar</button></td>");
		}
		
		$("#table-services").append(newRow);
	});
}

function onFieldSearchProfessionalChange(){
	
	$("#button-search-professional").on("click", function(){
		var value = $("#field-search-professional").val();
		if(value.length < 3){
			document.getElementById("alert-search-professional").style.display = 'block';
			
		}else{
			document.getElementById("alert-search-professional").style.display = 'none';
			
			var params = new Object();
			params["userName"] = $("#field-search-professional").val();
			
			ajaxCall("/siac/getUserByName?name="+params["userName"], function(json){
				
			});
			
		}
	});
}

function onGenerateReportButtonClicked(){
	$("#button-generate-report").click(function (){
		var reportType = $("#select-report-type").val();
		var serviceType = $("#select-service-type").val();
		var dataInicio = $("#input-dtpckr-start-report").val();
		var dataFim = $("#input-dtpckr-end-report").val();
		
	});
}
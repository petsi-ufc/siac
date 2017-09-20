/**
 * 
 */

//Colors
var colors = new Map();
colors.set("SC", {text: "Agendada", hex : "#4682B4", css: "color-blue"});
colors.set("RS", {text: "Agendada", hex : "#4682B4", css: "color-blue"});
colors.set("FR", {text: "Disponível", hex : "#32CD32", css: "color-green"});
colors.set("RD", {text: "Realizada", hex : "grey", css: "color-grey"});
colors.set("RV", {text: "Reservada", hex : "#D9D919", css: "color-yellow"});
colors.set("CD", {text: "Cancelada", hex : "#FF0000", css: "color-red"});
colors.set("NO", {text: "Não Agendado", hex : "#000000", css: "color-black"});
colors.set("GS", {hex : "#FA6900"});


self.addEventListener('message',function(e){
	//var list = [];
	e.data.datas.forEach(function (value, key){
		var title = "";
		if(value.state == "FR")	title = "Livre";
		else if (value.state == "CD" &&  value.group == null &&  value.patient == null) title = "Cancelada";
		else title =  value.group == null ? value.patient.name : value.group.title;
		var e = {
			title: title,
			color: colors.get(value.state).hex,
			state: value.state,
			start:value.dateInit,
			end: value.dateEnd,
			date: value.date,
			id: value.id,
			isGroup: value.group != null,
			comment: value.comment
		};
		if(value.group != null)
			e.group = value.group;
		$rootScope.events.push(e);
		//list.push(e);
	});
	/*e.data.forEach(function(value, key){
		$scope.events.push(value);
	});*/
	
	self.postMessage(list);
},false);
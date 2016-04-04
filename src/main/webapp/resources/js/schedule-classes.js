/**
 * Essa classe serve para gerênciar as datas e os
 * respectivos horários de consultas do profissional 
 * 
 */

var ScheduleTime = function(){
	var self = this;
	this.timeInit = moment();
	this.timeEnd = moment();
	var id = null;
	var state = "---";
	var rating = "---";
	var comment = "Nenhum comentário cadastrado!"
	
	self.__construct = function(hourInit, minuteInit, hourEnd, minuteEnd){
		self.setTimeInit(hourInit, minuteInit);
		self.setTimeEnd(hourEnd, minuteEnd);
	}
	
	self.getComment = function(){
		return self.comment;
	}
	
	self.setComment = function(comment){
		self.comment = comment;
	}
	
	self.setRating = function(rating){
		self.rating = rating;
	}
	
	self.getRating = function(){
		return self.rating;
	}
	
	self.setState = function(state){
		self.state = state;
	}
	
	self.getState = function(){
		return self.state;
	}
	
	self.setId = function(id){
		self.id = id;
	}
	
	self.getId = function(){
		return self.id;
	}
	
	self.setTimeInit = function(hourInit, minuteInit){
		this.timeInit.hours(hourInit);
		this.timeInit.minutes(minuteInit);
	}
	
	self.getTimeInit = function(){
		return this.timeInit.format("HH:mm");
	}
	
	self.setTimeEnd = function(hourEnd, minuteEnd){
		this.timeEnd.hours(hourEnd);
		this.timeEnd.minutes(minuteEnd);
	}
	
	self.getTimeEnd = function(){
		return this.timeEnd.format("HH:mm");
	}
	
	ScheduleTime.prototype.toJSON = function(){
		return {"id":this.id, "timeInit":this.timeInit.format('HH:mm'), "timeEnd":this.timeEnd.format('HH:mm')};
	}
	
};

var ScheduleDay = function(){
	var self = this;
	var date = "";
	this.listSchedules = [];
	
	
	self.setDate = function(date){
		self.date = date;
	}
	
	self.getDate = function(){
		return self.date;
	}
	
	self.addSchedule = function(hourInit, minuteInit, hourEnd, minuteEnd, state, rating, comment, id){
		var sch = new ScheduleTime();
		
		state = state ? state : "Sem Estado"; 
		
		rating = rating ? rating : "Sem Nota";
		
		comment = comment ? comment : "Sem comentário cadastrádo!";
	
		id = id ? id : null;
		
		sch.setRating(rating);
		sch.setState(state);
		sch.setComment(comment);
		sch.setId(id);
		
		sch.__construct(hourInit, minuteInit, hourEnd, minuteEnd);
		this.listSchedules.push(sch);
	}
	
	self.showListSchedules = function(){
		this.listSchedules.forEach(function(element, index, listSchedules){
			console.log(JSON.stringify(element));
		});
	}
	
	self.clearListSchedules = function(){
		this.listSchedules = [];
	}
	
	self.getListSchedules = function(){
		return this.listSchedules;
	}
	
	ScheduleDay.prototype.toJSON = function(){
		return {"date": this.date, "schedules": this.listSchedules};
	}	
};


var ScheduleManager = function(){
	var self = this;
	var mapScheduleDay = new Map();
	
	self.addScheduleDay = function(date, scheduleDay){
		mapScheduleDay.set(date, scheduleDay);
	}
	
	/*
	 * Adiciona um novo horário na data passada por parametro.
	 * Se a data não estive no mapa um novo ScheduleDay é criado.
	 */ 
	self.addNewScheduleTime = function(date, hourInit, minuteInit, hourEnd, minuteEnd, state, rating, comment, id){
		var scheduleDay = mapScheduleDay.get(date);
		if(scheduleDay){
			scheduleDay.addSchedule(hourInit, minuteInit, hourEnd, minuteEnd, state, rating, comment, id);
		}else{
			var sch = new ScheduleDay();
			sch.setDate(date);
			sch.addSchedule(hourInit, minuteInit, hourEnd, minuteEnd, state, rating, comment, id);
			self.addScheduleDay(date, sch);
		}
	}
	
	self.removeScheduleDay = function(date){
		mapScheduleDay.delete(date);
	}
	
	self.clearSchedules = function(){
		mapScheduleDay.clear();
	}
	
	self.getSchedulesMap = function(){
		return mapScheduleDay;
	}
	
	/*
	 * Essa função remove todos os horários que não estão
	 * na lista de horários atualizados. 
	 */
	self.updateSchedules = function(mapUpdated){
		var updatedMapSchedule = new Map();
		
		//Pegando todas as keys(datas) do mapa.
		var keys = mapUpdated.keys();
		for(var i = 0; i < mapUpdated.size; i++){
			
			date = keys.next().value;
		
			if(mapScheduleDay.has(date)){
				updatedMapSchedule.set(date, mapScheduleDay.get(date));
			}
		}
		mapScheduleDay = updatedMapSchedule;
	}
	
	/*
	 * Retorna true se existir horários cadastrados 
	 * na data passada por parâmetro e false caso contrário.
	 */
	self.isScheduleRegistered = function(date){
		var schedule = mapScheduleDay.get(date);
		if(schedule){
			return schedule.listSchedules.length > 0 ? true : false;
		}
		return false;
	}
	
	self.getScheduleDay = function(date){
		return mapScheduleDay.get(date);
	}
	
	ScheduleManager.prototype.toJSON = function(){
		var list = [];
		mapScheduleDay.forEach(function(value, key, mapScheduleDay){
			list.push(value);
		});
		return {"data":list};
	}
}
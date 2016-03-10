/**
 * Essa classe serve para gerênciar as datas e os
 * respectivos horários de consultas do profissional 
 * 
 * OBS: Essas classes possuem dependência da biblioteca moment.js
 */

var ScheduleTime = function(){
	var self = this;
	var timeInit = moment();
	var timeEnd = moment();
	
	self.__construct = function(hourInit, minuteInit, hourEnd, minuteEnd){
		self.setTimeInit(hourInit, minuteInit);
		self.setTimeEnd(hourEnd, minuteEnd);
	}
	
	self.setTimeInit = function(hourInit, minuteInit){
		self.timeInit.hours(hourInit);
		self.timeInit.minutes(minuteInit);
	}
	
	self.getTimeInit = function(){
		return self.timeInit;
	}
	
	self.setTimeEnd = function(hourEnd, minuteEnd){
		self.timeEnd.hours(hourEnd);
		self.timeEnd.minutes(minuteEnd);
	}
	
	self.getTimeEnd = function(){
		return self.timeEnd;
	}
	
}

var ScheduleDay = function(){
	var self = this;
	var date = "";
	var listSchedules = []
	
	self.setDate = function(date){
		self.date = date;
	}
	
	self.getDate = function(){
		return self.date;
	}
	
	self.addSchedule = function(hourInit, minuteInit, hourEnd, minuteEnd){
		var schedule = new ScheduleTime().__construct(hourInit, minuteInit, hourEnd, minuteEnd);
		listSchedules.append(schedule);
	}
	
	self.toString = function(){
		return JSON.stringify(self);
	}
}


var ScheduleManager = function(){
	var self = this;
	var listScheduleDay = [];
	
	self.addScheduleDay = function(scheduleDay){
		listScheduleDay.append(scheduleDay);
	}
	
	self.showAllSchedulesDay = function(){
		listScheduleDay.forEach(function(element, index, listScheduleDay){
			console.log(element.toString());
		});
	}
}
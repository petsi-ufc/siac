/**
 * Essa classe serve para gerênciar as datas e os
 * respectivos horários de consultas do profissional 
 * 
 */

var ScheduleTime = function(){
	var self = this;
	this.timeInit = moment();
	this.timeEnd = moment();
	
	self.__construct = function(hourInit, minuteInit, hourEnd, minuteEnd){
		self.setTimeInit(hourInit, minuteInit);
		self.setTimeEnd(hourEnd, minuteEnd);
	}
	
	self.setTimeInit = function(hourInit, minuteInit){
		this.timeInit.hours(hourInit);
		this.timeInit.minutes(minuteInit);
	}
	
	self.getTimeInit = function(){
		return timeInit;
	}
	
	self.setTimeEnd = function(hourEnd, minuteEnd){
		this.timeEnd.hours(hourEnd);
		this.timeEnd.minutes(minuteEnd);
	}
	
	self.getTimeEnd = function(){
		return timeEnd;
	}
	
	ScheduleTime.prototype.toJSON = function(){
		return {"timeInit":this.timeInit.format('HH:mm'), "timeEnd":this.timeEnd.format('HH:mm')};
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
	
	self.addSchedule = function(hourInit, minuteInit, hourEnd, minuteEnd){
		var sch = new ScheduleTime();
		sch.__construct(hourInit, minuteInit, hourEnd, minuteEnd);
		this.listSchedules.push(sch);
	}
	
	self.showListSchedules = function(){
		this.listSchedules.forEach(function(element, index, listSchedules){
			console.log(JSON.stringify(element));
		});
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
	
	self.removeScheduleDay = function(date){
		mapScheduleDay.delete(date);
	}
	
	self.getSchedulesMap = function(){
		return mapScheduleDay;
	}
	
	
	ScheduleManager.prototype.toJSON = function(){
		var list = [];
		mapScheduleDay.forEach(function(value, key, mapScheduleDay){
			list.push(value);
		});
		return {"data":list};
	}
}
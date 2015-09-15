package br.ufc.petsi.dao;

import java.util.List;

import br.ufc.petsi.model.Schedule;

public interface ScheduleDAO {
	public void save(Schedule sche);
	public Schedule getScheduleById(long id);
	public List<Schedule> getSchedulesByUserId(long userId);
	public List<Schedule> getAllSchedulesAvaliable();
}

package br.ufc.petsi.dao.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.ufc.petsi.dao.ScheduleDAO;
import br.ufc.petsi.model.Schedule;

@Repository
public class HBSchedule implements ScheduleDAO{


	@PersistenceContext
	private EntityManager manager;
		
	@Override
	public void save(Schedule sche) {
		manager.persist(sche);
	}

	@Override
	public Schedule getScheduleById(long id) {
		Query query = manager.createQuery("SELECT sche FROM Schedule sche WHERE sche.id = :id");
		query.setParameter("id", id);
		return (Schedule) query.getSingleResult();
	}

	@Override
	public List<Schedule> getSchedulesByUserId(long userId) {
		Query query = manager.createQuery("SELECT sche FROM Schedule sche WHERE sche.id = :userId");
		query.setParameter("userId", userId);
		List<Schedule> list = query.getResultList();
		return list;
	}

	@Override
	public List<Schedule> getAllSchedulesAvaliable() {
		Query query = manager.createQuery("SELECT sche FROM Schedule sche WHERE sche.avaliable = true");
		List<Schedule> list = query.getResultList();
		return list;
	}

}

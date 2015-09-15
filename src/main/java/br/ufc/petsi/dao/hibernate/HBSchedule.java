package br.ufc.petsi.dao.hibernate;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import br.ufc.petsi.dao.ScheduleDAO;
import br.ufc.petsi.model.Schedule;

@Repository
public class HBSchedule implements ScheduleDAO{

	@Inject
	private SessionFactory sessionFactory;
	
	@Override
	public void save(Schedule sche) {
		getSession().persist(sche);
	}

	@Override
	public Schedule getScheduleById(long id) {
		Query query = getSession().createQuery("SELECT sche FROM Schedule sche WHERE sche.id = :id");
		query.setParameter("id", id);
		return (Schedule) query.uniqueResult();
	}

	@Override
	public List<Schedule> getSchedulesByUserId(long userId) {
		Query query = getSession().createQuery("SELECT sche FROM Schedule sche WHERE sche.id = :userId");
		query.setParameter("userId", userId);
		List<Schedule> list = query.list();
		return list;
	}

	@Override
	public List<Schedule> getAllSchedulesAvaliable() {
		Query query = getSession().createQuery("SELECT sche FROM Schedule sche WHERE sche.avaliable = true");
		List<Schedule> list = query.list();
		return list;
	}
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}

}

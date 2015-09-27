package br.ufc.petsi.dao.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.ufc.petsi.dao.AgendaDAO;
import br.ufc.petsi.model.Agenda;


@Repository
public class HBAgenda implements AgendaDAO{
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public void save(Agenda ag) {
		manager.persist(ag);
	}
	
	@Override
	public void update(Agenda ag) {
		manager.merge(ag);
	}

	@Override
	public Agenda getAgendaById(long id) {
		Query query = (Query) manager.createQuery("SELECT ag FROM Agenda ag WHERE ag.id = :id");
		query.setParameter("id", id);
		try {
			return (Agenda) query.getSingleResult();	
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Agenda getAgendaByUserId(long userId) {
		Query query = (Query) manager.createQuery("SELECT ag FROM Agenda ag WHERE ag.userId = :userId");
		query.setParameter("userId", userId);
		try {
			return (Agenda) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	

}

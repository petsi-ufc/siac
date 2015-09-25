package br.ufc.petsi.dao.hibernate;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import br.ufc.petsi.dao.ServiceDAO;
import br.ufc.petsi.model.Service;

@Repository
public class HBService implements ServiceDAO{

	@PersistenceContext
	private EntityManager manager;
		
	
	@Override
	public void save(Service service) {
		manager.persist(service);
	}

	@Override
	public Service getServiceById(long id) {
		Query query = manager.createQuery("SELECT se FROM Session se WHERE se.id = :id");
		query.setParameter("id", id);
		return (Service) query.getSingleResult();
	}

	@Override
	public Service getServiceByName(String name) {
		Query query = manager.createQuery("SELECT se FROM Session se WHERE se.name = :name");
		query.setParameter("name", name);
		return (Service) query.getSingleResult();
	}
	
	
	
}

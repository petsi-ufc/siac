package br.ufc.petsi.dao.hibernate;


import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import br.ufc.petsi.dao.ServiceDAO;
import br.ufc.petsi.model.Service;

@Repository
public class HBService implements ServiceDAO{

	@PersistenceContext
	private EntityManager manager;
		
	
	@Override
	public void save(Service service) {
		this.manager.persist(service);
	}

	@Override
	public Service getServiceById(long id) {
		Query query = this.manager.createQuery("SELECT se FROM Service se WHERE se.id = :id");
		query.setParameter("id", id);
		return (Service) query.getSingleResult();
	}

	@Override
	public Service getServiceByName(String name) {
		Query query = this.manager.createQuery("SELECT se FROM Service se WHERE se.name = :name");
		query.setParameter("name", name);
		return (Service) query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Service> getAllServices(){
		List<Service> services = this.manager.createQuery("SELECT se FROM Service se").getResultList();
		return services;
	}

	@Override
	public void edit(Service service) {
		this.manager.merge(service);
	}
	
}

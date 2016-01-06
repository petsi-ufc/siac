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
import br.ufc.petsi.model.SocialService;

@Repository
public class HBService implements ServiceDAO{

	@PersistenceContext
	private EntityManager manager;
		
	
	@Override
	public void save(SocialService service) {
		this.manager.persist(service);
	}

	@Override
	public SocialService getServiceById(long id) {
		Query query = this.manager.createQuery("SELECT se FROM Service se WHERE se.id = :id");
		query.setParameter("id", id);
		return (SocialService) query.getSingleResult();
	}

	@Override
	public SocialService getServiceByName(String name) {
		Query query = this.manager.createQuery("SELECT se FROM Service se WHERE se.name = :name");
		query.setParameter("name", name);
		return (SocialService) query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<SocialService> getAllServices(){
		List<SocialService> services = this.manager.createQuery("SELECT se FROM Service se").getResultList();
		return services;
	}

	@Override
	public void edit(SocialService service) {
		this.manager.merge(service);
	}

	@Override
	public List<SocialService> getActiveServices() {
		return this.manager.createQuery("SELECT se FROM Service se WHERE se.active = true").getResultList();
	}
	
}

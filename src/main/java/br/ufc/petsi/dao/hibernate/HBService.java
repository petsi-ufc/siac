package br.ufc.petsi.dao.hibernate;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.ufc.petsi.dao.SocialServiceDAO;
import br.ufc.petsi.model.SocialService;
import br.ufc.petsi.util.LogGenerator;

@Repository
public class HBService implements SocialServiceDAO{

	@PersistenceContext
	private EntityManager manager;
		
	
	@Override
	public void save(SocialService service) {
		this.manager.persist(service);
	}

	@Override
	public SocialService getServiceById(long id) {
		try{
			Query query = this.manager.createQuery("SELECT se FROM SocialService se WHERE se.id = :id");
			query.setParameter("id", id);
			return (SocialService) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Erro HBDAO getServiceById: "+e);
			LogGenerator.getInstance().log(e, "Erro ao pegar um serviço pelo id");
			return null;
		}
	}

	@Override
	public SocialService getServiceByName(String name) {
		try{
			Query query = this.manager.createQuery("SELECT se FROM SocialService se WHERE se.name = :name");
			query.setParameter("name", name);
			return (SocialService) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Erro HBDAO getServiceByName: "+e);
			LogGenerator.getInstance().log(e, "Erro ao pegar um serviço pelo nome");
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<SocialService> getAllServices(){
		try{
			List<SocialService> services = this.manager.createQuery("SELECT se FROM SocialService se").getResultList();
			return services;
		} catch (NoResultException e) {
			System.out.println("Erro HBDAO getAllServices: "+e);
			LogGenerator.getInstance().log(e, "Erro ao pegar todos os serviços");
			return null;
		}
	}

	@Override
	public void edit(SocialService service) {
		this.manager.merge(service);
	}

	@Override
	public List<SocialService> getActiveServices() {
		try{
			return this.manager.createQuery("SELECT se FROM SocialService se WHERE se.active = true").getResultList();
		} catch (NoResultException e) {
			System.out.println("Erro HBDAO getServiceById: "+e);
			LogGenerator.getInstance().log(e, "Erro ao pegar um serviço pelo id");
			return null;
		}
	}
	
}

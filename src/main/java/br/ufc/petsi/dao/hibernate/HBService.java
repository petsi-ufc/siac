package br.ufc.petsi.dao.hibernate;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import br.ufc.petsi.dao.ServiceDAO;
import br.ufc.petsi.model.Service;

@Repository
public class HBService implements ServiceDAO{
	
	@Inject
	private SessionFactory sessionFactory;
	
	
	@Override
	public void save(Service service) {
		getSession().persist(service);
	}

	@Override
	public Service getServiceById(long id) {
		Query query = getSession().createQuery("SELECT se FROM Session se WHERE se.id = :id");
		query.setParameter("id", id);
		return (Service) query.uniqueResult();
	}

	@Override
	public Service getServiceByName(String name) {
		Query query = getSession().createQuery("SELECT se FROM Session se WHERE se.name = :name");
		query.setParameter("name", name);
		return (Service) query.uniqueResult();
	}
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
}

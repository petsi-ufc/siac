package br.ufc.petsi.dao.hibernate;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import br.ufc.petsi.dao.ConsultationDAO;
import br.ufc.petsi.enums.ConsultationState;
import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Service;

@Repository
public class HBConsultation implements ConsultationDAO{
	
	@Inject
	private SessionFactory sessionFactory;
	
	@Override
	public void save(Consultation cons) {
		getSession().save(cons);
	}

	@Override
	public Consultation getConsultationById(long id) {
		Query query = getSession().createQuery("SELECT cons FROM Consultation cons WHERE cons.id = :idConsultation");
		query.setParameter("idConsultation", id);
		return (Consultation) query.uniqueResult();
	}

	@Override
	public List<Consultation> getConsultationsByState(ConsultationState state) {
		Query query = getSession().createQuery("SELECT cons FROM Consultation cons WHERE cons.state = :state");
		query.setParameter("state", state);
		List<Consultation> cons = query.list();
		return cons;
	}

	@Override
	public List<Consultation> getConsultationsByService(Service service) {
		Query query = getSession().createQuery("SELECT cons FROM Consultation cons WHERE cons.service = :service");
		query.setParameter("service", service);
		List<Consultation> cons = query.list();
		return cons;
	}

	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
}

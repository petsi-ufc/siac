package br.ufc.petsi.dao.hibernate;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.ufc.petsi.dao.ConsultationDAO;
import br.ufc.petsi.enums.ConsultationState;
import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Patient;
import br.ufc.petsi.model.SocialService;

@Repository
public class HBConsultation implements ConsultationDAO{
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public void save(Consultation cons) {
		manager.persist(cons);
	}

	@Override
	public void update(Consultation con) {
		manager.merge(con);
	}
	
	@Override
	public Consultation getConsultationById(long id) {
		Query query = (Query) manager.createQuery("SELECT cons FROM Consultation cons WHERE cons.id = :idConsultation");
		query.setParameter("idConsultation", id);
		return (Consultation) query.getSingleResult();
	}

	@Override
	public List<Consultation> getConsultationsByState(ConsultationState state) {
		Query query = (Query) manager.createQuery("SELECT cons FROM Consultation cons WHERE cons.state = :state");
		query.setParameter("state", state);
		List<Consultation> cons = query.getResultList();
		return cons;
	}

	@Override
	public List<Consultation> getConsultationsBySocialService(SocialService service) {
		Query query = (Query) manager.createQuery("SELECT cons FROM Consultation cons WHERE cons.socialService = :service");
		query.setParameter("service", service);
		List<Consultation> cons = query.getResultList();
		return cons;
	}

	@Override
	public List<Consultation> getConsultationsBySocialServiceAndDate(SocialService service,
			Date startDay) {
		Query query = (Query) manager.createQuery("SELECT cons FROM Consultation cons WHERE"
				+ " cons.socialService = :service AND cons.schedule.dateInit = :startDay");
		query.setParameter("service", service);
		query.setParameter("startDay", startDay);
		
		try{
			List<Consultation> cons = query.getResultList();
			return cons;
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Consultation> getConsultationsByPatient(Patient p) {
		Query query = (Query) manager.createQuery("SELECT cons FROM Consultation cons WHERE cons.patient = :patient");
		query.setParameter("patient", p);
		
		try{
			List<Consultation> cons = query.getResultList();
			return cons;
		}catch(NoResultException e){
			return null;
		}
	}


		
}

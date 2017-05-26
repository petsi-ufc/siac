package br.ufc.petsi.dao.hibernate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.ufc.petsi.dao.ConsultationDAO;
import br.ufc.petsi.enums.ConsultationState;
import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Frequency;
import br.ufc.petsi.model.Group;
import br.ufc.petsi.model.Patient;
import br.ufc.petsi.model.Professional;
import br.ufc.petsi.model.Rating;
import br.ufc.petsi.model.SocialService;

@Repository
public class HBConsultation implements ConsultationDAO{
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public void save(Consultation cons) {
		manager.merge(cons);
	}

	@Override
	@Transactional
	public void update(Consultation con) {
		manager.merge(con);
	}
	
	@Override
	public Consultation getConsultationById(long id) {
		try{
			Query query = (Query) manager.createQuery("SELECT cons FROM Consultation cons WHERE cons.id = :idConsultation");
			query.setParameter("idConsultation", id);
			
			return (Consultation) query.getSingleResult();
		}catch(NoResultException ex){
			return null;
		}
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

	@Override
	public List<Consultation> getConsultationByProfessional(Professional professional) {
		Query query = (Query) manager.createQuery("SELECT cons FROM Consultation cons WHERE cons.professional = :professional ORDER BY cons.dateInit DESC");
		query.setParameter("professional", professional);
		List<Consultation> cons = new ArrayList<Consultation>();
		try{
			cons = query.getResultList();
		}catch(NoResultException e){
			System.out.println("No result at getConsultationByProfessional: "+e);
		}
		return cons;
	}
	
	@Override
	public List<Consultation> getConsultationByPeriod(Professional professional, Date dateInit, Date dateEnd) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Query query = (Query) manager.createNativeQuery("SELECT * FROM consultation WHERE date_init >= '"+formatter.format(dateInit)+"' AND date_end <= '"+formatter.format(dateEnd)+"' AND professional_id = "+professional.getId()+"",Consultation.class);
		
		List<Consultation> cons = new ArrayList<Consultation>();
		try{
			cons = query.getResultList();
		}catch(NoResultException e){
			System.out.println("No result at getConsultationByProfessional: "+e);
		}
		return cons;
	}
	
	@Override
	public List<Consultation> getConsultationByGroup(Group group) {
		Query query = (Query) manager.createQuery("SELECT cons FROM Consultation cons WHERE cons.group = :group");
		query.setParameter("group", group);
		
		try{
			List<Consultation> cons = query.getResultList();
			return cons;
		}catch(NoResultException e){
			return null;
		}
	}

	@Override
	public void cancelConsultation(Consultation con) {
		con.setState(ConsultationState.CD);
		this.update(con);
	}
	
	@Override
	public boolean isRatingNull(Consultation consultation) {
		Query query = (Query) manager.createQuery("SELECT cons.rating FROM Consultation cons WHERE cons.id = :id");
		query.setParameter("id", consultation.getId());
				
		return false;
	}

	@Override
	public Rating getRatingByIdConsultation(long idConsultation) {
		Query query = (Query) manager.createQuery("SELECT cons.rating FROM Consultation cons WHERE cons.id = :id");
		query.setParameter("id", idConsultation);
		return (Rating) query.getSingleResult();
	}

	@Override
	public void registerConsultation(Consultation cons) {
		Consultation oldCons = manager.find(Consultation.class, cons.getId());
		oldCons.setState(ConsultationState.RD);
		update(oldCons);
	}
	
}

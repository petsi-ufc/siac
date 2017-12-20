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

import com.lowagie.text.pdf.hyphenation.TernaryTree.Iterator;

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
		manager.persist(cons);
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
			System.out.println("ID do Consultation: "+id);
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
	public List<Consultation> getConsultationByProfessional(Professional professional, Date init, Date end) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Query query = manager.createNativeQuery("SELECT c.id, c.date_end, c.date_init, c.state, c.professional_id, c.id_service, c.comment, c.reason,c.id_group,g.title,c.id_patient,u.cpf,u.name"+ 
												" FROM consultation as c left join users as u on c.id_patient = u.id left join service_group as g on c.id_group = g.id WHERE c.professional_id = "+professional.getId()+" AND c.date_init >= to_date('"+formatter.format(init)+"','YYYY-MM-DD') AND c.date_end <= to_date('"+formatter.format(end)+"','YYYY-MM-DD')");
		
		List<Consultation> cons = new ArrayList<Consultation>();
		try{
			List<Object[]> list = (List<Object[]>) query.getResultList();
			System.out.println("[QUANTIDADE DE CONSULTAS]:"+list.size());
			for(Object[] obj: list){
				Consultation c = new Consultation();
				c.setId(Long.parseLong(String.valueOf(obj[0])));
				c.setDateEnd((Date) obj[1]);
				c.setDateInit((Date) obj[2]);
				c.setState(ConsultationState.valueOf(ConsultationState.class, (String) obj[3]));
				Professional p = new Professional();
				p.setId(Long.parseLong(String.valueOf(obj[4])));
				c.setProfessional(p);
				if(obj[5] != null){
					SocialService s = new SocialService();
					s.setId(Long.parseLong(String.valueOf(obj[5])));
					c.setService(s);
				}
				c.setComment((String) obj[6]);
				c.setReason((String) obj[7]);
				if(obj[8]!=null){
					Group g = new Group();
					g.setId(Long.parseLong(String.valueOf(obj[8])));
					g.setTitle((String) obj[9]);
					c.setGroup(g);
				}
				if(obj[10]!=null){
					Patient pat = new Patient();
					pat.setId(Long.parseLong(String.valueOf(obj[10])));
					pat.setCpf((String) obj[11]);
					pat.setName((String) obj[12]);
					c.setPatient(pat);
				}
				cons.add(c);
			}
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
	public Rating getRatingByIdConsultation(long idConsultation, long idPatient) {
		Query query = (Query) manager.createQuery("SELECT rat FROM Rating rat WHERE rat.consultation.id = :id AND rat.patient.id = :idPatient");
		query.setParameter("id", idConsultation);
		query.setParameter("idPatient", idPatient);
		return (Rating) query.getSingleResult();
	}

	@Override
	public void registerConsultation(Consultation cons) {
		Consultation oldCons = manager.find(Consultation.class, cons.getId());
		oldCons.setState(ConsultationState.RD);
		update(oldCons);
	}
	
}

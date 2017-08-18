package br.ufc.petsi.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.ufc.petsi.dao.ReserveDAO;
import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Group;
import br.ufc.petsi.model.Patient;
import br.ufc.petsi.model.Reserve;


@Repository
public class HBReserve implements ReserveDAO{
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public void save(Reserve reserve) {
		this.manager.persist(reserve);
		
	}

	@Override
	public void update(Reserve reserve) {
		this.manager.merge(reserve);
		
	}

	
	@Override
	public List<Reserve> getActiveReservesByPatient(Patient patient) {
		try{
			Query query = this.manager.createQuery("SELECT re FROM Reserve re WHERE re.patient.cpf = :id AND re.active = true");
			query.setParameter("id", patient.getCpf());
			return (List<Reserve>) query.getResultList();
		}catch(NoResultException ex){
			return new ArrayList<Reserve>();
		}
	}

	@Override
	public Reserve getReserveById(Reserve reserve) {
		Query query = this.manager.createQuery("SELECT re FROM Reserve re WHERE re.id = :id");
		query.setParameter("id", reserve.getId());
		return (Reserve) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Reserve> getActiveReservesByConsultation(Consultation consultation) {
		System.out.println("Consultaaaa: " + consultation.getId());
		try{
		Query query = this.manager.createQuery("SELECT re FROM Reserve re WHERE re.consultation.id = :id AND re.active = true");
		query.setParameter("id", consultation.getId());
		return (List<Reserve>) query.getResultList();
		}catch(Exception e){
			System.out.println(e);
			return null;
		}
	}
	
	@Override
	public List<Reserve> getActiveReservesByGroup(Group group) {
		Query query = this.manager.createQuery("SELECT re FROM Reserve re WHERE re.group.id = :id AND re.active = true");
		query.setParameter("id", group.getId());
		return (List<Reserve>) query.getResultList();
	}


	

}

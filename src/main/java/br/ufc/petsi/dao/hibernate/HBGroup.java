package br.ufc.petsi.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.ufc.petsi.dao.GroupDAO;
import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Group;
import br.ufc.petsi.model.Patient;
import br.ufc.petsi.model.Professional;

@Repository
public class HBGroup implements GroupDAO {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public void save(Group group){
		manager.persist(group);
	}
	
	@Override
	public void update(Group group){
		manager.merge(group);
	}
	
	@Override
	public void addPatient(Group group, Patient patient) {
		
		Group g = manager.find(Group.class, group.getId());
		Patient p = manager.find(Patient.class, patient.getId());
		g.getPatients().add(p);
		p.getGroups().add(g);
		
		manager.flush();
	}

	@Override
	public void removePatient(Group group, Patient patient) {
		
		Group g = manager.find(Group.class, group.getId());
		Patient p = manager.find(Patient.class, patient.getId());
		g.getPatients().remove(findPatient(g.getPatients(), patient));
		p.getGroups().remove(findGroup(p.getGroups(), group));
		
		manager.flush();
		
	}

	@Override
	public List<Patient> getPatients(Group group) {
		try{
			return manager.find(Group.class, group.getId()).getPatients();
		}catch(Exception e){
			System.out.println("No result at getPatients: "+e);
			return null;
		}
	}

	@Override
	public List<Group> getAllGroups(Professional professional) {
		Query query = (Query) manager.createQuery("SELECT grp FROM Group grp WHERE grp.facilitator = :facilitator");
		query.setParameter("facilitator", professional);
		List<Group> groups = query.getResultList();
		return groups;
	}

	@Override
	public Group find(long id) {
		return manager.find(Group.class, id);
	}
	
	@Override
	public List<Group> getGroupsByPatient(Patient patient) {
		Query query = (Query) manager.createQuery("SELECT grp FROM Group grp JOIN grp.patients ptn WHERE ptn.id = :id");
		query.setParameter("id",patient.getId());
		List<Group> groups = new ArrayList<Group>();
		try{
			groups = query.getResultList();
		}catch(NoResultException e){
			System.out.println("No result at getConsultationByProfessional: "+e);
		}
		return groups;
	}
	
	@Override
	public List<Group> getGroupsFree() {
		Query query = (Query) manager.createQuery("SELECT grp FROM Group grp WHERE grp.openGroup = true");
		List<Group> groups = query.getResultList();
		return groups;
	}
	
	/* AUXILIARES */
	private Patient findPatient(List<Patient> patients, Patient patient){
		for (Patient p : patients) {
			if(p.getId() == patient.getId())
				return p;
		}
		return null;
	}
	
	private Group findGroup(List<Group> groups, Group group){
		for (Group g : groups) {
			if(g.getId() == group.getId())
				return g;
		}
		return null;
	}
	
}

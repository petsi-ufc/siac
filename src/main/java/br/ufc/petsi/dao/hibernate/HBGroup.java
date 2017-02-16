package br.ufc.petsi.dao.hibernate;

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
		group.getPatients().add(patient);
		update(group);
	}

	@Override
	public void removePatient(Group group, Patient patient) {
		for(Patient p: group.getPatients())
			if(p.getId() == patient.getId())
				group.getPatients().remove(p);
		update(group);
		
	}

	@Override
	public List<Patient> getPatients(Group group) {
		try{
			Group g = manager.find(Group.class, group.getId());
			return g.getPatients();
		}catch(NoResultException ex){
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
	
	
	
}

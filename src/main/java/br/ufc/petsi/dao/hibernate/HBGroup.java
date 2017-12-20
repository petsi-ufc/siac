package br.ufc.petsi.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
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
		Query query = (Query) manager.createNativeQuery("select role,id,cpf,email,name from users u, group_patient gp where u.id = gp.id_patient and gp.id_group = "+group.getId());
		List<Patient> patients = new ArrayList<Patient>();
		try{
			List<Object[]> pats = query.getResultList();
			for(Object[] obj: pats){
				Patient p = new Patient(String.valueOf(obj[2]),String.valueOf((String)obj[4]),String.valueOf(obj[3]),String.valueOf(obj[0]));
				p.setId(Long.parseLong(String.valueOf(obj[1])));
				patients.add(p);
			}
			return patients;
		}catch(Exception e){
			System.out.println("No result at getPatients: "+e);
			return null;
		}
	}

	@Override
	public List<Consultation> getConsultations(Group group) {
		Query query = (Query) manager.createNativeQuery("select id, date_init, date_end from consultation where id_group = "+group.getId());
		List<Consultation> consultations = new ArrayList<Consultation>();
		try{
			List<Object[]> result = query.getResultList();
			for(Object[] obj: result){
				Consultation c = new Consultation();
				c.setId(Long.parseLong(String.valueOf(obj[0])));
				//Date dinit = new Date(String.valueOf());
				c.setDateInit((Date)obj[1]);
				//Date dend = new Date(String.valueOf(obj[2]));
				c.setDateEnd((Date)obj[2]);
				consultations.add(c);
			}
			return consultations;
		}catch(Exception e){
			System.out.println("No result at getConsultations: "+e.getMessage());
			return null;
		}
	}
	
	@Override
	public List<Group> getAllGroups(Professional professional) {
		Query query = manager.createNativeQuery("SELECT id,opengroup,title,patientlimit FROM service_group WHERE facilitator_id ="+professional.getId());
		List<Group> groups = new ArrayList<Group>(); 
		List<Object[]> objs = (List<Object[]>) query.getResultList();
		for(Object[] obj: objs){
			Group g = new Group();
			g.setId(Long.parseLong(String.valueOf(obj[0])));
			g.setOpenGroup((boolean) obj[1]);
			g.setTitle((String) obj[2]);
			g.setPatientLimit((int)obj[3]);
			groups.add(g);
		}
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

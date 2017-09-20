package br.ufc.petsi.dao;

import java.util.List;

import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Group;
import br.ufc.petsi.model.Patient;
import br.ufc.petsi.model.Professional;

public interface GroupDAO {
	public void save(Group group);
	public void update(Group group);
	public void addPatient(Group group, Patient patient);
	public void removePatient(Group group, Patient patient);
	public Group find(long id);
	public List<Patient> getPatients (Group group);
	public List<Group> getAllGroups (Professional professional);
	public List<Group> getGroupsByPatient(Patient patient);
	public List<Group> getGroupsFree();
	List<Consultation> getConsultations(Group group);
}

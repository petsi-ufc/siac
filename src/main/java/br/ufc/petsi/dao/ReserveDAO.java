package br.ufc.petsi.dao;

import java.util.List;

import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Group;
import br.ufc.petsi.model.Patient;
import br.ufc.petsi.model.Reserve;

public interface ReserveDAO {
	
	public void save(Reserve reserve);
	public void update(Reserve reserve);
	public List<Reserve> getActiveReservesByPatient(Patient patient);
	public List<Reserve> getActiveReservesByGroup(Group group);
	public Reserve getReserveById(Reserve reserve);
	public List<Reserve> getActiveReservesByConsultation(Consultation consultation);
}

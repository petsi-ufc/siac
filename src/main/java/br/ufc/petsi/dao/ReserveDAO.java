package br.ufc.petsi.dao;

import java.util.List;

import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Patient;
import br.ufc.petsi.model.Reserve;

public interface ReserveDAO {
	
	public void save(Reserve reserve);
	public void update(Reserve reserve);
	public List<Reserve> getActiveReservesByPatient(Patient patient);
	public Reserve getReserveById(Reserve reserve);
	public List<Reserve> getActiveReservesByConsultation(Consultation consultation);
}

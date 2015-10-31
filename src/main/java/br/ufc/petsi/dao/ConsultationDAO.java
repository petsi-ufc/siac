package br.ufc.petsi.dao;

import java.util.Date;
import java.util.List;

import br.ufc.petsi.enums.ConsultationState;
import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Service;

public interface ConsultationDAO {
	public void save(Consultation cons);
	public void update(Consultation con);
	public Consultation getConsultationById(long id);
	public List<Consultation> getConsultationsByState(ConsultationState state);
	public List<Consultation> getConsultationsByService(Service service);
	public List<Consultation> getConsultationsByServiceAndDate(Service service, Date startDay);
}

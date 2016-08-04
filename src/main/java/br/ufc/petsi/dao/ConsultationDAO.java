package br.ufc.petsi.dao;

import java.util.Date;
import java.util.List;

import br.ufc.petsi.enums.ConsultationState;
import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Patient;
import br.ufc.petsi.model.Professional;
import br.ufc.petsi.model.Rating;
import br.ufc.petsi.model.SocialService;

public interface ConsultationDAO {
	public void save(Consultation cons);
	public void update(Consultation con);
	public void cancelConsultation(Consultation con);
	public void registerConsultation(Consultation cons);
	public Consultation getConsultationById(long id);
	public List<Consultation> getConsultationsByPatient(Patient p);
	public List<Consultation> getConsultationsByState(ConsultationState state);
	public List<Consultation> getConsultationsBySocialService(SocialService service);
	public List<Consultation> getConsultationsBySocialServiceAndDate(SocialService service, Date startDay);
	public List<Consultation> getConsultationByProfessional(Professional professional);
	public boolean isRatingNull(Consultation consultation);
	public Rating getRatingByIdConsultation(long idConsultation);
	
}

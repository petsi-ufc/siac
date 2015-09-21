package br.ufc.petsi.service;

import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.User;

public class UserService {
	
	public boolean sheduleConsultation(User user, Consultation con) {
		return new AgendaService().schedule(user, con);
	}
	
	public boolean rateConsultation(User user, Consultation con) {
		return new ConsultationService().rate(user, con);
	}
	
	public boolean cancelConsultation(Consultation con) {
		return new ConsultationService().cancel(con);
	}
	
	public boolean editConsultation(Consultation con) {
		return new ConsultationService().edit(con);
	}
	
}

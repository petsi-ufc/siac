package br.ufc.petsi.service;

import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Rating;
import br.ufc.petsi.model.User;

public class UserService {
	
	public boolean rateConsultation(Consultation con, Rating rating) {
		return new ConsultationService().rate(con, rating);
	}
	
	public boolean cancelConsultation(Consultation con) {
		return new ConsultationService().cancel(con);
	}
	
	public boolean editConsultation(Consultation con) {
		return new ConsultationService().edit(con);
	}
	
}

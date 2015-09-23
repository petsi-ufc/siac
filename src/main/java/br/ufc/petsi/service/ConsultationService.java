package br.ufc.petsi.service;

import javax.inject.Inject;

import br.ufc.petsi.dao.ConsultationDAO;
import br.ufc.petsi.dao.RatingDAO;
import br.ufc.petsi.enums.ConsultationState;
import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Rating;
import br.ufc.petsi.model.User;

public class ConsultationService {
	
	@Inject
	private ConsultationDAO conDAO;
	
	private RatingDAO ratDAO;
	
	public boolean edit(Consultation con) {
		if(con == null)
			return false;
		
		conDAO.update(con);
		
		return true;
	}
	
	public boolean cancel(Consultation con) {
		if(con == null)
			return false;
		
		con.setState(ConsultationState.CD);
		
		conDAO.update(con);
		
		return true;
	}
	
	public boolean rate(Consultation con, Rating rating) {
		if(con == null || rating == null)
			return false;
		
		rating.setConsultation(con);
		
		ratDAO.save(rating);
		
		return true;
	}
}

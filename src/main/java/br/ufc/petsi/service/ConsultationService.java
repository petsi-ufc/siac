package br.ufc.petsi.service;

import javax.inject.Inject;

import br.ufc.petsi.dao.ConsultationDAO;
import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.User;

public class ConsultationService {
	
	@Inject
	private ConsultationDAO conDAO;
	
	public boolean edit(Consultation con) {
		if(con == null)
			return false;
		
		conDAO.update(con);
		
		return true;
	}
	
	public boolean cancel(Consultation con) {
		return true;
	}
	
	public boolean rate(User user, Consultation con) {
		return true;
	}
}

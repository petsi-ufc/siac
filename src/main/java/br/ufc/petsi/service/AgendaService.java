package br.ufc.petsi.service;

import javax.inject.Inject;

import br.ufc.petsi.dao.AgendaDAO;
import br.ufc.petsi.dao.ConsultationDAO;
import br.ufc.petsi.model.Agenda;
import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.User;

public class AgendaService {
	
	@Inject
	private ConsultationDAO conDAO;
	@Inject
	private AgendaDAO agendaDAO;
	
	public boolean schedule(User user, Consultation con) {
		
		if( con == null )
			return false;
		
		Agenda agenda = agendaDAO.getAgendaByUserId(user.getId());
		
		agenda.getConsultations().add(con);
		
		agendaDAO.update(agenda);
		
		return true;
	}
	
}

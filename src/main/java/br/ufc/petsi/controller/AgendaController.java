package br.ufc.petsi.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.swing.JOptionPane;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.ufc.petsi.dao.AgendaDAO;
import br.ufc.petsi.dao.ConsultationDAO;
import br.ufc.petsi.enums.ConsultationState;
import br.ufc.petsi.model.Agenda;
import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Schedule;
import br.ufc.petsi.model.Service;
import br.ufc.petsi.model.User;

import com.google.gson.Gson;

@Controller
@Transactional
public class AgendaController {
	
	@Inject
	private ConsultationDAO conDao; 
	
	@Inject
	private AgendaDAO agendaDao;
	
	@RequestMapping("/getServiceAgenda")
	@ResponseBody
	public String getServiceAgenda(long serviceId){
		Agenda agenda = new Agenda();
		if(serviceId == 2l){
			Service service = new Service();
			service.setId(2L);
			service.setName("Odontologia");
			
			//------------CÓDIGO DE TESTE --------------------\\
			Service service2 = new Service();
			service2.setId(2L);
			service2.setName("Odontologia");
			
			//List<Consultation> list = conDao.getConsultationsByService(service);
				List<Consultation> list = new ArrayList<Consultation>();
				
				Schedule sc = new Schedule();
				sc.setAvailable(true);
				sc.setDateInit(new Date());
				sc.setDateEnd(new Date());
				
				Schedule sc2 = new Schedule();
				sc2.setDateInit(new Date());
				sc2.setDateEnd(new Date());
				
				Consultation cons = new Consultation();
				cons.setService(service);
				cons.setState(ConsultationState.SC);
				cons.setSchedule(sc);
				
				Consultation cons2 = new Consultation();
				cons2.setService(service2);
				cons2.setState(ConsultationState.SC);
				cons2.setSchedule(sc2);
				
				list.add(cons);
				list.add(cons2);
				agenda.setConsultations(list);
			//------------CÓDIGO DE TESTE --------------------\\
		}	
		Gson gson = new Gson();
		String json = gson.toJson(agenda);
		return json;
				
	}
	
	@RequestMapping("/getUserAgenda")
	@ResponseBody
	public String getUserAgenda(long userId){
		User u = new User();
		u.setId(userId);
		
		Agenda ag = agendaDao.getAgendaByUserId(userId);
		
		Gson gson = new Gson();
		String json = "";
		
		if(ag == null){
			ag = new Agenda();
			ag.setConsultations(new ArrayList<Consultation>());
		}
		
		json = gson.toJson(ag);
		return json;
		
	}
	
}

package br.ufc.petsi.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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
		Service service = new Service();
		service.setId(serviceId);
		service.setName("Dentista");
		
		//List<Consultation> list = conDao.getConsultationsByService(service);
		//------------CÓDIGO DE TESTE --------------------\\
			Agenda agenda = new Agenda();
			List<Consultation> list = new ArrayList<Consultation>();
			
			Schedule sc = new Schedule();
			sc.setAvailable(true);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-DD");
			try {
			    sc.setDateInit(df.parse("2015-09-02"));
				sc.setDateEnd(df.parse("2015-09-04"));
			} catch (ParseException ex) {
			    ex.printStackTrace();
			}
			Consultation cons = new Consultation();
			cons.setService(service);
			cons.setState(ConsultationState.SC);
			cons.setSchedule(sc);
			
			list.add(cons);
			agenda.setConsultations(list);
		//------------CÓDIGO DE TESTE --------------------\\
			
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

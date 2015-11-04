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
	
	@RequestMapping("/getConsultationsByDate")
	@ResponseBody
	public String getConsultationsByDate(long serviceId, Date startDay, Date endDay){
		Service service = new Service();
		service.setId(serviceId);
		List<Consultation> list = conDao.getConsultationsByServiceAndDate(service, startDay, endDay);
		return "";
	}
	
	
	@RequestMapping("/getServiceAgenda")
	@ResponseBody
	public String getServiceAgenda(long serviceId){
		Agenda agenda = new Agenda();
		List<Consultation> list = new ArrayList<Consultation>();
		if(serviceId == 2l){
			Service service = new Service();
			service.setId(2L);
			service.setName("Odontologia");
			
			//------------CÓDIGO DE TESTE --------------------\\
			//List<Consultation> list = conDao.getConsultationsByService(service);
			Schedule sc = new Schedule();
			sc.setAvailable(true);
			sc.setDateInit(new Date());
			sc.setDateEnd(new Date());
			
			Consultation cons = new Consultation();
			cons.setService(service);
			cons.setState(ConsultationState.SC);
			cons.setSchedule(sc);
			
			list.add(cons);
			agenda.setConsultations(list);
			//------------CÓDIGO DE TESTE --------------------\\
		}else if(serviceId == 3l){
			Service service2 = new Service();
			service2.setId(3L);
			service2.setName("Psicologia");
			
			Schedule sc2 = new Schedule();
			sc2.setDateInit(new Date());
			sc2.setDateEnd(new Date());
			
			Consultation cons2 = new Consultation();
			cons2.setService(service2);
			cons2.setState(ConsultationState.SC);
			cons2.setSchedule(sc2);

			list.add(cons2);
			agenda.setConsultations(list);
		}
		
		Gson gson = new Gson();
		String json = gson.toJson(agenda);
		return json;
				
	}
	
	@RequestMapping("/getUserAgenda")
	@ResponseBody
	public String getUserAgenda(long userId){
		//TODO
		return "";
		
	}
	
}

package br.ufc.petsi.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
	public String getConsultationsByDate(long serviceId, String startDay){
		Gson gson = new Gson();

		Schedule sc3 = new Schedule();
		sc3.setAvailable(true);

		Date dateInit1;
		Date dateEnd1;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 12);
		cal.set(Calendar.MINUTE, 0);
		dateInit1 = cal.getTime();

		cal.set(Calendar.HOUR_OF_DAY, 13);
		cal.set(Calendar.MINUTE, 30);
		dateEnd1 = cal.getTime();

		sc3.setDateInit(dateInit1);
		sc3.setDateEnd(dateEnd1);

		Schedule sc = new Schedule();
		sc.setAvailable(false);

		Date dateInit2;
		Date dateEnd2;

		cal.set(Calendar.HOUR_OF_DAY, 8);
		cal.set(Calendar.MINUTE, 30);
		dateInit2 = cal.getTime();

		cal.set(Calendar.HOUR_OF_DAY, 9);
		cal.set(Calendar.MINUTE, 0);
		dateEnd2 = cal.getTime();

		sc.setDateInit(dateInit2);
		sc.setDateEnd(dateEnd2);



		String json = "{}";

		//		try {
			//			Date dayInit = format.parse(startDay);
		//			Date dayEnd = format.parse(endDay);

		List<Schedule> list = new ArrayList<Schedule>();//conDao.getConsultationsByServiceAndDate(service, dayInit);
		list.add(sc3);
		list.add(sc);
		json = gson.toJson(list);
		return json;
		//		} catch (ParseException e) {
		//			e.printStackTrace();
		//		}
		//		return json;
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

			Service service3 = new Service();
			service3.setId(3L);
			service3.setName("Psicologia");

			Schedule sc3 = new Schedule();
			try {
				Date dateInit = DateFormat.getDateInstance().parse("26/10/2015");
				Date dateEnd = DateFormat.getDateInstance().parse("26/10/2015");
				sc3.setDateInit(dateInit);
				sc3.setDateEnd(dateEnd);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

			Consultation cons3 = new Consultation();
			cons3.setService(service3);
			cons3.setState(ConsultationState.SC);
			cons3.setSchedule(sc3);

			list.add(cons2);
			list.add(cons3);
			agenda.setConsultations(list);
		}

		Gson gson = new Gson();
		String json = gson.toJson(agenda);
		return json;

	}

	@RequestMapping("/getUserAgenda")
	@ResponseBody
	public String getUserAgenda(String userCpf){
		
		Agenda ag = agendaDao.getAgendaByUserCPF(userCpf);

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

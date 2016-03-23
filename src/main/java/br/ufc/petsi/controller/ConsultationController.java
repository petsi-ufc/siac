package br.ufc.petsi.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.ufc.petsi.dao.ConsultationDAO;
import br.ufc.petsi.enums.ConsultationState;
import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Patient;
import br.ufc.petsi.model.Professional;
import br.ufc.petsi.model.SocialService;
import br.ufc.petsi.service.ConsultationService;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@Controller
@Transactional
public class ConsultationController {
	
	@Inject
	private ConsultationService consultationService;
	
	@Inject
	private ConsultationDAO consDAO;
	
	@RequestMapping("/getConsultationsBySocialService")
	@ResponseBody
	public String getConsultationsBySocialServices(Long socialServiceId){
		SocialService socialService = new SocialService();
		socialService.setId(socialServiceId);
		return consultationService.getConsultationsBySocialService(socialService, consDAO);
	}
	
	@RequestMapping("/getConsultationsByPatient")
	@ResponseBody
	public String getConsultationsByPatient(String cpf){
		Patient p = new Patient();
		p.setCpf(cpf);
		return consultationService.getConsultationsByPatient(p, consDAO);
	}
	
	@RequestMapping("/saveConsultation")
	public String saveConsultation(@RequestParam("json") String json, @RequestParam("cpf") String cpf, @RequestParam("social_service_id") String serviceId){
		System.out.println(json+" - "+cpf+" - "+serviceId);
		Professional pro = new Professional();
		pro.setCpf(cpf);
		SocialService serv = new SocialService();
		serv.setId(Long.parseLong(serviceId));
		
		try{
			JsonParser parser = new JsonParser();
			JsonObject jObject = parser.parse(json).getAsJsonObject(); 
			JsonArray data = jObject.getAsJsonArray("data");
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY HH:mm");
			
			for(int i = 0; i < data.size(); i++){
				JsonObject timeSchedule = data.get(i).getAsJsonObject();
				
				String date = timeSchedule.get("date").getAsString();
				JsonArray timeSchedules = timeSchedule.getAsJsonArray("schedules");
				
				for(int j = 0; j < timeSchedules.size(); j++){
					Consultation consultation = new Consultation();
					consultation.setProfessional(pro);
					consultation.setService(serv);
					consultation.setState(ConsultationState.SC);
					
					JsonElement timeInit = timeSchedules.get(j).getAsJsonObject().get("timeInit");
					JsonElement timeEnd = timeSchedules.get(j).getAsJsonObject().get("timeEnd");
					
					String sDateInit = date+" "+timeInit.getAsString();
					String sDateEnd = date+" "+timeEnd.getAsString();
					
					
					Date dateInit = format.parse(sDateInit);
					Date dateEnd = format.parse(sDateEnd);
					
					System.out.println(dateInit.getHours()+" : "+dateInit.getMinutes()+"  |   "+dateEnd.getHours()+" : "+dateEnd.getMinutes());
					
					consultation.setDateInit(dateInit);
					consultation.setDateEnd(dateEnd);
					
					consultationService.registerConsultation(consultation, consDAO);
					
				}
				
			}
		}catch(Exception e){
			System.out.println("Erro ao transformar o JSON: "+e);
		}
		return "home_professional";
	}
	
	
}

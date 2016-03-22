package br.ufc.petsi.controller;

import java.text.DateFormat;
import java.util.Date;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.ufc.petsi.dao.ConsultationDAO;
import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Patient;
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
	public String saveConsultation(@RequestParam("json") String json){
		System.out.println(json);
		try{
			JsonParser parser = new JsonParser();
			JsonObject jObject = parser.parse(json).getAsJsonObject(); 
			JsonArray data = jObject.getAsJsonArray("data");
			
			for(int i = 0; i < data.size(); i++){
				JsonObject timeSchedule = data.get(i).getAsJsonObject();
				
				JsonElement date = timeSchedule.get("date");
				JsonArray timeSchedules = timeSchedule.getAsJsonArray("schedules");
				
				Consultation consultation = new Consultation();
				
				for(int j = 0; j < timeSchedules.size(); j++){
					JsonElement timeInit = timeSchedules.get(j).getAsJsonObject().get("timeInit");
					JsonElement timeEnd = timeSchedules.get(j).getAsJsonObject().get("timeEnd");
					
				}
				
			}
		}catch(Exception e){
			System.out.println("Erro ao transformar o JSON: "+e);
		}
		return "home_professional";
	}
	
	
}

package br.ufc.petsi.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.ufc.petsi.dao.ConsultationDAO;
import br.ufc.petsi.dao.RatingDAO;
import br.ufc.petsi.enums.ConsultationState;
import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Patient;
import br.ufc.petsi.model.Professional;
import br.ufc.petsi.model.SocialService;
import br.ufc.petsi.service.ConsultationService;
import br.ufc.petsi.service.RatingService;

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
	private RatingService ratingService;
	
	@Inject
	private ConsultationDAO consDAO;
	
	@Inject
	private RatingDAO ratingDAO;
	
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


	@RequestMapping(value = "/cancelConsultation", method = RequestMethod.GET)
	@ResponseBody
	public void cancelConsultation(@RequestParam("id") long id){
		consultationService.cancelConsultationById(id, consDAO);
	}
	
	@RequestMapping("/saveConsultation")
	public String saveConsultation(@RequestParam("json") String json){
		
		SocialService serviceTemp = new SocialService();
		serviceTemp.setId(5l);
		Professional proTemp = new Professional();
		proTemp.setCpf("27240450848");
		proTemp.setSocialService(serviceTemp);
		//CurrentSession.getSession().setAttribute("user", proTemp);
		
		System.out.println("JSON   :"+ json);
		
		Professional pro = proTemp;
		SocialService serv = pro.getSocialService();
		
		try{
			JsonParser parser = new JsonParser();
			JsonObject jObject = parser.parse(json).getAsJsonObject(); 
			JsonArray data = jObject.getAsJsonArray("data");
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			for(int i = 0; i < data.size(); i++){
				JsonObject timeSchedule = data.get(i).getAsJsonObject();
				
				String date = timeSchedule.get("date").getAsString();
				JsonArray timeSchedules = timeSchedule.getAsJsonArray("schedules");
				
				for(int j = 0; j < timeSchedules.size(); j++){
					Consultation consultation = new Consultation();
					consultation.setProfessional(pro);
					consultation.setService(serv);
					consultation.setState(ConsultationState.FR);
					
					JsonElement timeInit = timeSchedules.get(j).getAsJsonObject().get("timeInit");
					JsonElement timeEnd = timeSchedules.get(j).getAsJsonObject().get("timeEnd");
					
					String sDateInit = date+" "+timeInit.getAsString();
					String sDateEnd = date+" "+timeEnd.getAsString();
					
					
					Date dateInit = format.parse(sDateInit);
					Date dateEnd = format.parse(sDateEnd);
					
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
	
	@RequestMapping("/getConsutationsByProfessionalJSON")
	@ResponseBody
	public String getConsultationsByProfessionalJSON(){
		SocialService serviceTemp = new SocialService();
		serviceTemp.setId(5l);
		Professional proTemp = new Professional();
		proTemp.setCpf("27240450848");
		proTemp.setSocialService(serviceTemp);
		
		return consultationService.getConsultationsByProfessionalJSON(proTemp, consDAO);
	}
	
	@RequestMapping("/updateConsultationRating")
	@ResponseBody
	public void updateConsultation(Consultation c){		
		Consultation consultation = consultationService.getConsultationsByIdC(c.getId(), consDAO);
		consultation.setRating(c.getRating());
		consultationService.updateConsultation(consultation, consDAO);
		
		
	}
	
	
	
}

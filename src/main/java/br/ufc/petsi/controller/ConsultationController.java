package br.ufc.petsi.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.ufc.petsi.constants.Constants;
import br.ufc.petsi.dao.ConsultationDAO;
import br.ufc.petsi.dao.ReserveDAO;
import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Patient;
import br.ufc.petsi.model.Professional;
import br.ufc.petsi.model.SocialService;
import br.ufc.petsi.service.ConsultationService;


@Controller
@Transactional
public class ConsultationController {
	
	@Inject
	private ConsultationService consultationService;
	
	@Inject
	private ConsultationDAO consDAO;
	
	@Inject
	private ReserveDAO reserveDAO;
	
	
	
	@RequestMapping("/getConsultationsBySocialService")
	@ResponseBody
	public String getConsultationsBySocialServices(Long socialServiceId, HttpSession session){
		Patient patient = (Patient) session.getAttribute(Constants.USER_SESSION);
		SocialService socialService = new SocialService();
		socialService.setId(socialServiceId);
		
		return consultationService.getConsultationsBySocialService(patient, socialService, consDAO);
	}
	
	@RequestMapping("/getConsultationsByPatient")
	@ResponseBody
	public String getConsultationsByPatient(String cpf){
		Patient p = new Patient();
		p.setCpf(cpf);
		return consultationService.getConsultationsByPatient(p, consDAO, reserveDAO);
	}

	@RequestMapping(value = "/cancelConsultation", method = RequestMethod.GET)
	@ResponseBody
	public String cancelConsultation(@RequestParam("id") long id, @RequestParam("message") String message){
		return consultationService.cancelConsultationById(id, message, consDAO);
	}
	
	@RequestMapping("/saveConsultation")
	@ResponseBody
	public String saveConsultation(@RequestParam("json") String json, HttpSession session){
		Professional proTemp = (Professional) session.getAttribute(Constants.USER_SESSION);
		return consultationService.saveConsultation(proTemp, json, consDAO);
	}
	
	@RequestMapping("/getConsutationsByProfessionalJSON")
	@ResponseBody
	public String getConsultationsByProfessionalJSON(HttpSession session){
		Professional proTemp = (Professional) session.getAttribute(Constants.USER_SESSION);
		return consultationService.getConsultationsByProfessionalJSON(proTemp, consDAO);
	}
	
	@RequestMapping("/updateConsultationRating")
	@ResponseBody
	public void updateConsultation(Consultation c){		
		Consultation consultation = consultationService.getConsultationsById(c.getId(), consDAO);
		consultation.setRating(c.getRating());
		consultationService.updateConsultation(consultation, consDAO);
	}
	
	@RequestMapping("/registerConsultation")
	@ResponseBody
	public String registerConsultation(Consultation cons){
		return consultationService.registerConsultation(cons, consDAO);
	}
	
	
	
}

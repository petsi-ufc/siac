package br.ufc.petsi.controller;



import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.ufc.petsi.constants.Constants;
import br.ufc.petsi.dao.ConsultationDAO;
import br.ufc.petsi.dao.ReserveDAO;
import br.ufc.petsi.dao.UserDAO;
import br.ufc.petsi.enums.ConsultationState;
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
	
	@Inject
	private UserDAO userDAO;
	
	@Secured({"ROLE_PATIENT", "ROLE_PROFESSIONAL"})
	@RequestMapping("/getConsultationsBySocialService")
	@ResponseBody
	public String getConsultationsBySocialServices(Long socialServiceId, HttpSession session){
		Patient patient = (Patient) session.getAttribute(Constants.USER_SESSION);
		SocialService socialService = new SocialService();
		socialService.setId(socialServiceId);
		
		return consultationService.getConsultationsBySocialService(patient, socialService, consDAO);
	}
	
	@Secured("ROLE_PATIENT")
	@RequestMapping("/getConsultationsByPatient")
	@ResponseBody
	public String getConsultationsByPatient(String cpf){
		Patient p = new Patient();
		p.setCpf(cpf);
		return consultationService.getConsultationsByPatient(p, consDAO, reserveDAO);
	}
	
	@Secured("ROLE_PROFESSIONAL")
	@RequestMapping(value = "/cancelConsultation", method = RequestMethod.GET)
	@ResponseBody
	public String cancelConsultation(@RequestParam("id") long id, @RequestParam("message") String message){
		return consultationService.cancelConsultationById(id, message, consDAO);
	}
	
	@Secured("ROLE_PROFESSIONAL")
	@RequestMapping(value = "/saveConsultation", method = RequestMethod.POST)
	@ResponseBody
	public String saveConsultation(@RequestParam("json") String json, HttpSession session){
		Professional proTemp = (Professional) session.getAttribute(Constants.USER_SESSION);
		return consultationService.saveConsultation(proTemp, json, consDAO, ConsultationState.FR);
	}
	
	@Secured("ROLE_PROFESSIONAL")
	@RequestMapping("/saveConsultationNow")
	@ResponseBody
	public String saveConsultationNow(@RequestParam("json") String json, HttpSession session){
		Professional proTemp = (Professional) session.getAttribute(Constants.USER_SESSION);
		return consultationService.saveConsultationNow(proTemp, json, consDAO, userDAO);
	}
	
	
	@Secured("ROLE_PROFESSIONAL")
	@RequestMapping("/getConsutationsByProfessionalJSON")
	@ResponseBody
	public String getConsultationsByProfessionalJSON(HttpSession session){
		Professional proTemp = (Professional) session.getAttribute(Constants.USER_SESSION);
		System.out.println("USER SESSION: "+proTemp);
		return consultationService.getConsultationsByProfessionalJSON(proTemp, consDAO);
	}
	
	@Secured("ROLE_PATIENT")
	@RequestMapping("/updateConsultationRating")
	@ResponseBody
	public String updateConsultationRating(Consultation c, HttpSession session){		
		
		Consultation consultation = consultationService.getConsultationsById(c.getId(), consDAO);
		consultation.setRating(c.getRating());
		Patient patient = (Patient) session.getAttribute(Constants.USER_SESSION);
		return consultationService.updateRating(consultation, consDAO, patient);
	}
	
	@Secured("ROLE_PROFESSIONAL")
	@RequestMapping("/registerConsultation")
	@ResponseBody
	public String registerConsultation(Consultation cons){
		return consultationService.registerConsultation(cons, consDAO);
	}
	
	@Secured("ROLE_PROFESSIONAL")
	@RequestMapping(value="/rescheduleConsultation", method=RequestMethod.POST)
	@ResponseBody
	public String rescheduleConsultation(long idConsultation, Date dateInit, Date dateEnd, String email){
		return consultationService.rescheduleConsultation(idConsultation, dateInit, dateEnd, email ,consDAO);
	}
	
}

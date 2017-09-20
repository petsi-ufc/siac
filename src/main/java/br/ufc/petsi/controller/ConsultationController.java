package br.ufc.petsi.controller;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.jca.cci.CciOperationNotSupportedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import br.ufc.petsi.constants.Constants;
import br.ufc.petsi.dao.ConsultationDAO;
import br.ufc.petsi.dao.FrequencyDAO;
import br.ufc.petsi.dao.ReserveDAO;
import br.ufc.petsi.dao.UserDAO;
import br.ufc.petsi.enums.ConsultationState;
import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Patient;
import br.ufc.petsi.model.Professional;
import br.ufc.petsi.model.Reserve;
import br.ufc.petsi.model.SocialService;
import br.ufc.petsi.service.ConsultationService;
import br.ufc.petsi.service.FrequencyService;


@Controller
@Transactional
public class ConsultationController {
	
	@Inject
	private ConsultationService consultationService;
	
	@Inject
	private FrequencyService freqService;
	
	@Inject
	private ConsultationDAO consDAO;
	
	@Inject
	private ReserveDAO reserveDAO;
	
	@Inject
	private UserDAO userDAO;
	
	@Inject
	private FrequencyDAO freqDAO;
	
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
		Patient p = (Patient) userDAO.getByCpf(cpf, Constants.ROLE_PATIENT);
		return consultationService.getConsultationsByPatient(p, consDAO, reserveDAO);
	}
	
	@Secured("ROLE_PROFESSIONAL")
	@RequestMapping(value = "/cancelConsultation", method = RequestMethod.GET)
	@ResponseBody
	public String cancelConsultation(@RequestParam("id") long id, @RequestParam("message") String message){
		return consultationService.cancelConsultationById(id, message, consDAO);
	}
	
	@Secured("ROLE_PROFESSIONAL")
	@RequestMapping(value = "/cancelAllConsultation", method = RequestMethod.POST)
	@ResponseBody
	public String cancelConsultation(@RequestParam("json") String json){
		System.out.println(json);
		return consultationService.cancelAllConsultationById(json, consDAO);
	}
	
	@Secured("ROLE_PROFESSIONAL")
	@RequestMapping(value = "/saveConsultation", method = RequestMethod.POST)
	@ResponseBody
	public String saveConsultation(@RequestParam("json") String json, HttpSession session){
		Professional proTemp = (Professional) session.getAttribute(Constants.USER_SESSION);
		System.out.println("JSON Chegando =>" + json);
		System.out.println("[Salvar Consulta] =>" + json);
		System.out.println("[CONTROLLER SAVE CONSULTATION]:"+json);
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
	@RequestMapping("/checkSchedules")
	@ResponseBody
	public String checkSchedules(@RequestParam("json") String json, HttpSession session){
		Professional proTemp = (Professional) session.getAttribute(Constants.USER_SESSION);
		System.out.println(json);
		return consultationService.checkSchedules(proTemp, json, consDAO);
	}
	
	@Secured("ROLE_PROFESSIONAL")
	@RequestMapping("/getConsutationsByProfessionalJSON")
	@ResponseBody
	public String getConsultationsByProfessionalJSON(@RequestParam("dateInit") String dateInit, @RequestParam("dateEnd") String dateEnd, HttpSession session){
		Professional proTemp = (Professional) session.getAttribute(Constants.USER_SESSION);
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("[DATE INIT]: "+dateInit);
		System.out.println("[DATE END]: "+dateEnd);
		Date init = null;
		Date end = null;
		try{
			init = (Date) formatter.parse(dateInit);
			end = (Date) formatter.parse(dateEnd);
		}catch (Exception e) {
			System.out.println(e);
			return "[]";
		}
		long start = System.currentTimeMillis();
		String cons = consultationService.getConsultationsByProfessionalJSON(proTemp, consDAO, init, end);
		long finish = System.currentTimeMillis() - start;
		System.out.println("[TEMPO DE EXECUÇÃO DA CONSULTA]: "+finish+" milisegundos");
		return cons;
	}
	
	@Secured("ROLE_PATIENT")
	@RequestMapping("/updateConsultationRating")
	@ResponseBody
	public String updateConsultationRating(@RequestParam("json") String json, HttpSession session){		
		Patient patient = (Patient) session.getAttribute(Constants.USER_SESSION);
		patient = (Patient) userDAO.getByCpf(patient.getCpf(), Constants.ROLE_PATIENT);
		return consultationService.updateRating(json, consDAO, patient);
	}
	
	@Secured("ROLE_PROFESSIONAL")
	@RequestMapping("/registerConsultation")
	@ResponseBody
	public String registerConsultation(Consultation cons){
		return consultationService.registerConsultation(cons, consDAO);
	}
	
	@Secured("ROLE_PROFESSIONAL")
	@RequestMapping("/registerConsultationAndFrequency")
	@ResponseBody
	public String registerConsultationAndFrequency(@RequestParam("json") String json){
		System.out.println(json);
		return consultationService.registerConsultationAndFrequency(json, consDAO, freqService, freqDAO);
	}
	
	@Secured("ROLE_PROFESSIONAL")
	@RequestMapping(value="/rescheduleConsultation", method=RequestMethod.POST)
	@ResponseBody
	public String rescheduleConsultation(long idConsultation, Date dateInit, Date dateEnd, String email){
		return consultationService.rescheduleConsultation(idConsultation, dateInit, dateEnd, email ,consDAO);
	}
	
	@Secured("ROLE_PROFESSIONAL")
	@RequestMapping("/registerComment")
	@ResponseBody
	public String registerComment(@RequestParam("json") String json){
		return consultationService.registerComment(json, consDAO);
	}
	@Secured("ROLE_PROFESSIONAL")
	@RequestMapping("/getReserveByidConsultation")
	@ResponseBody
	public String getReserveByidConsultation(@RequestParam("id") long id){
		Consultation consulta = new Consultation();
		consulta.setId(id);
		System.out.println("ID: Consulta " + consulta.getId());
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try{
			json = mapper.writeValueAsString(reserveDAO.getActiveReservesByConsultation(consulta));
		}catch (Exception e) {
			System.out.println(e.toString());
		}
		System.out.println(json);
		return json;
		
	}
	
	
}

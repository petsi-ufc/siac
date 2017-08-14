package br.ufc.petsi.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.ufc.petsi.constants.Constants;
import br.ufc.petsi.dao.ConsultationDAO;
import br.ufc.petsi.dao.GroupDAO;
import br.ufc.petsi.dao.RatingDAO;
import br.ufc.petsi.dao.ReserveDAO;
import br.ufc.petsi.dao.UserDAO;
import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Patient;
import br.ufc.petsi.model.Rating;
import br.ufc.petsi.model.Reserve;
import br.ufc.petsi.model.SocialService;
import br.ufc.petsi.service.ConsultationService;
import br.ufc.petsi.service.GroupService;
import br.ufc.petsi.service.RatingService;

@Controller
@Transactional
public class PatientController {

	@Inject
	private ConsultationService consService;

	@Inject
	private ConsultationDAO consDAO;

	@Inject
	private RatingService ratingService;

	@Inject
	private RatingDAO ratingDAO;

	@Inject
	private ReserveDAO reserveDAO;
	
	@Inject
	private UserDAO userDAO;
	
	@Inject
	private GroupService groupService;
	
	@Inject
	private GroupDAO groupDAO;

	@Secured("ROLE_PATIENT")
	@RequestMapping("/getMyConsultations")
	@ResponseBody
	public String getConsultationsByPatient(HttpSession session){

		Patient patient = (Patient) session.getAttribute(Constants.USER_SESSION);
		return consService.getConsultationsByPatient(patient, consDAO, reserveDAO);
	}

	@Secured("ROLE_PATIENT")
	@RequestMapping("/getConsultationById")
	@ResponseBody
	public String getConsultationById(long id, HttpSession session){

		Patient patient = (Patient) session.getAttribute(Constants.USER_SESSION);
		return consService.getConsultationsById(patient, id, consDAO);
		
	}

	@Secured("ROLE_PATIENT")
	@RequestMapping("/saveRating")
	@ResponseBody
	public void saveRating(Rating rating){
		ratingService.saveRating(rating, ratingDAO);

	}

	@Secured("ROLE_PATIENT")
	@RequestMapping("/getConsultationBySocialService")
	@ResponseBody
	public String getConsultationsBySocialService(SocialService socialService, HttpSession session){

		Patient patient = (Patient) session.getAttribute(Constants.USER_SESSION);

		return this.consService.getConsultationsBySocialService(patient, socialService, consDAO);

	}

	@Secured("ROLE_PATIENT")
	@RequestMapping("/scheduleConsultation")
	@ResponseBody
	public String scheduleConsultation(Consultation consultation, HttpSession session){
		Consultation consultation2 = this.consService.getConsultationsById(consultation.getId(), this.consDAO);
		consultation2.setReason(consultation.getReason());
		Patient patient = (Patient) session.getAttribute(Constants.USER_SESSION);
		Patient patient2 = (Patient) userDAO.getByCpf(patient.getCpf(), Constants.ROLE_PATIENT);
		return this.consService.updateConsultation(consultation2, this.consDAO, patient2);

	}

	@Secured("ROLE_PATIENT")
	@RequestMapping("/showRating")
	@ResponseBody
	public String getRatingByConsultation(Consultation consultation, HttpSession session){
		Patient patient = (Patient) session.getAttribute(Constants.USER_SESSION);
		return consService.getRatingByConsultation(consultation, patient, consDAO);
	}

	@Secured("ROLE_PATIENT")
	@RequestMapping("/cancelConsultationPatient")
	@ResponseBody
	public String cancelConsultation(Consultation consultation){
		Consultation consultation2 = this.consService.getConsultationsById(consultation.getId(), consDAO);
		consultation2.setReasonCancel(consultation.getReasonCancel());
		return consService.cancelConsultation(consultation2, consDAO, reserveDAO);
	}

	@Secured("ROLE_PATIENT")
	@RequestMapping("/reserveConsultation")
	@ResponseBody
	public String reserveConsultation(Consultation consultation, HttpSession session){
		Consultation consultation2 = this.consService.getConsultationsById(consultation.getId(), this.consDAO);
		Patient patient = (Patient) session.getAttribute(Constants.USER_SESSION);
		return consService.reserveConsultation(patient, consultation2, reserveDAO);
	}

	@Secured("ROLE_PATIENT")
	@RequestMapping("/cancelReserve")
	@ResponseBody
	public String cancelReserve(@RequestParam("id") long id){

		Reserve reserve = new Reserve();
		reserve.setId(id);
		Reserve reserve2 = this.reserveDAO.getReserveById(reserve);

		return consService.cancelReserve(reserve2, reserveDAO);

	}
	
	@Secured("ROLE_PATIENT")
	@RequestMapping("/getMyGroups")
	@ResponseBody
	public String getMyGroups(HttpSession session){
		Patient patient = (Patient) session.getAttribute(Constants.USER_SESSION);
		System.out.println("Groups - Patient: " + patient.getName());
		return groupService.getGroupsOfPatient(patient, groupDAO);
	}
	
	@Secured("ROLE_PATIENT")
	@RequestMapping("/getGroupsFree")
	@ResponseBody
	public String getGroupsFree(){
		return groupService.getGroupsFree(groupDAO);
	}	

}	
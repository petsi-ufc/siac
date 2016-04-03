package br.ufc.petsi.controller;



import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.ufc.petsi.dao.ConsultationDAO;
import br.ufc.petsi.dao.RatingDAO;
import br.ufc.petsi.enums.ConsultationState;
import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Patient;
import br.ufc.petsi.model.Rating;
import br.ufc.petsi.model.SocialService;
import br.ufc.petsi.service.ConsultationService;
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
		
		@RequestMapping("/getMyConsultations")
		@ResponseBody
		public String getConsultationsByPatient(){
			
			Patient patient = new Patient();
			patient.setCpf("12345678900");
			patient.setEmail("paciente@siac.com");
			patient.setName("Paciente Coisa de Coisado");
			
			return consService.getConsultationsByPatient(patient, consDAO);
		}
		
		@RequestMapping("/getConsultationById")
		@ResponseBody
		public String getConsultationById(long id){
			
			Patient patient = new Patient();
			patient.setCpf("12345678900");
			patient.setEmail("paciente@siac.com");
			patient.setName("Paciente Coisa de Coisado");
			
			return consService.getConsultationsById(patient, id, consDAO);
		}
		
		@RequestMapping("/saveRating")
		@ResponseBody
		public void saveRating(Rating rating){
			ratingService.saveRating(rating, ratingDAO);
			
		}
		
		@RequestMapping("/getConsultationBySocialService")
		@ResponseBody
		public String getConsultationsBySocialService(SocialService socialService){
			
			Patient patient = new Patient();
			patient.setCpf("12345678900");
			patient.setEmail("paciente@siac.com");
			patient.setName("Paciente Coisa de Coisado");
			
			return this.consService.getConsultationsBySocialService(patient, socialService, consDAO);
			
		}
		
		@RequestMapping("/scheduleConsultation")
		@ResponseBody
		public String scheduleConsultation(Consultation consultation){
			Consultation consultation2 = this.consService.getConsultationsByIdC(consultation.getId(), this.consDAO);
			
			Patient patient = new Patient();
			patient.setCpf("05771147354");
			patient.setEmail("pacientao@siac.com");
			patient.setName("Pacientão Diferentão");
			
			consultation2.setPatient(patient);
			consultation2.setState(ConsultationState.SC);
			
			this.consService.updateConsultation(consultation2, this.consDAO);
						
			return "{'id':"+consultation2.getId()+"}";
		}
	
		@RequestMapping("/showRating")
		@ResponseBody
		public String getRatingByConsultation(Consultation consultation){
			return consService.getRatingByConsultation(consultation, consDAO);
		}
		
		@RequestMapping("/cancelConsultation")
		@ResponseBody
		public String cancelConsultation(Consultation consultation){
			
			Consultation consultation2 = this.consService.getConsultationsByIdC(consultation.getId(), consDAO);
			
			return consService.cancelConsultation(consultation2, consDAO);
		}
		
		@RequestMapping("/reserveConsultation")
		@ResponseBody
		public String reserveConsultation(Consultation consultation){	
			
			Patient patient = new Patient();
			patient.setCpf("12345678900");
			patient.setEmail("paciente@siac.com");
			patient.setName("Paciente Coisa de Coisado");
			
			return "";
		}
		
		
}	
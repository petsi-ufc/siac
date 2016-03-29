package br.ufc.petsi.controller;



import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.ufc.petsi.dao.ConsultationDAO;
import br.ufc.petsi.dao.RatingDAO;
import br.ufc.petsi.model.Patient;
import br.ufc.petsi.model.Rating;
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
		
		
		
		
		@RequestMapping("/search/patient/consultations")
		@ResponseBody
		public String searchPatientConsultations(Patient p){
			System.out.println(p.getCpf());
			
			return consService.getConsultationsByPatient(p, consDAO);
		}
		
		@RequestMapping("/search/patient/scheduleday")
		@ResponseBody
		public String searchPatientScheduleDay(long id){
			System.out.println("PatientController");
			return consService.getConsultationsById(id, consDAO);
		}
		
		@RequestMapping("/saveRating")
		@ResponseBody
		public void saveRating(Rating rating){
			ratingService.saveRating(rating, ratingDAO);
			
		}
		
		

}
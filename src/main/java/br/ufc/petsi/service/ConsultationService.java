package br.ufc.petsi.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import br.ufc.petsi.dao.ConsultationDAO;
import br.ufc.petsi.event.Event;
import br.ufc.petsi.event.EventsDay;
import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Patient;
import br.ufc.petsi.model.Professional;
import br.ufc.petsi.model.SocialService;
import br.ufc.petsi.util.ConsultationExclusionStrategy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



@Named
public class ConsultationService {

	public void registerConsultation(Consultation con, ConsultationDAO consDAO){
		consDAO.save(con);
	}

	public String getConsultationsByPatient(Patient p, ConsultationDAO consDAO){
		String json;
		Gson gson = new Gson();

		List<Consultation> consultations = consDAO.getConsultationsByPatient(p);

		List<Event> events = new ArrayList<Event>();

		for(Consultation c : consultations){

			Event event = new Event(c);
			events.add(event);
			System.out.println(event);


		}

		json = gson.toJson(events);

		System.out.println(json);

		return json;
	}

	public String getConsultationsBySocialService(SocialService socialService, ConsultationDAO consDAO){
		String json = "";
		Gson gson = new Gson();

		List<Consultation> consultations = consDAO.getConsultationsBySocialService(socialService);
		json = gson.toJson(consultations);

		return json;
	}
	
	public String getConsultationsByProfessional(Professional professional, ConsultationDAO consDAO){
		List<Consultation> cons = consDAO.getConsultationByProfessional(professional);
		
		String json = "";
		try{
			Gson gson = new GsonBuilder().setExclusionStrategies(new ConsultationExclusionStrategy()).serializeNulls().create();
			json = gson.toJson(cons);
			
		}catch(Exception e){
			System.out.println("Error at getConsultationByProfessional Service: ");
		}
		System.out.println(json);
		return json;
	}
	
	public String getConsultationsById(long id, ConsultationDAO consDAO){
		String json = "";
		Gson gson = new Gson();
		
		Consultation c = consDAO.getConsultationById(id);		
	
			EventsDay eventsDay = new EventsDay(c);

		json = gson.toJson(eventsDay);
		return json;
	}
}

package br.ufc.petsi.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import br.ufc.petsi.dao.ConsultationDAO;
import br.ufc.petsi.enums.ConsultationState;
import br.ufc.petsi.event.Event;
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

	public String getConsultationsByPatient(Patient patient, ConsultationDAO consDAO){
		String json;
		Gson gson = new Gson();

		List<Consultation> consultations = consDAO.getConsultationsByPatient(patient);

		List<Event> events = new ArrayList<Event>();

		for(Consultation c : consultations){

			Event event = new Event(patient, c);
			events.add(event);
			
		}

		json = gson.toJson(events);

		return json;
	}

	public String getConsultationsBySocialService(Patient patient, SocialService socialService, ConsultationDAO consDAO){
		String json = "";
		Gson gson = new Gson();

		List<Consultation> consultations = consDAO.getConsultationsBySocialService(socialService);
		
		List<Event> events = new ArrayList<Event>();

		for(Consultation c : consultations){
			if(c.getState().name() == ConsultationState.RD.name()){
				if(c.getPatient().getCpf().equals(patient.getCpf())){
					Event event = new Event(patient, c);
					events.add(event);
				}				
			
			}else{
				Event event = new Event(patient, c);
				events.add(event);
			}
		}
		
		json = gson.toJson(events);

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
	
	public String getConsultationsById(Patient patient, long id, ConsultationDAO consDAO){
		String json = "";
		Gson gson = new Gson();
		
		Consultation c = consDAO.getConsultationById(id);		
	
			Event event = new Event(patient, c);

		json = gson.toJson(event);
		return json;
	}
	
	
	public Consultation getConsultationsByIdC(long id, ConsultationDAO consDAO){
		Consultation c = consDAO.getConsultationById(id);		
		return c;
	}
	
	
	public void updateConsultation(Consultation consultation, ConsultationDAO consDAO){
		consDAO.update(consultation);
	}
	
	
	public String getRatingByConsultation(Consultation consultation, ConsultationDAO consultationDAO){
		String json = "";
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		json = gson.toJson(consultationDAO.getRatingByIdConsultation(consultation.getId()));
		return json;
	
	}
	
	public String cancelConsultation(Consultation consultation, ConsultationDAO consultationDAO){
		consultation.setState(ConsultationState.FR);
		consultation.setPatient(null);
		consultationDAO.update(consultation);
		return "{'msg':Consulta cancelada com sucesso}";
	}
}

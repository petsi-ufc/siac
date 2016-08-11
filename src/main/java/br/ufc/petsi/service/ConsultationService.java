package br.ufc.petsi.service;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.ufc.petsi.dao.ConsultationDAO;
import br.ufc.petsi.dao.ReserveDAO;
import br.ufc.petsi.enums.ConsultationState;
import br.ufc.petsi.event.Event;
import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Patient;
import br.ufc.petsi.model.Professional;
import br.ufc.petsi.model.Reserve;
import br.ufc.petsi.model.SocialService;
import br.ufc.petsi.util.ConsultationExclusionStrategy;
import br.ufc.petsi.util.Response;


@Named
public class ConsultationService {

	@Inject
	private EmailService emailService;


	public String saveConsultation(Professional proTemp, String json, ConsultationDAO consDAO){
		Gson gson = new Gson();
		Response response = new Response();
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
					consultation.setProfessional(proTemp);
					consultation.setService(proTemp.getSocialService());
					consultation.setState(ConsultationState.FR);

					long consultationId = 0;

					if(!timeSchedules.get(j).getAsJsonObject().get("id").isJsonNull()){
						consultationId = timeSchedules.get(j).getAsJsonObject().get("id").getAsLong();
					}

					JsonElement timeInit = timeSchedules.get(j).getAsJsonObject().get("timeInit");
					JsonElement timeEnd = timeSchedules.get(j).getAsJsonObject().get("timeEnd");

					String sDateInit = date+" "+timeInit.getAsString();
					String sDateEnd = date+" "+timeEnd.getAsString();


					Date dateInit = format.parse(sDateInit);
					Date dateEnd = format.parse(sDateEnd);

					if(dateInit.after(dateEnd)){
						response.setCode(Response.ERROR);
						response.setMessage("Ops, existe uma consulta com horário de inicio superior ao de fim");
						return gson.toJson(response);
					}

					consultation.setDateInit(dateInit);
					consultation.setDateEnd(dateEnd);

					consultation.setId(consultationId);

					consDAO.save(consultation);
				}

			}
		}catch(Exception e){
			System.out.println("Erro ao transformar o JSON: "+e);
			response.setCode(Response.ERROR);
			response.setMessage("Ops, não foi possível cadastrar a(s) consulta(s)");
			return gson.toJson(response);
		}

		response.setCode(Response.SUCCESS);
		response.setMessage("Consulta(s) cadastrada(s) com sucesso");
		return gson.toJson(response);

	}

	public String registerConsultation(Consultation con, ConsultationDAO consDAO){
		Consultation oldConsultation = consDAO.getConsultationById(con.getId());
		Gson gson = new Gson();
		Response res = new Response();
		if(oldConsultation.getPatient() == null){
			res.setCode(Response.ERROR);
			res.setMessage("Ops, não é possível registrar uma consulta quando a mesma não possui nenhum paciente!");
			return gson.toJson(res);
		}
		Date today = new Date();
		if(today.before(oldConsultation.getDateEnd())){
			res.setCode(Response.ERROR);
			res.setMessage("Ops, não é possível registrar uma consulta que ainda não aconteceu!");
			return gson.toJson(res);
		}
		consDAO.registerConsultation(con);
		res.setCode(Response.SUCCESS);
		res.setMessage("Consulta registrada com sucesso!");
		return gson.toJson(res);
	}

	public String getConsultationsByPatient(Patient patient, ConsultationDAO consDAO, ReserveDAO reserveDAO){
		String json;
		Gson gson = new Gson();

		List<Consultation> consultations = consDAO.getConsultationsByPatient(patient);

		List<Reserve> reserves = reserveDAO.getActiveReservesByPatient(patient);

		List<Event> events = new ArrayList<Event>();

		for(Consultation c : consultations){

			Event event = new Event(patient, c);
			events.add(event);

		}

		for(Reserve reserve: reserves){

			Consultation consultation = reserve.getConsultation();
			Event event = new Event(consultation.getPatient(), consultation);
			event.setState("Reservado");
			event.setColor("#D9D919");
			event.setTextColor("white");
			event.setIdReserve(reserve.getId());
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

	public String getConsultationsByProfessionalJSON(Professional professional, ConsultationDAO consDAO){
		List<Consultation> cons = consDAO.getConsultationByProfessional(professional);
		String json = "";
		try{
			Gson gson = new GsonBuilder().setExclusionStrategies(new ConsultationExclusionStrategy()).serializeNulls().create();
			json = gson.toJson(cons);

		}catch(Exception e){
			System.out.println("Error at getConsultationByProfessional Service: ");
		}
		return json;
	}

	public List<Consultation> getConsultationsByProfessional(Professional professional, ConsultationDAO consDAO){
		List<Consultation> cons = consDAO.getConsultationByProfessional(professional);
		return cons;
	}

	public String getConsultationsById(Patient patient, long id, ConsultationDAO consDAO){
		String json = "";
		Gson gson = new Gson();

		Consultation c = consDAO.getConsultationById(id);		

		Event event = new Event(patient, c);

		json = gson.toJson(event);
		return json;
	}

	public Consultation getConsultationsById(long id, ConsultationDAO consDAO){
		Consultation c = consDAO.getConsultationById(id);		
		return c;
	}

	public String updateConsultation(Consultation consultation, ConsultationDAO consDAO, Patient patient){
		
		Gson gson = new Gson();
		Response response = new Response();
		Date date = new Date();
		
		try{
			if(consultation.getState().equals(ConsultationState.FR) && consultation.getDateInit().after(date)){
				consultation.setState(ConsultationState.SC);
				consultation.setPatient(patient);
				response.setCode(Response.SUCCESS);
				response.setMessage("Consulta agendada com sucesso");
				consDAO.update(consultation);
			} 
			else{
				response.setCode(Response.ERROR);
				response.setMessage("A consulta não está mais disponível");
				
			}
			
			return gson.toJson(response);
			
		}catch(Exception e){
			response.setCode(Response.ERROR);
			response.setMessage("Ops, não foi possível agendar a consulta");
			System.out.println("Error at registerConsultation by id: "+e);
			return gson.toJson(response);
		}
		
		
	}

	public String cancelConsultationById(long id, String message, ConsultationDAO consDAO){

		Gson gson = new Gson();
		Response response = new Response();

		if( message == "" || message == null){
			message = "Informanmos que sua consulta foi cancelada!";
		}

		try{
			Consultation oldCons = getConsultationsById(id, consDAO);
			if(oldCons != null){
				Date today = new Date();

				if(oldCons.getDateEnd().before(today)){
					response.setCode(Response.ERROR);
					response.setMessage("Ops, não é possível cancelar consultas anteriores a data de hoje");
				}else{
					consDAO.cancelConsultation(oldCons);
					response.setCode(Response.SUCCESS);
					response.setMessage("Consulta cancelada com sucesso!");

					if(oldCons.getPatient() != null){
						emailService.sendConsultationCancelEmail(oldCons, message);
					}
				}
				return gson.toJson(response);
			}
			response.setCode(Response.ERROR);
			response.setMessage("Ops, não foi possível cancelar a consulta pois a mesma não existe");
			return gson.toJson(response);
		}catch(Exception e){
			response.setCode(Response.ERROR);
			response.setMessage("Ops, não foi possível cancelar a consulta");
			System.out.println("Error at cancelConsultation by id: "+e);
			return gson.toJson(response);
		}
	}

	public String getRatingByConsultation(Consultation consultation, ConsultationDAO consultationDAO){
		String json = "";
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		json = gson.toJson(consultationDAO.getRatingByIdConsultation(consultation.getId()));
		return json;

	}

	public String cancelConsultation(Consultation consultation, ConsultationDAO consultationDAO, ReserveDAO reserveDAO){

		List<Reserve> reserves = reserveDAO.getActiveReservesByConsultation(consultation);

		if(reserves.isEmpty()){
			consultation.setState(ConsultationState.FR);
			consultation.setPatient(null);
			consultationDAO.update(consultation);

		}else{
			Collections.sort(reserves);
			Reserve reserve = reserves.get(0);
			reserve.setActive(false);
			reserveDAO.update(reserve);
			consultation.setPatient(reserve.getPatient());
			consultationDAO.update(consultation);
		}

		return "{'msg':Consulta cancelada com sucesso}";
	}

	public String reserveConsultation(Patient patient, Consultation consultation, ReserveDAO reserveDAO){

		Reserve reserve = new Reserve();
		reserve.setPatient(patient);
		reserve.setConsultation(consultation);
		reserve.setDate(new Date());
		reserve.setActive(true);

		reserveDAO.save(reserve);

		return "{'msg':Consulta reservada com sucesso}";
	}

	public String cancelReserve(Reserve reserve, ReserveDAO reserveDAO){
		reserve.setActive(false);
		reserveDAO.update(reserve);
		return "{'msg': Reserva cancelada com sucesso}";
	}
	
	public String updateRating(Consultation consultation, ConsultationDAO consultationDAO, Patient patient){
				
			Gson gson = new Gson();
			Response response = new Response();
		System.out.println("Estado da consulta :    " + consultation.getState());
		System.out.println("CPF paciente da sessão:    "+ patient.getCpf());
			try{
				if(consultation.getState().equals(ConsultationState.RD) && consultation.getPatient().getCpf().equals(patient.getCpf())){
					response.setCode(Response.SUCCESS);
					response.setMessage("Consulta avaliada com sucesso");
					consultationDAO.update(consultation);
				} 
				else{
					response.setCode(Response.ERROR);
					response.setMessage("A consulta não pode ser avaliada");
					
				}
				return gson.toJson(response);
				
				
			}catch(Exception e){
				response.setCode(Response.ERROR);
				response.setMessage("Ops, não foi possível avaliar a consulta");
				System.out.println("Error at ratingConsultation by id: "+e);
				return gson.toJson(response);
			}
			
		}

}

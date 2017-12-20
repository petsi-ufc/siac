package br.ufc.petsi.service;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.JOptionPane;

import org.apache.poi.util.SystemOutLogger;
import org.eclipse.jdt.internal.compiler.impl.Constant;

import br.ufc.petsi.constants.Constants;
import br.ufc.petsi.dao.ConsultationDAO;
import br.ufc.petsi.dao.FrequencyDAO;
import br.ufc.petsi.dao.GroupDAO;
import br.ufc.petsi.dao.ReserveDAO;
import br.ufc.petsi.dao.UserDAO;
import br.ufc.petsi.dao.hibernate.HBUserDAO;
import br.ufc.petsi.enums.ConsultationState;
//import br.ufc.petsi.enums.QueryTemplate;
import br.ufc.petsi.event.Event;
import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.ConsultationList;
import br.ufc.petsi.model.Group;
import br.ufc.petsi.model.Patient;
import br.ufc.petsi.model.Professional;
import br.ufc.petsi.model.Rating;
import br.ufc.petsi.model.Reserve;
import br.ufc.petsi.model.Scheduler;
import br.ufc.petsi.model.SocialService;
import br.ufc.petsi.util.ConsultationExclusionStrategy;
import br.ufc.petsi.util.DateDeserializer;
import br.ufc.petsi.util.Response;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@Named
public class ConsultationService {

	@Inject
	private EmailService emailService;
	
	@Inject
	private GroupDAO gpDAO;
	
	@Inject 
	private UserDAO udao;


	public String saveConsultation(Professional proTemp, String json, ConsultationDAO consDAO, ConsultationState state){
		
		GsonBuilder gsonb = new GsonBuilder();
		DateDeserializer ds = new DateDeserializer();
		gsonb.registerTypeAdapter(Date.class, ds);
		Gson gson = gsonb.create();

		Response response = new Response();
		
		try{
			//Scheduler scheduler = mapper.readValue(json, Scheduler.class);
			Json scheduler = gson.fromJson(json, Json.class);
			
			for (Consultation consultation : scheduler.json.getSchedule()) {
			//for (Consultation consultation : scheduler.getSchedule()) {
				if(!consultation.getState().equals(ConsultationState.FR)){
					System.out.println("Usuario LPA:=> " + consultation.getPatient());
					System.out.println("Data:=> " + consultation.getDateInit());
					if(consultation.getPatient() != null){
						Patient patient = (Patient)udao.getByCpf(consultation.getPatient().getCpf(), Constants.ROLE_PATIENT);
						if(patient == null){
							udao.save(consultation.getPatient());
							patient = (Patient)udao.getByCpf(consultation.getPatient().getCpf(), Constants.ROLE_PATIENT);
							consultation.setPatient(patient);
						}
						System.out.println("Paciente do Banco" + patient.toString());
					}else if(consultation.getGroup() != null){
						consultation.getGroup().setPatients(gpDAO.getPatients(consultation.getGroup()));
					}
				}
				consultation.setProfessional(proTemp);
				consultation.setService(proTemp.getSocialService());
				//consultation.setState(state);
				System.out.println(consultation.getDateInit() + "; " + consultation.getDateEnd());
				if(consultation.getDateInit().after(consultation.getDateEnd())){
					response.setCode(Response.ERROR);
					response.setMessage("Ops, existe uma consulta com horário de inicio superior ao de fim");
					return gson.toJson(response);
				}
				
				consDAO.save(consultation);
				if(((consultation.getPatient() != null && consultation.getPatient().getEmail() != null) || consultation.getGroup() != null) ){
					String date = consultation.getDateInit().getDate()+"/"+consultation.getDateInit().getMonth()+"/"+(consultation.getDateInit().getYear()+1900);
					String hour = consultation.getDateInit().getHours()+":"+consultation.getDateInit().getMinutes();
					emailService.sendEmail(consultation, "A sua consulta com o(a) "+consultation.getService().getName()+" foi marcada para o dia "+date+" às "+hour);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			response.setCode(Response.ERROR);
			response.setMessage("Ops, não foi possível cadastrar a(s) consulta(s)");
			return gson.toJson(response);
		}

		response.setCode(Response.SUCCESS);
		response.setMessage("Consulta(s) cadastrada(s) com sucesso");
		return gson.toJson(response);

	}
	
	public String saveConsultationNow(Professional proTemp, String json, ConsultationDAO consDAO, UserDAO userDAO){
		GsonBuilder gsonb = new GsonBuilder();
		DateDeserializer ds = new DateDeserializer();
		gsonb.registerTypeAdapter(Date.class, ds);
		Gson gson = gsonb.create();
		Response response = new Response();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
		Consultation con = gson.fromJson(json, Consultation.class);
		
		if(con.getPatient() != null){
			con.setPatient((Patient)udao.getByCpf(con.getPatient().getCpf(), Constants.ROLE_PATIENT));
		}
		con.setProfessional(proTemp);
		con.setService(proTemp.getSocialService());
		//con.setState(ConsultationState.NO);
		
		if(con.getDateInit().after(con.getDateEnd())){
			response.setCode(Response.ERROR);
			response.setMessage("Ops, existe uma consulta com horário de inicio superior ao de fim");
			return gson.toJson(response);
		}
		
		try{
			List<Consultation> cons = consDAO.getConsultationByProfessional(proTemp, null, null);
			for(Consultation c: cons){
				
				if(formatter.format(c.getDateInit()).equals(formatter.format(con.getDateInit())) && formatter.format(c.getDateEnd()).equals(formatter.format(con.getDateEnd())) && (c.getState().equals(ConsultationState.SC) || c.getState().equals(ConsultationState.RV))){
					response.setCode(Response.ERROR);
					response.setMessage("Ops, não foi possível agendar a consulta, pois a mesma já está reservada/agendada");
					return gson.toJson(response);
				}
			}
			consDAO.save(con);
		}catch (Exception e) {
			e.printStackTrace();
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
		
		if(oldConsultation.getPatient() == null && oldConsultation.getGroup() == null){
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
	
	public String registerConsultationAndFrequency(String json, ConsultationDAO consDAO, FrequencyService freqServ, FrequencyDAO freqDAO){
		Gson gson = new Gson();
		Response res = new Response();
		try{
			JsonParser parser = new JsonParser();
			JsonObject obj = parser.parse(json).getAsJsonObject();
			Long id = obj.get("id").getAsLong();
			String comment = obj.get("comment").getAsString();
			Consultation consultation = new Consultation();
			consultation.setId(id);
			consultation.setComment(comment);
			
			Consultation oldConsultation = consDAO.getConsultationById(id);
			
			if(oldConsultation.getGroup() == null){
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
			
			consDAO.registerConsultation(consultation);
			freqServ.registerFrequency(String.valueOf(obj.get("frequencyList").getAsJsonArray()), freqDAO);
			res.setCode(Response.SUCCESS);
			res.setMessage("Consulta registrada com sucesso!");
		}catch (Exception e) {
			System.out.println(e);
			res.setCode(Response.ERROR);
			res.setMessage("Não foi possível registrar sua consulta!");
			return gson.toJson(res);
		}
		return gson.toJson(res);
	}
	
	public String getConsultationsByPatient(Patient patient, ConsultationDAO consDAO, ReserveDAO reserveDAO){
		String json;
		Gson gson = new Gson();

		List<Consultation> consultations = consDAO.getConsultationsByPatient(patient);

		List<Reserve> reserves = reserveDAO.getActiveReservesByPatient(patient);
		
		for(Group g: gpDAO.getGroupsByPatient(patient)){
			consultations.addAll(consDAO.getConsultationByGroup(g));
			reserves.addAll(reserveDAO.getActiveReservesByGroup(g));
		}
		

		List<Event> events = new ArrayList<Event>();

		if(consultations != null){
			if(consultations.size() > 0){
				for(Consultation c : consultations){
					Event event = new Event(patient, c);
					events.add(event);
				}
			}
		}

		for(Reserve reserve: reserves){

			Consultation consultation = reserve.getConsultation();
			Event event;
			if(consultation.getGroup() == null){
				event = new Event(consultation.getPatient(), consultation);
			}else{
				event = new Event(patient, consultation);
			}
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
		List<Group> groups = gpDAO.getGroupsByPatient(patient);

		List<Event> events = new ArrayList<Event>();

		for(Consultation c : consultations){
			if(c.getState().name() == ConsultationState.RD.name()){
				if(c.getGroup() != null){
					if(contain(c.getGroup(), groups)){
						Event event = new Event(patient, c);
						events.add(event);
					}
				}else{
					System.out.println(c);
					if(c.getPatient().getCpf().equals(patient.getCpf())){
						Event event = new Event(patient, c);
						events.add(event);
					}		
				}

			}else{
				Event event = new Event(patient, c);
				events.add(event);
			}
		}

		json = gson.toJson(events);
		return json;
	}

	public String getConsultationsByProfessionalJSON(Professional professional, ConsultationDAO consDAO, Date init, Date end){
		List<Consultation> cons = consDAO.getConsultationByProfessional(professional, init, end);
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
		List<Consultation> cons = consDAO.getConsultationByProfessional(professional, null, null);
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

	public String rescheduleConsultation(long idConsultation, Date dateInit, Date dateEnd, String email, ConsultationDAO consDAO){
		Gson gson = new Gson();
		Response response = new Response();
		try{
			Consultation consultation = consDAO.getConsultationById(idConsultation);
			consultation.setDateInit(dateInit);
			consultation.setDateEnd(dateEnd);
			//Email não é o email da(s) pessoa(s) e sim o corpo de email ou motivo da remarcação ou cancelamento
			consultation.setReasonCancel(email);
			consultation.setState(ConsultationState.RS);
			if(((consultation.getPatient() != null && consultation.getPatient().getEmail() != null) || consultation.getGroup() != null) && !email.equals("")){
				emailService.sendEmail(consultation, email);
			}
			consDAO.update(consultation);
			response.setMessage("Consulta reagendada com sucesso!");
			response.setCode(Response.SUCCESS);
		}catch(Exception e){
			e.printStackTrace();
			response.setMessage("Não foi possível reagendar essa consulta!");
			response.setCode(Response.ERROR);
		}
		return gson.toJson(response);
	}

	public void updateConsultation(Consultation consultation, ConsultationDAO consDAO){
		consDAO.update(consultation);
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

		try{
			Consultation oldCons = getConsultationsById(id, consDAO);
			
			//Caso a consulta esteja livre
			if(oldCons.getState().equals(ConsultationState.FR)){
				oldCons.setReasonCancel(message);
				consDAO.cancelConsultation(oldCons);
				response.setCode(Response.SUCCESS);
				response.setMessage("Consulta cancelada com sucesso!");
				
			}

			if(oldCons != null){
				Date today = new Date();

				DateFormat formatDate = new SimpleDateFormat("dd/MM/YYYY HH:mm");
				DateFormat formatHours = new SimpleDateFormat("HH:mm");
				if( message == "" || message == null){
					message = "Informamos que sua consulta do dia "+formatDate.format(oldCons.getDateInit())+" de "+formatHours.format(oldCons.getDateInit())+" às "+ formatHours.format(oldCons.getDateEnd()) +" foi cancelada!";
				}

				if(oldCons.getDateEnd().before(today)){
					response.setCode(Response.ERROR);
					response.setMessage("Ops, não é possível cancelar consultas anteriores a data de hoje");
				}else{
					consDAO.cancelConsultation(oldCons);
					response.setCode(Response.SUCCESS);
					response.setMessage("Consulta cancelada com sucesso!");

					if(oldCons.getPatient() != null || oldCons.getGroup() != null){
						if(!message.equals(""))
							emailService.sendEmail(oldCons, message);
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

			e.printStackTrace();

			System.out.println("Error at cancelConsultation by id: "+e);

			return gson.toJson(response);
		}
	}
	
	public String cancelAllConsultationById(String json, ConsultationDAO consDAO){
		
		Gson gson = new Gson();
		Response response = new Response();
		ConsultationList consultations = null;

		try{
			
			consultations = gson.fromJson(json, ConsultationList.class);
			
			for (Consultation consultation : consultations.getConsultations()) {
				Consultation oldCons = consDAO.getConsultationById(consultation.getId());
				String result = cancelConsultationById(oldCons.getId(), consultation.getComment(), consDAO);
				response = gson.fromJson(result, Response.class);
				
				if(response.getCode() == Response.ERROR){
					response.setCode(Response.ERROR);
					response.setMessage("Ops, não foi possível cancelar todas as consultas. \n"+response.getMessage());
					return gson.toJson(response);
				}
				
				if(oldCons.getPatient() != null || oldCons.getGroup() != null){
					if(!consultation.getComment().equals(""))
						emailService.sendEmail(oldCons, consultation.getComment());
				}
				
			}
			
			response.setCode(Response.SUCCESS);
			response.setMessage("Consultas canceladas com sucesso!");
			return gson.toJson(response);
			
			
		}catch(Exception e){
			response.setCode(Response.ERROR);
			response.setMessage("Ops, não foi possível cancelar a consulta");

			e.printStackTrace();

			System.out.println("Error at cancelConsultation by id: "+e);

			return gson.toJson(response);
		}
	}

	public String getRatingByConsultation(Consultation consultation, Patient patient, ConsultationDAO consultationDAO){
		String json = "";
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		Rating rating = consultationDAO.getRatingByIdConsultation(consultation.getId(), patient.getId());
		json = gson.toJson(rating);
		return json;

	}

	public String cancelConsultation(Consultation consultation, ConsultationDAO consultationDAO, ReserveDAO reserveDAO){

		List<Reserve> reserves = reserveDAO.getActiveReservesByConsultation(consultation);

		if(reserves.isEmpty()){
			consultation.setState(ConsultationState.FR);
			consultation.setPatient(null);
			consultation.setGroup(null);
			consultationDAO.update(consultation);

		}else{
			Collections.sort(reserves);
			Reserve reserve = reserves.get(0);
			reserve.setActive(false);
			reserveDAO.update(reserve);
			consultation.setPatient(reserve.getPatient());
			consultation.setGroup(reserve.getGroup());
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

	public String updateRating(String json, ConsultationDAO consultationDAO, Patient patient){

		Gson gson = new Gson();
		Response response = new Response();
		
		try{
			Rating rating  = gson.fromJson(json, Rating.class);
			rating.setPatient(patient);
			Consultation consultation = consultationDAO.getConsultationById(rating.getConsultation().getId());
			consultation.getRatings().add(rating);
			rating.setConsultation(consultation);
			
			if(consultation.getState().equals(ConsultationState.RD)){
				if(consultation.getPatient() != null){
					if(consultation.getPatient().getCpf().equals(patient.getCpf())){
						response.setCode(Response.SUCCESS);
						response.setMessage("Consulta avaliada com sucesso");
						consultationDAO.update(consultation);
					}else{
						response.setCode(Response.ERROR);
						response.setMessage("A consulta não pode ser avaliada");
					}
				}else{
					if(verifierPatientInGroup(consultation.getGroup(), patient)){
						response.setCode(Response.SUCCESS);
						response.setMessage("Consulta avaliada com sucesso");
						consultationDAO.update(consultation);
					}else{
						response.setCode(Response.ERROR);
						response.setMessage("A consulta não pode ser avaliada");
					}
				}
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
	


	public String checkSchedules(Professional proTemp, String json, ConsultationDAO consDAO){
		GsonBuilder gsonb = new GsonBuilder();
		DateDeserializer ds = new DateDeserializer();
		gsonb.registerTypeAdapter(Date.class, ds);
		Gson gson = gsonb.create();
		
		Response response = new Response();
		//JsonObject schedules = (JsonObject) new JsonParser().parse(json);
		//SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		
		//System.out.println(json);
		
		//Date dateInit = new Date(Long.parseLong(schedules.get("dateInit").toString()));
		//Date dateEnd = new Date(Long.parseLong(schedules.get("dateEnd").toString()));
		try{
			
			Json scheduler = gson.fromJson(json, Json.class);
			
			ArrayList<Consultation> occupieds = new ArrayList<Consultation>();
			
			for (Consultation consultation : scheduler.json.getSchedule()) {
				List<Consultation> cons = consDAO.getConsultationByPeriod(proTemp, consultation.getDateInit(), consultation.getDateEnd());
				for(Consultation c: cons){
					
					if(c.getDateInit().getHours() >= consultation.getDateInit().getHours() && c.getDateInit().getMinutes() >= consultation.getDateInit().getMinutes() && c.getDateEnd().getHours() <= consultation.getDateEnd().getHours() && c.getDateEnd().getMinutes() <= consultation.getDateEnd().getMinutes() ){						
						occupieds.add(c);
					}
				}
			}
			
			if(occupieds.size() > 0){
				String occupied = "";
				for(Consultation c:occupieds){
					occupied += "["+c.getDateInit()+"] ";
				}
				response.setCode(Response.ERROR);
				response.setMessage("As seguintes consultas estão conflitando com o(s) horário(s) enviados: "+occupied);
				return gson.toJson(response);
			}
			
			/*List<Consultation> cons = consDAO.getConsultationByPeriod(proTemp, dateInit, dateEnd);
			for(Consultation c: cons){
				System.out.println(c);
				
				Date di = new Date();
				String si = schedules.get("hourInit").toString().replaceAll("\"", "");
				di.setHours(Integer.parseInt(si.split(":")[0]));
				di.setMinutes(Integer.parseInt(si.split(":")[1]));
				di.setSeconds(Integer.parseInt(si.split(":")[2]));
				
				Date de = new Date();
				String se = schedules.get("hourEnd").toString().replaceAll("\"", "");
				de.setHours(Integer.parseInt(se.split(":")[0]));
				de.setMinutes(Integer.parseInt(se.split(":")[1]));
				de.setSeconds(Integer.parseInt(se.split(":")[2]));
				
				if(c.getDateInit().getHours() >= di.getHours() && c.getDateInit().getMinutes() >= di.getMinutes() && c.getDateEnd().getHours() <= de.getHours() && c.getDateEnd().getMinutes() <= de.getMinutes() ){
					System.out.println("Entrou!!");
					response.setCode(Response.ERROR);
					response.setMessage("Ops, outra(s) consulta(s) está(ão) agendada(s) neste período");
					return gson.toJson(response);
				}
			}*/
		}catch (Exception e) {
			e.printStackTrace();
			response.setCode(Response.ERROR);
			response.setMessage("Ops, não foi possível verificar os horários");
			return gson.toJson(response);
		}
		
		response.setCode(Response.SUCCESS);
		response.setMessage("Todos os horários estão vagos!");
		return gson.toJson(response);
		
	}
	
	public String registerComment(String json, ConsultationDAO consDAO){
		Gson gson = new Gson();
		Response response = new Response();
		
		try{
			
			Consultation consultation = gson.fromJson(json, Consultation.class);
			String message = consultation.getComment();
			consultation = consDAO.getConsultationById(consultation.getId());
			consultation.setComment(message);
			consDAO.update(consultation);
			
			response.setCode(Response.SUCCESS);
			response.setMessage("Comentário registrado!");
			return gson.toJson(response);
			
		}catch (Exception e) {
			response.setCode(Response.ERROR);
			response.setMessage("Ops, não foi possível registrar o comentário!");
			return gson.toJson(response);
		}
	}
	
	// Métodos auxiliares
	
	private boolean contain(Group group, List<Group> groups){
		for (Group g : groups) {
			if(g.getId() == group.getId())
				return true;
		}
		return false;
	}
	
	public boolean verifierPatientInGroup(Group group, Patient patient) {
		if(group != null){
			for(Patient p: group.getPatients())
				if(p.getCpf().equals(patient.getCpf()))
					return true;
		}
		return false;
	}
	
	private class Json{
		public Scheduler json;
		
		@Override
		public String toString() {
			String consultations = "";
			for(Consultation c: json.getSchedule())
				consultations += c.toString();
			return consultations;
		}
		
	}
	
}

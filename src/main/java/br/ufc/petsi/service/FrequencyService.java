package br.ufc.petsi.service;

import javax.inject.Named;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import br.ufc.petsi.dao.FrequencyDAO;
import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Frequency;
import br.ufc.petsi.model.FrequencyList;
import br.ufc.petsi.util.Response;

@Named
public class FrequencyService {
	
	public String registerFrequency(String json, FrequencyDAO freqDAO){
		Gson gson = new Gson();
		ObjectMapper mapper = new ObjectMapper();
		Response response = new Response();
		
		try{
		
			FrequencyList frequencyList = mapper.readValue(json, FrequencyList.class);
			
			if(frequencyList.getFrequencyList() == null){
				response.setCode(Response.ERROR);
				response.setMessage("Sua lista de frequência está vazia!");
				return gson.toJson(response);
			}
			
			for(Frequency frequency: frequencyList.getFrequencyList()){
				freqDAO.register(frequency);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			response.setCode(Response.ERROR);
			response.setMessage("Não foi possível registrar a frequência da consulta");
			return gson.toJson(response);
		}
		
		response.setCode(Response.SUCCESS);
		response.setMessage("Frequência registrada com sucesso!");
		return gson.toJson(response);
	}
	
	public String getFrequencyList(String json, FrequencyDAO freqDAO){
		Gson gson = new Gson();
		ObjectMapper mapper = new ObjectMapper();
		Response response = new Response();
		
		try{
			Consultation consultation = mapper.readValue(json, Consultation.class);
			
			response.setCode(Response.SUCCESS);
			response.setMessage(mapper.writeValueAsString(freqDAO.getFrequencyList(consultation)));
			return gson.toJson(response);
			
		}catch (Exception e) {
			e.printStackTrace();
			response.setCode(Response.ERROR);
			response.setMessage("Não foi possível registrar a frequência da consulta");
			return gson.toJson(response);
		}	
	}

}

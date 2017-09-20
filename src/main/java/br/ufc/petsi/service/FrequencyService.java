package br.ufc.petsi.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
		
			//FrequencyList frequencyList = mapper.readValue(json, FrequencyList.class);
			//FrequencyList frequencyList = gson.fromJson(json, FrequencyList.class);
			List<Frequency> frequencyList = new ArrayList<Frequency>();
			
			JsonParser parser = new JsonParser();
			JsonArray array = parser.parse(json).getAsJsonArray();
			System.out.println(array);
			for(int i =0; i < array.size();i++){
				Frequency freq = gson.fromJson(array.get(i), Frequency.class);
				frequencyList.add(freq);
			}
			
			if(frequencyList.size() == 0){
				response.setCode(Response.ERROR);
				response.setMessage("Sua lista de frequência está vazia!");
				return gson.toJson(response);
			}else{
				for(Frequency frequency: frequencyList){
					freqDAO.register(frequency);
				}
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
		
		System.out.println("[GET FREQUENCY LIST]: "+json);
		try{
			Consultation consultation = gson.fromJson(json, Consultation.class);
			System.out.println("[GET FREQUENCY LIST]: "+consultation.getId());
			
			FrequencyList frequency = freqDAO.getFrequencyList(consultation);
			System.out.println("[FREQUENCY]: "+(frequency == null));
			
			response.setCode(Response.SUCCESS);
			response.setMessage(mapper.writeValueAsString(frequency));
			return gson.toJson(response);
			
		}catch (Exception e) {
			e.printStackTrace();
			response.setCode(Response.ERROR);
			response.setMessage("Não foi possível retornar a frequência da consulta");
			return gson.toJson(response);
		}	
	}

}

package br.ufc.petsi.service;

import java.util.List;

import javax.inject.Named;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import br.ufc.petsi.dao.ProfessionalDAO;
import br.ufc.petsi.model.Professional;

@Named
public class ProfessionalService {

	public String getProfessionalsByService(ProfessionalDAO professionalDao, long serviceId){
		List<Professional> professionals = professionalDao.getProfessionalsByService(serviceId);
		/*Gson gson = new Gson();
		
		String json = "";
		json = gson.toJson(professionals);
		
		return json;*/
		ObjectMapper mapper = new ObjectMapper();
		try{
			return mapper.writeValueAsString(professionals);
		}catch (Exception e) {
			System.out.println(e.toString());
		}
		return "";
	}
}

package br.ufc.petsi.service;

import java.util.List;

import javax.inject.Named;

import br.ufc.petsi.dao.ProfessionalDAO;
import br.ufc.petsi.model.Professional;
import br.ufc.petsi.util.ProfessionalExclusionStrategy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Named
public class ProfessionalService {

	public String getProfessionalsByService(ProfessionalDAO professionalDao, long serviceId){
		List<Professional> professionals = professionalDao.getProfessionalsByService(serviceId);
		String json = "";
		try{
			Gson gson = new GsonBuilder().setExclusionStrategies(new ProfessionalExclusionStrategy()).serializeNulls().create();
			json = gson.toJson(professionals);

		}catch(Exception e){
			System.out.println("Error at getProfessionalByService Service: ");
		}
		return json;
	}
}

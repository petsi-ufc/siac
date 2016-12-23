package br.ufc.petsi.util;

import java.util.List;

import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.SocialService;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class ProfessionalExclusionStrategy implements ExclusionStrategy{
	
	@Override
	public boolean shouldSkipClass(Class<?> clas) {
		return  clas == SocialService.class || clas == List.class || clas == Consultation.class; 
	}

	@Override
	public boolean shouldSkipField(FieldAttributes attr) {
		return false;
	}
	
}

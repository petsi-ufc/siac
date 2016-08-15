package br.ufc.petsi.util;
import java.util.List;

import br.ufc.petsi.model.Professional;
import br.ufc.petsi.model.Rating;
import br.ufc.petsi.model.SocialService;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;


public class ConsultationExclusionStrategy implements ExclusionStrategy{

	@Override
	public boolean shouldSkipClass(Class<?> clas) {
		return clas == List.class || clas == Professional.class || clas == SocialService.class || clas == Rating.class ; 
	}

	@Override
	public boolean shouldSkipField(FieldAttributes attr) {
		return false;
	}

}

package br.ufc.petsi.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import br.ufc.petsi.model.Professional;

public class UserExclusionStrategy implements ExclusionStrategy{

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		return (f.getDeclaringClass() == Professional.class && f.getName().equals("listConsultations"));
	}

	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}

}

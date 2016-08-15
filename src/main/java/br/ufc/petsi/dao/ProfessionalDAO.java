package br.ufc.petsi.dao;

import java.util.List;

import br.ufc.petsi.model.Professional;

public interface ProfessionalDAO {
	
	public List<Professional> getProfessionalsByService(long serviceId);
}

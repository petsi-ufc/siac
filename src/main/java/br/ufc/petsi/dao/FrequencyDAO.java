package br.ufc.petsi.dao;

import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Frequency;
import br.ufc.petsi.model.FrequencyList;

public interface FrequencyDAO {
	public void register(Frequency frequency);
	public FrequencyList getFrequencyList(Consultation consultation);
}

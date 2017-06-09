package br.ufc.petsi.dao.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.ufc.petsi.dao.FrequencyDAO;
import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Frequency;
import br.ufc.petsi.model.FrequencyList;

@Repository
public class HBFrequency implements FrequencyDAO{
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public void register(Frequency frequency) {
		manager.persist(frequency);
	}

	@Override
	public FrequencyList getFrequencyList(Consultation consultation) {
		FrequencyList frequency = new FrequencyList();
		
		Query query = (Query) manager.createQuery("SELECT freq FROM Frequency freq WHERE freq.consultation = :idConsultation");
		query.setParameter("idConsultation", consultation);
		
		frequency.setFrequencyList(query.getResultList());
		
		return frequency;
	}
	
	
	
}

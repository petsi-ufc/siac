package br.ufc.petsi.dao.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.ufc.petsi.dao.ProfessionalDAO;
import br.ufc.petsi.model.Professional;

@Repository
public class HBProfessional implements ProfessionalDAO{
	

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Professional> getProfessionalsByService(long serviceId) {
		Query query = this.manager.createQuery("SELECT pe FROM Professional pe WHERE pe.socialService.id = :id ORDER BY pe.name");
		query.setParameter("id", serviceId);
		return query.getResultList();
		
	}


}

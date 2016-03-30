package br.ufc.petsi.dao.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.ufc.petsi.dao.RatingDAO;
import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Rating;

@Repository
public class HBRating implements RatingDAO{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Rating> getListByRating(int rating) {
		Query query = (Query) manager.createQuery("SELECT ra FROM Rating ra WHERE ra.rating = :rating");
		query.setParameter("rating", rating);
		List<Rating> list = query.getResultList();
		return list;
	}
	
	@Override
	public void save(Rating rating) {
		manager.persist(rating);
		
	}

	@Override
	public Rating getRatingById(long id) {
		Query query = (Query) manager.createQuery("SELECT rating FROM Rating cons WHERE rating.id = :id");
		query.setParameter("id", id);
		return (Rating) query.getSingleResult();
	}
	

}

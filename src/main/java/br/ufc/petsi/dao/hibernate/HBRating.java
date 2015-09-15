package br.ufc.petsi.dao.hibernate;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import br.ufc.petsi.dao.RatingDAO;
import br.ufc.petsi.model.Rating;

@Repository
public class HBRating implements RatingDAO{
	
	@Inject
	private SessionFactory sessionFactory;
	
	@Override
	public List<Rating> getListByRating(int rating) {
		Query query = getSession().createQuery("SELECT ra FROM Rating ra WHERE ra.rating = :rating");
		query.setParameter("rating", rating);
		List<Rating> list = query.list();
		return list;
	}
	
	@Override
	public void save(Rating rating) {
		getSession().persist(rating);
	}
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}

}

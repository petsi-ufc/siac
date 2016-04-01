package br.ufc.petsi.service;



import javax.inject.Named;


import br.ufc.petsi.dao.RatingDAO;
import br.ufc.petsi.model.Rating;


@Named
public class RatingService {

	public void saveRating(Rating rating, RatingDAO ratingDAO){
		ratingDAO.save(rating);
	}
	
	public Rating getRatingById(long id, RatingDAO ratingDAO){
		return ratingDAO.getRatingById(id);
	}
	
	
	

	
}

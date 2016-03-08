package br.ufc.petsi.dao;

import java.util.List;

import br.ufc.petsi.model.Rating;


public interface RatingDAO {
	public List<Rating> getListByRating(int rating);
	public void save(Rating rating);
}

package br.ufc.petsi.model;

import java.util.Date;
import java.util.List;

public class RatingReport {
	private String name;
	private Date dateBegin;
	private Date dateEnd;
	private List<Rating> ratings;
	private Double average;
	
	public RatingReport() {
		super();
	}
	
	public RatingReport(String name, Date dateBegin,
			Date dateEnd, List ratings, Double average) {
		super();
		this.name = name;
		this.dateBegin = dateBegin;
		this.dateEnd = dateEnd;
		this.ratings = ratings;
		this.average = average;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateBegin() {
		return dateBegin;
	}

	public void setDateBegin(Date dateBegin) {
		this.dateBegin = dateBegin;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	public Double getAverage() {
		return average;
	}

	public void setAverage(Double average) {
		this.average = average;
	}

	@Override
	public String toString() {
		return "RatingReport [name=" + name + ", dateBegin=" + dateBegin
				+ ", dateEnd=" + dateEnd + ", ratings=" + ratings
				+ ", average=" + average + "]";
	}

	
	
}

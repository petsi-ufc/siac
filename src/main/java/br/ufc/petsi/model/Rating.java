package br.ufc.petsi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table( name = "rating")
public class Rating {
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	private String comment;
	
	@NotNull(message="Informe uma nota para a consulta!")
	@Column(nullable = false)
	private int rating;

	@OneToOne(mappedBy="rating")
	private Consultation consultation;
	
	public Rating() {}

	public Rating(Long id, String comment, int rating) {
		this.id = id;
		this.comment = comment;
		this.rating = rating;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Consultation getConsultation() {
		return consultation;
	}

	public void setConsultation(Consultation consultation) {
		this.consultation = consultation;
	}
	
	
	
	
}

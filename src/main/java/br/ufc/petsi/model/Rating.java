package br.ufc.petsi.model;

import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;

@Entity
@Table( name = "rating")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
public class Rating implements Serializable{
	
	@Expose
	@Id
	@GeneratedValue
	@JsonProperty("id")
	private Long id;
	
	@Expose
	@NotNull
	@JsonProperty("comment")
	private String comment;
	
	@Expose
	@Column(nullable = false)
	@JsonProperty("rating")
	private int rating;

	@Expose
	@ManyToOne(cascade=CascadeType.MERGE)
	@JsonProperty("consultation")
	@JsonManagedReference(value="ratings")
	private Consultation consultation;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JsonProperty("patient")
	@JsonManagedReference
	private Patient patient;
	
	public Rating() {}

	public Rating(Long id, String comment, int rating) {
		this.id = id;
		this.comment = comment;
		this.rating = rating;
	}
	
	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(Long id) {
		this.id = id;
	}

	@JsonProperty("comment")
	public String getComment() {
		return comment;
	}

	@JsonProperty("comment")
	public void setComment(String comment) {
		this.comment = comment;
	}

	@JsonProperty("rating")
	public int getRating() {
		return rating;
	}

	@JsonProperty("rating")
	public void setRating(int rating) {
		this.rating = rating;
	}

	@JsonProperty("consultation")
	public Consultation getConsultation() {
		return consultation;
	}

	@JsonProperty("consultation")
	public void setConsultation(Consultation consultation) {
		this.consultation = consultation;
	}
	
	@JsonProperty("patient")
	public Patient getPatient() {
		return patient;
	}

	@JsonProperty("patient")
	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@Override
	public String toString() {
		return "Rating [id=" + id + ", comment=" + comment + ", rating="
				+ rating + ", consultation=" + consultation.getId()+", patient="+patient.getId() + "]";
	}

}

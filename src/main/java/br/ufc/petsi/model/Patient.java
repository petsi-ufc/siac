package br.ufc.petsi.model;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.ufc.petsi.constants.Constants;

@Entity
//@Table(name="patient")
@DiscriminatorValue(Constants.ROLE_PATIENT)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
public class Patient extends User implements Serializable{
	
	
	@OneToMany(mappedBy="patient", fetch = FetchType.LAZY, cascade=CascadeType.MERGE)
	@JsonProperty("listConsultations")
	@JsonManagedReference(value="patient1")
	private List<Consultation> listConsultations;
	
	@ManyToMany(mappedBy="patients", fetch=FetchType.LAZY)
	@JsonProperty("groups")
	@JsonBackReference
	private List<Group> groups;
	
	@OneToMany(mappedBy="patient", fetch=FetchType.LAZY,cascade=CascadeType.MERGE)
	@JsonProperty("ratings")
	@JsonBackReference
	private List<Rating> ratings;

	public Patient() {}
	
	public Patient(String cpf, String name, String email, String role) {
		super(cpf, name, email, role);
	}

	@JsonProperty("listConsultations")
	public List<Consultation> getListConsultations() {
		return listConsultations;
	}

	@JsonProperty("listConsultations")
	public void setListConsultations(List<Consultation> listConsultations) {
		this.listConsultations = listConsultations;
	}

	@JsonProperty("groups")
	public List<Group> getGroups() {
		return groups;
	}

	@JsonProperty("groups")
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	@JsonProperty("ratings")
	public List<Rating> getRatings() {
		return ratings;
	}

	@JsonProperty("ratings")
	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}
	
	

}

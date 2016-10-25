package br.ufc.petsi.model;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.ufc.petsi.constants.Constants;

@Entity
@Table(name="patient")
@DiscriminatorValue(Constants.ROLE_PATIENT)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
public class Patient extends User implements Serializable {
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="patient", cascade=CascadeType.ALL)
	@JsonProperty("listConsultations")
	private List<Consultation> listConsultations;

	public Patient() {
		//DEFAULT
	}
	
	public Patient(String cpf, String name, String email, String role, List<Consultation> listConsultations) {
		super(cpf, name, email, role);
		this.listConsultations = listConsultations;
	}

	@JsonProperty("listConsultations")
	public List<Consultation> getListConsultations() {
		return listConsultations;
	}

	@JsonProperty("listConsultations")
	public void setListConsultations(List<Consultation> listConsultations) {
		this.listConsultations = listConsultations;
	}
	
}

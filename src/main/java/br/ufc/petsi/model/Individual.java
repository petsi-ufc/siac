package br.ufc.petsi.model;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.ufc.petsi.enums.ConsultationState;

//@Entity
//@Table( name = "individual" )
//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
//@Generated("org.jsonschema2pojo")
public class Individual implements Serializable{
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JsonProperty("patient")
	private Patient patient;
	
	public Individual(){}

	/*public Individual(Long id, SocialService socialService, Professional profesisonal, ConsultationState state,
			Date dateInit, Date dateEnd, Patient patient) {
		super(id, socialService, profesisonal, state, dateInit, dateEnd);
		this.patient = patient;
	}*/

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	
	
}

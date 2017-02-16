package br.ufc.petsi.model;

import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table( name = "frequency" )
@Generated("org.jsonschema2pojo")
public class Frequency implements Serializable{
	
	@Id
	@JsonProperty("id")
	private Long id;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JsonProperty("consultation")
	private Group group;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JsonProperty("patient")
	private Patient patient;
	
	@Column(name="presence")
	@JsonProperty("presence")
	private boolean presence;
	
	public Frequency(){}
	
	public Frequency(Group group, Patient patient, boolean presence) {
		super();
		this.group = group;
		this.patient = patient;
		this.presence = presence;
	}
	
	@JsonProperty("consultation")
	public Group getGroup() {
		return group;
	}

	@JsonProperty("consultation")
	public void setGroup(Group group) {
		this.group = group;
	}

	@JsonProperty("patient")
	public Patient getPatient() {
		return patient;
	}

	@JsonProperty("patient")
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	@JsonProperty("presence")
	public boolean isPresence() {
		return presence;
	}

	@JsonProperty("presence")
	public void setPresence(boolean presence) {
		this.presence = presence;
	}

}

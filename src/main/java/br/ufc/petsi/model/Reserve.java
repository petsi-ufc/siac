package br.ufc.petsi.model;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "reserve")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
public class Reserve implements Comparable<Reserve>, Serializable{

	@Id
	@GeneratedValue
	@JsonProperty("id")
	private Long id;
	
	@Column(name="date")
	@Temporal(value = TemporalType.TIMESTAMP)
	@JsonProperty("date")
	private Date date;
	
	@ManyToOne(optional=false)
	@JsonProperty("consultation")
	private Consultation consultation;
	
	@ManyToOne
	@JsonProperty("patient")
	private Patient patient;
	
	@ManyToOne
	@JsonProperty("group")
	private Group group;
	
	@JsonProperty("active")
	private boolean active;
	
	public Reserve() {
		// TODO Auto-generated constructor stub
	}

	public Reserve(Long id, Date date, Consultation consultation, Patient patient, boolean active) {
		this.id = id;
		this.date = date;
		this.consultation = consultation;
		this.patient = patient;
		this.active = active;
	}
	
	public Reserve(Long id, Date date, Consultation consultation, Group group, boolean active) {
		this.id = id;
		this.date = date;
		this.consultation = consultation;
		this.group = group;
		this.active = active;
	} 

	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(Long id) {
		this.id = id;
	}

	@JsonProperty("date")
	public Date getDate() {
		return date;
	}

	@JsonProperty("date")
	public void setDate(Date date) {
		this.date = date;
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

	@JsonProperty("active")
	public boolean isActive() {
		return active;
	}

	@JsonProperty("active")
	public void setActive(boolean active) {
		this.active = active;
	}
	
	@JsonProperty("group")
	public Group getGroup() {
		return group;
	}

	@JsonProperty("group")
	public void setGroup(Group group) {
		this.group = group;
	}

	@Override
	public int compareTo(Reserve reserve) {
		if(this.getDate().before(reserve.getDate())) return 1;
		else if(this.getDate().after(reserve.getDate())) return -1;
		return 0;
	}
	
	
	
	
	
}

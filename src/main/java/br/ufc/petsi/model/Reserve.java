package br.ufc.petsi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "reserve")
public class Reserve implements Comparable<Reserve>{

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date date;
	
	@ManyToOne
	private Consultation consultation;
	
	@ManyToOne
	private Patient patient;
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Consultation getConsultation() {
		return consultation;
	}

	public void setConsultation(Consultation consultation) {
		this.consultation = consultation;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public int compareTo(Reserve reserve) {
		if(this.getDate().before(reserve.getDate())) return 1;
		else if(this.getDate().after(reserve.getDate())) return -1;
		return 0;
	}
	
	
	
	
	
}

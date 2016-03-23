package br.ufc.petsi.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import br.ufc.petsi.enums.ConsultationState;

//Adicionar id ou cpf do profissional e o cpf ou id do pacientes

@Entity
@Table( name = "consultation" )
public class Consultation {
	@Id
	@GeneratedValue
	private Long id;
	
	@OneToOne(targetEntity = SocialService.class, 
			  cascade = { CascadeType.MERGE }, 
			  fetch = FetchType.EAGER )
	@JoinColumn( name = "id_service" )
	private SocialService socialService;
	
	@ManyToOne
	private Professional professional;
	
	@ManyToOne
	private Patient patient;
	
	@Column(name="date_init")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dateInit;
	
	@Column(name="date_end")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dateEnd;
	
	@Enumerated( EnumType.STRING )
	private ConsultationState state;
	
	@OneToOne(cascade = CascadeType.ALL, optional = true, 
			fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name="id_rating")
	private Rating rating;
	
	public Consultation(Long id, SocialService socialService, Professional profesisonal, Patient patient, 
			ConsultationState state, Date dateInit, Date dateEnd) {
		this.id = id;
		this.socialService = socialService;
		this.state = state;
		this.professional = profesisonal;
		this.patient = patient;
		this.dateEnd = dateEnd;
		this.dateInit = dateInit;
	}

	public Consultation() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Professional getProfessional() {
		return professional;
	}
	
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Date getDateInit() {
		return dateInit;
	}

	public void setDateInit(Date dateInit) {
		this.dateInit = dateInit;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public void setProfessional(Professional professional) {
		this.professional = professional;
	}

	public SocialService getService() {
		return socialService;
	}

	public void setService(SocialService service) {
		this.socialService = service;
	}

	public ConsultationState getState() {
		return state;
	}

	public void setState(ConsultationState state) {
		this.state = state;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}
}

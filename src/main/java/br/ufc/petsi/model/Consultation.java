package br.ufc.petsi.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.ufc.petsi.enums.ConsultationState;

@Entity
@Table( name = "consultation" )
public class Consultation {
	@Id
	@GeneratedValue
	private Long id;
	
	@OneToOne(targetEntity = Service.class, 
			  cascade = { CascadeType.MERGE }, 
			  fetch = FetchType.EAGER )
	@JoinColumn( name = "id_service" )
	private Service service;
	
	@Enumerated( EnumType.STRING )
	private ConsultationState state;
	
	@OneToOne(targetEntity = Schedule.class, 
			  cascade = { CascadeType.MERGE }, 
			  fetch = FetchType.EAGER )
	@JoinColumn( name = "id_schedule" )
	private Schedule schedule;
	
	@OneToOne(cascade = CascadeType.ALL, optional = false, 
			fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name="id_rating")
	private Rating rating;

	public Consultation(Long id, Service service, ConsultationState state, Schedule schedule) {
		this.id = id;
		this.service = service;
		this.state = state;
		this.schedule = schedule;
	}

	public Consultation() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public ConsultationState getState() {
		return state;
	}

	public void setState(ConsultationState state) {
		this.state = state;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}
	
}

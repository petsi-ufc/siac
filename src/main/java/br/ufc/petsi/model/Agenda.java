package br.ufc.petsi.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table( name = "agenda" )
public class Agenda {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@OneToMany(mappedBy = "agenda", targetEntity = Consultation.class, 
			cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Consultation> consultations;
	
	@NotNull
	@Column(name="id_user")
	private Long userId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Consultation> getConsultations() {
		return consultations;
	}

	public void setConsultations(List<Consultation> consultations) {
		this.consultations = consultations;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}

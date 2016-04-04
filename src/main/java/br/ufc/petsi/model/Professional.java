package br.ufc.petsi.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.ufc.petsi.constants.Constants;

@Entity
@Table( name = "professional" )
public class Professional extends User {
	
	@OneToOne(targetEntity = SocialService.class, 
			  cascade = { CascadeType.MERGE }, 
			  fetch = FetchType.EAGER )
	@JoinColumn( name = "id_social_service" )
	private SocialService socialService;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="professional")
	private List<Consultation> listConsultations;
	
	public Professional(String cpf, String name, String email, String role, SocialService socialService, List<Consultation> listConsultations) {
		super(cpf, name, email, role);
		this.socialService = socialService;
		this.listConsultations = listConsultations;
	}

	public Professional() {
		super();
	}

	public SocialService getSocialService() {
		return socialService;
	}

	public void setSocialService(SocialService socialService) {
		this.socialService = socialService;
	}

	public List<Consultation> getListConsultations() {
		return listConsultations;
	}

	public void setListConsultations(List<Consultation> listConsultations) {
		this.listConsultations = listConsultations;
	}
	
	
}

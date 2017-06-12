package br.ufc.petsi.model;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.ufc.petsi.constants.Constants;

@Entity
//@Table( name = "professional" )
@DiscriminatorValue(Constants.ROLE_PROFESSIONAL)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
public class Professional extends User implements Serializable {
	
	@OneToOne(targetEntity = SocialService.class, 
			  cascade = { CascadeType.MERGE }, 
			  fetch = FetchType.EAGER )
	@JoinColumn( name = "id_social_service" )
	@JsonProperty("socialService")
	private SocialService socialService;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(fetch = FetchType.LAZY, mappedBy="professional", cascade=CascadeType.MERGE)
	@JsonProperty("listConsultations")
	@JsonManagedReference(value="professional1")
	private List<Consultation> listConsultations;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="facilitator", cascade=CascadeType.MERGE)
	@JsonProperty("listGroups")
	@JsonManagedReference
	private List<Group> listGroups;
	
	public Professional(String cpf, String name, String email, String role, SocialService socialService, List<Consultation> listConsultations) {
		super(cpf, name, email, role);
		this.socialService = socialService;
		this.listConsultations = listConsultations;
	}

	public Professional() {
		super();
	}
	
	@JsonProperty("socialService")
	public SocialService getSocialService() {
		return socialService;
	}

	@JsonProperty("socialService")
	public void setSocialService(SocialService socialService) {
		this.socialService = socialService;
	}
	
	@JsonProperty("listConsultations")
	public List<Consultation> getListConsultations() {
		return listConsultations;
	}

	@JsonProperty("listConsultations")
	public void setListConsultations(List<Consultation> listConsultations) {
		this.listConsultations = listConsultations;
	}

	@JsonProperty("listGroups")
	public List<Group> getListGroups() {
		return listGroups;
	}
	
	@JsonProperty("listGroups")
	public void setListGroups(List<Group> listGroup) {
		this.listGroups = listGroup;
	}
	
	
	
	
}

package br.ufc.petsi.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.ufc.petsi.enums.ConsultationState;

@Entity
@Table( name = "service_group" )
@Generated("org.jsonschema2pojo")
public class Group implements Serializable{
	
	@Id
	@GeneratedValue
	@JsonProperty("id")
	private Long id;
	
	@Column(name="title")
	@JsonProperty("title")
	private String title;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade={CascadeType.REMOVE, CascadeType.PERSIST})
	@JoinTable(name="group_patient",
		joinColumns= @JoinColumn(name="id_group"),
		inverseJoinColumns=@JoinColumn(name="id_patient")
	)
	@JsonProperty("patients")
	@JsonManagedReference(value="group1")
	private List<Patient> patients;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JsonProperty("facilitator")
	@JsonBackReference
	private Professional facilitator;
	
	@Column(name="openGroup")
	@JsonProperty("openGroup")
	private boolean openGroup = true;
	
	@Column(name="patientLimit")
	@JsonProperty("patientLimit")
	private int patientLimit;
	
	@OneToMany(mappedBy="group", fetch = FetchType.LAZY, cascade=CascadeType.MERGE)
	@JsonProperty("listConsultations")
	@JsonManagedReference
	private List<Consultation> listConsultations;
	
	public Group(){}

	public Group(String title, List<Patient> patients, boolean openGroup, Professional facilitator) {
		super();
		this.title = title;
		this.patients = patients;
		this.openGroup = openGroup;
		this.facilitator = facilitator;
	}

	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(Long id) {
		this.id = id;
	}

	@JsonProperty("patients")
	public List<Patient> getPatients() {
		return patients;
	}

	@JsonProperty("patients")
	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	@JsonProperty("title")
	public String getTitle() {
		return title;
	}

	@JsonProperty("title")
	public void setTitle(String title) {
		this.title = title;
	}

	@JsonProperty("openGroup")
	public boolean isOpenGroup() {
		return openGroup;
	}

	@JsonProperty("openGroup")
	public void setOpenGroup(boolean openGroup) {
		this.openGroup = openGroup;
	}
	
	@JsonProperty("patientLimit")
	public int getPatientLimit() {
		return patientLimit;
	}

	@JsonProperty("patientLimit")
	public void setPatientLimit(int patientLimit) {
		this.patientLimit = patientLimit;
	}

	@JsonProperty("facilitator")
	public Professional getFacilitator() {
		return facilitator;
	}

	@JsonProperty("facilitator")
	public void setFacilitator(Professional facilitator) {
		this.facilitator = facilitator;
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

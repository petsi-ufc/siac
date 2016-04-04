package br.ufc.petsi.model;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.ufc.petsi.constants.Constants;

@Entity
@Table(name="patient")
public class Patient extends User {
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="patient")
	private List<Consultation> listConsultations;

	public Patient() {
		//DEFAULT
	}
	
	public Patient(String cpf, String name, String email, String role, List<Consultation> listConsultations) {
		super(cpf, name, email, role);
		this.listConsultations = listConsultations;
	}

	public List<Consultation> getListConsultations() {
		return listConsultations;
	}

	public void setListConsultations(List<Consultation> listConsultations) {
		this.listConsultations = listConsultations;
	}
	
}

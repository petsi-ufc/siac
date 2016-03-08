package br.ufc.petsi.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.ufc.petsi.enums.Role;

@Entity
@Table(name="patient")
public class Patient extends User {
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="patient")
	private List<Consultation> listConsultations;

	public Patient() {
		//DEFAULT
	}
	
	public Patient(String cpf, String name, String email, Role role, List<Consultation> listConsultations) {
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

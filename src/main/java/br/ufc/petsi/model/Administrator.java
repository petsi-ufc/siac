package br.ufc.petsi.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="administrator")
public class Administrator extends User{
	
	public Administrator(String cpf, String name, String email, Role role) {
		super(cpf, name, email, role);
	}
	
	public Administrator(){
		//DEFAULT
	}
	
}

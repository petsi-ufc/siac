package br.ufc.petsi.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.ufc.petsi.enums.Role;

@Entity
@Table(name="administrators")
public class Administrator extends User{
	
	public Administrator(String cpf, String name, String email, Role role) {
		super(cpf, name, email, role);
	}
	
	public Administrator(){
		//DEFAULT
	}
	
}

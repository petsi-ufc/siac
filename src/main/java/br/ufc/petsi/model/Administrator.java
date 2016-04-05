package br.ufc.petsi.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.ufc.petsi.constants.Constants;

@Entity
@Table(name="administrator")
@DiscriminatorValue(Constants.ROLE_ADMIN)
public class Administrator extends User{
	
	public Administrator(String cpf, String name, String email, String role) {
		super(cpf, name, email, role);
	}
	
	public Administrator(){
		//DEFAULT
	}
	
}

package br.ufc.petsi.model;

import javax.annotation.Generated;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.ufc.petsi.constants.Constants;

@Entity
//@Table(name="administrator")
@DiscriminatorValue(Constants.ROLE_ADMIN)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
public class Administrator extends User{
	
	public Administrator(String cpf, String name, String email, String role) {
		super(cpf, name, email, role);
	}
	
	public Administrator(){
		//DEFAULT
	}
	
}

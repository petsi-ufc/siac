package br.ufc.petsi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity(name="users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", length = 17, discriminatorType = DiscriminatorType.STRING)
public abstract class User implements Serializable {
	
	@Id
	private String cpf;
	
	private String name;
	
	private String email;
	
	@Column(insertable=false, updatable=false)
	private String role;
	
	public User() {
		//DEFAUTL
	}
	
	public User(String cpf, String name, String email, String role) {
		this.cpf = cpf;
		this.name = name;
		this.email = email;
		this.role = role;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
	
	
}

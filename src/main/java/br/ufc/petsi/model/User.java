package br.ufc.petsi.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table( name = "users" )
public class User {
	
	@Id
	private String cpf;
	
	private String name;
	
	private String email;
	@OneToMany( targetEntity = Role.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER )
	@JoinTable(
			name = "usuario_papel",
			joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "cpf"), 
			inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id")
			)
	private List<Role> roles;

	public User() {

	}
	
	public User(String cpf, String name, String email, List<Role> roles) {
		this.cpf = cpf;
		this.name = name;
		this.email = email;
		this.roles = roles;
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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	
}

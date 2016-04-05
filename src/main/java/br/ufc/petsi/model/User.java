package br.ufc.petsi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity(name="users")
//@IdClass(UserPrimaryKey.class)
@DiscriminatorColumn(name = User.DISCRIMINATOR_COLUMN, length = 20, discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User implements Serializable {
	
	public static final String DISCRIMINATOR_COLUMN = "role";
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(name=DISCRIMINATOR_COLUMN, insertable=false, updatable=false)
	private String role;
	
	private String cpf;
	
	private String name;
	
	private String email;
	
	
	public User() {
		//DEFAUTL
	}
	
	public User(UserPrimaryKey primaryKey) {
		System.out.println("CPF: "+primaryKey.getCpf());
		this.cpf = primaryKey.getCpf();
		this.role = primaryKey.getRole();
	}
	
	public User(String cpf, String name, String email, String role) {
		this.cpf = cpf;
		this.name = name;
		this.email = email;
		this.role = role;
	}
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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



	@Override
	public String toString() {
		return "User [cpf=" + cpf + ", name=" + name + ", email=" + email
				+ ", role=" + role + "]";
	}
}





package br.ufc.petsi.model;

import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity(name="users")
//@IdClass(UserPrimaryKey.class)
@DiscriminatorColumn(name = User.DISCRIMINATOR_COLUMN, length = 20, discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
	"id",
	"role",
	"cpf",
	"name",
	"email"
})
public class User implements Serializable {
	
	public static final String DISCRIMINATOR_COLUMN = "role";
	
	@Id
	@GeneratedValue
	@JsonProperty("id")
	private long id;
	
	@Column(name=DISCRIMINATOR_COLUMN, insertable=false, updatable=false)
	@JsonProperty("role")
	private String role;
	
	@JsonProperty("cpf")
	private String cpf;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("email")
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
	
	
	@JsonProperty("id")
	public long getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(long id) {
		this.id = id;
	}

	@JsonProperty("cpf")
	public String getCpf() {
		return cpf;
	}

	@JsonProperty("cpf")
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("email")
	public String getEmail() {
		return email;
	}

	@JsonProperty("email")
	public void setEmail(String email) {
		this.email = email;
	}

	@JsonProperty("role")
	public String getRole() {
		return role;
	}
	
	@JsonProperty("role")
	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [cpf=" + cpf + ", name=" + name + ", email=" + email
				+ ", role=" + role + "]";
	}
}





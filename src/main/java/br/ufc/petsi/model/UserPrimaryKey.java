package br.ufc.petsi.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class UserPrimaryKey implements Serializable{
	
	private String role;
	
	private String cpf;
	
	public UserPrimaryKey(String cpf, String role) {
		System.out.println("CPF"+cpf+" ROLE:"+role);
		this.cpf = cpf;
		this.role = role;
	}
	
	public UserPrimaryKey() {
		
	}

	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserPrimaryKey other = (UserPrimaryKey) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		return true;
	}
	
	
	
}
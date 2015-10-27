package br.ufc.petsi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table( name = "service" )
public class Service {
	@Id
	@GeneratedValue
	private Long id;
	
	@NotEmpty
	@NotNull(message="Informe o nome do serviço!")
	private String name;

	private boolean active;

	public Service() {}
	
	public Service(Long id, String name, boolean active) {
		this.id = id;
		this.name = name;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Service [id=" + id + ", name=" + name + ", active=" + active
				+ "]";
	}
	
}

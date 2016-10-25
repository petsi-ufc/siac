package br.ufc.petsi.model;

import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table( name = "social_service" )
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
public class SocialService implements Serializable{
	@Id
	@GeneratedValue
	@JsonProperty("id")
	private Long id;
	
	@NotEmpty
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("active")
	private boolean active;

	public SocialService(){}
	
	public SocialService(Long id, String name, boolean active) {
		this.id = id;
		this.name = name;
		this.active = active;
	}

	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(Long id) {
		this.id = id;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public boolean isActive() {
		return active;
	}

	@JsonProperty("active")
	public void setActive(boolean active) {
		this.active = active;
	}

	@JsonProperty("active")
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Service [id=" + id + ", name=" + name + ", active=" + active
				+ "]";
	}
	
}

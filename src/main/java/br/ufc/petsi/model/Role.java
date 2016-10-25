package br.ufc.petsi.model;

import javax.annotation.Generated;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
public class Role implements GrantedAuthority {

	@JsonProperty("name")
	private String name;
	
	public Role() {
		
	}
	
	public Role(String name) {
		this.name = name;
	}
	
	@JsonProperty("name")
	public String getName() {
		return name;
	}
	
	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getAuthority() {
		return this.name;
	}

}

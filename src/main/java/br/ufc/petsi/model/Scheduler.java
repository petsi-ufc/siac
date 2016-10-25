package br.ufc.petsi.model;

import java.util.List;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
public class Scheduler {

	@JsonProperty("schedule")
	List<Consultation> schedule;

	@JsonProperty("schedule")
	public List<Consultation> getSchedule() {
		return schedule;
	}

	@JsonProperty("schedule")
	public void setSchedule(List<Consultation> schedule) {
		this.schedule = schedule;
	}
	
}

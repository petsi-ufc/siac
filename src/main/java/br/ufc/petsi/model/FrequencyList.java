package br.ufc.petsi.model;

import java.util.List;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
public class FrequencyList {
	
	@JsonProperty("frequencyList")
	List<Frequency> frequencyList;

	@JsonProperty("frequencyList")
	public List<Frequency> getFrequencyList() {
		return frequencyList;
	}

	@JsonProperty("frequencyList")
	public void setFrequencyList(List<Frequency> frequencyList) {
		this.frequencyList = frequencyList;
	}
	
}

package br.ufc.petsi.enums;

public enum TypeConsultation {
	
	FC("First Consultation"), RE("Return");
	
	private String type;
	
	TypeConsultation(String type) {
		this.type = type;
	}
	
	public String getType(){
		return type;
	}
	
}

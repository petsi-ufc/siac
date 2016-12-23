package br.ufc.petsi.enums;

public enum ConsultationState {
	
	FR("FREE"), CD("CANCELED"), SC("SCHEDULED"), RV("RESERVED"), RD("REALIZED"), NO("NOT_SCHEDULED");
	
	private String name;
	
	ConsultationState(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}

package br.ufc.petsi.enums;

public enum ConsultationState {
	/*	CD - CANCELED
	 * 	SC - SCHEDULED
	 * 	AV - AVALIABLE
	 *  NSC - NOT SCHEDULED
	 */

	FR("FREE"), CD("CANCELED"), SC("SCHEDULED"), RV("RESERVED"), RD("REALIZED"); 
	
	private String name;
	
	ConsultationState(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}

package br.ufc.petsi.enums;

public enum ConsultationState {
	/*	CD - CANCELED
	 * 	SC - SCHEDULED
	 * 	HD - HELD
	 */

	CD("Cancelada"), SC("Agendada"), HD("Realizada");
	
	private String name;
	
	ConsultationState(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}

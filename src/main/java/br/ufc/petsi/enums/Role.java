package br.ufc.petsi.enums;

public enum Role {
	
	ADM("Administrator"), PT("Patient"), PF("Professional");
	
	private Role(String role) {
		this.role = role;
	}

	private String role;

	public String getRole() {
		return role;
	}
		
	
	
}

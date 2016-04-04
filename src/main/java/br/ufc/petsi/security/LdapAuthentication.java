package br.ufc.petsi.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Named;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import br.ufc.petsi.model.Role;
import br.ufc.petsi.model.User;

@Named
public class LdapAuthentication implements Authentication {

	private User user;
	private String password;
	private boolean authenticated;
	
	public LdapAuthentication() {

	}
	
	public LdapAuthentication(User user, String password, String role) {
		super();
		this.user = user;
		this.password = password;
		this.user.setRole(role);
	}



	@Override
	public String getName() {
		return user != null ? user.getCpf() : null;
	}

	

	@Override
	public Object getCredentials() {
		return password != null ? password : null;
	}

	@Override
	public Object getDetails() {	
		return user;
	}

	@Override
	public Object getPrincipal() {
		return user != null ? user.getCpf() : null;
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthendicated) throws IllegalArgumentException {
		this.authenticated = isAuthendicated;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRole() {
		return user.getRole();
	}

	public void setRole(String role) {
		this.user.setRole(role);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<Role> roles = new ArrayList<Role>();
		roles.add(new Role(this.user.getRole()));
		return roles;
	}

	
}

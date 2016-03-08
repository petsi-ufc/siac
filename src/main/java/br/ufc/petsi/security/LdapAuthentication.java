package br.ufc.petsi.security;

import java.util.Collection;
import java.util.List;

import javax.inject.Named;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import br.ufc.petsi.enums.Role;
import br.ufc.petsi.model.User;

@Named
public class LdapAuthentication implements Authentication{

	private User user;
	private String password;
	private boolean authenticated;
	
	public LdapAuthentication() {

	}
	
	public LdapAuthentication(User user, String password, List<Role> roles) {
		super();
		this.user = user;
		this.password = password;
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

//	public List<Role> getRoles() {
//		return roles;
//	}

//	public void setRoles(List<Role> roles) {
//		this.roles = roles;
//	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	
}

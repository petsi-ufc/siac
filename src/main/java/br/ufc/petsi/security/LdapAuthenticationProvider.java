package br.ufc.petsi.security;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import br.ufc.petsi.dao.UserDAO;
import br.ufc.petsi.model.Role;
import br.ufc.petsi.model.User;
import br.ufc.petsi.session.CurrentSession;

@Named
public class LdapAuthenticationProvider implements AuthenticationProvider, Serializable{

	@Inject
	private UserDAO userDAO;
	
	@Override
	public Authentication authenticate(Authentication authen)
			throws AuthenticationException {
		String name = authen.getName();
		String password = authen.getCredentials() != null ? (String) authen.getCredentials() : null;
		
		User user = userDAO.getByCpf(name);
		
		if( user == null || !userDAO.authenticate(name, password)) {
			throw new BadCredentialsException("Login e/ou senha inválidos");
		}
		
		user.setRole(new Role("ROLE_USER"));
				
		LdapAuthentication result = new LdapAuthentication(user, password, user.getRole());
		result.setAuthenticated( true );		
		
		return result;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}

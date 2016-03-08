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
import br.ufc.petsi.model.User;

@Named
public class LdapAuthenticationProvider implements AuthenticationProvider, Serializable{

	@Inject
	@Qualifier("ldapUser")
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
		
//		List<Role> roles = user.getRoles();
//		LdapAuthentication result = new LdapAuthentication(user, password, roles);
//		result.setAuthenticated( user != null );		
		
//		return result;
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}

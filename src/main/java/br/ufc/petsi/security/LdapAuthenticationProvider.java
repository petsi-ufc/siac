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

import br.ufc.petsi.dao.ProfessionalDAO;
import br.ufc.petsi.dao.UserDAO;
import br.ufc.petsi.model.Role;
import br.ufc.petsi.model.User;
import br.ufc.petsi.session.CurrentSession;

@Named
public class LdapAuthenticationProvider implements AuthenticationProvider, Serializable{

	@Inject
	private UserDAO userDAO;
	
	@Inject
	private ProfessionalDAO profDAO;
	
	@Override
	public Authentication authenticate(Authentication authen)
			throws AuthenticationException {
		String name = authen.getName();
		String password = authen.getCredentials() != null ? (String) authen.getCredentials() : null;
		
		User user = userDAO.getByCpf(name);
		
		if( user == null || !userDAO.authenticate(name, password)) {
			throw new BadCredentialsException("Login e/ou senha inválidos");
		}
		
		String role = (String)CurrentSession.getSession().getAttribute("loginRole");
		if(role.equals("Profissional")) {
			User u = profDAO.getByCpf(name);
			if( u == null )
				throw new BadCredentialsException("Você não possui essa permissão!");
			else
				user.setRole(new Role("ROLE_PROF"));
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

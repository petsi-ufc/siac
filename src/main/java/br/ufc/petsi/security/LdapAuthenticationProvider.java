package br.ufc.petsi.security;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import br.ufc.petsi.constants.Constants;
import br.ufc.petsi.dao.UserDAO;
import br.ufc.petsi.dao.UserLdapDAO;
import br.ufc.petsi.model.User;
import br.ufc.petsi.session.CurrentSession;
import br.ufc.petsi.util.LogGenerator;

@Named
public class LdapAuthenticationProvider implements AuthenticationProvider {

	@Inject
	private UserLdapDAO ldapDAO;
	@Inject
	private UserDAO userDAO;
	
	@Override
	public Authentication authenticate(Authentication authen)
			throws AuthenticationException {
		String name = authen.getName();
		String password = authen.getCredentials() != null ? (String) authen.getCredentials() : null;
		String role = (String)CurrentSession.getSession().getAttribute("loginRole");
		
		//CPF
//		String name = "27240450848";
//		String role = "ROLE_ADMIN";
//		String password = "1234";
		
		User user = userDAO.getByCpf(name, role);
		
		if( user == null )
		{
			if(!role.equals(Constants.ROLE_PATIENT))
				throw new BadCredentialsException("Você não possui essa permissão!");
			User u = (User)ldapDAO.getByCpf(name);
			if( u != null )
			{
				u.setRole(Constants.ROLE_PATIENT);
				userDAO.save(u); 
				user = u;
			}
			else{
				throw new BadCredentialsException("Usuário inexistente");
			}
		}
		if(!ldapDAO.authenticate(name, password)) 
			throw new BadCredentialsException("Login e/ou senha inválidos");
		
	
		if(!role.equals(user.getRole())) 
			throw new BadCredentialsException("Você não possui essa permissão!");
		
		LdapAuthentication result = new LdapAuthentication(user, password, user.getRole());
		result.setAuthenticated( true );		
		
		return result;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}

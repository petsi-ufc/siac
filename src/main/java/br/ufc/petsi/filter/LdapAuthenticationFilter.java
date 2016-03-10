package br.ufc.petsi.filter;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.ufc.petsi.session.CurrentSession;

@Named
public class LdapAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		String role = request.getParameter("role");
		HttpSession session = request.getSession();
		session.setAttribute("loginRole", role);
		
		CurrentSession.setSession(session);
		
		return super.attemptAuthentication(request, response);
	}
	
}

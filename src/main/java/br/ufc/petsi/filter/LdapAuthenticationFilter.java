package br.ufc.petsi.filter;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.ufc.petsi.constants.Constants;
import br.ufc.petsi.session.CurrentSession;

@Named
public class LdapAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		String role = request.getParameter("role");
		
		if(role.equals("Profissional"))
			role = Constants.ROLE_PROFESSIONAL;
		else if(role.equals("Paciente"))
			role = Constants.ROLE_PATIENT;
		else if(role.equals("Administrador"))
			role = Constants.ROLE_ADMIN;
		
		HttpSession session = request.getSession();
		session.setAttribute("loginRole", role);
		
		System.out.println("[PAPEL DO LOGIN DO USUÁRIO]: "+role);
		
		CurrentSession.setSession(session);
		
		return super.attemptAuthentication(request, response);
	}
	
}

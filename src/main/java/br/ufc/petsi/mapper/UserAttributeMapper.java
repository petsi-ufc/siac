package br.ufc.petsi.mapper;

import java.io.Serializable;

import javax.inject.Named;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.servlet.http.HttpSession;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import br.ufc.petsi.constants.Constants;
import br.ufc.petsi.model.Administrator;
import br.ufc.petsi.model.Patient;
import br.ufc.petsi.model.Professional;
import br.ufc.petsi.model.User;
import br.ufc.petsi.session.CurrentSession;

@Named
public class UserAttributeMapper implements AttributesMapper<User>, Serializable {

	@Override
	public User mapFromAttributes(Attributes attrs) throws NamingException {
		
		HttpSession session = CurrentSession.getSession();
		String role = (String) session.getAttribute("loginRole");
		
		
		User user = null;
		
		if(role.equals(Constants.ROLE_PROFESSIONAL))
			user = new Professional();
		else if(role.equals(Constants.ROLE_ADMIN))
			user = new Administrator();
		else
			user = new Patient();
		
		if(attrs.get(Constants.CPF_USER) != null) {
			user.setCpf(attrs.get(Constants.CPF_USER).get().toString());
		}
		
		if(attrs.get(Constants.NAME_USER) != null) {
			user.setName(attrs.get(Constants.NAME_USER).get().toString());
		}
		
		if(attrs.get(Constants.EMAIL_USER) != null) {
			user.setEmail(attrs.get(Constants.EMAIL_USER).get().toString());
		}
		
		return user;
	}
	
}

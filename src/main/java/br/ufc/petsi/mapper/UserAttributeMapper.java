package br.ufc.petsi.mapper;

import java.io.Serializable;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.ldap.core.AttributesMapper;

import br.ufc.petsi.constants.Constants;
import br.ufc.petsi.model.User;

public class UserAttributeMapper implements AttributesMapper<User>, Serializable {

	@Override
	public User mapFromAttributes(Attributes attrs) throws NamingException {
		
//		User user = new User();
		
		if(attrs.get(Constants.CPF_USER) != null) {
//			user.setCpf(attrs.get(Constants.CPF_USER).get().toString());
		}
		
		if(attrs.get(Constants.NAME_USER) != null) {
//			user.setName(attrs.get(Constants.NAME_USER).get().toString());
		}
		
		if(attrs.get(Constants.EMAIL_USER) != null) {
//			user.setEmail(attrs.get(Constants.EMAIL_USER).get().toString());
		}
		
//		return user;
		return null;
	}
	
}

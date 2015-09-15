package br.ufc.petsi.mapper;

import java.io.Serializable;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.ldap.core.AttributesMapper;

import br.ufc.petsi.constants.Constants;
import br.ufc.petsi.model.Role;

public class RoleAttributeMapper implements AttributesMapper<Role>, Serializable {

	@Override
	public Role mapFromAttributes(Attributes attrs) throws NamingException {
		Role role = new Role(); 
		
		if( attrs.get(Constants.AFFILIATION_NAME) != null ) {
			role.setRole( ((String)attrs.get(Constants.AFFILIATION_NAME).get()).toUpperCase() );
		}
		
		return role;
	}

}

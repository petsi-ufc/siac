package br.ufc.petsi.dao.ldap;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
 
import br.ufc.petsi.constants.Constants;
import br.ufc.petsi.dao.UserLdapDAO;
import br.ufc.petsi.mapper.UserAttributeMapper;
import br.ufc.petsi.model.User;

@Named
public class LdapUser implements UserLdapDAO {

	@Inject
	private LdapContextSource contextSource;
	@Inject
	private LdapTemplate ldapTemplate;
	@Inject
	private String base;
	
	@Override
	public List<User> getAll() {
		LdapQuery query = LdapQueryBuilder.query().base(base).where("objectclass").is("person");
		return ldapTemplate.search(query, new UserAttributeMapper());
	}

	@Override
	public List<User> getByCpfList(String cpf) { 
		LdapQuery query = LdapQueryBuilder.query().base(base).where("objectclass").is("person").and(Constants.CPF_USER).is(cpf);
		return ldapTemplate.search(query, new UserAttributeMapper());
	}
	
	@Override
	public List<User> getByNameLike(String name) {
		LdapQuery query = LdapQueryBuilder.query().base(base).where("objectclass").is("person").and(Constants.NAME_USER).like("*"+name+"*");
		return ldapTemplate.search(query, new UserAttributeMapper());
	}
	
	@Override
	public User getByCpf(String cpf) {
		List<User> users = getByCpfList(cpf);
		
		if( users != null && !users.isEmpty() ) {
			User user = users.get(0);
//			user.setRoles( getRoles(cpf) );
			return user;
		}
		
		return null;
	}
	
//	@Override
//	public List<Role> getRoles(String cpf) {
//		return ldapTemplate.search(Constants.ID_USER + "=" + cpf + "," + base , "(objectclass=brEduPerson)", new RoleAttributeMapper());
//	}
	
	@Override
	public boolean authenticate(String login, String password) {
		LdapQuery query = LdapQueryBuilder.query().base(base).where("objectclass").is("person").and(Constants.CPF_USER).is(login);
		return ldapTemplate.authenticate(base, query.filter().encode(), password);
	}
	
	public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}
}

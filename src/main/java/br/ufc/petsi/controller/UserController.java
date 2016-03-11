package br.ufc.petsi.controller;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.ufc.petsi.dao.UserDAO;
import br.ufc.petsi.dao.ldap.LdapUser;
import br.ufc.petsi.model.User;

@Controller
@Transactional
public class UserController {
	
//	@Inject
//	@Qualifier("ldapUser")
//	private UserDAO userDAO;
	
	@RequestMapping("/getUserByName")
	@ResponseBody
	public String getUserByName(String name){
		return "";
	}
	
}

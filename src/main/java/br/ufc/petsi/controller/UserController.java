package br.ufc.petsi.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.ufc.petsi.constants.Constants;
import br.ufc.petsi.dao.ldap.LdapUser;
import br.ufc.petsi.model.User;

@Controller
@Transactional
public class UserController {
	
	@Inject
	private LdapUser userDAO;
	
	@RequestMapping("/getUserByName")
	@ResponseBody
	public String getUserByName(String name){
		return "";
	}
	
	public User getUserLogged(HttpSession session)
	{
		if(session.getAttribute(Constants.USER_SESSION) == null)
		{
			User user = userDAO.getByCpf(SecurityContextHolder.getContext().getAuthentication().getName());
			session.setAttribute(Constants.USER_SESSION, user);
		}
		return (User) session.getAttribute(Constants.USER_SESSION);
	}
	
}

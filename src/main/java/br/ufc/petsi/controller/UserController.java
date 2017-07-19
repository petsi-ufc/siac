package br.ufc.petsi.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import br.ufc.petsi.constants.Constants;
import br.ufc.petsi.dao.ldap.LdapUser;
import br.ufc.petsi.model.User;
import br.ufc.petsi.service.UserService;

@Controller
@Transactional
public class UserController {
	
	@Inject
	private LdapUser userDAO;
	
	@Inject
	private UserService userService;
	
	@RequestMapping("/getAllUsers")
	@ResponseBody
	public String getAllUsers(){
		return this.userService.getAllUsers(userDAO);
	}
	
	@RequestMapping("/getUserByName")
	@ResponseBody
	public String getUserByName(String name){
		return this.userService.getUserByName(name, userDAO);
	}
	
	@Secured("ROLE_PATIENT")
	@RequestMapping("/getUserByCpf")
	@ResponseBody
	public User getUserLogged(HttpSession session)
	{
		System.out.println("Session: " + session);
		
		if(session.getAttribute(Constants.USER_SESSION) == null)
		{
			User user = userDAO.getByCpf(SecurityContextHolder.getContext().getAuthentication().getName());
			session.setAttribute(Constants.USER_SESSION, user);
		}
		return (User) session.getAttribute(Constants.USER_SESSION);
	}
	
	@Secured("ROLE_PATIENT")
	@RequestMapping("/getUserLogado/cpf/{cpf}")
	@ResponseBody
	public String getUserLogado(@PathVariable("cpf") String cpf,HttpSession session){
		System.out.println("CPF Controler =>" +  cpf);
		//Gson so = new Gson();
		User user = (userService.getIdUserByCpf(cpf));
		System.out.println("Meu ID; "+user.getId()+" Meu nome; "+ user.getName());
		return String.valueOf(user.getId());
	}
	
	
}

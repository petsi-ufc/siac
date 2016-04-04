package br.ufc.petsi.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.ufc.petsi.constants.Constants;

import br.ufc.petsi.dao.hibernate.HBUserDAO;
import br.ufc.petsi.dao.ldap.LdapUser;
import br.ufc.petsi.model.User;

@Controller
public class AuthenticationController {
	
	@Inject
	private LdapUser ldapDAO;
	
	@Inject
	private HBUserDAO userDAO;
	
	@RequestMapping( value = {"/", "/login"} )
	public ModelAndView home() {
		return new ModelAndView("login");
	}
	
	@RequestMapping("/authentication/success")
	public ModelAndView success(HttpSession session) {
		try{
			User user = (User)this.getUserLogged(session);
			
			if(user.getRole().equals(Constants.ROLE_PROFESSIONAL))
				return new ModelAndView("redirect:/professional");
			else if(user.getRole().equals(Constants.ROLE_ADMIN))
				return new ModelAndView("redirect:/manager");
			else
				return new ModelAndView("redirect:/patient");
		}
		catch(NullPointerException e)
		{
			System.out.println("Error: " + e);
		}
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping("/logout")
	public ModelAndView logout(HttpSession session)
	{
		session.invalidate();
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping("/professional")
	public ModelAndView professional() {
		return new ModelAndView("home_professional");
	}
	
	@RequestMapping("/manager")
	public ModelAndView manager() {
		return new ModelAndView("home_manager");
	}
	
	@RequestMapping("/professional/schedule")
	public ModelAndView scheduleProfessional() {
		return new ModelAndView("schedule_professional");
	}
	
	@RequestMapping("/patient")
	public ModelAndView patient() {
		return new ModelAndView("home_patient");
	}
	
	public User getUserLogged(HttpSession session)
	{
		if(session.getAttribute("userLogged") == null)
		{
			User user = (User)SecurityContextHolder.getContext().getAuthentication().getDetails();
			session.setAttribute("userLogged", user);
		}
		return (User) session.getAttribute("userLogged");
	}
	
}

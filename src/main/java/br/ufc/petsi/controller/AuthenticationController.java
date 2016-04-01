package br.ufc.petsi.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.ufc.petsi.constants.Constants;
import br.ufc.petsi.model.User;

@Controller
public class AuthenticationController {
	
	@RequestMapping( value = {"/", "/home"} )
	public ModelAndView home() {
		return new ModelAndView("login");
	}
	
	@RequestMapping("/authentication/success")
	public ModelAndView success(HttpSession session) {
		User user = (new UserController()).getUserLogged(session);
		if(user.getRole().equals(Constants.ROLE_PROFESSIONAL))
			return new ModelAndView("home_professional");
		else if(user.getRole().equals(Constants.ROLE_ADMIN))
			return new ModelAndView("home_manager");
		else
			return new ModelAndView("home_patient");
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
	
	
}

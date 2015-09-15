package br.ufc.petsi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthenticationController {
	
	@RequestMapping( value = {"/", "/home"} )
	public ModelAndView home() {
		System.out.println("aqui");
		return new ModelAndView("login");
	}
	
	@RequestMapping("/authentication/success")
	public ModelAndView success() {
		return new ModelAndView("success");
	}
}

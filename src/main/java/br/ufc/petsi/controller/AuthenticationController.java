package br.ufc.petsi.controller;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.ufc.petsi.constants.Constants;
import br.ufc.petsi.model.Email;
import br.ufc.petsi.model.User;
import br.ufc.petsi.service.EmailService;

@Controller
public class AuthenticationController {
	
	@Inject
	private EmailService emailService;
	
	@RequestMapping( value = {"/", "/login"} )
	public ModelAndView home() {
		return new ModelAndView("login");
	}
	
	@RequestMapping("/authentication/success")
	public ModelAndView success(@RequestParam(value = "error", required = false) String error, HttpSession session) {
		ModelAndView mv = new ModelAndView("redirect:/");
		if(error != null)
			mv.addObject("error", "Login e/ou senha inválidos");
		try{
			User user = (User)this.getUserLogged(session);
			
			if(user.getRole().equals(Constants.ROLE_PROFESSIONAL))
				mv.setViewName("redirect:/professional");
			else if(user.getRole().equals(Constants.ROLE_ADMIN))
				mv.setViewName("redirect:/manager");
			else
				mv.setViewName("redirect:/patient");
		}
		catch(NullPointerException e)
		{
			System.out.println("Error: " + e);
		} 
		return mv;
	}
	
	@RequestMapping("/failureLogin")
	public ModelAndView failure()
	{
		ModelAndView mv = new ModelAndView("login");
		mv.addObject("error", "Login e/ou senha inválidos");
		return mv;
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
	
	@RequestMapping("/teste")
	public ModelAndView teste()
	{
		final Email email = new Email();
		email.setFrom("siac.ufc@gmail.com");
		email.setTo("lucashenriquejbe@gmail.com");
		email.setText("teste");
		email.setSubject("não responda este email");
		
		try {
			emailService.sendEmail(email);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("redirect:/");
	}
}

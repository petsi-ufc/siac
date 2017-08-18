package br.ufc.petsi.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.ufc.petsi.dao.ProfessionalDAO;
import br.ufc.petsi.dao.SocialServiceDAO;
import br.ufc.petsi.dao.UserDAO;
import br.ufc.petsi.dao.ldap.LdapUser;
import br.ufc.petsi.model.User;
import br.ufc.petsi.service.ProfessionalService;
import br.ufc.petsi.service.UserService;

@Controller
@Transactional
public class ProfessionalController {
	
	@Inject
	UserDAO userDAO;
	
	@Inject
	UserService userService;
	
	@Inject
	SocialServiceDAO socialServiceDAO;
	
	@Inject
	private ProfessionalService professionalService;
	
	@Inject
	private ProfessionalDAO professionalDao;
	
	@Inject
	LdapUser ldapUser;
	
	@RequestMapping("/saveProfessional")
	@ResponseBody
	public String saveProfessional(String cpf, Long idService){
		
		User user = ldapUser.getByCpf(cpf);
		
		return userService.saveProfessional(userDAO, user, idService, socialServiceDAO);
	}
	
	@RequestMapping("/getProfessionals")
	@ResponseBody
	public String getProfessionals(){
		
		return userService.getProfessionals(userDAO);
		
	}
	
	@RequestMapping("/getProfessionalsByService")
	@ResponseBody
	public String getProfessionalsByService(long serviceId){
		return professionalService.getProfessionalsByService(professionalDao, serviceId);
	}
	
	@RequestMapping("/getPatientsByName")
	@ResponseBody
	public String getPatientsByName(@RequestParam String name){
		
		return userService.getUserByName(name, ldapUser);
	}
	

}

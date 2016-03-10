package br.ufc.petsi.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.ufc.petsi.dao.ConsultationDAO;
import br.ufc.petsi.model.Patient;
import br.ufc.petsi.model.SocialService;
import br.ufc.petsi.service.ConsultationService;


@Controller
@Transactional
public class ConsultationController {
	
	@Inject
	private ConsultationService consultationService;
	
	@Inject
	private ConsultationDAO consDAO;
	
	@RequestMapping("/getConsultationsBySocialService")
	@ResponseBody
	public String getConsultationsBySocialServices(Long socialServiceId){
		SocialService socialService = new SocialService();
		socialService.setId(socialServiceId);
		return consultationService.getConsultationsBySocialService(socialService, consDAO);
	}
	
	@RequestMapping("/getConsultationsByPatient")
	@ResponseBody
	public String getConsultationsByPatient(String cpf){
		Patient p = new Patient();
		p.setCpf(cpf);
		return consultationService.getConsultationsByPatient(p, consDAO);
	}
	
}

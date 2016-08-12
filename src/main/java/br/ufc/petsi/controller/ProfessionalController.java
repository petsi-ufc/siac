package br.ufc.petsi.controller;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.ufc.petsi.dao.ProfessionalDAO;
import br.ufc.petsi.service.ProfessionalService;

@Controller
@Transactional
public class ProfessionalController {

	
	@Inject
	private ProfessionalDAO professionalDao;
	
	@Inject
	private ProfessionalService professionalService;
	
	@RequestMapping("/getProfessionalsByService")
	@ResponseBody
	public String getProfessionalsByService(long serviceId){
		return professionalService.getProfessionalsByService(professionalDao, serviceId);
	}
}

package br.ufc.petsi.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.ufc.petsi.dao.SocialServiceDAO;
import br.ufc.petsi.model.SocialService;
import br.ufc.petsi.service.SocialServiceService;

@Controller
@Transactional
public class SocialServiceController {
	
	@Inject
	private SocialServiceService serviceService;
	
	@Inject
	private SocialServiceDAO serviceDao;
	
	@RequestMapping("/registerService")
	@ResponseBody
	public String registerService(SocialService name){
		serviceService.registerService(name, this.serviceDao);
		return this.getServices();
	}
	
	@RequestMapping("/getActiveServices")
	@ResponseBody
	public String getActiveServices(){
		return serviceService.getActiveServices(this.serviceDao);
	}
	
	@RequestMapping("/getServices")
	@ResponseBody
	public String getServices(){
		return serviceService.getServices(this.serviceDao);
	}
	
	@RequestMapping("/setInactiveService")
	@ResponseBody
	public String setInactiveService(SocialService service){
		serviceService.setInactiveService(this.serviceDao, service);
		return this.getServices();
	}
	
	@RequestMapping("/setActiveService")
	@ResponseBody
	public String setActiveService(SocialService service){
		serviceService.setActiveService(this.serviceDao, service);
		return this.getServices();
	}
	
	@RequestMapping("/editService")
	@ResponseBody
	public String editService(SocialService service){
		System.out.println("[SERVIÇO QUE VAI SER EDITADO]:"+service);
		serviceService.editService(serviceDao, service);
		return this.getServices();
	}
}

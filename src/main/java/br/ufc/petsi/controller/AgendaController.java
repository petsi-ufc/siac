package br.ufc.petsi.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.ufc.petsi.dao.ConsultationDAO;
import br.ufc.petsi.model.Consultation;
import br.ufc.petsi.model.Service;

import com.google.gson.Gson;

@Controller
@Transactional
public class AgendaController {
	
	@Inject
	private ConsultationDAO conDao; 
	
	@RequestMapping("/getAgenda")
	@ResponseBody
	public String getAgenda(long serviceId){
		Service service = new Service();
		service.setId(serviceId);
		List<Consultation> list = conDao.getConsultationsByService(service);
		Gson gson = new Gson();
		String json = gson.toJson(list);
		return json;
	}
}

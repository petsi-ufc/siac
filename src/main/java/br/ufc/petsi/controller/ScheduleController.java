package br.ufc.petsi.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.ufc.petsi.dao.ScheduleDAO;

@Controller
public class ScheduleController {
	
	@Inject
	private ScheduleDAO scDao; 
	
	@RequestMapping("/getSchedules")
	@ResponseBody
	public String getSchedules(int id){
		//String gson = GsonscDao.getScheduleById(id);
		return "";
	}
}

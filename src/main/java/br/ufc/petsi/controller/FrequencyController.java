package br.ufc.petsi.controller;

import javax.inject.Inject;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.ufc.petsi.dao.FrequencyDAO;
import br.ufc.petsi.service.FrequencyService;

@Controller
@Transactional
public class FrequencyController {
	
	@Inject
	private FrequencyService frequencyService;
	
	@Inject
	private FrequencyDAO frequencyDAO;
	
	
	@Secured("ROLE_PROFESSIONAL")
	@RequestMapping("/registerFrequency")
	@ResponseBody
	public String registerFrequency(@RequestParam("json") String json){
		return frequencyService.registerFrequency(json, frequencyDAO);
	}
	
	@Secured("ROLE_PROFESSIONAL")
	@RequestMapping("/getFrequency")
	@ResponseBody
	public String getFrequency(@RequestParam("json") String json){
		return frequencyService.getFrequencyList(json, frequencyDAO);
	}
	
}

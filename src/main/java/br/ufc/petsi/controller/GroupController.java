package br.ufc.petsi.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.ufc.petsi.dao.GroupDAO;
import br.ufc.petsi.service.GroupService;

@Controller
@Transactional
public class GroupController {
	
	@Inject
	private GroupService groupService;
	
	@Inject
	private GroupDAO groupDAO;
	
	@Secured("ROLE_PROFESSIONAL")
	@RequestMapping("/createGroup")
	@ResponseBody
	public String createGroup(@RequestParam("json") String json, HttpSession session){
		return groupService.saveGroup(json, groupDAO);
	}
	
	@Secured("ROLE_PROFESSIONAL")
	@RequestMapping("/updateGroup")
	@ResponseBody
	public String updateGroup(@RequestParam("json") String json, HttpSession session){
		return groupService.updateGroup(json, groupDAO);
	}
	
	@Secured("ROLE_PROFESSIONAL")
	@RequestMapping("/addPatient")
	@ResponseBody
	public String addPatient(@RequestParam("json") String json){
		return groupService.addPatient(json, groupDAO);
	}
	
	@Secured("ROLE_PROFESSIONAL")
	@RequestMapping("/removePatient")
	@ResponseBody
	public String removePatient(@RequestParam("json") String json){
		return groupService.removePatient(json, groupDAO);
	}
	
	@Secured("ROLE_PROFESSIONAL")
	@RequestMapping("/getPatients")
	@ResponseBody
	public String getPatients(@RequestParam("json") String json){
		return groupService.listPatientsOfGroup(json, groupDAO);
	}
	
	@Secured("ROLE_PROFESSIONAL")
	@RequestMapping("/getAllGroups")
	@ResponseBody
	public String getAllGroups(@RequestParam("json") String json){
		return groupService.getAllGroups(json, groupDAO);
	}
	
	@Secured("ROLE_PROFESSIONAL")
	@RequestMapping("/closeGroup")
	@ResponseBody
	public String closeGroup(@RequestParam("json") String json){
		return groupService.closeGroup(json, groupDAO);
	}
	
	@Secured("ROLE_PROFESSIONAL")
	@RequestMapping("/openGroup")
	@ResponseBody
	public String openGroup(@RequestParam("json") String json){
		return groupService.openGroup(json, groupDAO);
	}
	
	@Secured("ROLE_PATIENT")
	@RequestMapping("/joinGroup")
	@ResponseBody
	public String joinGroup(@RequestParam("json") String json, HttpSession session){ 
		return groupService.joinGroup(json, groupDAO);
	}
	
	@Secured("ROLE_PATIENT")
	@RequestMapping("/leaveGroup")
	@ResponseBody
	public String leaveGroup(@RequestParam("json") String json, HttpSession session){
		return groupService.leaveGroup(json, groupDAO);
	}

}

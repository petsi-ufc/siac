package br.ufc.petsi.service;

import java.util.List;

import javax.inject.Named;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.ufc.petsi.constants.Constants;
import br.ufc.petsi.dao.SocialServiceDAO;
import br.ufc.petsi.dao.UserDAO;
import br.ufc.petsi.dao.ldap.LdapUser;
import br.ufc.petsi.enums.Role;
import br.ufc.petsi.model.Professional;
import br.ufc.petsi.model.SocialService;
import br.ufc.petsi.model.User;
import br.ufc.petsi.util.Response;
import br.ufc.petsi.util.UserExclusionStrategy;

@Named
public class UserService {
	
	public String getUserByName(String name, LdapUser userDAO){
		
		List<User> users = userDAO.getByNameLike(name);
		
		Gson gson = new Gson();
		String json = "";
		
		json = gson.toJson(users);
		
		return json;
	}
	
	public String saveProfessional(UserDAO userDAO, User user, Long idService, SocialServiceDAO socialServiceDAO){
		
		Response response = new Response();
		
		SocialService service = socialServiceDAO.getServiceById(idService);
		
		if(userDAO.isExistent(user.getCpf(), Constants.ROLE_PROFESSIONAL)){
			
			response.setCode(Response.ERROR);
			response.setMessage("Profissional já está cadastrado!");

		}else{
			
			Professional professional = new Professional(user.getCpf(), user.getName(), user.getEmail(), Role.PF.getRole(), service, null);
			userDAO.save(professional);
			
			response.setCode(Response.SUCCESS);
			response.setMessage("Profissional cadastrado com sucesso");
			
		}
		
		Gson gson = new Gson();
		
		
		return gson.toJson(response);
	}
	
	public String getProfessionals(UserDAO userDAO){
		
		List<User> users = userDAO.getUsersByRole(Constants.ROLE_PROFESSIONAL);
		Gson gson = new GsonBuilder()
		        .setExclusionStrategies(new UserExclusionStrategy())
		        .create();
		
		return gson.toJson(users);
		
	}
}

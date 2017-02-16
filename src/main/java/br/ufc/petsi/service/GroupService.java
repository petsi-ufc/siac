package br.ufc.petsi.service;

import javax.inject.Named;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.ufc.petsi.dao.GroupDAO;
import br.ufc.petsi.model.Group;
import br.ufc.petsi.model.Patient;
import br.ufc.petsi.model.Professional;
import br.ufc.petsi.util.Response;

@Named
public class GroupService {
	
	public String saveGroup(String json, Professional facilitator , GroupDAO gdao){
		Gson gson = new Gson();
		Response response = new Response();
		
		try{
			Group group = gson.fromJson(json, Group.class);
			//group.setFacilitator(facilitator);
			gdao.save(group);
		}catch (Exception e) {
			response.setCode(Response.ERROR);
			response.setMessage("Ops, não foi possível criar o grupo");
			return gson.toJson(response);
		}
		
		response.setCode(Response.SUCCESS);
		response.setMessage("Grupo criado com sucesso!");
		return gson.toJson(response);
	}
	
	public String closeGroup(String json, GroupDAO gdao){
		Gson gson = new Gson();
		Response response = new Response();
		
		try{
			Group group = gson.fromJson(json, Group.class);
			group.setOpenGroup(false);
			gdao.update(group);
		}catch (Exception e) {
			response.setCode(Response.ERROR);
			response.setMessage("Ops, não foi possível fechar o grupo");
			return gson.toJson(response);
		}
		
		response.setCode(Response.SUCCESS);
		response.setMessage("O grupo foi fechado!");
		return gson.toJson(response);
	}
	
	public String openGroup(String json, GroupDAO gdao){
		Gson gson = new Gson();
		Response response = new Response();
		
		try{
			Group group = gson.fromJson(json, Group.class);
			group.setOpenGroup(true);
			gdao.update(group);
		}catch (Exception e) {
			response.setCode(Response.ERROR);
			response.setMessage("Ops, não foi possível abrir o grupo");
			return gson.toJson(response);
		}
		
		response.setCode(Response.SUCCESS);
		response.setMessage("O grupo foi aberto!");
		return gson.toJson(response);
	}
	
	public String addPatient(String json, GroupDAO gdao){
		Gson gson = new Gson();
		Response response = new Response();
		
		try{
			
			Group group = gson.fromJson(json, Group.class);
			gdao.addPatient(group, group.getPatients().get(0));
			
			response.setCode(Response.SUCCESS);
			response.setMessage("O paciente adicionado!");
			return gson.toJson(response);
			
		}catch (Exception e) {
			response.setCode(Response.ERROR);
			response.setMessage("Ops, não foi possível adicionar o paciente!");
			return gson.toJson(response);
		}
		
	}
	
	public String removePatient(String json, GroupDAO gdao){
		Gson gson = new Gson();
		Response response = new Response();
		
		try{
			
			Group group = gson.fromJson(json, Group.class);
			gdao.removePatient(group, group.getPatients().get(0));
			
			response.setCode(Response.SUCCESS);
			response.setMessage("O paciente removido!");
			return gson.toJson(response);
			
		}catch (Exception e) {
			response.setCode(Response.ERROR);
			response.setMessage("Ops, não foi possível remover o paciente!");
			return gson.toJson(response);
		}
	}
	
	public String listPatientsOfGroup(String json, GroupDAO gdao){
		Gson gson = new Gson();
		Response response = new Response();
		try{
			
			Group group= gson.fromJson(json, Group.class);
			return gson.toJson(gdao.getPatients(group));
			
		}catch (Exception e) {
			response.setCode(Response.ERROR);
			response.setMessage("Ops, não foi possível listar os pacientes deste grupo!");
			return gson.toJson(response);
		}
	}
	
	public String getAllGroups(String json, GroupDAO gdao){
		Gson gson = new Gson();
		Response response = new Response();
		try{
			
			Professional professional = gson.fromJson(json, Professional.class);
			return gson.toJson(gdao.getAllGroups(professional));
			
		}catch (Exception e) {
			response.setCode(Response.ERROR);
			response.setMessage("Ops, não foi possível listar os grupos deste profissional!");
			return gson.toJson(response);
		}
	}
	
	public String joinGroup(String json, Patient patient, GroupDAO gdao){
		return null;
	}
	
	public String leaveGroup(String json, Patient patient, GroupDAO gdao){
		return null;
	}
	
}

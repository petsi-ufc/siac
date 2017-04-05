package br.ufc.petsi.service;

import javax.inject.Named;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.ufc.petsi.dao.GroupDAO;
import br.ufc.petsi.model.Group;
import br.ufc.petsi.model.Patient;
import br.ufc.petsi.model.Professional;
import br.ufc.petsi.util.Response;

@Named
public class GroupService {
	
	public String saveGroup(String json, GroupDAO gdao){
		Gson gson = new Gson();
		Response response = new Response();
		
		try{
			Group group = gson.fromJson(json, Group.class);
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
	
	public String updateGroup(String json, GroupDAO gdao){
		Gson gson = new Gson();
		Response response = new Response();
		
		try{
			
			Group group = gson.fromJson(json, Group.class);
			Group old = gdao.find(group.getId());
			old.setTitle(group.getTitle());
			old.setOpenGroup(group.isOpenGroup());
			old.setPatientLimit(group.getPatientLimit());
			gdao.update(old);
			
		}catch (Exception e) {
			response.setCode(Response.ERROR);
			response.setMessage("Ops, não foi possível atualizar os dados do grupo");
			return gson.toJson(response);
		}
		
		response.setCode(Response.SUCCESS);
		response.setMessage("Os dados do grupo foram atualizado!");
		return gson.toJson(response);
	}
	
	public String closeGroup(String json, GroupDAO gdao){
		Gson gson = new Gson();
		Response response = new Response();
		
		try{
			Group group = gson.fromJson(json, Group.class);
			group = gdao.find(group.getId());
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
			group = gdao.find(group.getId());
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
			for (Patient patient : group.getPatients()) {
				gdao.addPatient(group, patient);
			}
			
			response.setCode(Response.SUCCESS);
			response.setMessage("O(s) paciente(s) foi(ram) adicionado(s)!");
			return gson.toJson(response);
			
		}catch (Exception e) {
			e.printStackTrace();
			response.setCode(Response.ERROR);
			response.setMessage("Ops, não foi possível adicionar o(s) paciente(s)!");
			return gson.toJson(response);
		}
		
	}
	
	public String removePatient(String json, GroupDAO gdao){
		Gson gson = new Gson();
		Response response = new Response();
		
		try{
			
			Group group = gson.fromJson(json, Group.class);
			for (Patient patient : group.getPatients()) {
				gdao.removePatient(group, patient);
			}
			
			response.setCode(Response.SUCCESS);
			response.setMessage("O(s) paciente(s) foi(ram) removido(s)!");
			return gson.toJson(response);
			
		}catch (Exception e) {
			response.setCode(Response.ERROR);
			response.setMessage("Ops, não foi possível remover o(s) paciente(s)!");
			return gson.toJson(response);
		}
	}
	
	public String listPatientsOfGroup(String json, GroupDAO gdao){
		Gson gson = new Gson();
		ObjectMapper mapper = new ObjectMapper();
		Response response = new Response();
		
		try{
			
			Group group = gson.fromJson(json, Group.class);
			group.setPatients(gdao.getPatients(group));
			return mapper.writeValueAsString(group);
			
		}catch (Exception e) {
			e.printStackTrace();
			response.setCode(Response.ERROR);
			response.setMessage("Ops, não foi possível listar os pacientes deste grupo!");
			return gson.toJson(response);
		}
	}
	
	public String getAllGroups(String json, GroupDAO gdao){
		Gson gson = new Gson();
		ObjectMapper mapper = new ObjectMapper();
		Response response = new Response();
		try{
			
			Professional professional = gson.fromJson(json, Professional.class);
			professional.setListGroups(gdao.getAllGroups(professional));
			return mapper.writeValueAsString(professional);
			
		}catch (Exception e) {
			e.printStackTrace();
			response.setCode(Response.ERROR);
			response.setMessage("Ops, não foi possível listar os grupos deste profissional!");
			return gson.toJson(response);
		}
	}
	
	public String joinGroup(String json, GroupDAO gdao){
		Gson gson = new Gson();
		Response response = new Response();
		
		try{
			Group group = gson.fromJson(json, Group.class);
			for (Patient patient : group.getPatients()) {
				gdao.addPatient(group, patient);
			}
			
			response.setCode(Response.SUCCESS);
			response.setMessage("Bem-vindo ao grupo!");
			return gson.toJson(response);
			
		}catch (Exception e) {
			e.printStackTrace();
			response.setCode(Response.ERROR);
			response.setMessage("Ops, não foi possível adicioná-lo ao grupo!");
			return gson.toJson(response);
		}
	}
	
	public String leaveGroup(String json, GroupDAO gdao){
		Gson gson = new Gson();
		Response response = new Response();
		
		try{
			
			Group group = gson.fromJson(json, Group.class);
			for (Patient patient : group.getPatients()) {
				gdao.removePatient(group, patient);
			}
			
			response.setCode(Response.SUCCESS);
			response.setMessage("Você saiu do grupo!");
			return gson.toJson(response);
			
		}catch (Exception e) {
			response.setCode(Response.ERROR);
			response.setMessage("Ops, não foi possível removê-lo deste grupo!");
			return gson.toJson(response);
		}
	}
	
}

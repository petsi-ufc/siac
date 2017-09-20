package br.ufc.petsi.service;

import java.util.List;

import javax.inject.Named;

import org.eclipse.jdt.internal.compiler.impl.Constant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.ufc.petsi.constants.Constants;
import br.ufc.petsi.dao.GroupDAO;
import br.ufc.petsi.dao.UserDAO;
import br.ufc.petsi.model.Group;
import br.ufc.petsi.model.Patient;
import br.ufc.petsi.model.Professional;
import br.ufc.petsi.util.Response;

@Named
public class GroupService {
	
	public String saveGroup(String json, Professional professional, GroupDAO gdao){
		Gson gson = new Gson();
		Response response = new Response();
		
		try{
			Group group = gson.fromJson(json, Group.class);
			group.setFacilitator(professional);
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
			
			System.out.println("[UPDATE at UPDATE GROUP]: "+old.getTitle());
			gdao.update(old);
			
		}catch (Exception e) {
			e.printStackTrace();
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
			System.out.println(json);
			Group group = gson.fromJson(json, Group.class);
			group = gdao.find(group.getId());
			System.out.println("[CLOSE GROUP]: "+(group == null));
			group.setOpenGroup(false);
			gdao.update(group);
			
			response.setCode(Response.SUCCESS);
			response.setMessage("O grupo foi fechado!");
			return gson.toJson(response);
		}catch (Exception e) {
			System.out.println(e);
			response.setCode(Response.ERROR);
			response.setMessage("Ops, não foi possível fechar o grupo");
			return gson.toJson(response);
		}
		
		
	}
	
	public String openGroup(String json, GroupDAO gdao){
		Gson gson = new Gson();
		Response response = new Response();
		
		try{
			Group group = gson.fromJson(json, Group.class);
			group = gdao.find(group.getId());
			group.setOpenGroup(true);
			gdao.update(group);
			
			
			response.setCode(Response.SUCCESS);
			response.setMessage("O grupo foi aberto!");
			return gson.toJson(response);
		}catch (Exception e) {
			System.out.println(e);
			response.setCode(Response.ERROR);
			response.setMessage("Ops, não foi possível abrir o grupo");
			return gson.toJson(response);
		}
	}
	
	public String addPatient(String json, GroupDAO gdao, UserDAO udao){
		Gson gson = new Gson();
		Response response = new Response();
		
		try{
			Group group = gson.fromJson(json, Group.class);
			for (Patient patient : group.getPatients()) {
				if(udao.getByCpf(patient.getCpf(), Constants.ROLE_PATIENT)  == null){
					udao.save(patient);
				}
				gdao.addPatient(group, (Patient)udao.getByCpf(patient.getCpf(), Constants.ROLE_PATIENT));
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
	
	public String removePatient(String json, GroupDAO gdao, UserDAO udao){
		Gson gson = new Gson();
		Response response = new Response();
		
		try{
			
			Group group = gson.fromJson(json, Group.class);
			for (Patient patient : group.getPatients()) {
				gdao.removePatient(group, (Patient)udao.getByCpf(patient.getCpf(), Constants.ROLE_PATIENT));
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
		Response response = new Response();
		
		try{
			
			Group group = gson.fromJson(json, Group.class);
			response.setCode(Response.SUCCESS);
			System.out.println("[QTD PATIENTS]: "+gdao.getPatients(group).size());
			response.setMessage(gson.toJson(gdao.getPatients(group)));
			return gson.toJson(response);
			
		}catch (Exception e) {
			e.printStackTrace();
			response.setCode(Response.ERROR);
			response.setMessage("Ops, não foi possível listar os pacientes deste grupo!");
			return gson.toJson(response);
		}
	}
	
	public String listConsultationsOfGroup(String json, GroupDAO gdao){
		Gson gson = new Gson();
		Response response = new Response();
		
		try{
			
			Group group = gson.fromJson(json, Group.class);
			response.setCode(Response.SUCCESS);
			response.setMessage(gson.toJson(gdao.getConsultations(group)));
			return gson.toJson(response);
			
		}catch (Exception e) {
			e.printStackTrace();
			response.setCode(Response.ERROR);
			response.setMessage("Ops, não foi possível listar as consultas deste grupo!");
			return gson.toJson(response);
		}
	}
	
	public String getAllGroups(Professional professional, GroupDAO gdao){
		Gson gson = new Gson();
		ObjectMapper mapper = new ObjectMapper();
		Response response = new Response();
		try{
			List<Group> groups = gdao.getAllGroups(professional);
			response.setCode(Response.SUCCESS);
			response.setMessage(mapper.writeValueAsString(groups));
			return gson.toJson(response);
			
		}catch (Exception e) {
			System.out.println("[ERRO at getAllGroups]: "+e);
			response.setCode(Response.ERROR);
			response.setMessage("Ops, não foi possível listar os grupos deste profissional!");
			return gson.toJson(response);
		}
	}
	
	public String getGroupsFree(GroupDAO gdao){
		Gson gson = new Gson();
		ObjectMapper mapper = new ObjectMapper();
		Response response = new Response();
		try{
			response.setCode(Response.SUCCESS);
			response.setMessage(mapper.writeValueAsString(gdao.getGroupsFree()));
			System.out.println(gson.toJson(response));
			return gson.toJson(response);
		}catch (Exception e) {
			System.out.println(e);
			response.setCode(Response.ERROR);
			response.setMessage("Ops, não foi possível listar os grupos!");
			return gson.toJson(response);
		}
	}
	
	public String getGroupsOfPatient(Patient patient, GroupDAO gdao){
		Gson gson = new Gson();
		ObjectMapper mapper = new ObjectMapper();
		Response response = new Response();
		
		try{
			response.setCode(Response.SUCCESS);
			response.setMessage(mapper.writeValueAsString(gdao.getGroupsByPatient(patient)));
			return gson.toJson(response);
		}catch (Exception e) {
			e.printStackTrace();
			response.setCode(Response.ERROR);
			response.setMessage("Ops, não foi possível retornar seus grupos!");
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
			System.out.println("Json Chegando => " + json);
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

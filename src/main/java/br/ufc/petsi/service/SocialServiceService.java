package br.ufc.petsi.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.inject.Named;

import com.google.gson.Gson;

import br.ufc.petsi.dao.SocialServiceDAO;
import br.ufc.petsi.model.SocialService;

@Named
public class SocialServiceService {
	
	public void registerService(SocialService service, SocialServiceDAO serviceDao){
		service.setActive(true);
		serviceDao.save(service);
	}
	
	public String getActiveServices(SocialServiceDAO serviceDao){
		List<SocialService> services = serviceDao.getActiveServices();
		
		Gson gson = new Gson();
		
		String json = "";
		json = gson.toJson(services);
		
		return json;
	}
	
	public String getServices(SocialServiceDAO serviceDao){
		
		List<SocialService> services = serviceDao.getAllServices();
		
		Gson gson = new Gson();
		
		String json = "";
		json = gson.toJson(services);
		
		return json;
	}
	
	public void setInactiveService(SocialServiceDAO serviceDao, SocialService service){
		
		SocialService service2 = serviceDao.getServiceById(service.getId());
		
		service2.setActive(false);
		serviceDao.edit(service2);
		
	}
	
	public void setActiveService(SocialServiceDAO serviceDao, SocialService service){
		
		SocialService service2 = serviceDao.getServiceById(service.getId());
		
		service2.setActive(true);
		serviceDao.edit(service2);
	}
	
	public void editService(SocialServiceDAO serviceDao, SocialService service){
		
		SocialService service2 = serviceDao.getServiceById(service.getId());
		
		try {
			service2.setName(new String(service.getName().getBytes(),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		serviceDao.edit(service2);
	}

}

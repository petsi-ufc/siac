package br.ufc.petsi.dao;

import java.util.List;

import br.ufc.petsi.model.SocialService;

public interface SocialServiceDAO {
	public void save(SocialService service);
	public SocialService getServiceById(long id);
	public SocialService getServiceByName(String name);
	public List<SocialService> getAllServices();
	public List<SocialService> getActiveServices();
	public void edit(SocialService service);
}

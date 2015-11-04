package br.ufc.petsi.dao;

import java.util.List;

import br.ufc.petsi.model.Service;

public interface ServiceDAO {
	public void save(Service service);
	public Service getServiceById(long id);
	public Service getServiceByName(String name);
	public List<Service> getAllServices();
	public void edit(Service service);
}

package br.ufc.petsi.dao;

import br.ufc.petsi.model.Service;

public interface ServiceDAO {
	public void save(Service service);
	public Service getServiceById(long id);
	public Service getServiceByName(String name);
}

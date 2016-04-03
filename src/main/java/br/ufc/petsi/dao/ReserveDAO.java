package br.ufc.petsi.dao;

import br.ufc.petsi.model.Reserve;

public interface ReserveDAO {
	
	public void save(Reserve reserve);
	public void update(Reserve reserve);
}

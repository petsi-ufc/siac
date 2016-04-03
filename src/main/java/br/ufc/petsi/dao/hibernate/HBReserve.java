package br.ufc.petsi.dao.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.ufc.petsi.dao.ReserveDAO;
import br.ufc.petsi.model.Reserve;

@Repository
public class HBReserve implements ReserveDAO{
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public void save(Reserve reserve) {
		this.manager.persist(reserve);
		
	}

	@Override
	public void update(Reserve reserve) {
		this.manager.merge(reserve);
		
	}

}

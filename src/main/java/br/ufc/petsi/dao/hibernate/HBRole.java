package br.ufc.petsi.dao.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.ufc.petsi.dao.RoleDAO;
import br.ufc.petsi.model.Role;

@Repository
public class HBRole implements RoleDAO {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public void save(Role role) {
		manager.persist(role);
	}

	@Override
	public void update(Role role) {
		manager.merge(role);
	}

	@Override
	public Role getRoleById(long id) {
		return manager.find(Role.class, id);	
	}

	@Override
	public List<Role> getRoles() {
		return manager.createQuery("select r from Role r").getResultList();
	}
	
}

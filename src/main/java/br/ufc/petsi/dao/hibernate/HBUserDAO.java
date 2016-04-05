package br.ufc.petsi.dao.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.ufc.petsi.dao.UserDAO;
import br.ufc.petsi.model.User;

@Repository
public class HBUserDAO implements UserDAO{

	@PersistenceContext
	protected EntityManager manager;
	
	@Override
	public List<User> getAll() {
		List<User> listUsers = null;
		try {
			listUsers = manager.createQuery("from User").getResultList();
		} catch (NoResultException e) {
			System.out.println("Erro HBDAO getALL: "+e);
		}
		return listUsers;
	}

	@Override
	public User getByCpf(String cpf, String role) {
		User u = null;
		try{
			Query query = manager.createQuery("from users WHERE cpf = :paramCpf AND role = :paramRole");
			query.setParameter("paramCpf", cpf);
			query.setParameter("paramRole", role);
			u = (User) query.getSingleResult();
		}catch(NoResultException e){
			System.out.println("Erro HBDAO getALL: "+e);	
		}
		return u;
	}

	@Override
	public List<User> getByCpfList(String cpf) {
		List<User> listUsers = null;
		try {
			Query query = manager.createQuery("from User WHERE cpf = :paramCpf"); 
			query.setParameter("paramCpf", cpf);
			listUsers = query.getResultList();
		} catch (NoResultException e) {
			System.out.println("Erro HBDAO getALL: "+e);
		}
		return listUsers;
	}

	@Override
	public List<User> getByNameLike(String name) {
		List<User> listUsers = null;
		try{
			Query query = manager.createQuery("from User WHERE lower(name) LIKE '%'+lower(:paramName)+'%'");
			query.setParameter("paramName", name);
			listUsers = query.getResultList();
		}catch(NoResultException e){
			System.out.println("Erro HBDAO getALL: "+e);	
		}
		return listUsers;
	}

	@Override
	@Transactional
	public void save(User u) {
		this.manager.persist(u);
	}
}

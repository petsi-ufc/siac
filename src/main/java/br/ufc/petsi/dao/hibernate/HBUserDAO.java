package br.ufc.petsi.dao.hibernate;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.ufc.petsi.dao.UserDAO;
import br.ufc.petsi.model.User;

@Named
@Repository
public class HBUserDAO implements UserDAO{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<User> getAll() {
		List<User> listUsers = null;
		try {
			listUsers = manager.createQuery("from User").getResultList();
		} catch (NoResultException e) {
			System.out.println("Erro USERDAO getALL: "+e);
		}
		return listUsers;
	}

	@Override
	public User getByCpf(String cpf) {
		User u = null;
		try{
			Query query = manager.createQuery("from users WHERE cpf = :paramCpf");
			query.setParameter("paramCpf", cpf);
			u = (User) query.getSingleResult();
		}catch(NoResultException e){
			System.out.println("Erro USERDAO getALL: "+e);	
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
			System.out.println("Erro USERDAO getALL: "+e);
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
			System.out.println("Erro USERDAO getALL: "+e);	
		}
		return listUsers;
	}

	@Override
	public boolean authenticate(String login, String password) {
		return false;
	}

}

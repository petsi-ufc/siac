package br.ufc.petsi.dao.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.ufc.petsi.constants.Constants;
import br.ufc.petsi.dao.UserDAO;
import br.ufc.petsi.model.Administrator;
import br.ufc.petsi.model.Patient;
import br.ufc.petsi.model.Professional;
import br.ufc.petsi.model.SocialService;
import br.ufc.petsi.model.User;
import br.ufc.petsi.util.LogGenerator;

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
			LogGenerator.getInstance().log(e, "Erro ao buscar todos os usuários");
			System.out.println("Erro HBDAO getALL: "+e);
		}
		return listUsers;
	}

	@Override
	public boolean isExistent(String cpf, String role){

		try {
			Query query = manager.createQuery("SELECT count(u) from users u WHERE u.cpf = :cpf and u.role = :role");
			query.setParameter("cpf", cpf);
			query.setParameter("role", role);

			long n = (long) query.getSingleResult();

			if(n > 0) return true;

		} catch (NoResultException e) {
			LogGenerator.getInstance().log(e, "Erro ao conferir se um usuário já existe");
			System.out.println("Erro HBDAO getALL: "+e);
		}

		return false;
	}

	@Override
	public User getByCpf(String cpf, String role) {
		System.out.println("CPF: "+cpf+" - Role: "+ role);
		User u = null;
		try{
			Query query = manager.createNativeQuery("SELECT cpf, email, name, role, id, id_social_service FROM users WHERE cpf = :paramCpf AND role = :paramRole");
			query.setParameter("paramCpf", cpf);
			query.setParameter("paramRole", role);
			List<Object[]> objs = (List<Object[]>) query.getResultList();
			if(objs.size() > 0){
				Object[] obj = (Object[]) objs.get(0);
				if(role.equals(Constants.ROLE_PROFESSIONAL)){
					SocialService service = new SocialService();
					if(obj[5] != null){
						service.setId(Long.parseLong(String.valueOf(obj[5])));
					}
					u = new Professional(String.valueOf(obj[0]), String.valueOf(obj[2]), String.valueOf(obj[1]), String.valueOf(obj[3]), service, null);
				}else if(role.equals(Constants.ROLE_PATIENT)){
					u = new Patient(String.valueOf(obj[0]), String.valueOf(obj[2]), String.valueOf(obj[1]), String.valueOf(obj[3]));
				}else if(role.equals(Constants.ROLE_ADMIN)){
					u = new Administrator(String.valueOf(obj[0]), String.valueOf(obj[2]), String.valueOf(obj[1]), String.valueOf(obj[3]));
				}
				u.setId(Long.parseLong(String.valueOf(obj[4])));
			}
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
			LogGenerator.getInstance().log(e, "Erro ao pegar um usuário pelo CPF");
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
			LogGenerator.getInstance().log(e, "Erro ao pegar um usuário pelo nome");
			System.out.println("Erro HBDAO getALL: "+e);	
		}
		return listUsers;
	}
	

	@Override
	@Transactional
	public void save(User u) {
		this.manager.persist(u);
	}

	@Override
	public List<User> getUsersByRole(String role) {
		List<User> listUsers = null;
		try{
			
			Query query = manager.createQuery("from users WHERE role = :role");
			query.setParameter("role", role);
			
			listUsers = (List<User>) query.getResultList();
			
		}catch(NoResultException e){
			System.out.println("Erro HBDAO getALL: "+e);
			LogGenerator.getInstance().log(e, "Erro ao pegar um usuário pelo Papel");
		}
		
		return listUsers;
	}
	
}

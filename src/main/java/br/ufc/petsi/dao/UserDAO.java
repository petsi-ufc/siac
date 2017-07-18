package br.ufc.petsi.dao;

import java.util.List;

import br.ufc.petsi.model.Patient;
import br.ufc.petsi.model.User;

public interface UserDAO {
	public List<User> getAll();
	public User getByCpf(String cpf, String role);
	public List<User> getByCpfList(String cpf);
	public List<User> getByNameLike(String name);
	public void save(User u);
	public boolean isExistent(String cpf, String role);
	public List<User> getUsersByRole(String role);

}

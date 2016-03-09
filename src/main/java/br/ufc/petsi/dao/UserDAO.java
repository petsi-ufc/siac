package br.ufc.petsi.dao;

import java.util.List;

import br.ufc.petsi.model.User;

public interface UserDAO {
	public List<User> getAll();
	public User getByCpf(String cpf);
	public List<User> getByCpfList(String cpf);
	public List<User> getByNameLike(String name);
	public boolean authenticate(String login, String password);
}

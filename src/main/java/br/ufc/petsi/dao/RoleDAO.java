package br.ufc.petsi.dao;

import java.util.List;

import br.ufc.petsi.model.Role;

public interface RoleDAO {
	public void save(Role role);
	public void update(Role role);
	public Role getRoleById(long id);
	public List<Role> getRoles();
}

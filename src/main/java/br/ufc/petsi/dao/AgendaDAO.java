package br.ufc.petsi.dao;

import br.ufc.petsi.model.Agenda;

public interface AgendaDAO {
	public void save(Agenda ag);
	public void update(Agenda ag);
	public Agenda getAgendaById(long id);
	public Agenda getAgendaByUserCPF(String cpf);
}

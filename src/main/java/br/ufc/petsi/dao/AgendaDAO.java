package br.ufc.petsi.dao;

import java.util.List;

import br.ufc.petsi.model.Agenda;

public interface AgendaDAO {
	public void save(Agenda ag);
	public Agenda getAgendaById(long id);
	public List<Agenda> getAgendaListByUserId(long idUser);
}

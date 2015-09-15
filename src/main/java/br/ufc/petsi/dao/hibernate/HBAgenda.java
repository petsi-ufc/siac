package br.ufc.petsi.dao.hibernate;

import java.util.List;
import javax.inject.Inject;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import br.ufc.petsi.dao.AgendaDAO;
import br.ufc.petsi.model.Agenda;


@Repository
public class HBAgenda implements AgendaDAO{
	
	@Inject
	private SessionFactory sessionFactory;
	
	@Override
	public void save(Agenda ag) {
		getSession().persist(ag);
	}

	@Override
	public Agenda getAgendaById(long id) {
		Query query = getSession().createQuery("SELECT ag FROM Agenda ag WHERE ag.id = :id");
		query.setParameter("id", id);
		return (Agenda) query.uniqueResult();
	}

	@Override
	public List<Agenda> getAgendaListByUserId(long idUser) {
		Query query = getSession().createQuery("SELECT ag FROM Agenda ag WHERE ag.userId = :userId");
		query.setParameter("userId", idUser);
		List<Agenda> agendas = query.list();
		return agendas;
	}
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}

}

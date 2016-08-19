package br.edu.ifpb.tsi.gcd.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.jboss.logging.Logger;

import br.edu.ifpb.tsi.gcd.model.Event;


public class EventoDAO extends GenericDAOJPAImpl<Event, Long>{
	private static Logger logger = Logger.getLogger(EventoDAO.class);
	
	public EventoDAO() {
		this(PersistenceUtil.getCurrentEntityManager());
	}

	public EventoDAO(EntityManager em) {
		super(em);
	}
	
	public Event read(Event e){
		try{
			Query q = this.getEntityManager().createQuery("select e from Evento e where e.id ='"+e.getId()+"' ");
			return (Event) q.getSingleResult();
		}catch(NoResultException e1){
			return null;
		}
	}
	
	public List<Event> findAll() throws DAOException{
		List<Event> eventos = null;
		try {
			Query q = this.getEntityManager()
					.createQuery("from Evento e");
			eventos = (List<Event>) q.getResultList();
		} catch (HibernateException e) {}
		return eventos;
	}
	
	public List<Event> findAll(Integer idClube) throws DAOException{
		List<Event> eventos = null;
		try {
			Query q = this.getEntityManager()
					.createQuery("from Evento e where e.clube.id = '"+idClube+"' ");
			eventos = (List<Event>) q.getResultList();
		} catch (HibernateException e) {}
		return eventos;
	}
	
}

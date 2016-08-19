package br.edu.ifpb.tsi.gcd.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import br.edu.ifpb.tsi.gcd.model.Distrital;


public class DistritalDAO extends GenericDAOJPAImpl<Distrital, Long>{
	private static Logger logger = Logger.getLogger(DistritalDAO.class);
	
	public DistritalDAO() {
		this(PersistenceUtil.getCurrentEntityManager());
	}

	public DistritalDAO(EntityManager em) {
		super(em);
	}
	
	public List<Distrital> findAll() throws DAOException{
		List<Distrital> distritais = new ArrayList<Distrital>();
		try{
			Query q = this.getEntityManager().createQuery("from Distrital d");
			distritais = (List<Distrital>) q.getResultList();
		}catch (NoResultException nre){
			return null;
		}
		return distritais;
	}
	
	public Distrital findByLogin(String login){
		Distrital d = new Distrital();
		try{
			Query q = this.getEntityManager().createQuery("from Distrital u where u.login='"+login+"'");
			d = (Distrital) q.getSingleResult();
		}catch (NoResultException nre){
			return null;
		}
		return d;
	}
}

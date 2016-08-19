package br.edu.ifpb.tsi.gcd.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.jboss.logging.Logger;

import br.edu.ifpb.tsi.gcd.model.Clube;
import br.edu.ifpb.tsi.gcd.model.Desbravador;


public class ClubeDAO extends GenericDAOJPAImpl<Clube, Long>{
	private static Logger logger = Logger.getLogger(ClubeDAO.class);
	
	public ClubeDAO() {
		this(PersistenceUtil.getCurrentEntityManager());
	}

	public ClubeDAO(EntityManager em) {
		super(em);
	}
	
	public Clube read(Clube c){
		try{
			Query q = this.getEntityManager().createQuery("select c from Clube c where c.id ='"+c.getId()+"' ");
			return (Clube) q.getSingleResult();
		}catch(NoResultException e){
			return null;
		}
	}
	
	public Clube read(Integer id){
		try{
			Query q = this.getEntityManager().createQuery("select c from Clube c where c.id ='"+id+"' ");
			return (Clube) q.getSingleResult();
		}catch(NoResultException e){
			return null;
		}
	}
	
	public List<Clube> findAll() throws DAOException{
		List<Clube> clubes = new ArrayList<Clube>();
		try {
			Query q = this.getEntityManager().createQuery("from Clube c");
			clubes = (List<Clube>) q.getResultList();
		}catch (NoResultException nre){
			return null;
		}
		return clubes;
	}


	public Clube findByDiretor(Desbravador d) {
		Clube c = new Clube();
		try{
			Query q = this.getEntityManager().createQuery("from Clube c where c.diretor='"+d.getId()+"'");
			c = (Clube) q.getSingleResult();
		}catch (NoResultException nre){
			return null;
		}
		
		return c;
	}
}

package br.edu.ifpb.tsi.gcd.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import br.edu.ifpb.tsi.gcd.model.Classe;
import br.edu.ifpb.tsi.gcd.model.Unidade;


public class ClasseDAO extends GenericDAOJPAImpl<Classe, Long>{
	private static Logger logger = Logger.getLogger(ClasseDAO.class);
	
	public ClasseDAO() {
		this(PersistenceUtil.getCurrentEntityManager());
	}

	public ClasseDAO(EntityManager em) {
		super(em);
	}
	
	public Classe read(Classe c){
		try{
			Query q = this.getEntityManager().createQuery("select c from Classe c where c.id ='"+c.getId()+"' ");
			return (Classe) q.getSingleResult();
		}catch(NoResultException e){
			return null;
		}
	}
	
}

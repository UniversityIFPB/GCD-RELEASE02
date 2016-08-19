package br.edu.ifpb.tsi.gcd.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.jboss.logging.Logger;

import br.edu.ifpb.tsi.gcd.model.Unidade;
import br.edu.ifpb.tsi.gcd.model.Unidade;


public class UnidadeDAO extends GenericDAOJPAImpl<Unidade, Long>{
	private static Logger logger = Logger.getLogger(UnidadeDAO.class);
	
	public UnidadeDAO() {
		this(PersistenceUtil.getCurrentEntityManager());
	}

	public UnidadeDAO(EntityManager em) {
		super(em);
	}
	
	public Unidade read(Unidade u){
		try{
			Query q = this.getEntityManager().createQuery("select u from Unidade u where u.id ='"+u.getId()+"' ");
			return (Unidade) q.getSingleResult();
		}catch(NoResultException e){
			return null;
		}
	}
	
	public Unidade findById(long id){
		try{
			Query q = this.getEntityManager().createQuery("select u from Unidade u where u.id ='"+id+"' ");
			return (Unidade) q.getSingleResult();
		}catch(NoResultException e){
			return null;
		}
	}
	
	public Unidade findById(Integer id){
		try{
			Query q = this.getEntityManager().createQuery("select u from Unidade u where u.id ='"+id+"' ");
			return (Unidade) q.getSingleResult();
		}catch(NoResultException e){
			return null;
		}
	}
	
	public List<Unidade> findAll() throws DAOException{
		List<Unidade> unidades = null;
		try {
			Query q = this.getEntityManager()
					.createQuery("from Unidade u");
			unidades = (List<Unidade>) q.getResultList();
		} catch (HibernateException e) {
			throw new DAOException("Erro ao tentar pegar unidades", e);
		}
		return unidades;
	}
}

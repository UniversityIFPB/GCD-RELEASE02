package br.edu.ifpb.tsi.gcd.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.edu.ifpb.tsi.gcd.model.Clube;
import br.edu.ifpb.tsi.gcd.model.Desbravador;

public class DesbravadorDAO extends GenericDAOJPAImpl<Desbravador, Long>{

	public DesbravadorDAO() {
		this(PersistenceUtil.getCurrentEntityManager());
	}

	public DesbravadorDAO(EntityManager em) {
		super(em);
	}
	
	public List<Desbravador> findAll() throws DAOException{
		List<Desbravador> desbravadores = new ArrayList<Desbravador>();
		try {
			Query q = this.getEntityManager().createQuery("from Desbravador d");
			desbravadores = (List<Desbravador>) q.getResultList();
		}catch (NoResultException nre){
			return null;
		}
		return desbravadores;
	}

	public Desbravador findByLogin(String login) {
		Desbravador d = new Desbravador();
		try{
			Query q = this.getEntityManager().createQuery("from Desbravador d where d.login='"+login+"'");
			d = (Desbravador) q.getSingleResult();
		}catch (NoResultException nre){
			return null;
		}
		return d;
	}
	
	@SuppressWarnings("unchecked")
	public List<Desbravador> findByClube(Clube c) throws DAOException{
		List<Desbravador> desbravadores = new ArrayList<Desbravador>();
		try {
			Query q = this.getEntityManager().createQuery("from Desbravador d where d.clubeAtual='"+c.getId()+"'");
			desbravadores = (List<Desbravador>) q.getResultList();
		}catch (NoResultException nre){
			return null;
		}
		return desbravadores;
	}
}

package br.edu.ifpb.tsi.gcd.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;

import br.edu.ifpb.tsi.gcd.dao.ClubeDAO;
import br.edu.ifpb.tsi.gcd.dao.DesbravadorDAO;
import br.edu.ifpb.tsi.gcd.dao.DistritalDAO;
import br.edu.ifpb.tsi.gcd.dao.PersistenceUtil;
import br.edu.ifpb.tsi.gcd.dao.UnidadeDAO;
import br.edu.ifpb.tsi.gcd.model.Clube;
import br.edu.ifpb.tsi.gcd.model.Desbravador;
import br.edu.ifpb.tsi.gcd.model.Distrital;
import br.edu.ifpb.tsi.gcd.model.Unidade;


@ManagedBean(name="distritalBean")
@SessionScoped
public class DistritalBean {

	private List<Desbravador> desbravadores;
	private List<Clube> clubes;
	private boolean editando;
	private String senha;
	private String confirmSenha;
	private String novaSenha;
	

	private long clubeID;
	private List<Desbravador> membrosDoClube;
	private Clube clubeVisto;
	//event
	//comentario
	
	@ManagedProperty("#{sessionBean}")
	public SessionBean sessionBean;
	
	@PostConstruct
	public void init() {
		this.desbravadores = new ArrayList<Desbravador>();
		this.clubes = new ArrayList<Clube>();
		this.membrosDoClube = new ArrayList<Desbravador>();
		reset();
	}
	
//	Metodos de LoginBean
	public void reset(){
		
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		DesbravadorDAO daoDesbravador = new DesbravadorDAO(em);
		ClubeDAO daoClube = new ClubeDAO(em);
		
		this.desbravadores = daoDesbravador.findAll();
		this.clubes = daoClube.findAll();
		
	}

	public String mudarSenha(){
		if(novaSenha.equals(confirmSenha)){
			EntityManager em = PersistenceUtil.getCurrentEntityManager();
			DistritalDAO distDAO = new DistritalDAO(em);
			
			Distrital d = distDAO.findByLogin(sessionBean.getUsuarioLogado().getLogin());
			d.setSenha(novaSenha);
			
			distDAO.beginTransaction();
			distDAO.update(d);
			distDAO.commit();
			
			sessionBean.addMensagem("Senha Alterada Com Sucesso!", 0);
			return "page_painel?faces-redirect=true";
		}else{
			sessionBean.addMensagem("Nova Senha e confirmação de senha não conferem",1);
			return "page_mudar_senha?faces-redirect=true";
		}
	}
	
	
	public String verMembrosClube(long clube){
		
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		ClubeDAO daoClube = new ClubeDAO(em);
		
		Clube c = daoClube.find(clube);
		this.membrosDoClube = c.getMembros();
		this.clubeVisto = c;
		
		return "page_membros_clube?faces-redirect=true";
	}
	
	public String transferirDesbravador(long id){
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		DesbravadorDAO dao = new DesbravadorDAO(em);
		ClubeDAO daoClube = new ClubeDAO(em);
		
		Clube c = daoClube.find(this.clubeID);
		Desbravador d = dao.find(id);
		this.clubeVisto.removeMembro(d);
		c.addMembro(d);
		
		dao.beginTransaction();
		dao.update(d);
		dao.commit();
		
		return "page_membros_clube?faces-redirect=true";
	}
	
	
//	Gets e Sets
	public List<Desbravador> getDesbravadores() {
		return desbravadores;
	}

	public void setDesbravadores(List<Desbravador> desbravadores) {
		this.desbravadores = desbravadores;
	}

	public List<Clube> getClubes() {
		return clubes;
	}

	public void setClubes(List<Clube> clubes) {
		this.clubes = clubes;
	}

	public boolean isEditando() {
		return editando;
	}

	public void setEditando(boolean editando) {
		this.editando = editando;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmSenha() {
		return confirmSenha;
	}

	public void setConfirmSenha(String confirmSenha) {
		this.confirmSenha = confirmSenha;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public List<Desbravador> getMembrosDoClube() {
		return membrosDoClube;
	}

	public void setMembrosDoClube(List<Desbravador> membrosDoClube) {
		this.membrosDoClube = membrosDoClube;
	}

	public Clube getClubeVisto() {
		return clubeVisto;
	}

	public void setClubeVisto(Clube clubeVisto) {
		this.clubeVisto = clubeVisto;
	}

	public long getClubeID() {
		return clubeID;
	}

	public void setClubeID(long clubeID) {
		this.clubeID = clubeID;
	}
	
	
}

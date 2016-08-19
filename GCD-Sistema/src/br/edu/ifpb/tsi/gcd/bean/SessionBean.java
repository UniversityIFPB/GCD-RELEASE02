package br.edu.ifpb.tsi.gcd.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import br.edu.ifpb.tsi.gcd.dao.ClasseDAO;
import br.edu.ifpb.tsi.gcd.dao.ClubeDAO;
import br.edu.ifpb.tsi.gcd.dao.DesbravadorDAO;
import br.edu.ifpb.tsi.gcd.dao.PersistenceUtil;
import br.edu.ifpb.tsi.gcd.model.Classe;
import br.edu.ifpb.tsi.gcd.model.Clube;
import br.edu.ifpb.tsi.gcd.model.Desbravador;
import br.edu.ifpb.tsi.gcd.model.Distrital;
import br.edu.ifpb.tsi.gcd.model.Unidade;


@ManagedBean(name="sessionBean")
@SessionScoped
public class SessionBean {

	public Desbravador usuarioLogado;
	public Clube clube;
	public List<Desbravador> desbravadores;
	
	public Clube clubeEditado;
	public Desbravador membroEditado;
	public Unidade unidadeEditada;
	public Classe classeEditada;
	public String dataNascimentoedit;
	
	public Desbravador desbravadorEditado;
	public String msg;
	public String result="NONE";
	public boolean localErro;
	
	@PostConstruct
	public void init(){
		this.desbravadores = new ArrayList<Desbravador>();
	}
	
//	Metodos de SessionBean
	
	public void atualizaDesbravadores(){
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		DesbravadorDAO daoDesbravador = new DesbravadorDAO(em);
		ClubeDAO daoClube = new ClubeDAO(em); 
		
		if(this.clube != null){
			this.clube = daoClube.findByDiretor(usuarioLogado);
			this.desbravadores = daoDesbravador.findByClube(this.clube);
		}else{
			this.desbravadores = daoDesbravador.findAll();
		}
	}
	
	public void addMensagem(String mensagem, int tipo){
		switch(tipo){
			case 1:
				this.setResult("ERRO");
				this.msg = mensagem;
				FacesMessage msgError = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, null);
				FacesContext.getCurrentInstance().addMessage(null, msgError);
				break;
			case 0:
				this.setResult("SUCESSO");
				this.msg = mensagem;
				FacesMessage msgInfo = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, null);
				FacesContext.getCurrentInstance().addMessage(null, msgInfo);
				break;
		}
	}
	
	public void check(){
	    if (this.usuarioLogado == null) {
	    	try {
	    		ServletRequest req = (ServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    		ServletResponse res = (ServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	    		RequestDispatcher rd = req.getRequestDispatcher("index.jsf");
	    		rd.forward(req, res);
	    	} catch (IOException | ServletException e) {
	    		e.printStackTrace();
	    	}
	    }
	}
	
	public void checkViewAdmin(){
		if (!verificarTipoDistrital(this.usuarioLogado)) {
	    	try {
	    		ServletRequest req = (ServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    		ServletResponse res = (ServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	    		RequestDispatcher rd = req.getRequestDispatcher("page_painel.jsf");
	    		rd.forward(req, res);
	    	} catch (IOException | ServletException e) {
	    		e.printStackTrace();
	    	}
	    }
	}
	
	public void checkViewDiretor(){
		if (verificarTipoDistrital(this.usuarioLogado)) {
			try {
				ServletRequest req = (ServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    		ServletResponse res = (ServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	    		RequestDispatcher rd = req.getRequestDispatcher("page_painel.jsf");
	    		rd.forward(req, res);
	    	} catch (IOException | ServletException e) {
	    		e.printStackTrace();
	    	}
	    }
	}
	
	public boolean checkAdmin(){
	    if (verificarTipoDistrital(this.usuarioLogado)) {
	        return true;
	    }else{
	    	return false;
	    }
	}
	
	public boolean verificarTipoDistrital(Desbravador p){
		return (p instanceof Distrital);
	}
	
	public String validaClasse(){
		
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		ClasseDAO daoClasse = new ClasseDAO(em);
		
		daoClasse.beginTransaction();
		daoClasse.update(classeEditada);
		daoClasse.commit();
		
		this.addMensagem("Classe Editada Com Sucesso!", 0);
		
		return "page_classes?faces-redirect=true";
	}
	
	public Boolean verificarRequisito(String key){
		System.out.println(classeEditada.getValidaRequisitos().get(key));
		return classeEditada.getValidaRequisitos().get(key);
	}
	
	public void validar(String key){
		classeEditada.validarRequisito(key);
	}
	
	public void invalidar(String key){
		classeEditada.invalidarRequisito(key);
	}

	
//	Gets e Sets
	public Desbravador getUsuarioLogado() {
		return usuarioLogado;
	}
	public void setUsuarioLogado(Desbravador usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Clube getClube() {
		return clube;
	}
	public void setClube(Clube clube) {
		this.clube = clube;
	}
	public Clube getClubeEditado() {
		return clubeEditado;
	}
	public void setClubeEditado(Clube clubeEditado) {
		this.clubeEditado = clubeEditado;
	}
	public Desbravador getDesbravadorEditado() {
		return desbravadorEditado;
	}
	public void setDesbravadorEditado(Desbravador desbravadorEditado) {
		this.desbravadorEditado = desbravadorEditado;
	}
	public boolean isLocalErro() {
		return localErro;
	}
	public void setLocalErro(boolean localErro) {
		this.localErro = localErro;
	}
	public List<Desbravador> getDesbravadores() {
		return desbravadores;
	}
	public void setDesbravadores(List<Desbravador> desbravadores) {
		this.desbravadores = desbravadores;
	}
	public Unidade getUnidadeEditada() {
		return unidadeEditada;
	}
	public void setUnidadeEditada(Unidade unidadeEditada) {
		this.unidadeEditada = unidadeEditada;
	}
	public Desbravador getMembroEditado() {
		return membroEditado;
	}
	public void setMembroEditado(Desbravador membroEditado) {
		this.membroEditado = membroEditado;
	}
	public String getDataNascimentoedit() {
		return dataNascimentoedit;
	}
	public void setDataNascimentoedit(String dataNascimentoedit) {
		this.dataNascimentoedit = dataNascimentoedit;
	}

	public Classe getClasseEditada() {
		return classeEditada;
	}

	public void setClasseEditada(Classe classeEditada) {
		this.classeEditada = classeEditada;
	}
	
	
}

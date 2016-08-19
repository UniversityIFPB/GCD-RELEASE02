package br.edu.ifpb.tsi.gcd.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import br.edu.ifpb.tsi.gcd.dao.ClubeDAO;
import br.edu.ifpb.tsi.gcd.dao.DesbravadorDAO;
import br.edu.ifpb.tsi.gcd.dao.PersistenceUtil;
import br.edu.ifpb.tsi.gcd.model.Clube;
import br.edu.ifpb.tsi.gcd.model.Desbravador;


@ManagedBean(name="clubeBean")
@SessionScoped
public class ClubeBean {

	private String nomeClube;
	private String distritoClube;
	private String nomeDiretor;
	private String emailDiretor;
	private String telefoneDiretor;
	private boolean sexoDiretor;
	private String loginDiretor;
	private String senhaDiretor;
	private String confirmSenhaDiretor;
	private String novaSenha;
	
	private Boolean situacao;
	
	@ManagedProperty("#{sessionBean}")
	public SessionBean sessionBean;
	
	@ManagedProperty("#{distritalBean}")
	public DistritalBean distritalBean;
	
	@PostConstruct
	public void init() {
		reset();
	}
	
//	Metodos de LoginBean
	public void reset(){
		this.situacao = null;
		this.nomeClube = null;
		this.distritoClube = null;
		this.nomeDiretor = null;
		this.emailDiretor = null;
		this.telefoneDiretor = null;
		this.loginDiretor = null;
		this.senhaDiretor = null;
		
	}
	
	
	public String addClube(){
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		ClubeDAO dao = new ClubeDAO(em);
		
		if(nomeDiretor == null || emailDiretor == null || loginDiretor == null || senhaDiretor == null || telefoneDiretor == null){
			sessionBean.addMensagem("Preencha todos os dados do Diretor", 1);
			sessionBean.localErro = true;
		}else if(nomeClube == null || distritoClube == null){
			sessionBean.addMensagem("Preencha todos os dados do Clube", 1);
			sessionBean.localErro = true;
		}else if(!senhaDiretor.equals(confirmSenhaDiretor)){
			sessionBean.addMensagem("Senha e confirmação de senha não conferem", 1);
			sessionBean.localErro = true;
		}else{
			Desbravador diretor = new Desbravador(nomeDiretor, sexoDiretor, emailDiretor, telefoneDiretor, "DIRETOR", loginDiretor, senhaDiretor);
			Clube clube = new Clube(nomeClube, distritoClube, diretor);
			
			dao.beginTransaction();
			dao.insert(clube);
			dao.commit();
			
			HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			req.setAttribute("endofconversation", 1);
			
			sessionBean.addMensagem("Clube Criado com Sucesso!\nLogin: " + loginDiretor + "\nSenha:" + senhaDiretor, 0);
			sessionBean.localErro = false;
			reset();
			distritalBean.reset();
			return "page_painel?faces-redirect=true";
		}
		return "page_cadastro_clube?faces-redirect=true";
	}
	
	public String preparaEdicao(long id){
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		ClubeDAO dao = new ClubeDAO(em);
		
		Clube c = dao.find(id);
		sessionBean.setResult("NONE");
		sessionBean.clubeEditado = c;
		confirmSenhaDiretor = c.getDiretor().getSenha();
		return "page_editar_clube?faces-redirect=true";
	}

	public String editarClube(){
		
			EntityManager em = PersistenceUtil.getCurrentEntityManager();
			ClubeDAO dao = new ClubeDAO(em);
			
			//verificar preenchimento aqui
			if(!sessionBean.getClubeEditado().getDiretor().getSenha().equals(confirmSenhaDiretor)){
				sessionBean.addMensagem("Senha e confirmação de senha não conferem",1);
				
				return "page_editar_clube?faces-redirect=true";
				
			}else{
				dao.beginTransaction();
				dao.update(sessionBean.getClubeEditado());
				dao.commit();
				
				sessionBean.addMensagem("Clube Editado Com Sucesso!", 0);
				
				sessionBean.setClubeEditado(null);
			}
			HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			req.setAttribute("endofconversation", 1);
			return "page_painel?faces-redirect=true";
	}
	
	public void desativarClube(long id){
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		ClubeDAO dao = new ClubeDAO(em);
		
		Clube c = dao.find(id);
		c.setAtivo(false);
		
		dao.beginTransaction();
		dao.update(c);
		dao.commit();
		
		sessionBean.setResult("NONE");
	}
	
	public void ativarClube(long id){
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		ClubeDAO dao = new ClubeDAO(em);

		Clube c = dao.find(id);
		c.setAtivo(true);

		dao.beginTransaction();
		dao.update(c);
		dao.commit();

		sessionBean.setResult("NONE");
	}
	
	public String mudarSenha(){
		if(novaSenha.equals(confirmSenhaDiretor)){
			EntityManager em = PersistenceUtil.getCurrentEntityManager();
			DesbravadorDAO desbDAO = new DesbravadorDAO(em);
			ClubeDAO clubeDAO = new ClubeDAO(em);
			
			Desbravador d = desbDAO.findByLogin(sessionBean.getUsuarioLogado().getLogin());
			Clube c = clubeDAO.find(sessionBean.clube.getId());
			d.setSenha(novaSenha);
			c.setDiretor(d);
			
			clubeDAO.beginTransaction();
			clubeDAO.update(c);
			clubeDAO.commit();
			sessionBean.addMensagem("Senha Alterada Com Sucesso!", 0);
			return "page_painel?faces-redirect=true";
		}else{
			sessionBean.addMensagem("Nova Senha e confirmação de senha não conferem",1);
			return "page_mudar_senha?faces-redirect=true";
		}
		
	}
	
//	Gets e Sets
	public String getNomeClube() {
		return nomeClube;
	}

	public void setNomeClube(String nomeClube) {
		this.nomeClube = nomeClube;
	}

	public String getDistritoClube() {
		return distritoClube;
	}

	public void setDistritoClube(String distritoClube) {
		this.distritoClube = distritoClube;
	}

	public String getNomeDiretor() {
		return nomeDiretor;
	}

	public void setNomeDiretor(String nomeDiretor) {
		this.nomeDiretor = nomeDiretor;
	}

	public String getEmailDiretor() {
		return emailDiretor;
	}

	public void setEmailDiretor(String emailDiretor) {
		this.emailDiretor = emailDiretor;
	}

	public String getTelefoneDiretor() {
		return telefoneDiretor;
	}

	public void setTelefoneDiretor(String telefoneDiretor) {
		this.telefoneDiretor = telefoneDiretor;
	}

	public boolean isSexoDiretor() {
		return sexoDiretor;
	}

	public void setSexoDiretor(boolean sexoDiretor) {
		this.sexoDiretor = sexoDiretor;
	}

	public String getLoginDiretor() {
		return loginDiretor;
	}

	public void setLoginDiretor(String loginDiretor) {
		this.loginDiretor = loginDiretor;
	}

	public String getSenhaDiretor() {
		return senhaDiretor;
	}

	public void setSenhaDiretor(String senhaDiretor) {
		this.senhaDiretor = senhaDiretor;
	}
	
	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String senha) {
		this.novaSenha = senha;
	}
	
	public String getConfirmSenhaDiretor() {
		return confirmSenhaDiretor;
	}

	public void setConfirmSenhaDiretor(String confirmSenhaDiretor) {
		this.confirmSenhaDiretor = confirmSenhaDiretor;
	}

	public Boolean getSituacao() {
		return situacao;
	}

	public void setSituacao(Boolean situacao) {
		this.situacao = situacao;
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public DistritalBean getDistritalBean() {
		return distritalBean;
	}

	public void setDistritalBean(DistritalBean distritalBean) {
		this.distritalBean = distritalBean;
	}
	
	

//	public DesbravadorBean getDesbravadorBean() {
//		return desbravadorBean;
//	}
//
//	public void setDesbravadorBean(DesbravadorBean desbravadorBean) {
//		this.desbravadorBean = desbravadorBean;
//	}
	
	
}

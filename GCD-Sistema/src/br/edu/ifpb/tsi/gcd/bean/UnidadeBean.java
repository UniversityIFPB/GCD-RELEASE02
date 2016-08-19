package br.edu.ifpb.tsi.gcd.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import br.edu.ifpb.tsi.gcd.dao.ClubeDAO;
import br.edu.ifpb.tsi.gcd.dao.DesbravadorDAO;
import br.edu.ifpb.tsi.gcd.dao.PersistenceUtil;
import br.edu.ifpb.tsi.gcd.dao.UnidadeDAO;
import br.edu.ifpb.tsi.gcd.model.Clube;
import br.edu.ifpb.tsi.gcd.model.Desbravador;
import br.edu.ifpb.tsi.gcd.model.Unidade;


@ManagedBean(name="unidadeBean")
public class UnidadeBean {

	private String nome;
	
	@ManagedProperty("#{sessionBean}")
	public SessionBean sessionBean;
	
	@PostConstruct
	public void init() {
		reset();
	}
	
//	Metodos de LoginBean
	public void reset(){
		this.nome = null;
		sessionBean.atualizaDesbravadores();
	}
	
	
	public String addUnidade(){
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		UnidadeDAO daoUnid = new UnidadeDAO(em);
		
		if(nome == null){
			sessionBean.addMensagem("Preencha um nome para a Unidade", 1);
		}else{
			Clube clube = sessionBean.clube;
			
			Unidade u = new Unidade(nome);
			
			clube.addUnidade(u);
			
			daoUnid.beginTransaction();
			daoUnid.insert(u);
			daoUnid.commit();
			
			HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			req.setAttribute("endofconversation", 1);
			
			sessionBean.addMensagem("Unidade Criada com Sucesso!", 0);
			sessionBean.localErro = false;
			reset();
			return "page_unidades?faces-redirect=true";
		}
		return "page_cadastro_unidade?faces-redirect=true";
	}

	
	
	public String preparaEdicao(long id){
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		UnidadeDAO dao = new UnidadeDAO(em);
		
		Unidade u = dao.find(id);
		sessionBean.setResult("NONE");
		sessionBean.unidadeEditada = u;
		sessionBean.clube.removeUnidade(u);
		return "page_editar_unidade?faces-redirect=true";
	}
	
	public String editarUnidade(){
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		UnidadeDAO dao = new UnidadeDAO(em);
		
		sessionBean.clube.addUnidade(sessionBean.unidadeEditada);
		
		if(sessionBean.unidadeEditada.getNome().equals("") || sessionBean.unidadeEditada.equals(null)){
			sessionBean.addMensagem("Preencha um nome para a Unidade", 1);
			return "page_editar_unidade?faces-redirect=true";
			
		}else{
			dao.beginTransaction();
			dao.update(sessionBean.unidadeEditada);
			dao.commit();
			
			sessionBean.atualizaDesbravadores();
			sessionBean.addMensagem("Unidade Editada Com Sucesso!", 0);
			
			sessionBean.unidadeEditada = null;
			HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			req.setAttribute("endofconversation", 1);
			return "page_unidades?faces-redirect=true";
		}
	}
	
	public String excluirUnidade(long id){
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		UnidadeDAO dao = new UnidadeDAO(em);
		ClubeDAO daoClube = new ClubeDAO(em);
		DesbravadorDAO daoDesbrav = new DesbravadorDAO(em);
		
		Unidade u = dao.find(id);
		
		sessionBean.clube.removeUnidade(u);
		daoClube.beginTransaction();
		daoClube.update(sessionBean.clube);
		daoClube.commit();
		
		List<Desbravador> membrosDaUnidade = u.getMembros();
		if(!membrosDaUnidade.isEmpty() && membrosDaUnidade!=null){
			daoDesbrav.beginTransaction();
			for(Desbravador d : membrosDaUnidade){
				d.setUnidadeAtual(null);
				daoDesbrav.update(d);
			}
			daoDesbrav.commit();
		}
		
		dao.beginTransaction();
		dao.delete(u);
		dao.commit();
		
		this.reset();
		
		sessionBean.addMensagem("Unidade Excluida Com Sucesso!", 0);
		return "page_unidades?faces-redirect=true";
	}

	public String transferir(long id){
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		DesbravadorDAO daoDesbrav = new DesbravadorDAO(em);
		
		Desbravador d = daoDesbrav.find(id);
		d.setUnidadeAtual(null);
		daoDesbrav.beginTransaction();
		daoDesbrav.update(d);
		daoDesbrav.commit();
		
		return "page_painel?faces-redirect=true";
	}
	
//	Gets e Sets
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}
	
}

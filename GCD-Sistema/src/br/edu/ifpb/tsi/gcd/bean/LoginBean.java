package br.edu.ifpb.tsi.gcd.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import br.edu.ifpb.tsi.gcd.dao.ClubeDAO;
import br.edu.ifpb.tsi.gcd.dao.DesbravadorDAO;
import br.edu.ifpb.tsi.gcd.dao.DistritalDAO;
import br.edu.ifpb.tsi.gcd.dao.PersistenceUtil;
import br.edu.ifpb.tsi.gcd.model.Clube;
import br.edu.ifpb.tsi.gcd.model.Desbravador;
import br.edu.ifpb.tsi.gcd.model.Distrital;


@ManagedBean(name="loginBean")
public class LoginBean {

	public String login="";
	public String senha;
	
	@ManagedProperty("#{sessionBean}")
	public SessionBean sessionBean;
	
//	Metodos de LoginBean
	public String fazerLogin(){
		
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		DesbravadorDAO dao = new DesbravadorDAO(em);
		ClubeDAO daoClube = new ClubeDAO(em);
		DistritalDAO daoDistrital = new DistritalDAO(em);
			
		if(!login.equals(null) && !login.equals("")){
			if(login.toUpperCase().equals("ADMIN")){
				Distrital d = daoDistrital.findByLogin(login);	
				if(d != null){
					if(d.getSenha().equals(senha)){
						sessionBean.usuarioLogado = d;
						sessionBean.setResult("NONE");
						return "page_painel?faces-redirect=true";
					}else{
						sessionBean.addMensagem("Senha Invalida!", 1);
					}
				}else{
					sessionBean.addMensagem("Usuário não encontrado!", 1);
				}
			}else{
				Desbravador d = dao.findByLogin(login);	
				if(d != null){
					if(d.getSenha().equals(senha)){
						Clube c = daoClube.findByDiretor(d);
						sessionBean.usuarioLogado = d;
						sessionBean.clube = c;
						sessionBean.setResult("NONE");
						return "page_painel?faces-redirect=true";
					}else{
						sessionBean.addMensagem("Senha Invalida!", 1);
					}
				}else{
					sessionBean.addMensagem("Usuário não encontrado!", 1);
				}
			}
		}else{
			sessionBean.addMensagem("Preencha os campos com login e senha!", 1);
		}
		return null;
	}
	
	
	public String fazerLogout(){
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		req.setAttribute("endofconversation", 1);
		this.sessionBean.setUsuarioLogado(null);
		this.sessionBean.setMsg(null);
		this.sessionBean.setResult("NONE");
//		req.getSession().invalidate();
		return "index?faces-redirect=true";
	}
	
	
	public boolean verificarTipoDistrital(Desbravador p){
		return (p instanceof Distrital);
	}
	
	
//	Gets e Sets
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

}

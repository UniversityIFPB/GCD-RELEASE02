package br.edu.ifpb.tsi.gcd.bean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import br.edu.ifpb.tsi.gcd.dao.ClasseDAO;
import br.edu.ifpb.tsi.gcd.dao.ClubeDAO;
import br.edu.ifpb.tsi.gcd.dao.DesbravadorDAO;
import br.edu.ifpb.tsi.gcd.dao.PersistenceUtil;
import br.edu.ifpb.tsi.gcd.dao.UnidadeDAO;
import br.edu.ifpb.tsi.gcd.model.Classe;
import br.edu.ifpb.tsi.gcd.model.Desbravador;
import br.edu.ifpb.tsi.gcd.model.Unidade;

@ManagedBean(name="desbravadorBean")
@SessionScoped
public class DesbravadorBean {

	private String nome;
	private String email;
	private String telefone;
	private Date dataNascimento;
	private Boolean sexo;
	private String nomePai;
	private String nomeMae;
	private String cargo;
	private String endereco;
	private long unidadeID;
	
	@ManagedProperty("#{sessionBean}")
	public SessionBean sessionBean;
	
	public DesbravadorBean(){}

	@PostConstruct
	public void init() {
		this.reset();
	}
	
	public void reset(){
		sessionBean.atualizaDesbravadores();
	}
	
//	Metodos DesbravadorBean
	public String addDesbravador(){
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		DesbravadorDAO dao = new DesbravadorDAO(em);

		//verificar se está tudo preenchido corretamente
		if(nome == null || nomePai == null || nomeMae == null || dataNascimento == null){
			sessionBean.addMensagem("Preencha o nome, sexo e nome dos pais do membro",1);
		}else{
		
			Calendar today = Calendar.getInstance();
			Calendar cal = Calendar.getInstance();
			cal.setTime(dataNascimento);
			SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
			int testeAno = Integer.parseInt(formatYear.format(today.getTime())) - Integer.parseInt(formatYear.format(dataNascimento));
			System.out.println(testeAno);
		
//			verificar data de nascimento deve ter no mínimo 10 anos
			if(testeAno >= 10 && testeAno < 16){
				if(cargo == null){
					this.cargo = "DESBRAVADOR";
				}
			}else if(testeAno >= 16){
				if(cargo == null){
					sessionBean.addMensagem("Preencha o cargo do membro",1);
					return "page_cadastro_membro?faces-redirect=true";
				}
			}else{
				sessionBean.addMensagem("O membro precisa ter mais de 10 anos",1);
				return "page_cadastro_membro?faces-redirect=true";
			}
			
			Desbravador membro = new Desbravador(nome, sexo, nomePai, nomeMae, cargo);
			
			if(endereco != null){
				membro.setEndereco(endereco);
			}
			if(email != null){
				membro.setEmail(email);
			}
			
			membro.setDataNascimento(cal.getTime());
			membro.setClubeAtual(sessionBean.clube);

			dao.beginTransaction();
			dao.insert(membro);
			dao.commit();

			HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			req.setAttribute("endofconversation", 1);

			sessionBean.addMensagem("Membro Adicionado Com Sucesso!",0);

			return "page_painel?faces-redirect=true";
		}
		return "page_cadastro_membro?faces-redirect=true";
		
	}
	
	public String preparaEdicao(long id){
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		DesbravadorDAO dao = new DesbravadorDAO(em);
		
		Desbravador d = dao.find(id);
		sessionBean.setResult("NONE");
		sessionBean.membroEditado = d;
		return "page_editar_membro?faces-redirect=true";
	}
	
	public String editarMembro(){
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		DesbravadorDAO dao = new DesbravadorDAO(em);
		
		//verificar se está tudo preenchido corretamente
		if(sessionBean.membroEditado.getNome().equals("") || sessionBean.membroEditado.getNomePai().equals("") || sessionBean.membroEditado.getNomeMae().equals("") || sessionBean.membroEditado.getDataNascimento() == null){
			sessionBean.addMensagem("Preencha o nome, sexo e nome dos pais do membro",1);
		}else{

			Calendar today = Calendar.getInstance();
			Calendar cal = Calendar.getInstance();
			cal.setTime(sessionBean.membroEditado.getDataNascimento());
			SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
			int testeAno = Integer.parseInt(formatYear.format(today.getTime())) - Integer.parseInt(formatYear.format(sessionBean.membroEditado.getDataNascimento()));
			System.out.println(testeAno);

			//					verificar data de nascimento deve ter no mínimo 10 anos
			if(testeAno >= 10 && testeAno < 16){
				if(sessionBean.membroEditado.getCargo().equals("")){
					this.cargo = "DESBRAVADOR";
				}
			}else if(testeAno >= 16){
				if(sessionBean.membroEditado.getCargo().equals("")){
					sessionBean.addMensagem("Preencha o cargo do membro",1);
					return "page_editar_membro?faces-redirect=true";
				}
			}else{
				sessionBean.addMensagem("O membro precisa ter mais de 10 anos",1);
				return "page_editar_membro?faces-redirect=true";
			}
			dao.beginTransaction();
			dao.update(sessionBean.membroEditado);
			dao.commit();

			sessionBean.addMensagem("Membro Editado Com Sucesso!", 0);

			sessionBean.setMembroEditado(null);
			HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			req.setAttribute("endofconversation", 1);
			return "page_painel?faces-redirect=true";
		}
		return "page_editar_membro?faces-redirect=true";
	}
	
	public String excluirMembro(long id){
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		DesbravadorDAO dao = new DesbravadorDAO(em);
		ClubeDAO daoClube = new ClubeDAO(em);
		
		Desbravador d = dao.find(id);
		sessionBean.clube.removeMembro(d);
		daoClube.beginTransaction();
		daoClube.update(sessionBean.clube);
		daoClube.commit();
		
		dao.beginTransaction();
		dao.delete(d);
		dao.commit();
		
		this.reset();
		
		sessionBean.addMensagem("Membro Excluido Com Sucesso!", 0);
		return "page_painel?faces-redirect=true";
	}
	
	public String inserirNaUnidade(long id){
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		DesbravadorDAO dao = new DesbravadorDAO(em);
		UnidadeDAO daoUnid = new UnidadeDAO(em);
		
		Unidade u = daoUnid.find(this.unidadeID);
		Desbravador d = dao.find(id);
		u.addMembro(d);
		
		dao.beginTransaction();
		dao.update(d);
		dao.commit();
		
		this.reset();
		
		return "page_painel?faces-redirect=true";
	}
	
	public String irGerenciarClasse(long id, long idClasse){
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		DesbravadorDAO dao = new DesbravadorDAO(em);
		ClasseDAO daoClasse = new ClasseDAO(em);
		
		Classe c = daoClasse.find(idClasse);
		Desbravador d = dao.find(id);
		
		sessionBean.desbravadorEditado = d;
		sessionBean.classeEditada = c;
		
		return "page_gerenciar_classe?faces-redirect=true";
	}
	
//	Gets e Sets
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Boolean getSexo() {
		return sexo;
	}

	public void setSexo(Boolean sexo) {
		this.sexo = sexo;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public long getUnidadeID() {
		return unidadeID;
	}

	public void setUnidadeID(long unidade) {
		this.unidadeID = unidade;
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

}

package br.edu.ifpb.tsi.gcd.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Desbravador{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String nome;
	private String endereco;
	private String email;
	private String telefone;
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	private Boolean sexo;
	private String nomePai;
	private String nomeMae;
	
	private String cargo;
	private String login;
	private String senha;
	
	@ManyToOne
	private Unidade unidadeAtual;
	@ManyToOne
	private Clube clubeAtual;
	@ManyToOne
	private Classe classeAtual;
	
//	private List<Classe> classes;
	//especialidades
	
	public Desbravador(){}

	public Desbravador(String nome, Boolean sexo, String email, String telefone, String cargo, String login, String senha) {
		super();
		this.nome = nome;
		this.sexo = sexo;
		this.email = email;
		this.telefone = telefone;
		this.cargo = cargo.toUpperCase();
		
		if(!this.cargo.equals("DIRETOR")){
			this.login = null;
			this.senha = null;
		}else{
			this.login = login;
			this.senha = senha;
		}
	}
	
	public Desbravador(String nome, Boolean sexo, String nomePai, String nomeMae, String cargo) {
		super();
		this.nome = nome;
		this.sexo = sexo;
		this.nomePai = nomePai;
		this.nomeMae = nomeMae;
		this.cargo = cargo.toUpperCase();
		
		if(!this.cargo.equals("DIRETOR")){
			this.login = null;
			this.senha = null;
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
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

	public Unidade getUnidadeAtual() {
		return unidadeAtual;
	}

	public void setUnidadeAtual(Unidade unidadeAtual) {
		this.unidadeAtual = unidadeAtual;
	}

	public Clube getClubeAtual() {
		return clubeAtual;
	}

	public void setClubeAtual(Clube clubeAtual) {
		this.clubeAtual = clubeAtual;
	}

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

	public Classe getClasseAtual() {
		return classeAtual;
	}

	public void setClasseAtual(Classe classeAtual) {
		this.classeAtual = classeAtual;
	}

	
	

	
}

package br.edu.ifpb.tsi.gcd.bean;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.edu.ifpb.tsi.gcd.model.Event;

@ManagedBean(name = "eventBean")
@RequestScoped
public class EventBean {	
	private Boolean situacao;
	private String tipo;
	private String nivel;
	private String descricao;	
	private Date data;
	
	@PostConstruct
	public void init() {
		reset();
	}
	
//	Metodos de LoginBean
	public void reset(){
		this.situacao = null;
		this.tipo = null;
		this.nivel = null;
		this.descricao = null;	
		this.data = null;
		
	}
	
	public String saveEvent(){
		Event e = new Event(this.tipo, this.nivel, this.descricao, this.data);	
		System.out.println(e.toString());
		return "page_eventos?faces-redirect=true";
		
	}
	
	public Boolean getSituacao() {
		return situacao;
	}

	public void setSituacao(Boolean situacao) {
		this.situacao = situacao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
}

package br.edu.ifpb.tsi.gcd.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


@Entity
@Table(name="evento")
public class Event {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@OneToMany(mappedBy="evento", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	private List<Comentario> comentarios;
	
	@Transient
	private Boolean situacao;
	private String tipo;
	private String nivel;
	private String descricao;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	
	@ManyToOne
	private Clube clube;
	@ManyToOne
	private Distrital distrital;
	
	public Event() {
		this.comentarios = new ArrayList<Comentario>();
	}

	public Event(String tipo, String nivel, String descricao, Date data) {
		super();
		this.tipo = tipo;
		this.nivel = nivel;
		this.descricao = descricao;
		this.data = data;
		this.comentarios = new ArrayList<Comentario>();
	}

	public void addComentario(Comentario comentario){
		comentario.setEvento(this);
		this.comentarios.add(comentario);
	}
	
	public void removeComentario(Comentario comentario){
		comentario.setEvento(null);
		this.comentarios.remove(comentario);
	}
	
	public Comentario getComentario(Comentario comentario){
		return this.comentarios.get(this.comentarios.indexOf(comentario));
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
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

	public Clube getClube() {
		return clube;
	}

	public void setClube(Clube clube) {
		this.clube = clube;
	}

	public Distrital getDistrital() {
		return distrital;
	}

	public void setDistrital(Distrital distrital) {
		this.distrital = distrital;
	}
	
	
}

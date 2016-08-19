package br.edu.ifpb.tsi.gcd.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Unidade {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String nome;
	@OneToOne
	private Desbravador conselheiro;

	@ManyToOne
	private Clube clube;
	@OneToMany(mappedBy="unidadeAtual", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	private List<Desbravador> membros;
	
	public Unidade(String nome) {
		super();
		this.nome = nome;
		this.membros = new ArrayList<Desbravador>();
	}
	
	public Unidade() {
		this.membros = new ArrayList<Desbravador>();
	}
	
	public void addMembro(Desbravador d){
		this.membros.add(d);
		d.setUnidadeAtual(this);
	}
	
	public Desbravador removeMembro(Desbravador d){
		Desbravador removido = d;
		this.membros.remove(d);
		return removido;
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

	public Desbravador getConselheiro() {
		return conselheiro;
	}

	public void setConselheiro(Desbravador conselheiro) {
		this.conselheiro = conselheiro;
	}

	public Clube getClube() {
		return clube;
	}

	public void setClube(Clube clube) {
		this.clube = clube;
	}

	public List<Desbravador> getMembros() {
		return membros;
	}

	public void setMembros(List<Desbravador> membros) {
		this.membros = membros;
	}
	
	
	
}

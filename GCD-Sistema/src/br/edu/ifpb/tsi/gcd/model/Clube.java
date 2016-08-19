package br.edu.ifpb.tsi.gcd.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="clube")
public class Clube {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String nome;
	private String distrito;
	private boolean ativo;
	private boolean situacao;
	
	@OneToOne
	private Desbravador diretor;
	
	@OneToMany(mappedBy="clube", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	private List<Event> eventos;
	
	@OneToMany(mappedBy="clubeAtual", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	private List<Desbravador> membros;
	
	@OneToMany(mappedBy="clube", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	private List<Unidade> unidades;
	
	@OneToMany(mappedBy="clube", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	private List<Classe> classes;
	
	public Clube(){
		this.ativo = true;
		this.eventos = new ArrayList<Event>();
		this.membros = new ArrayList<Desbravador>();
		this.unidades = new ArrayList<Unidade>();
		this.classes = new ArrayList<Classe>();
	}
	
	public Clube(String nome, String distrito, Desbravador diretor){
		this.nome = nome;
		this.distrito = distrito;
		this.diretor = diretor;
		this.ativo = true;
		this.eventos = new ArrayList<Event>();
		this.membros = new ArrayList<Desbravador>();
		this.unidades = new ArrayList<Unidade>();
		this.classes = new ArrayList<Classe>();
		this.addMembro(diretor);
	}
	
	public void addUnidade(Unidade u){
		u.setClube(this);
		this.unidades.add(u);
	}
	
	public void removeUnidade(Unidade u){
		u.setClube(null);
		this.unidades.remove(u);
	}
	
	public void addEvento(Event evento){
		evento.setClube(this);
		evento.setDistrital(null);
		this.eventos.add(evento);
	}
	
	public void removeEvento(Event evento){
		evento.setClube(null);
		this.eventos.remove(evento);
	}
	
	public Event getEvento(Event evento){
		return this.eventos.get(this.eventos.indexOf(evento));
	}
	
	public void addMembro(Desbravador d){
			this.membros.add(d);
			d.setClubeAtual(this);
	}
	
	public Desbravador removeMembro(Desbravador d){
		Desbravador removido = d;
		this.membros.remove(removido);
		d.setClubeAtual(null);
		return removido;
	}
	
	public void addDBVemUnidade(Desbravador d){
		//implementar
	}
	
	public Desbravador removerDBVdeUnidade(Desbravador d){
		//implementar
		return null;
	}
	
	public void addClasse(Classe c){
		c.setClube(this);
		this.classes.add(c);
	}
	
	public void removeClasse(Classe c){
		c.setClube(null);
		this.classes.remove(c);
	}
	
	//-----------------

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

	public String getDistrito() {
		return distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}

	public Desbravador getDiretor() {
		return diretor;
	}

	public void setDiretor(Desbravador diretor) {
		this.diretor = diretor;
	}

	public boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public List<Event> getEventos() {
		return eventos;
	}

	public void setEventos(List<Event> eventos) {
		this.eventos = eventos;
	}

	public List<Desbravador> getMembros() {
		return membros;
	}

	public void setMembros(List<Desbravador> membros) {
		this.membros = membros;
	}

	public boolean isSituacao() {
		return situacao;
	}

	public void setSituacao(boolean situacao) {
		this.situacao = situacao;
	}

	public List<Unidade> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidade> unidades) {
		this.unidades = unidades;
	}

	public List<Classe> getClasses() {
		return classes;
	}

	public void setClasses(List<Classe> classes) {
		this.classes = classes;
	}

	
	
}

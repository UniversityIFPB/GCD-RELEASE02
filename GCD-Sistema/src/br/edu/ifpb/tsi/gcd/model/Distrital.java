package br.edu.ifpb.tsi.gcd.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="Desbravador")
@Inheritance(strategy=InheritanceType.JOINED)
public class Distrital extends Desbravador{
	
	@OneToMany(mappedBy="distrital", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	private List<Event> eventos;
	
	public Distrital(){
		super();
		this.eventos = new ArrayList<Event>();
	}
	
	public Distrital(String nome, String email, String telefone, String login, String senha){
		super();
		this.setNome(nome);
		this.setEmail(email);
		this.setTelefone(telefone);
		this.setLogin(login);
		this.setSenha(senha);
		this.eventos = new ArrayList<Event>();
	}
	

	public void addEvento(Event evento){
		evento.setClube(null);
		evento.setDistrital(this);
		evento.setSituacao(true);
		this.eventos.add(evento);
	}
	
	public void removeEvento(Event evento){
		evento.setDistrital(null);
		this.eventos.remove(evento);
	}
	
	public Event getEvento(Event evento){
		return this.eventos.get(this.eventos.indexOf(evento));
	}

	public List<Event> getEventos() {
		return eventos;
	}

	public void setEventos(List<Event> eventos) {
		this.eventos = eventos;
	}

	
	
}

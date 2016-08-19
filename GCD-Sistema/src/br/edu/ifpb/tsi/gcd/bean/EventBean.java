package br.edu.ifpb.tsi.gcd.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.tomcat.jni.Time;

import br.edu.ifpb.tsi.gcd.dao.EventoDAO;
import br.edu.ifpb.tsi.gcd.dao.PersistenceUtil;
import br.edu.ifpb.tsi.gcd.model.Event;

@ManagedBean(name = "eventBean")
@RequestScoped
public class EventBean {	
	private Boolean situacao;
	private String tipo;
	private String nivel;
	private String descricao;	
	private Date data;	

	private List<Event> events;

	@PostConstruct
	public void init() {
		this.events = new ArrayList<Event>();	
		this.events.add(new Event("casa", "da", "veiaaa", new Date()));
		reset();
	}

	//	Metodos de LoginBean
	public void reset(){
		this.situacao = null;
		this.tipo = null;
		this.nivel = null;
		this.descricao = null;	
		this.data = null;
		this.events = null;

	}

	public String saveEvent(){
		Event e = new Event(this.tipo, this.nivel, this.descricao, this.data);	

		EventoDAO dao = new EventoDAO(PersistenceUtil.getCurrentEntityManager());

		dao.beginTransaction();
		dao.insert(e);
		dao.commit();
		
		System.out.println("evento salvo!");
		return "page_eventos?faces-redirect=true";

	}

	
	

	public List<Event> getEvents() {
		/*EventoDAO dao = new EventoDAO(PersistenceUtil.getEntityManager());		

		if(this.events == null){
			this.events = dao.findAll();			
		}*/
		
		return this.events;
				
	}

	public void setEvents(List<Event> events) {
		this.events = events;
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

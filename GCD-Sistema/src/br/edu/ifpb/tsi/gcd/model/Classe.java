package br.edu.ifpb.tsi.gcd.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class Classe {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String nome;
	
	@OneToOne
	private Desbravador instrutor;

	@ManyToOne
	private Clube clube;
	
	@ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name = "REQUISITOS")
    @MapKeyColumn(name = "KEY")
    @Column(name = "VALUE")
	Map<String,String> requisitos;
	
	@ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name = "VAL_REQUISITOS")
    @MapKeyColumn(name = "KEY")
    @Column(name = "VALUE")
	Map<String,Boolean> validaRequisitos;
	
	@OneToMany(mappedBy="classeAtual", cascade=CascadeType.MERGE, orphanRemoval=true, fetch=FetchType.EAGER)
	private List<Desbravador> membros;
	
	public Classe(String nome) {
		super();
		this.nome = nome;
		this.membros = new ArrayList<Desbravador>();
		this.requisitos = new HashMap<String,String>();
		this.validaRequisitos = new HashMap<String,Boolean>();
		
		this.adicionaRequisitos(nome.toUpperCase());
	}
	
	public Classe() {
		this.membros = new ArrayList<Desbravador>();
	}
	
	public void addMembro(Desbravador d){
		d.setClasseAtual(this);
		this.membros.add(d);
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

	public Desbravador getInstrutor() {
		return instrutor;
	}

	public void setInstrutor(Desbravador instrutor) {
		this.instrutor = instrutor;
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

	public Map<String, String> getRequisitos() {
		return requisitos;
	}

	public void setRequisitos(Map<String, String> requisitos) {
		this.requisitos = requisitos;
	}
	
	public int size(){
		return this.membros.size();
	}
	
	public Map<String, Boolean> getValidaRequisitos() {
		return validaRequisitos;
	}

	public void setValidaRequisitos(Map<String, Boolean> validaRequisitos) {
		this.validaRequisitos = validaRequisitos;
	}
	
	public void validarRequisito(String req){
		this.validaRequisitos.replace(req, false, true);
	}
	
	public void invalidarRequisito(String req){
		this.validaRequisitos.replace(req, true, false);
	}

	public void adicionaRequisitos(String c){
		
		switch(c){
			case "AMIGO" : 
				this.requisitos.put( "G1", new String( "REQUISITO GERAL 01" ));
				this.validaRequisitos.put("G1", false);
			    this.requisitos.put( "G2", new String( "REQUISITO GERAL 02" ));
			    this.validaRequisitos.put("G2", false);
			    this.requisitos.put( "G3", new String( "REQUISITO GERAL 03" ));
			    this.validaRequisitos.put("G3", false);
			    this.requisitos.put( "G4", new String( "REQUISITO GERAL 04" ));
			    this.validaRequisitos.put("G4", false);
			    this.requisitos.put( "G5", new String( "REQUISITO GERAL 05" ));
			    this.validaRequisitos.put("G5", false);
			    this.requisitos.put( "EN1", new String( "REQUISITO ESTUDO DA NATUREZA 01" ));
			    this.validaRequisitos.put("EN1", false);
			    this.requisitos.put( "EN2", new String( "REQUISITO ESTUDO DA NATUREZA 02" ));
			    this.validaRequisitos.put("EN2", false);
			    this.requisitos.put( "EN3", new String( "REQUISITO ESTUDO DA NATUREZA 03" ));
			    this.validaRequisitos.put("EN3", false);
			    this.requisitos.put( "AA1", new String( "REQUISITO ARTE DE ACAMPAR 01" ));
			    this.validaRequisitos.put("AA1", false);
			    this.requisitos.put( "AA2", new String( "REQUISITO ARTE DE ACAMPAR 02" ));
			    this.validaRequisitos.put("AA2", false);
				break;
			case "COMPANHEIRO" : 
				this.requisitos.put( "G1", new String( "REQUISITO GERAL 01" ));
				this.validaRequisitos.put("G1", false);
			    this.requisitos.put( "G2", new String( "REQUISITO GERAL 02" ));
			    this.validaRequisitos.put("G2", false);
			    this.requisitos.put( "G3", new String( "REQUISITO GERAL 03" ));
			    this.validaRequisitos.put("G3", false);
			    this.requisitos.put( "G4", new String( "REQUISITO GERAL 04" ));
			    this.validaRequisitos.put("G4", false);
			    this.requisitos.put( "G5", new String( "REQUISITO GERAL 05" ));
			    this.validaRequisitos.put("G5", false);
			    this.requisitos.put( "EN1", new String( "REQUISITO ESTUDO DA NATUREZA 01" ));
			    this.validaRequisitos.put("EN1", false);
			    this.requisitos.put( "EN2", new String( "REQUISITO ESTUDO DA NATUREZA 02" ));
			    this.validaRequisitos.put("EN2", false);
			    this.requisitos.put( "EN3", new String( "REQUISITO ESTUDO DA NATUREZA 03" ));
			    this.validaRequisitos.put("EN3", false);
			    this.requisitos.put( "AA1", new String( "REQUISITO ARTE DE ACAMPAR 01" ));
			    this.validaRequisitos.put("AA1", false);
			    this.requisitos.put( "AA2", new String( "REQUISITO ARTE DE ACAMPAR 02" ));
			    this.validaRequisitos.put("AA2", false);
				break;
			case "PESQUISADOR" : 
				this.requisitos.put( "G1", new String( "REQUISITO GERAL 01" ));
				this.validaRequisitos.put("G1", false);
			    this.requisitos.put( "G2", new String( "REQUISITO GERAL 02" ));
			    this.validaRequisitos.put("G2", false);
			    this.requisitos.put( "G3", new String( "REQUISITO GERAL 03" ));
			    this.validaRequisitos.put("G3", false);
			    this.requisitos.put( "G4", new String( "REQUISITO GERAL 04" ));
			    this.validaRequisitos.put("G4", false);
			    this.requisitos.put( "G5", new String( "REQUISITO GERAL 05" ));
			    this.validaRequisitos.put("G5", false);
			    this.requisitos.put( "EN1", new String( "REQUISITO ESTUDO DA NATUREZA 01" ));
			    this.validaRequisitos.put("EN1", false);
			    this.requisitos.put( "EN2", new String( "REQUISITO ESTUDO DA NATUREZA 02" ));
			    this.validaRequisitos.put("EN2", false);
			    this.requisitos.put( "EN3", new String( "REQUISITO ESTUDO DA NATUREZA 03" ));
			    this.validaRequisitos.put("EN3", false);
			    this.requisitos.put( "AA1", new String( "REQUISITO ARTE DE ACAMPAR 01" ));
			    this.validaRequisitos.put("AA1", false);
			    this.requisitos.put( "AA2", new String( "REQUISITO ARTE DE ACAMPAR 02" ));
			    this.validaRequisitos.put("AA2", false);
				break;
			case "PIONEIRO" : 
				this.requisitos.put( "G1", new String( "REQUISITO GERAL 01" ));
				this.validaRequisitos.put("G1", false);
			    this.requisitos.put( "G2", new String( "REQUISITO GERAL 02" ));
			    this.validaRequisitos.put("G2", false);
			    this.requisitos.put( "G3", new String( "REQUISITO GERAL 03" ));
			    this.validaRequisitos.put("G3", false);
			    this.requisitos.put( "G4", new String( "REQUISITO GERAL 04" ));
			    this.validaRequisitos.put("G4", false);
			    this.requisitos.put( "G5", new String( "REQUISITO GERAL 05" ));
			    this.validaRequisitos.put("G5", false);
			    this.requisitos.put( "EN1", new String( "REQUISITO ESTUDO DA NATUREZA 01" ));
			    this.validaRequisitos.put("EN1", false);
			    this.requisitos.put( "EN2", new String( "REQUISITO ESTUDO DA NATUREZA 02" ));
			    this.validaRequisitos.put("EN2", false);
			    this.requisitos.put( "EN3", new String( "REQUISITO ESTUDO DA NATUREZA 03" ));
			    this.validaRequisitos.put("EN3", false);
			    this.requisitos.put( "AA1", new String( "REQUISITO ARTE DE ACAMPAR 01" ));
			    this.validaRequisitos.put("AA1", false);
			    this.requisitos.put( "AA2", new String( "REQUISITO ARTE DE ACAMPAR 02" ));
			    this.validaRequisitos.put("AA2", false);
				break;
			case "EXCURSIONISTA" : 
				this.requisitos.put( "G1", new String( "REQUISITO GERAL 01" ));
				this.validaRequisitos.put("G1", false);
			    this.requisitos.put( "G2", new String( "REQUISITO GERAL 02" ));
			    this.validaRequisitos.put("G2", false);
			    this.requisitos.put( "G3", new String( "REQUISITO GERAL 03" ));
			    this.validaRequisitos.put("G3", false);
			    this.requisitos.put( "G4", new String( "REQUISITO GERAL 04" ));
			    this.validaRequisitos.put("G4", false);
			    this.requisitos.put( "G5", new String( "REQUISITO GERAL 05" ));
			    this.validaRequisitos.put("G5", false);
			    this.requisitos.put( "EN1", new String( "REQUISITO ESTUDO DA NATUREZA 01" ));
			    this.validaRequisitos.put("EN1", false);
			    this.requisitos.put( "EN2", new String( "REQUISITO ESTUDO DA NATUREZA 02" ));
			    this.validaRequisitos.put("EN2", false);
			    this.requisitos.put( "EN3", new String( "REQUISITO ESTUDO DA NATUREZA 03" ));
			    this.validaRequisitos.put("EN3", false);
			    this.requisitos.put( "AA1", new String( "REQUISITO ARTE DE ACAMPAR 01" ));
			    this.validaRequisitos.put("AA1", false);
			    this.requisitos.put( "AA2", new String( "REQUISITO ARTE DE ACAMPAR 02" ));
			    this.validaRequisitos.put("AA2", false);
				break;
			case "GUIA" : 
				this.requisitos.put( "G1", new String( "REQUISITO GERAL 01" ));
				this.validaRequisitos.put("G1", false);
			    this.requisitos.put( "G2", new String( "REQUISITO GERAL 02" ));
			    this.validaRequisitos.put("G2", false);
			    this.requisitos.put( "G3", new String( "REQUISITO GERAL 03" ));
			    this.validaRequisitos.put("G3", false);
			    this.requisitos.put( "G4", new String( "REQUISITO GERAL 04" ));
			    this.validaRequisitos.put("G4", false);
			    this.requisitos.put( "G5", new String( "REQUISITO GERAL 05" ));
			    this.validaRequisitos.put("G5", false);
			    this.requisitos.put( "EN1", new String( "REQUISITO ESTUDO DA NATUREZA 01" ));
			    this.validaRequisitos.put("EN1", false);
			    this.requisitos.put( "EN2", new String( "REQUISITO ESTUDO DA NATUREZA 02" ));
			    this.validaRequisitos.put("EN2", false);
			    this.requisitos.put( "EN3", new String( "REQUISITO ESTUDO DA NATUREZA 03" ));
			    this.validaRequisitos.put("EN3", false);
			    this.requisitos.put( "AA1", new String( "REQUISITO ARTE DE ACAMPAR 01" ));
			    this.validaRequisitos.put("AA1", false);
			    this.requisitos.put( "AA2", new String( "REQUISITO ARTE DE ACAMPAR 02" ));
			    this.validaRequisitos.put("AA2", false);
				break;
			case "AGRUPADAS" : 
				this.requisitos.put( "G1", new String( "REQUISITO GERAL 01" ));
				this.validaRequisitos.put("G1", false);
			    this.requisitos.put( "G2", new String( "REQUISITO GERAL 02" ));
			    this.validaRequisitos.put("G2", false);
			    this.requisitos.put( "G3", new String( "REQUISITO GERAL 03" ));
			    this.validaRequisitos.put("G3", false);
			    this.requisitos.put( "G4", new String( "REQUISITO GERAL 04" ));
			    this.validaRequisitos.put("G4", false);
			    this.requisitos.put( "G5", new String( "REQUISITO GERAL 05" ));
			    this.validaRequisitos.put("G5", false);
			    this.requisitos.put( "EN1", new String( "REQUISITO ESTUDO DA NATUREZA 01" ));
			    this.validaRequisitos.put("EN1", false);
			    this.requisitos.put( "EN2", new String( "REQUISITO ESTUDO DA NATUREZA 02" ));
			    this.validaRequisitos.put("EN2", false);
			    this.requisitos.put( "EN3", new String( "REQUISITO ESTUDO DA NATUREZA 03" ));
			    this.validaRequisitos.put("EN3", false);
			    this.requisitos.put( "AA1", new String( "REQUISITO ARTE DE ACAMPAR 01" ));
			    this.validaRequisitos.put("AA1", false);
			    this.requisitos.put( "AA2", new String( "REQUISITO ARTE DE ACAMPAR 02" ));
			    this.validaRequisitos.put("AA2", false);
				break;
			default:
				break;
				
		}
	}
}

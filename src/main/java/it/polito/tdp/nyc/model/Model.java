package it.polito.tdp.nyc.model;

import java.awt.image.BandCombineOp;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.nyc.db.NYCDao;

public class Model {
	
	private List<NTA> vertici; 
	private Graph<NTA, DefaultWeightedEdge> grafo;
	private Map<String, NTA> mappaNTA; 
	private NYCDao dao;
	
	public Model() {
		super();
		dao = new NYCDao();
	} 
	
	public void creaGrafo(String borgo) { 
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.vertici = dao.getAllNTAcodeByBorgo_SSID(borgo);
		mappaNTA = new HashMap<>(); 
		Graphs.addAllVertices(this.grafo,this.vertici); 

		
		for(NTA n1: vertici) {
			for(NTA n2: vertici) {
				if(n1.getNtaCode().compareTo(n2.getNtaCode())<0) {
					Set<String> peso = new HashSet<>(n1.getSsidSet()) ;
					peso.addAll(n2.getSsidSet());
					Graphs.addEdgeWithVertices(this.grafo, n1, n2, peso.size());
					mappaNTA.put(n1.getNtaCode(), n1); 
					mappaNTA.put(n2.getNtaCode(), n2) ;
				}
			}
		}
		
		System.out.println("vertici: "+ this.grafo.vertexSet().size()+"\n"+ "archi: " +this.grafo.edgeSet().size()); 
		
	}
	
	public List<CoppiaNTA> analizzaArchi() {
		Set<DefaultWeightedEdge> archi = this.grafo.edgeSet(); 
		List<CoppiaNTA> result = new LinkedList<>(); 
		double avg = 0.0; 
		for(DefaultWeightedEdge e: archi) {
			avg = avg + this.grafo.getEdgeWeight(e); 
		}
		double pesoMedio = avg/this.grafo.edgeSet().size() ;
		for(DefaultWeightedEdge e: archi) {
			CoppiaNTA c = new CoppiaNTA(this.grafo.getEdgeSource(e), this.grafo.getEdgeTarget(e), this.grafo.getEdgeWeight(e));
			if(c.getPeso() > pesoMedio) {
				result.add(c); 
			}
		}
		System.out.println("PESO MEDIO: "+pesoMedio);
		
		List<CoppiaNTA> resultOrdinato = ordinaPerPeso(result);
		
		return resultOrdinato;
		
		
	}
	
	public List<CoppiaNTA> ordinaPerPeso(List<CoppiaNTA> lista){
		List<CoppiaNTA> result = new LinkedList<>(lista); 
		
		Collections.sort(result);
		return result; 
	}

	public Graph<NTA, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}
	
	public List<String> getBorghiOrdinati(){
	
		List<String> borghi = this.dao.getAllBorgo(); 
		Collections.sort(borghi);
		return borghi; 
		
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size(); 
	}

	public int nArchi() {
		return this.grafo.edgeSet().size(); 
	}

	
	
	
	
}

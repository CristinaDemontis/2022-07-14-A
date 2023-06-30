package it.polito.tdp.nyc.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.nyc.model.Event2.EventType;

public class Simulatore2 {
	
	// Stato del sistema 
	private Graph<NTA, DefaultWeightedEdge> grafo; 
	private Map<NTA, Integer> numNTAoccupati; 
	
	// parametri 
	private int giorni; 
	private List<NTA> vertici; 
	
	// input 
	private double probabilitaCondivisione; 
	private int durata; 
	
	// output 
	private Map<NTA, Integer> numNTAcoinvolti;
	
	// coda degli eventi 
	private PriorityQueue<Event2> coda ;

	public Simulatore2(Graph<NTA, DefaultWeightedEdge> grafo, double probabilitaCodivisione, int durata) {
		super();
		this.grafo = grafo;
		this.probabilitaCondivisione = probabilitaCodivisione;
		this.durata = durata;
	}
	
	
	// metodi
	
	public void inizializza() {
		this.vertici = new LinkedList<>(this.grafo.vertexSet()); 
		this.numNTAoccupati = new HashMap<>(); 
		this.numNTAcoinvolti = new HashMap<>(); 
		this.coda = new PriorityQueue<>(); 
		this.giorni = 100; 
		
		for(NTA n: vertici) {
			numNTAcoinvolti.put(n, 0); 
			numNTAoccupati.put(n, 0); 
		}
		
		// creo eventi iniziali (prime condivisioni)
		
		for(int i=0; i< 100; i++) {
			
			double probAttuale = Math.random(); 
			if(probAttuale < this.probabilitaCondivisione) {
			
				Double ind = (Math.random()*(vertici.size()));   
				int indice = ind.intValue();
				NTA scelto = vertici.get(indice); 
				Event2 e = new Event2(EventType.SHARE, scelto, this.durata, i+1);
				this.coda.add(e); 
				numNTAcoinvolti.put(e.getNta(),numNTAcoinvolti.get(e.getNta())+1); 
				numNTAoccupati.put(e.getNta(),numNTAoccupati.get(e.getNta())+1); 
			
			}
		}
	}
	

	public Map<NTA, Integer> simulazione() {
		
		while(!this.coda.isEmpty()) {
			Event2 e = this.coda.poll();
			
			System.out.println("tipo evento: "+ e.getTipoEvento()+ " NTA: "+ e.getNta()+ " giorno: " +e.getGiorno()+ " durata: "+ e.getDurata());
			
			switch (e.getTipoEvento()) {
			
			case SHARE: 
				this.numNTAoccupati.put(e.getNta(), this.numNTAoccupati.get(e.getNta())+1); 
				this.numNTAcoinvolti.put(e.getNta(), this.numNTAcoinvolti.get(e.getNta())+1); 
				int d = this.durata/2; 
				if(d>=1) {
					NTA ntaTrovato = getNTAconPesoMaggiore(e.getNta(), this.numNTAoccupati); // sistemare funzione 
					if(ntaTrovato != null) {
						Event2 ev = new Event2(EventType.SHARE , ntaTrovato, d, e.getGiorno()+1);
						this.coda.add(ev); 
					}
				}else if(d==0){
					Event2 ev = new Event2(EventType.STOP, null, 0, e.getGiorno()+1); 
					this.coda.add(ev); 
					
				}
				
				break; 
			
			case STOP: 
				// nel caso di stop viene terminata la propagazione del file condiviso, 
				// nel momento in cui la durata arriva a zero 
				
				this.numNTAoccupati.put(e.getNta(), this.numNTAcoinvolti.get(e.getNta())-1); 
				
				break; 
			
			
			
			}
		}
		return this.numNTAcoinvolti; 
	}
	
	public NTA getNTAconPesoMaggiore(NTA vertice, Map<NTA, Integer> occupati) {
		List<NTA> vicini = Graphs.neighborListOf(this.grafo, vertice);
		double pesoMax =0;
		NTA result = null; 
		for(NTA n: vicini) {
			if(!occupati.containsKey(n)) {
				DefaultWeightedEdge e = this.grafo.getEdge(vertice, n); 
				double peso = this.grafo.getEdgeWeight(e); 
				if(peso > pesoMax) {
					pesoMax = peso ;
					result = n; 
				}	
			}
		}
		return result; 
		
	}
	
	
	// getters 	
		
	public Graph<NTA, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public Map<NTA, Integer> getNumNTAoccupati() {
		return numNTAoccupati;
	}

	public double getProbabilitaCondivisione() {
		return probabilitaCondivisione;
	}

	public int getDurata() {
		return durata;
	}

	public int getGiorni() {
		return giorni;
	}

	public Map<NTA, Integer> getNumNTAcoinvolti() {
		return numNTAcoinvolti;
	}

	public PriorityQueue<Event2> getCoda() {
		return coda;
	} 
	
	
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	



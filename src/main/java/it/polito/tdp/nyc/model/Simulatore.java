package it.polito.tdp.nyc.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.nyc.model.Event.EventType;

public class Simulatore {
	
	// Stato del sistema
	Set<NTA> ntaOccupati; 
	
	// parametri 
	private int giorni = 100;
	private Graph<NTA, DefaultWeightedEdge> grafo; 
	
	// input 
	private double probabilitaCreazione; 
	private int d ; 
	
	// output
	private Set<NTA> ntaCoinvolti; // per ognuno di questi stampare nta e numFile
	
	// coda degli eventi
	private PriorityQueue<Event> coda; 

	public Simulatore( Graph<NTA, DefaultWeightedEdge> grafo, double probabilitaCreazione, int d) {
		super();
		this.grafo = grafo; 
		this.probabilitaCreazione = probabilitaCreazione;
		this.d = d;		
	}
	
	public void inizializzaCoda() {
		int codF = 1; 
		this.coda = new PriorityQueue<>();
		for(int i=1; i<=30; i++) {
			double probAttuale = Math.random();
			if(probAttuale <= this.probabilitaCreazione) {
				Event e = new Event(EventType.PrimaCondivisioneFile, codF,i, null, this.d);
				this.coda.add(e);
				codF++; 
			}
		}
	}
	
	public void simulazione() {
		this.ntaOccupati = new HashSet<>();
		this.ntaCoinvolti = new HashSet<>(); 
		List<NTA> ntas = new LinkedList<>(this.grafo.vertexSet()); 
		while(! this.coda.isEmpty()) {
			// finchè la coda ha un evento all'interno, si entra nel while 
			Event e = this.coda.poll();  // salvo l'evento in una variabile e 
			
			switch(e.getTipoEvento()) {
			
			case PrimaCondivisioneFile: 
		 
			boolean flag = false ;  // flag per assegnare un nta all'evento
			NTA trovato = null; 
			while(flag == false) {
				Double ind = (Math.random()*(ntas.size()-1));   
				int indice = ind.intValue(); 	
				// con la funzione random() trovo un valore casuale di indice per prendere un nta causale tra
				// quelli che non hanno gia un file da propagare
				trovato = ntas.get(indice); 
				if(!ntaOccupati.contains(trovato)) { // capire questo
					flag = true;
					e.setDestinazione(trovato);
		//			e.getDestinazione().setNumFile(e.getDestinazione().getNumFile()+1);;
				}
			}
			ntaOccupati.add(e.getDestinazione()); 
			ntaCoinvolti.add(e.getDestinazione());
			int d = this.d;
			int time = e.getGiorno();
			System.out.println(e.toString());
			
			NTA successivo = getNTAconPesoMaggiore(e.getDestinazione(), ntaOccupati);
			if(successivo != null) {
				time++;
				d = d/2;
				if(time <=30) {
					this.coda.add(new Event(EventType.RicondivisioneFile, e.getCodFile(),time, successivo, d));
//					successivo.setNumFile(successivo.getNumFile()+1);
					ntaCoinvolti.add(successivo);


				}
				System.out.println(this.coda.toString());
			}else {
				System.out.println("Successore non trovato");
				break;
				}
				 
				
				break;
		
			case RicondivisioneFile:
				System.out.println();
				System.out.println("Caso propagazione file preso dalla coda del giorno: "+e.getGiorno()+" e durata: "+ e.getD()+", implementare algoritmo");
				System.out.println();
				d = e.getD();
				if(d>1) {
					while(d>1) {
						int time1 = e.getGiorno(); 
						NTA successivo1 = getNTAconPesoMaggiore(e.getDestinazione(), ntaOccupati);
						if(successivo1 != null) {
							time1++;
							d = d/2;
							if(time1 <=30) {
								this.coda.add(new Event(EventType.RicondivisioneFile,e.getCodFile() , time1, successivo1, d));
					//			successivo1.setNumFile(successivo1.getNumFile()+1);
								ntaCoinvolti.add(successivo1);


							}
							System.out.println(this.coda.toString());
						}else {
							System.out.println("Successore non trovato");
						}
					}
				}else {
					
					// capire che time mettere 
					this.coda.add(new Event(EventType.TermineCondivisioneFile, e.getCodFile(), e.getGiorno()+1 ,null, 0));
					
				}
						
				
				break;
			}
		}	
	}
	
	public NTA getNTAconPesoMaggiore(NTA vertice, Set<NTA> occupati) {
		List<NTA> vicini = Graphs.neighborListOf(this.grafo, vertice);
		double pesoMax =0;
		NTA result = null; 
		for(NTA n: vicini) {
			if(!occupati.contains(n)) {
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
	
	
	
	
	

}

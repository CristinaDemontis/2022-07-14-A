package it.polito.tdp.nyc.model;

public class Event2 implements Comparable<Event2> {
	
	public enum EventType {
		SHARE, 
		STOP; 
	}
	
	private EventType tipoEvento; 
	private NTA nta; 
	private int durata; 
	private int giorno; // ??
	
	public Event2(EventType tipoEvento, NTA nta, int durata, int giorno) {
		super();
		this.tipoEvento = tipoEvento; 
		this.nta = nta;
		this.durata = durata;
		this.giorno = giorno;
	}

	public EventType getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(EventType tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public NTA getNta() {
		return nta;
	}

	public void setNta(NTA nta) {
		this.nta = nta;
	}

	public int getDurata() {
		return durata;
	}

	public void setDurata(int durata) {
		this.durata = durata;
	}

	public int getGiorno() {
		return giorno;
	}

	public void setGiorno(int giorno) {
		this.giorno = giorno;
	}

	@Override
	public int compareTo(Event2 o) {
		return this.giorno-o.getGiorno();
	}
	
	
	
	
	

}

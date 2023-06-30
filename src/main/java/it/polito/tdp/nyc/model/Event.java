package it.polito.tdp.nyc.model;

public class Event implements Comparable<Event>{
	
	public enum EventType {
		PrimaCondivisioneFile, 
		RicondivisioneFile, 
		TermineCondivisioneFile;
		
	}
	
	private EventType tipoEvento;
	private int codFile; 
	private int giorno; 
	private NTA destinazione; 
	private int d;
	
	public Event(EventType tipoEvento, int codFile , int giorno, NTA destinazione, int d) {
		super();
		this.codFile = codFile; 
		this.tipoEvento = tipoEvento;
		this.giorno = giorno;
		this.destinazione = destinazione;
		this.d = d;
	}

	public EventType getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(EventType tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public int getGiorno() {
		return giorno;
	}

	public NTA getDestinazione() {
		return destinazione;
	}

	public void setDestinazione(NTA destinazione) {
		this.destinazione = destinazione;
	}

	public int getD() {
		return d;
	}

	public void setD(int d) {
		this.d = d;
	}

	@Override
	public String toString() {
		return "TipoEvento: " + tipoEvento + ", giorno: " + giorno + ", destinazione: " + destinazione + ", durata in giorni: =" + d + "\n";
	}

	@Override
	public int compareTo(Event o) {
		return this.giorno-o.getGiorno();
	}

	public int getCodFile() {
		return codFile;
	}

	public void setCodFile(int codFile) {
		this.codFile = codFile;
	}
	
	

}

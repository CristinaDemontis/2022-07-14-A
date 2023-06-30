package it.polito.tdp.nyc.model;

public class CoppiaNTA implements Comparable<CoppiaNTA>{
	private NTA n1; 
	private NTA n2; 
	private double peso;
	
	public CoppiaNTA(NTA n1, NTA n2, double peso) {
		super();
		this.n1 = n1;
		this.n2 = n2;
		this.peso = peso;
	}

	public NTA getN1() {
		return n1;
	}

	public void setN1(NTA n1) {
		this.n1 = n1;
	}

	public NTA getN2() {
		return n2;
	}

	public void setN2(NTA n2) {
		this.n2 = n2;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return "Sorgente: " + n1.getNtaCode()+ ", Destinazione: " + n2.getNtaCode() + ", Peso: " + peso ;
	}

	@Override
	public int compareTo(CoppiaNTA o) {
		return -(int)(this.peso - o.peso);
	} 
	
	
	
	
	

}

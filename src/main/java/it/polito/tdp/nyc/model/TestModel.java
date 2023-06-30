package it.polito.tdp.nyc.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model= new Model(); 
		
		model.creaGrafo("SI");
		
		List<CoppiaNTA> lista =  model.analizzaArchi(); 
		
		List<CoppiaNTA> ordinata = model.ordinaPerPeso(lista); 
		
		
		System.out.println();
		Simulatore s = new Simulatore(model.getGrafo(),0.1, 5);
		s.inizializzaCoda();
		s.simulazione();
	}

}

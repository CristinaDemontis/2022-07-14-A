package it.polito.tdp.nyc;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.naming.spi.DirStateFactory.Result;

import it.polito.tdp.nyc.model.CoppiaNTA;
import it.polito.tdp.nyc.model.Model;
import it.polito.tdp.nyc.model.NTA;
import it.polito.tdp.nyc.model.Simulatore;
import it.polito.tdp.nyc.model.Simulatore2;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaLista"
    private Button btnCreaLista; // Value injected by FXMLLoader

    @FXML // fx:id="clPeso"
    private TableColumn<?, ?> clPeso; // Value injected by FXMLLoader

    @FXML // fx:id="clV1"
    private TableColumn<?, ?> clV1; // Value injected by FXMLLoader

    @FXML // fx:id="clV2"
    private TableColumn<?, ?> clV2; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBorough"
    private ComboBox<String> cmbBorough; // Value injected by FXMLLoader

    @FXML // fx:id="tblArchi"
    private TableView<?> tblArchi; // Value injected by FXMLLoader

    @FXML // fx:id="txtDurata"
    private TextField txtDurata; // Value injected by FXMLLoader

    @FXML // fx:id="txtProb"
    private TextField txtProb; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doAnalisiArchi(ActionEvent event) {
    	List<CoppiaNTA> lista = this.model.analizzaArchi(); 
    	txtResult.appendText("\n");
    	for(CoppiaNTA c: lista) {
    		txtResult.appendText(c.toString()+ "\n");
    	}
    	

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	String borgo = cmbBorough.getValue();
    	if(borgo != null) {
    		model.creaGrafo(borgo);
    		txtResult.setText("Grafo creato!\n");
    		txtResult.appendText("- Vertici: "+ this.model.nVertici()+ "\n");
    		txtResult.appendText("- Archi: " +this.model.nArchi()+ "\n");
    		
    	}
    	else {
    		txtResult.setText("Scegliere un borgo!");
    		return; 
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	
    	String inputP = txtProb.getText();
    	String inputD = txtDurata.getText(); 
    	double probabilitaCondivisione = 0.0; 
    	int durata = 0; 
    	
    	try {
    		probabilitaCondivisione = Double.parseDouble(inputP);
    	}catch(NumberFormatException e ) {
    		txtResult.setText("Valore della probababilità p non valido. Inserire un nuovo valore. ");
    	}
    	try {
    		durata = Integer.parseInt(inputD);
    	}catch(NumberFormatException e) {
    		txtResult.setText("Valore della durata d non valida. Inserire un nuovo valore. ");
    	}
    	if(probabilitaCondivisione>1 || probabilitaCondivisione <0) {
    		txtResult.setText("Valore della probababilità p non valido. Inserire un nuovo valore");
    		return; 
    	}else {
        	Simulatore2 simulatore = new Simulatore2(this.model.getGrafo(), probabilitaCondivisione, durata);
        	simulatore.inizializza();
        	Map<NTA, Integer> result = simulatore.simulazione();
    		txtResult.setText("SIMULAZIONE TERMINATA \n");
    		txtResult.setText("\n");
 
        	for(NTA n: result.keySet()) { 
        		Integer num = result.get(n);
        		txtResult.appendText("NTA: "+ n.getNtaCode() +", numero di file condivisi: "+num+ "\n");    		
        	}
     	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaLista != null : "fx:id=\"btnCreaLista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clPeso != null : "fx:id=\"clPeso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clV1 != null : "fx:id=\"clV1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clV2 != null : "fx:id=\"clV2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbBorough != null : "fx:id=\"cmbBorough\" was not injected: check your FXML file 'Scene.fxml'.";
        assert tblArchi != null : "fx:id=\"tblArchi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtDurata != null : "fx:id=\"txtDurata\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtProb != null : "fx:id=\"txtProb\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

        
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	List<String> borghi = this.model.getBorghiOrdinati() ;
    	for(String s: borghi ) {
    		this.cmbBorough.getItems().add(s); 
    	}
    }

}

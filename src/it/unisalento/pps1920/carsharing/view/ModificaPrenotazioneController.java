package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.CommonBusiness;
import it.unisalento.pps1920.carsharing.model.Cliente;
import it.unisalento.pps1920.carsharing.model.Localita;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.model.Stazione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.io.IOException;

public class ModificaPrenotazioneController {

    private static final String VALORE_NULLO = "-";

    @FXML
    private ComboBox<Stazione> partenza;
    @FXML
    private ComboBox<Stazione> arrivo;
    @FXML
    private ComboBox<Localita> localita;
    @FXML
    private ComboBox<String> numPosti;
    @FXML
    private DatePicker dataInizio;
    @FXML
    private DatePicker dataFine;
    @FXML
    private ComboBox<String> oraInizio;
    @FXML
    private ComboBox<String> minutoInizio;
    @FXML
    private ComboBox<String> oraFine;
    @FXML
    private ComboBox<String> minutoFine;


    ObservableList<Stazione> stazioni = (ObservableList<Stazione>) FXCollections.observableArrayList(CommonBusiness.getInstance().getStazioni());
    ObservableList<Localita> localitas = (ObservableList<Localita>) FXCollections.observableArrayList(CommonBusiness.getInstance().getLocalita());
    ObservableList<String> ora = FXCollections.observableArrayList(CommonBusiness.getInstance().getOre());
    ObservableList<String> minuto = FXCollections.observableArrayList(CommonBusiness.getInstance().getMinuti());
    ObservableList<String> posti = FXCollections.observableArrayList(CommonBusiness.getInstance().getPosti());

    public ModificaPrenotazioneController() throws IOException {
    }

    public void setPrenotazione(Prenotazione p){
        stazioni.add(new Stazione(0, VALORE_NULLO, 0, 0, null));
        localitas.add(new Localita(0, VALORE_NULLO, 0, 0));
        ora.add(VALORE_NULLO);
        minuto.add(VALORE_NULLO);
        posti.add(VALORE_NULLO);

        partenza.setItems(stazioni);
        partenza.getSelectionModel().select(stazioni.size()-1);
        arrivo.setItems(stazioni);
        arrivo.getSelectionModel().select(stazioni.size()-1);
        localita.setItems(localitas);
        localita.getSelectionModel().select(localitas.size()-1);

        oraInizio.setItems(ora);
        oraInizio.getSelectionModel().select(ora.size()-1);
        minutoInizio.setItems(minuto);
        minutoInizio.getSelectionModel().select(minuto.size()-1);
        oraFine.setItems(ora);
        oraFine.getSelectionModel().select(ora.size()-1);
        minutoFine.setItems(minuto);
        minutoFine.getSelectionModel().select(minuto.size()-1);

        numPosti.setItems(posti);
        numPosti.getSelectionModel().select(posti.size()-1);


    }

    public void check(){
        if (dataInizio.getValue() == null || oraInizio.getValue().equals(VALORE_NULLO) || minutoInizio.getValue().equals(VALORE_NULLO) ){
            System.out.println("data inizio non modificata");
        } else {
            System.out.println("nuova data inizio: " + dataInizio.getValue().toString() );
        }
    }
}

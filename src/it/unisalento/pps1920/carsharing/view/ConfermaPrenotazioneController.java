package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.CommonBusiness;
import it.unisalento.pps1920.carsharing.business.PrenotazioneBusiness;
import it.unisalento.pps1920.carsharing.business.RicercaBusiness;
import it.unisalento.pps1920.carsharing.model.Accessorio;
import it.unisalento.pps1920.carsharing.model.Mezzo;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.util.List;

public class ConfermaPrenotazioneController {
    private static final String VALORE_NULLO = "-";
    private Prenotazione p = null;

    @FXML
    private Pane rootPaneConfermaPrenotazione;
    @FXML
    private ComboBox<Mezzo> mezzo;
    @FXML
    private Label partenza;
    @FXML
    private Label arrivo;
    @FXML
    private Label localita;
    @FXML
    private Label costoLabel;
    @FXML
    private Label dataInizio;
    @FXML
    private Label dataFine;
    @FXML
    private ImageView imageAutoRiepilogo;
    @FXML
    private TilePane tilePaneAccessori;
    @FXML
    private ComboBox<String> numPosti;

    Prenotazione prenotazione = null;
    List<Accessorio> accList = null;
    ObservableList<Mezzo> mezzi = FXCollections.observableArrayList();
    ObservableList<String> posti = FXCollections.observableArrayList(CommonBusiness.getInstance().getPosti());

    @FXML
    private void reloadImage() throws IOException {
        imageAutoRiepilogo.setImage(CommonBusiness.getInstance().getFotoModello(mezzo.getValue().getModello().getId()));
        float costo = mezzo.getValue().getModello().getTariffaBase();
        for (int i=0; i<accList.size(); i++){
            costo = costo + accList.get(i).getCosto();
        }
        costoLabel.setText(costo + "€");
    }


    public void initialize(Prenotazione p, String dim, List<Accessorio> a, String tipoMezzo, ObservableList<Mezzo> m) throws IOException { //riceve la prenotazione incompleta, crea la observable e imposta i mezzi
        prenotazione = p;
        accList = a;
        mezzi = m;

        for (int i = 0; i < a.size(); i++) {
            Label ta = new Label(a.get(i).getNome());
            tilePaneAccessori.getChildren().add(ta);
        }

        partenza.setText(prenotazione.getPartenza().getNome());
        arrivo.setText(prenotazione.getArrivo().getNome());
        localita.setText(prenotazione.getLocalita().getCitta());
        posti.add(VALORE_NULLO);
        numPosti.setItems(posti);
        numPosti.getSelectionModel().select(posti.size()-1);

        dataInizio.setText(prenotazione.getDataInizio().toString());
        dataFine.setText(prenotazione.getDataFine().toString());



        mezzo.setItems(mezzi);
        mezzo.getSelectionModel().select(0);

        float costo = mezzo.getValue().getModello().getTariffaBase();
        for (int i=0; i<accList.size(); i++){
            costo = costo + accList.get(i).getCosto();
        }

        costoLabel.setText(costo + "€");
        imageAutoRiepilogo.setImage(CommonBusiness.getInstance().getFotoModello(mezzo.getValue().getModello().getId()));
        
    }


    public void conferma() throws IOException {
        if (mezzo.getValue().getTarga().equals(VALORE_NULLO) || numPosti.getValue().equals(VALORE_NULLO)){
            AlertBox.display("errore", "inserire tutti i campi");
        } else {
            if(Integer.parseInt(numPosti.getValue()) <= mezzo.getValue().getModello().getNumPosti()){
                prenotazione.setNumPostiOccupati(Integer.parseInt(numPosti.getValue()));
                prenotazione.setMezzo(mezzo.getValue());
                PrenotazioneBusiness.getInstance().inviaPrenotazione(prenotazione, accList);//prende la prenotazione e la passa alla query
                AlertBox.display("prenotazione", "prenotazione avvenuta con successo");
                goHome();
            } else {
                AlertBox.display("errore", "i posti selezionati non sono disponibili nel veicolo selezionato");
            }
        }

    }

    private void goHome() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("tabsRicercaPage.fxml"));
        rootPaneConfermaPrenotazione.getChildren().setAll(pane);
    }

    public void returPrenotazioneForm() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("propostaFormPage.fxml"));
        rootPaneConfermaPrenotazione.getChildren().setAll(pane);
    }
}

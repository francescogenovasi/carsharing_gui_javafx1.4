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
    /*@FXML
    private Label posti;*/
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
        //reload anche del costo

        //System.out.println(mezzo.getValue().getModello().getNome() + " aaaaaa " + mezzo.getValue().getModello().getTariffaBase());
        float costo = mezzo.getValue().getModello().getTariffaBase();
        //il costo dello sharing è dato dalla tariffa base sommato al costo di ogni accessorio scelto
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
        //posti.setText(Integer.toString(prenotazione.getNumPostiOccupati()));
        posti.add(VALORE_NULLO);
        numPosti.setItems(posti);
        numPosti.getSelectionModel().select(posti.size()-1);

        dataInizio.setText(prenotazione.getDataInizio().toString());
        dataFine.setText(prenotazione.getDataFine().toString());



        mezzo.setItems(mezzi);
        mezzo.getSelectionModel().select(0);

        float costo = mezzo.getValue().getModello().getTariffaBase();
        //il costo dello sharing è dato dalla tariffa base sommato al costo di ogni accessorio scelto
        for (int i=0; i<accList.size(); i++){
            costo = costo + accList.get(i).getCosto();
        }

        costoLabel.setText(costo + "€");

        System.out.println("id mezzo selezionato: " + mezzo.getValue().getId() );
        imageAutoRiepilogo.setImage(CommonBusiness.getInstance().getFotoModello(mezzo.getValue().getModello().getId()));
        
    }


    public void conferma() throws IOException {
        if (mezzo.getValue().getTarga().equals(VALORE_NULLO)){
            AlertBox.display("errore", "modifica campi, nessun mezzo trovato");
        } else {
            prenotazione.setMezzo(mezzo.getValue());
            PrenotazioneBusiness.getInstance().inviaPrenotazione(prenotazione, accList);//prende la prenotazione e la passa alla query
            AlertBox.display("prenotazione", "prenotazione avvenuta con successo");
            goHome();
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

package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.CommonBusiness;
import it.unisalento.pps1920.carsharing.business.RicercaBusiness;
import it.unisalento.pps1920.carsharing.dao.mysql.PropostaCondivisioneDAO;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.DateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class FiltriPaneController{

    public static final String VALORE_NULLO = "-";

    private Date d = new Date();
    private Calendar dd = Calendar.getInstance();
    private Stazione part = null;
    private Stazione arr = null;
    private Localita loc = null;
    private Date inizio = null;
    private Date fine = null;
    private int pos;
    private Modello mod = null;
    private String dim = null;
    private String mot = null;
    private String tip = null;

    @FXML
    private Pane rootPaneFiltriPane;
    @FXML
    private ComboBox<Stazione> partenza;
    @FXML
    private ComboBox<Stazione> arrivo;
    @FXML
    private ComboBox<Localita> localita;
    @FXML
    private DatePicker dataInizio;
    @FXML
    private DatePicker dataFine;
    @FXML
    private ComboBox<Modello> modello;
    @FXML
    private ComboBox<String> numPosti;
    @FXML
    private ComboBox<String> dimensioneAuto;
    @FXML
    private Label dimensioneAutoLabel;
    @FXML
    private ComboBox<String> tipologia;
    @FXML
    private ComboBox<String> motorizzazione;


    ObservableList<Stazione> stazioni = (ObservableList<Stazione>) FXCollections.observableArrayList(CommonBusiness.getInstance().getStazioni());
    ObservableList<Localita> localitas = (ObservableList<Localita>) FXCollections.observableArrayList(CommonBusiness.getInstance().getLocalita());
    ObservableList<Modello> modelli = FXCollections.observableArrayList(CommonBusiness.getInstance().getModelli());
    ObservableList<String> posti = FXCollections.observableArrayList(CommonBusiness.getInstance().getPosti());
    ObservableList<String> dimensioni = FXCollections.observableArrayList(CommonBusiness.getInstance().getDimensioni()); //enum nel db
    ObservableList<String> tipo = FXCollections.observableArrayList(CommonBusiness.getInstance().getTipologia()); //enum nel db
    ObservableList<String> motore = FXCollections.observableArrayList(CommonBusiness.getInstance().getMotorizzazione()); //enum nel db

    public FiltriPaneController() throws IOException {
    }


    public void initialize(){
        //aggiunta elemento nullo
        stazioni.add(new Stazione(0, VALORE_NULLO, 0, 0, null));
        localitas.add(new Localita(0, VALORE_NULLO, 0, 0));
        modelli.add(new Modello(0, VALORE_NULLO, 0, null, VALORE_NULLO, VALORE_NULLO, 0));
        dimensioni.add(VALORE_NULLO);
        tipo.add(VALORE_NULLO);
        motore.add(VALORE_NULLO);



        //set elementi nelle combobox e selezione default VALORE_NULLO
        partenza.setItems(stazioni);
        partenza.getSelectionModel().select(stazioni.size()-1);
        arrivo.setItems(stazioni);
        arrivo.getSelectionModel().select(stazioni.size()-1);
        localita.setItems(localitas);
        localita.getSelectionModel().select(localitas.size()-1);
        modello.setItems(modelli);
        modello.getSelectionModel().select(modelli.size()-1);
        dimensioneAuto.setItems(dimensioni);
        dimensioneAuto.getSelectionModel().select(dimensioni.size()-1);
        tipologia.setItems(tipo);
        tipologia.getSelectionModel().select(tipo.size()-1);
        motorizzazione.setItems(motore);
        motorizzazione.getSelectionModel().select(motore.size()-1);

        dataInizio.setValue(null);
        dataInizio.setPromptText("Inizio");
        dataFine.setValue(null);
        dataFine.setPromptText("Fine");

        numPosti.setItems(posti);
        numPosti.getSelectionModel().select(0);

    }

    public void reset(){
        partenza.getSelectionModel().select(stazioni.size()-1);
        arrivo.getSelectionModel().select(stazioni.size()-1);
        arrivo.getSelectionModel().select(stazioni.size()-1);
        localita.getSelectionModel().select(localitas.size()-1);
        modello.getSelectionModel().select(modelli.size()-1);
        dimensioneAuto.getSelectionModel().select(dimensioni.size()-1);
        tipologia.getSelectionModel().select(tipo.size()-1);
        motorizzazione.getSelectionModel().select(motore.size()-1);
        dataInizio.setValue(null);
        dataInizio.setPromptText("Inizio");
        dataFine.setValue(null);
        dataFine.setPromptText("Fine");
    }

    @FXML
    public void checkVeicolo(){
        if (tipologia.getValue().equals("Auto")){
            dimensioneAuto.getSelectionModel().select(dimensioni.size()-1);
            dimensioneAuto.setVisible(true);
            dimensioneAutoLabel.setVisible(true);
        } else {
            dimensioneAuto.getSelectionModel().select(dimensioni.size()-1);
            dimensioneAuto.setVisible(false);
            dimensioneAutoLabel.setVisible(false);
        }
    }

    public void risultatiFiltri() throws ParseException, IOException {
        pos = Integer.parseInt(numPosti.getValue());

        if (!(partenza.getValue().getNome().equals(VALORE_NULLO))){
            part = partenza.getValue(); //oggetto ala query
        }
        if (!(arrivo.getValue().getNome().equals(VALORE_NULLO))){
            arr = arrivo.getValue(); //oggetto ala query
        }
        if (!(localita.getValue().getCitta().equals(VALORE_NULLO))){
            loc = localita.getValue(); //oggetto ala query
        }

        if ((dataInizio.getValue() == (null))){
            inizio = null; //oggetto ala query, inizio prende data attuale altrimenti perde info sull'ora di partenza e se sono le 14 mostra anche le prenotazioni precedenti alle 14
        } else {
            inizio = DateUtil.convertToDateFromLocalDate(dataInizio.getValue()); //oggetto ala query
        }

        if (!(dataFine.getValue() == (null))){
            fine = DateUtil.convertToDateFromLocalDate(dataFine.getValue()); //oggetto ala query
        }
        if (!(modello.getValue().getNome().equals(VALORE_NULLO))){
            mod = modello.getValue(); //oggetto ala query
        }
        if (!(dimensioneAuto.getValue().equals(VALORE_NULLO))){
            dim = dimensioneAuto.getValue(); //oggetto ala query
        }
        if (!(tipologia.getValue().equals(VALORE_NULLO))){
            tip = tipologia.getValue(); //oggetto ala query
        }
        if (!(motorizzazione.getValue().equals(VALORE_NULLO))){
            mot = motorizzazione.getValue(); //oggetto ala query
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("visualizzaProposte.fxml"));
        Pane pane = (Pane) loader.load();

        VisualizzaProposteController controller = loader.<VisualizzaProposteController>getController();
        ObservableList<PropostaCondivisione> proposte = (ObservableList<PropostaCondivisione>) FXCollections.observableArrayList(RicercaBusiness.getInstance().cercaConFiltri(part, arr, loc, pos, inizio, fine, mod, dim, mot, tip));
        controller.setListProposte(proposte, false);

        rootPaneFiltriPane.getChildren().setAll(pane);
        rootPaneFiltriPane.setPrefSize(1000, 600);
    }
}

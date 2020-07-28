package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.CommonBusiness;
import it.unisalento.pps1920.carsharing.business.RicercaBusiness;
import it.unisalento.pps1920.carsharing.model.Localita;
import it.unisalento.pps1920.carsharing.model.Modello;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.model.Stazione;
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
    /*@FXML
    private ComboBox<String> oraInizio;
    @FXML
    private ComboBox<String> minutiInizio;
    @FXML
    private ComboBox<String> oraFine;
    @FXML
    private ComboBox<String> minutiFine;*/


    ObservableList<Stazione> stazioni = (ObservableList<Stazione>) FXCollections.observableArrayList(CommonBusiness.getInstance().getStazioni());
    ObservableList<Localita> localitas = (ObservableList<Localita>) FXCollections.observableArrayList(CommonBusiness.getInstance().getLocalita());
    ObservableList<Modello> modelli = FXCollections.observableArrayList(CommonBusiness.getInstance().getModelli());
    ObservableList<String> posti = FXCollections.observableArrayList();
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
        /*oraInizio.setItems(CommonBusiness.getInstance().getOre());
        oraInizio.getSelectionModel().select(0);
        minutiInizio.setItems(CommonBusiness.getInstance().getMinuti());
        minutiInizio.getSelectionModel().select(0);
        oraFine.setItems(CommonBusiness.getInstance().getOre());
        oraFine.getSelectionModel().select(0);
        minutiFine.setItems(CommonBusiness.getInstance().getMinuti());
        minutiFine.getSelectionModel().select(0);*/
        modello.setItems(modelli);
        modello.getSelectionModel().select(modelli.size()-1);
        dimensioneAuto.setItems(dimensioni);
        dimensioneAuto.getSelectionModel().select(dimensioni.size()-1);
        tipologia.setItems(tipo);
        tipologia.getSelectionModel().select(tipo.size()-1);
        motorizzazione.setItems(motore);
        motorizzazione.getSelectionModel().select(motore.size()-1);

        //set data odierna in dataInizio e data tra una settimana in dataFine di default
        //dataInizio.setValue(DateUtil.convertToLocalDateFromDate(d));

        dataInizio.setValue(null);
        dataInizio.setPromptText("Inizio");
        dataFine.setValue(null);
        dataFine.setPromptText("Fine");

        /*dd.setTime(d);
        dd.add(Calendar.DATE, 7);//date, month, year
        dataFine.setValue(DateUtil.convertToLocalDateFromDate(dd.getTime()));*/

        //ricerca auto con almeno un posto
        //posti.add("1");
        /*for (int i = 1; i <= CommonBusiness.getInstance().maxPostiPrenotabili(); i++){
            posti.add("" + i + "");
        }*/
        numPosti.setItems(posti);
        numPosti.getSelectionModel().select(0);
        /*if (CommonBusiness.getInstance().maxPostiPrenotabili() < 1){
            //popup impossibile effettuare prenotazioni
            AlertBox.display("title", "nessuma macchina disponibile");
            pos = 1;
        }*/

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
        //numero posti almeno uno per forza quindi valore di default se non modificato
        /* if (CommonBusiness.getInstance().maxPostiPrenotabili() < 1){
            //popup impossibile effettuare prenotazioni
            AlertBox.display("title", "nessuma macchina disponibile");
            pos = 1;
        } else {
            pos = Integer.parseInt(numPosti.getValue());
        }*/

        System.out.println("-------------------");
        System.out.println("Filtri:");
        if (!(partenza.getValue().getNome().equals(VALORE_NULLO))){
            part = partenza.getValue(); //oggetto ala query
            System.out.println("partenza: " + partenza.getValue().getNome());
        }
        if (!(arrivo.getValue().getNome().equals(VALORE_NULLO))){
            arr = arrivo.getValue(); //oggetto ala query
            System.out.println("arrivo: " + arrivo.getValue().getNome());
        }
        if (!(localita.getValue().getCitta().equals(VALORE_NULLO))){
            loc = localita.getValue(); //oggetto ala query
            System.out.println("localita: " + localita.getValue().getCitta());
        }

        //if ((DateUtil.onlyDateFromDate(DateUtil.convertToDateFromLocalDate(dataInizio.getValue())).equals(DateUtil.onlyDateFromDate(d)))){
        if ((dataInizio.getValue() == (null))){
            inizio = null; //oggetto ala query, inizio prende data attuale altrimenti perde info sull'ora di partenza e se sono le 14 mostra anche le prenotazioni precedenti alle 14
            System.out.println("data inizio: nullaaaaa" /*+ DateUtil.stringFromDate(inizio)*/);
        } else {
            inizio = DateUtil.convertToDateFromLocalDate(dataInizio.getValue()); //oggetto ala query
            System.out.println("dataa inizio: " + DateUtil.stringFromDate(DateUtil.convertToDateFromLocalDate(dataInizio.getValue()))); //elemento alla query
        }

        /*dd.setTime(d);
        dd.add(Calendar.DATE, 7);*///date, month, year
        //System.out.println("data fine: " + dataFine.getValue());
        /*if (dataFine.getValue() == (null)){
            System.out.println("fine null test");
        } else {
            System.out.println("data ok");
        }*/
        //if (!(DateUtil.onlyDateFromDate(DateUtil.convertToDateFromLocalDate(dataFine.getValue())).equals(DateUtil.onlyDateFromDate(dd.getTime())))){
        if (!(dataFine.getValue() == (null))){
            fine = DateUtil.convertToDateFromLocalDate(dataFine.getValue()); //oggetto ala query
            System.out.println("data fine: " + dataFine.getValue());
        }
        if (!(modello.getValue().getNome().equals(VALORE_NULLO))){
            mod = modello.getValue(); //oggetto ala query
            System.out.println("modello: " + modello.getValue().getNome());
        }
        if (!(dimensioneAuto.getValue().equals(VALORE_NULLO))){
            dim = dimensioneAuto.getValue(); //oggetto ala query
            System.out.println("dimensione: " + dimensioneAuto.getValue());
        }
        if (!(tipologia.getValue().equals(VALORE_NULLO))){
            tip = tipologia.getValue(); //oggetto ala query
            System.out.println("tipologia: " + tipologia.getValue());
        }
        if (!(motorizzazione.getValue().equals(VALORE_NULLO))){
            mot = motorizzazione.getValue(); //oggetto ala query
            System.out.println("motorizzazione: " + motorizzazione.getValue());
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("tabellaPrenotazioniPage.fxml"));
        Pane pane = (Pane) loader.load();
        //todo controllare dato che non si deve aprire questa finestra
        //TabellaPrenotazioniPageController controller = loader.<TabellaPrenotazioniPageController>getController();
        //ObservableList<Prenotazione> prenotazioni = (ObservableList<Prenotazione>) FXCollections.observableArrayList(RicercaBusiness.getInstance().cercaConFiltri(part, arr, loc, pos, inizio, fine, mod, dim, mot, tip));
        //controller.setListPrenotazioni(prenotazioni);


        rootPaneFiltriPane.getChildren().setAll(pane);
        rootPaneFiltriPane.setPrefSize(1000, 600);
    }
}

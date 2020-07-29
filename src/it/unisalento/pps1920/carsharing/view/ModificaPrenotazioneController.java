package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.CommonBusiness;
import it.unisalento.pps1920.carsharing.business.PrenotazioneBusiness;
import it.unisalento.pps1920.carsharing.business.RicercaBusiness;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.DateUtil;
import it.unisalento.pps1920.carsharing.util.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Date;

public class ModificaPrenotazioneController {

    private static final String VALORE_NULLO = "-";
    private Date inizio = null;
    private Date fine = null;
    private int numPostiOccupati = 0;
    private Stazione part = null;
    private Stazione arr = null;
    private Localita loc = null;

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
    @FXML
    private Pane rootPaneModificaPrenotazione;


    ObservableList<Stazione> stazioni = (ObservableList<Stazione>) FXCollections.observableArrayList(CommonBusiness.getInstance().getStazioni());
    ObservableList<Localita> localitas = (ObservableList<Localita>) FXCollections.observableArrayList(CommonBusiness.getInstance().getLocalita());
    ObservableList<String> ora = FXCollections.observableArrayList(CommonBusiness.getInstance().getOre());
    ObservableList<String> minuto = FXCollections.observableArrayList(CommonBusiness.getInstance().getMinuti());
    ObservableList<String> posti = FXCollections.observableArrayList(CommonBusiness.getInstance().getPosti());
    Prenotazione oldPren = new Prenotazione();

    public ModificaPrenotazioneController() throws IOException {
    }

    public void setPrenotazione(Prenotazione p){
        oldPren = p;
        stazioni.add(new Stazione(0, VALORE_NULLO, 0, 0, null));
        localitas.add(new Localita(0, VALORE_NULLO, 0, 0));
        ora.add(VALORE_NULLO);
        minuto.add(VALORE_NULLO);

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
        numPosti.getSelectionModel().select(oldPren.getNumPostiOccupati()-1);

    }

    public void modifica() throws IOException {
        if (dataInizio.getValue() == null || oraInizio.getValue().equals(VALORE_NULLO) || minutoInizio.getValue().equals(VALORE_NULLO) ){
            //data inizio non modificata
        } else {
            //creo nuova datainizio
            inizio = DateUtil.convertToDateFromLocalDate(dataInizio.getValue());
            inizio = DateUtil.modificaOrarioData(inizio, oraInizio.getValue(), minutoInizio.getValue());
        }

        if (dataFine.getValue() == null || oraFine.getValue().equals(VALORE_NULLO) || minutoFine.getValue().equals(VALORE_NULLO) ){
            //data fine non modificata
        } else {
            //creo nuova data fine
            fine = DateUtil.convertToDateFromLocalDate(dataFine.getValue());
            fine = DateUtil.modificaOrarioData(fine, oraFine.getValue(), minutoFine.getValue());
        }

        if (!partenza.getValue().getNome().equals(VALORE_NULLO)){
            part = partenza.getValue();
        }
        if (!arrivo.getValue().getNome().equals(VALORE_NULLO)){
            arr = arrivo.getValue();
        }
        if (!localita.getValue().getCitta().equals(VALORE_NULLO)){
            loc = localita.getValue();
        }
        numPostiOccupati = Integer.parseInt(numPosti.getValue());

        boolean procedi = true;
        Date dataAttuale = new Date();
        if (inizio != null && fine == null){ //modificato solo inizio
            if (inizio.compareTo(dataAttuale) <=0){
                procedi=false;
                AlertBox.display("Modifica prenotazione", "Controlla data!");
            }
            if (inizio.compareTo(oldPren.getDataFine()) >= 0 ){
                procedi=false;
                AlertBox.display("Modifica prenotazione", "Controlla data!");
            }
        }

        if (fine != null && inizio == null){ //modificato solo fine
            if (fine.compareTo(dataAttuale) <=0){
                procedi=false;
                AlertBox.display("Modifica prenotazione", "Controlla data!");
            }
            if (fine.compareTo(oldPren.getDataInizio()) <= 0 ){
                procedi=false;
                AlertBox.display("Modifica prenotazione", "Controlla data!");
            }
        }


        if (inizio != null && fine != null){ //modificato sia inizio che fine
            if (inizio.compareTo(dataAttuale) <=0){
                procedi=false;
                AlertBox.display("Modifica prenotazione", "Controlla data!");
            }
            if (fine.compareTo(dataAttuale) <=0){
                procedi=false;
                AlertBox.display("Modifica prenotazione", "Controlla data!");
            }
            if (fine.compareTo(inizio) <=0){
                procedi=false;
                AlertBox.display("Modifica prenotazione", "Controlla data!");
            }
        }

        if (procedi){
            if (!partenza.getValue().getNome().equals(VALORE_NULLO) || !arrivo.getValue().getNome().equals(VALORE_NULLO) || !localita.getValue().getCitta().equals(VALORE_NULLO)
                    || Integer.parseInt(numPosti.getValue()) != oldPren.getNumPostiOccupati() || inizio!=null || fine != null){

                if (PrenotazioneBusiness.getInstance().modificaPrenotazione(inizio, fine, numPostiOccupati, arr, part, loc, oldPren) && procedi){
                    AlertBox.display("Modifica prenotazione", "Modifica fatta");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("tabellaPrenotazioniPage.fxml"));
                    Pane pane = (Pane) loader.load();
                    TabellaPrenotazioniPageController controller = loader.<TabellaPrenotazioniPageController>getController();

                    if (CommonBusiness.getInstance().checkCliente(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId())){
                        ObservableList<Prenotazione> prenotazioni = (ObservableList<Prenotazione>) FXCollections.observableArrayList(CommonBusiness.getInstance().getPrenotazioniUtente(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId()));
                        controller.setListPrenotazioni(prenotazioni);
                    }

                    rootPaneModificaPrenotazione.getChildren().setAll(pane);
                    rootPaneModificaPrenotazione.setPrefSize(1000, 600);
                } else {
                    AlertBox.display("Modifica prenotazione", "Modifica non effettuata. \n Riprovare controllano i campi");
                }
            } else {
                AlertBox.display("Modifica prenotazione", "Nessuna modifica fatta");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("tabellaPrenotazioniPage.fxml"));
                Pane pane = (Pane) loader.load();
                TabellaPrenotazioniPageController controller = loader.<TabellaPrenotazioniPageController>getController();

                if (CommonBusiness.getInstance().checkCliente(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId())){
                    ObservableList<Prenotazione> prenotazioni = (ObservableList<Prenotazione>) FXCollections.observableArrayList(CommonBusiness.getInstance().getPrenotazioniUtente(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId()));
                    controller.setListPrenotazioni(prenotazioni);
                }

                rootPaneModificaPrenotazione.getChildren().setAll(pane);
                rootPaneModificaPrenotazione.setPrefSize(1000, 600);

            }
        } else {
            AlertBox.display("Modifica prenotazione", "Controlla le date e riprova!");
        }



    }

    public void indietro() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tabellaPrenotazioniPage.fxml"));
        Pane pane = (Pane) loader.load();
        TabellaPrenotazioniPageController controller = loader.<TabellaPrenotazioniPageController>getController();

        if (CommonBusiness.getInstance().checkAmministratore(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId())){
            ObservableList<Prenotazione> prenotazioni = (ObservableList<Prenotazione>) FXCollections.observableArrayList(CommonBusiness.getInstance().getPrenotazioni());
            controller.setListPrenotazioni(prenotazioni);
        } else {
            if (CommonBusiness.getInstance().checkCliente(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId())){
                ObservableList<Prenotazione> prenotazioni = (ObservableList<Prenotazione>) FXCollections.observableArrayList(CommonBusiness.getInstance().getPrenotazioniUtente(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId()));
                controller.setListPrenotazioni(prenotazioni);
            } else {
            }
        }

        rootPaneModificaPrenotazione.getChildren().setAll(pane);
        rootPaneModificaPrenotazione.setPrefSize(1000, 600);
    }
}

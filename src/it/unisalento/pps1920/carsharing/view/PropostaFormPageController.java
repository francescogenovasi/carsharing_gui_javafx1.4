package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.AccessorioBusiness;
import it.unisalento.pps1920.carsharing.business.CommonBusiness;
import it.unisalento.pps1920.carsharing.business.RicercaBusiness;
import it.unisalento.pps1920.carsharing.dao.interfaces.IMezzoDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.MezzoDAO;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.DateUtil;
import it.unisalento.pps1920.carsharing.util.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PropostaFormPageController { //la proposta è sia una proposta che una prenotazione

    private static final String VALORE_NULLO = "-";

    private Date d = new Date();
    private Calendar dd = Calendar.getInstance();
    private Stazione part = null;
    private Stazione arr = null;
    private Localita loc = null;
    private Cliente cli = null;
    private Date inizio = null;
    private Date fine = null;
    private int pos;
    private Modello mod = null;
    private String dim = null;
    private boolean error = false;
    private String tipoMezzo;

    List<Accessorio> acc = new ArrayList<Accessorio>(); //lista accessori selezionati da chi prenota

    @FXML
    private Pane rootPanePropostaFormPage;
    @FXML
    private Label dimensioneAutoLabel;
    @FXML
    private Label accessoriLabel;
    @FXML
    private ScrollPane scrollAccessori;
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
    private ComboBox<String> tipologiaCombo;
    @FXML
    private ComboBox<String> dimensioneAuto;
    @FXML
    private ComboBox<String> oraInizio;
    @FXML
    private ComboBox<String> minutoInizio;
    @FXML
    private ComboBox<String> oraFine;
    @FXML
    private ComboBox<String> minutoFine;
    @FXML
    private ComboBox<Cliente> cliente;
    @FXML
    private TilePane tilePaneAccessori;

    ObservableList<Stazione> stazioni = (ObservableList<Stazione>) FXCollections.observableArrayList(CommonBusiness.getInstance().getStazioni());
    ObservableList<Cliente> clienti = (ObservableList<Cliente>) FXCollections.observableArrayList(CommonBusiness.getInstance().getClienti());
    ObservableList<Localita> localitas = (ObservableList<Localita>) FXCollections.observableArrayList(CommonBusiness.getInstance().getLocalita());
    ObservableList<String> tipologia = FXCollections.observableArrayList(CommonBusiness.getInstance().getTipologia());
    ObservableList<String> ora = FXCollections.observableArrayList(CommonBusiness.getInstance().getOre());
    ObservableList<String> minuto = FXCollections.observableArrayList(CommonBusiness.getInstance().getMinuti());
    ObservableList<String> dimensioni = FXCollections.observableArrayList(CommonBusiness.getInstance().getDimensioni());


    ObservableList<Accessorio> accessori = (ObservableList<Accessorio>) FXCollections.observableArrayList(CommonBusiness.getInstance().getAccessori());

    public PropostaFormPageController() throws IOException {
    }


    public void initialize() throws IOException {
        //aggiunta elemento nullo
        stazioni.add(new Stazione(0, VALORE_NULLO, 0, 0, null));
        clienti.add(new Cliente(0, VALORE_NULLO, "", "", "", "", "", "", 0, "", 0, null));
        localitas.add(new Localita(0, VALORE_NULLO, 0, 0));
        tipologia.add(VALORE_NULLO);
        ora.add(VALORE_NULLO);
        minuto.add(VALORE_NULLO);
        dimensioni.add(VALORE_NULLO);


        //set elementi nelle combobox e selezione default VALORE_NULLO
        partenza.setItems(stazioni);
        partenza.getSelectionModel().select(stazioni.size()-1);
        if(CommonBusiness.getInstance().checkCliente(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId())){ //se è un utente
            cliente.setValue(CommonBusiness.getInstance().getCliente(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId()));
            //cliente.setEditable(false);
            cliente.setDisable(true); //conversazione telegram 21/7/2020 13:00
        } else { //se è un addetto
            //set elementi nelle combobox e selezione default VALORE_NULLO
            cliente.setItems(clienti);
            cliente.getSelectionModel().select(clienti.size()-1);
        }
        //set elementi nelle combobox e selezione default VALORE_NULLO
        arrivo.setItems(stazioni);
        arrivo.getSelectionModel().select(stazioni.size()-1);
        localita.setItems(localitas);
        localita.getSelectionModel().select(localitas.size()-1);


        for (int i = 0; i < accessori.size(); i++) {
            CheckBox cb = new CheckBox(accessori.get(i).getNome());
            tilePaneAccessori.getChildren().add(cb);
            EventHandler<ActionEvent> ev = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    if (cb.isSelected()){
                        //System.out.println(cb.getText() + " selezionato con id " + AccessorioBusiness.getInstance().findIdAccessorio(cb.getText()));
                        Accessorio acc_selected = null;
                        try {
                            acc_selected = CommonBusiness.getInstance().getAccessorio(AccessorioBusiness.getInstance().findIdAccessorio(cb.getText()));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        acc.add(acc_selected);
                    } else {
                        //System.out.println(cb.getText() + " deselezionato con id " + AccessorioBusiness.getInstance().findIdAccessorio(cb.getText()));
                        for (int i=0; i<acc.size(); i++){
                            if (acc.get(i).getId() == AccessorioBusiness.getInstance().findIdAccessorio(cb.getText())){
                                acc.remove(i);
                                break;
                            }
                        }
                    }
                }
            };
            cb.setOnAction(ev);
        }

        tipologiaCombo.setItems(tipologia);
        tipologiaCombo.getSelectionModel().select(tipologia.size()-1);

        oraInizio.setItems(ora);
        oraInizio.getSelectionModel().select(ora.size()-1);
        minutoInizio.setItems(minuto);
        minutoInizio.getSelectionModel().select(minuto.size()-1);
        oraFine.setItems(ora);
        oraFine.getSelectionModel().select(ora.size()-1);
        minutoFine.setItems(minuto);
        minutoFine.getSelectionModel().select(minuto.size()-1);

        dimensioneAuto.setItems(dimensioni);
        dimensioneAuto.getSelectionModel().select(dimensioni.size()-1);

        dataInizio.setValue(null);
        dataInizio.setPromptText("Inizio");
        dataFine.setValue(null);
        dataFine.setPromptText("Fine");
    }

    @FXML
    public void checkVeicolo(){
        if (tipologiaCombo.getValue().equals("Auto")){
            dimensioneAuto.getSelectionModel().select(dimensioni.size()-1);
            dimensioneAuto.setVisible(true);
            dimensioneAutoLabel.setVisible(true);
            scrollAccessori.setVisible(true);
            accessoriLabel.setVisible(true);

            acc = new ArrayList<Accessorio>();
            tilePaneAccessori.getChildren().clear();
            for (int i = 0; i < accessori.size(); i++) {
                CheckBox cb = new CheckBox(accessori.get(i).getNome());
                tilePaneAccessori.getChildren().add(cb);
                EventHandler<ActionEvent> ev = new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e)
                    {
                        if (cb.isSelected()){
                            //System.out.println(cb.getText() + " selezionato con id " + AccessorioBusiness.getInstance().findIdAccessorio(cb.getText()));
                            Accessorio acc_selected = null;
                            try {
                                acc_selected = CommonBusiness.getInstance().getAccessorio(AccessorioBusiness.getInstance().findIdAccessorio(cb.getText()));
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            acc.add(acc_selected);
                        } else {
                            //System.out.println(cb.getText() + " deselezionato con id " + AccessorioBusiness.getInstance().findIdAccessorio(cb.getText()));
                            for (int i=0; i<acc.size(); i++){
                                if (acc.get(i).getId() == AccessorioBusiness.getInstance().findIdAccessorio(cb.getText())){
                                    acc.remove(i);
                                    break;
                                }
                            }
                        }
                    }
                };
                cb.setOnAction(ev);
            }

        } else {
            acc = new ArrayList<Accessorio>();
            dimensioneAuto.getSelectionModel().select(dimensioni.size()-1);
            dimensioneAuto.setVisible(false);
            dimensioneAutoLabel.setVisible(false);
            scrollAccessori.setVisible(false);
            accessoriLabel.setVisible(false);
        }
    }

    @FXML
    public void nextStep() throws ParseException, IOException {
        error = false;

        //numero posti almeno uno per forza quindi valore di default se non modificato
        /* if (Integer < 1){
            //popup impossibile effettuare prenotazioni
            AlertBox.display("Nuova proposta", "nessuna macchina disponibile");
            error = true;
            pos = 1;
        } else {
            //pos = Integer.parseInt(numPosti.getValue());
        }*/
        System.out.println("-------------------");
        System.out.println("Prenotazione:");


        if ((partenza.getValue().getNome().equals(VALORE_NULLO)) || (arrivo.getValue().getNome().equals(VALORE_NULLO)) || (cliente.getValue().getUsername().equals(VALORE_NULLO))
                || (localita.getValue().getCitta().equals(VALORE_NULLO)) || (dataInizio.getValue() == null) || (dataFine.getValue() == null) || (oraInizio.getValue().equals(VALORE_NULLO))
                || (oraFine.getValue().equals(VALORE_NULLO)) || (minutoInizio.getValue().equals(VALORE_NULLO)) || (minutoFine.getValue().equals(VALORE_NULLO))
                || (tipologiaCombo.getValue().equals(VALORE_NULLO))){
            //non tutti i campi sono stati inseriti
            AlertBox.display("Nuova proposta", "campi mancanti");
            error = true;
        } else {
            //sono stati inseriti tutti i campi
            inizio = DateUtil.convertToDateFromLocalDate(dataInizio.getValue());
            inizio = DateUtil.modificaOrarioData(inizio, oraInizio.getValue(), minutoInizio.getValue());
            System.out.println(inizio.toString());
            fine = DateUtil.convertToDateFromLocalDate(dataFine.getValue());
            fine = DateUtil.modificaOrarioData(fine, oraFine.getValue(), minutoFine.getValue());
            System.out.println(fine.toString());
            Date f = new Date();
            if ((inizio.compareTo(fine) > 0)  || (f.compareTo(inizio) > 0)){//poiche le date sono state sicuramente inserite le posso controllare
                //maggiore di zero allora la data di fine è precedente alla data di inizio oppure la data di inizio è precedente alla data odierna
                System.out.println("male");
                AlertBox.display("Nuova proposta", "ricontrollare date!");
                error = true;
            } else {
                cli = cliente.getValue();
                System.out.println("cliente: " + cliente.getValue().getUsername()); //elemento alla query
                part = partenza.getValue();
                System.out.println("partenza: " + partenza.getValue().getNome()); //elemento alla query
                arr = arrivo.getValue();
                System.out.println("arrivo: " + arrivo.getValue().getNome()); //elemento alla query
                loc = localita.getValue();
                System.out.println("localita: " + localita.getValue().getCitta()); //elemento alla query

                tipoMezzo = tipologiaCombo.getValue();

                System.out.println("tutto ok");
            }
        }

        /*if (!(dimensioneAuto.getValue().equals(VALORE_NULLO))){

            System.out.println("dimensione: " + dimensioneAuto.getValue()); //elemento alla query
        }*/


        if (error == false){
            Prenotazione p = new Prenotazione();
            p.setData(new Date());
            //p.setCliente(((Cliente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)));
            if (CommonBusiness.getInstance().checkCliente(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId())){ //se cliente allora prendi l'id e mandalo, se addetto allora prendi le cose dalla label
                p.setCliente(CommonBusiness.getInstance().getCliente(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId()));//prendo il cliente da passare alla prenotazione
            } else {
                p.setCliente(cli);
            }

            //p.setNumPostiOccupati(Integer.parseInt(numPosti.getValue()));
            p.setPartenza(part);
            p.setArrivo(arr);
            p.setLocalita(loc);
            p.setDataInizio(inizio);
            p.setDataFine(fine);
            p.setPagamento(false);
            p.setPronta(false);

            dim = dimensioneAuto.getValue();

            ObservableList<Mezzo> mezzi = (ObservableList<Mezzo>) FXCollections.observableArrayList(RicercaBusiness.getInstance().mezziPrenotabili(dim, tipoMezzo, p.getDataInizio(), p.getDataFine()));
            if(mezzi.size()==0){
                AlertBox.display("Nuova proposta", "Nessun mezzo disponibile della tipologia richiesta!");
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("confermaPrenotazione.fxml"));
                Pane pane = (Pane) loader.load();
                ConfermaPrenotazioneController controller = loader.<ConfermaPrenotazioneController>getController();

                controller.initialize(p, dimensioneAuto.getValue(), acc, tipoMezzo, mezzi);//passa la prenotazione incompleta (manca mezzo)

                rootPanePropostaFormPage.getChildren().setAll(pane);
                rootPanePropostaFormPage.setPrefSize(1000, 600);
            }

        }
    }

    @FXML
    private void goHome(ActionEvent event) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("tabsRicercaPage.fxml"));
        rootPanePropostaFormPage.getChildren().setAll(pane);
    }

}

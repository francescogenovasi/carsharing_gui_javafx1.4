package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.AccessorioBusiness;
import it.unisalento.pps1920.carsharing.business.CommonBusiness;
import it.unisalento.pps1920.carsharing.business.PrenotazioneBusiness;
import it.unisalento.pps1920.carsharing.business.RichiestaBusiness;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RichiestaCondivisioneController {

    private static final String VALORE_NULLO = "-";
    private int pos;
    List<Accessorio> accessoriAggiunti = new ArrayList<Accessorio>(); //lista accessori selezionati da chi richiede lo sharing

    @FXML
    private Pane rootPaneRichiestaCondivisione;
    @FXML
    private ComboBox<String> postiCombo;
    @FXML
    private Label partenza;
    @FXML
    private Label arrivo;
    @FXML
    private Label localita;
    @FXML
    private Label dataInizio;
    @FXML
    private Label dataFine;
    @FXML
    private ImageView imageAutoRiepilogo;
    @FXML
    private TilePane tilePaneAccessori;
    @FXML
    private TilePane tilePaneAccessoriAgg;
    @FXML
    private Label mezzo;
    @FXML
    private ScrollPane scrollAccessori1;
    @FXML
    private Label accessoriLabel1;

    ObservableList<String> posti = FXCollections.observableArrayList(CommonBusiness.getInstance().getPosti());
    ObservableList<Accessorio> acc = FXCollections.observableArrayList();
    ObservableList<Accessorio> accessori = (ObservableList<Accessorio>) FXCollections.observableArrayList(CommonBusiness.getInstance().getAccessori());

    PropostaCondivisione prop = new PropostaCondivisione();

    public RichiestaCondivisioneController() throws IOException {
    }


    public void initialize(PropostaCondivisione p) throws IOException {
        prop = p;
        //System.out.println("aaaaaaaaaaaaaaaa: ijijijijijijijjijij: " + p.getId());
        //System.out.println("bbbbbbbbbbbbbbbb: ijijijijijijijjijij: " + CommonBusiness.getInstance().getIdPrenFromIdPropCon(p.getId()));
        acc = FXCollections.observableArrayList(CommonBusiness.getInstance().getAccessoriPrenotazione(CommonBusiness.getInstance().getIdPrenFromIdPropCon(p.getId())));
        partenza.setText(p.getPartenza().getNome());
        arrivo.setText(p.getArrivo().getNome());
        localita.setText(p.getLocalita().getCitta());
        dataInizio.setText(p.getDataInizio().toString());
        dataFine.setText(p.getDataFine().toString());
        imageAutoRiepilogo.setImage(CommonBusiness.getInstance().getFotoModello(p.getMezzo().getModello().getId()));

        if (p.getMezzo().getModello().getTipologia().equals("Auto")){
            System.out.println();
            scrollAccessori1.setVisible(true);
            accessoriLabel1.setVisible(true);
        }

        //accessori già presenti
        for (int i = 0; i < acc.size(); i++) {
            Label ta = new Label(acc.get(i).getNome());
            tilePaneAccessori.getChildren().add(ta);
        }

        for (int i = 0; i < acc.size(); i++){ //rimuove dalla lista accessori totali gli accessori gia inseriti
            for (int j = 0; j < accessori.size(); j++){
                if (acc.get(i).getId() == accessori.get(j).getId()){
                    accessori.remove(j);
                    break;
                }
            }
        }

        for (int i = 0; i < accessori.size(); i++) { //elenca gli accessori che si possono aggiungere
            CheckBox cb = new CheckBox(accessori.get(i).getNome());
            tilePaneAccessoriAgg.getChildren().add(cb);
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
                        accessoriAggiunti.add(acc_selected);
                    } else {
                        //System.out.println(cb.getText() + " deselezionato con id " + AccessorioBusiness.getInstance().findIdAccessorio(cb.getText()));
                        for (int i=0; i<accessoriAggiunti.size(); i++){
                            if (accessoriAggiunti.get(i).getId() == AccessorioBusiness.getInstance().findIdAccessorio(cb.getText())){
                                accessoriAggiunti.remove(i);
                                break;
                            }
                        }
                    }
                }
            };
            cb.setOnAction(ev);
        }


        mezzo.setText(p.getMezzo().getModello().getNome() + " " + p.getMezzo().getTarga());
        posti.add(VALORE_NULLO);
        postiCombo.setItems(posti);
        postiCombo.getSelectionModel().select(posti.size()-1);
    }

    @FXML
    public void conferma() throws IOException {
        if(postiCombo.getValue().equals(VALORE_NULLO)){
            //errore campi mancanti
            AlertBox.display("Richiesta condivisione", "Selezionare posti richiesti");
        } else {
            if (Integer.parseInt(postiCombo.getValue()) > RichiestaBusiness.getInstance().numeroPostiDisponibili(prop.getDataInizio(), prop.getDataFine(), prop.getMezzo().getId())){
                System.out.println("ghuguhuhhhhuhuh"+Integer.parseInt(postiCombo.getValue()) + ">" + RichiestaBusiness.getInstance().numeroPostiDisponibili(prop.getDataInizio(), prop.getDataFine(), prop.getMezzo().getId()));
                //errore troppi posti richiesti, non ci sono abbastanza
                AlertBox.display("Richiesta condivisione", "I posti richiesti non sono disponibili. Selezionare un altra proposta o ridurre il numero di posti richiesti");
            } else {
                RichiestaCondivisione r = new RichiestaCondivisione();
                r.setStato("Attesa");

                System.out.println("id: " + ((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId());
                r.setCliente(CommonBusiness.getInstance().getCliente(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId()));
                r.setData(prop.getData());
                r.setProposta(prop);
                r.setNumPostiRichiesti(Integer.parseInt(postiCombo.getValue()));

                //salvare accessori aggiunti dalla richiesta di condivisione e poi una volta confermata la richiesta aggiungerli alla prenotazione

                boolean inserimentoRichiesta = RichiestaBusiness.getInstance().inviaRichiestaCondivisione(r, accessoriAggiunti);



                if (inserimentoRichiesta){
                    AlertBox.display("Richiesta condivisione", "Richiesta fatta");
                } else {
                    AlertBox.display("Richiesta condivisione", "Errore, riprovare");
                }

                FXMLLoader lo = new FXMLLoader(getClass().getResource("tabsRicercaPage.fxml"));
                Pane pane = (Pane) lo.load();
                rootPaneRichiestaCondivisione.getChildren().setAll(pane);
                rootPaneRichiestaCondivisione.setPrefSize(1000, 600);
            }

        }
    }
    @FXML
    public void indietro() throws IOException {
        FXMLLoader lo = new FXMLLoader(getClass().getResource("visualizzaProposte.fxml"));
        Pane pane = (Pane) lo.load();
        VisualizzaProposteController controller = lo.<VisualizzaProposteController>getController();
        ObservableList<PropostaCondivisione> prop = FXCollections.observableArrayList(CommonBusiness.getInstance().getProposteCondivisione()) ;
        controller.setListProposte(prop, true);
        rootPaneRichiestaCondivisione.getChildren().setAll(pane);
        rootPaneRichiestaCondivisione.setPrefSize(1000, 600);
    }
}

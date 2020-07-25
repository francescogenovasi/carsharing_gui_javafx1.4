package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.CommonBusiness;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.model.PropostaCondivisione;
import it.unisalento.pps1920.carsharing.model.TabConfermaRichieste;
import it.unisalento.pps1920.carsharing.model.Utente;
import it.unisalento.pps1920.carsharing.util.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class StrutturaPageController{
    @FXML
    private Pane rootPaneStrutturaPage;
    @FXML
    private BorderPane borderPaneStrutturaPage;
    @FXML
    private Menu menuAdmin;
    @FXML
    private Menu menuAddetto;
    @FXML
    private Menu menuOperatore;
    @FXML
    private Menu menuCliente;



    @FXML
    private void loadPrenotazioniFormPage() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("propostaFormPage.fxml"));
        rootPaneStrutturaPage.getChildren().setAll(pane);
    }

    @FXML
    private void loadRegistrazioneAddetto() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("registrazioneAddetto.fxml"));
        rootPaneStrutturaPage.getChildren().setAll(pane);
    }

    @FXML
    private void loadAggiungiMezzo() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("aggiuntaMezzo.fxml"));
        rootPaneStrutturaPage.getChildren().setAll(pane);
    }

    @FXML
    private void loadAggiungiModello() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("aggiuntaModello.fxml"));
        rootPaneStrutturaPage.getChildren().setAll(pane);
    }

    @FXML
    private void loadRegistrazioneOperatore() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("registrazioneOperatore.fxml"));
        rootPaneStrutturaPage.getChildren().setAll(pane);
    }

    @FXML
    private void loadAggiungiAccessorio() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("aggiuntaAccessorio.fxml"));
        rootPaneStrutturaPage.getChildren().setAll(pane);
    }

    @FXML
    private void goHome() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("tabsRicercaPage.fxml"));
        rootPaneStrutturaPage.getChildren().setAll(pane);
    }

    /* @FXML
    private void loadTabellaPrenotazioniPage() throws IOException {
        //todo cambiare tabella visualizzata
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tabellaPrenotazioniPage.fxml"));
        Pane pane = (Pane) loader.load();
        TabellaPrenotazioniPageController controller = loader.<TabellaPrenotazioniPageController>getController();

        //ObservableList<Prenotazione> prenotazioni = (ObservableList<Prenotazione>) FXCollections.observableArrayList(CommonBusiness.getInstance().getPrenotazioni());
        //controller.setListPrenotazioni(prenotazioni);
        if (CommonBusiness.getInstance().checkAmministratore(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId())){
            ObservableList<Prenotazione> prenotazioni = (ObservableList<Prenotazione>) FXCollections.observableArrayList(CommonBusiness.getInstance().getPrenotazioni());
            controller.setListPrenotazioni(prenotazioni);
        } else {
            if (CommonBusiness.getInstance().checkCliente(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId())){
                ObservableList<Prenotazione> prenotazioni = (ObservableList<Prenotazione>) FXCollections.observableArrayList(CommonBusiness.getInstance().getPrenotazioniUtente(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId()));
                controller.setListPrenotazioni(prenotazioni);
            } else {
                //todo aggiungere se è un addetto o un operatore
            }
        }

        rootPaneStrutturaPage.getChildren().setAll(pane);
        rootPaneStrutturaPage.setPrefSize(1000, 600);
    }*/

    @FXML
    private void loadVisualizzaProposte() throws IOException {
        FXMLLoader lo = new FXMLLoader(getClass().getResource("visualizzaProposte.fxml"));
        Pane pane = (Pane) lo.load();
        VisualizzaProposteController controller = lo.<VisualizzaProposteController>getController();

        ObservableList<PropostaCondivisione> prop = FXCollections.observableArrayList(CommonBusiness.getInstance().getProposteCondivisione()) ;
        controller.setListProposte(prop);
        rootPaneStrutturaPage.getChildren().setAll(pane);
        rootPaneStrutturaPage.setPrefSize(1000, 600);
    }

    @FXML
    private void loadVisualizzaRichieste() throws IOException {
        FXMLLoader lo = new FXMLLoader(getClass().getResource("visualizzaRichieste.fxml"));
        Pane pane = (Pane) lo.load();
        VisualizzaRichiesteController controller = lo.<VisualizzaRichiesteController>getController();

        ObservableList<TabConfermaRichieste> rich = FXCollections.observableArrayList(CommonBusiness.getInstance().getRichiesteInAttesa(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId())) ;
        controller.setListRichieste(rich);
        rootPaneStrutturaPage.getChildren().setAll(pane);
        rootPaneStrutturaPage.setPrefSize(1000, 600);
    }

    @FXML
    private void logout() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("homePage.fxml"));
        borderPaneStrutturaPage.getChildren().setAll(pane);
        Utente u = new Utente();
        u = null;
        Session.getInstance().inserisci(Session.UTENTE_LOGGATO, u);
    }


    public void initialize() {
        if (CommonBusiness.getInstance().checkAmministratore(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId())){
            menuAdmin.setVisible(true);
            menuAddetto.setVisible(true);
            menuOperatore.setVisible(true);
            menuCliente.setVisible(true);
        } else {
            if (CommonBusiness.getInstance().checkAddetto(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId())){
                menuAddetto.setVisible(true);
            } else {
                if (CommonBusiness.getInstance().checkCliente(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId())){
                    menuCliente.setVisible(true);
                } else {
                    if (CommonBusiness.getInstance().checkOperatore(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId())){
                        menuAddetto.setVisible(true);
                    }
                }
            }
        }



        Pane pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("tabsRicercaPage.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        rootPaneStrutturaPage.getChildren().setAll(pane);

        System.out.println("si è loggato: " + ((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getUsername());


    }


}

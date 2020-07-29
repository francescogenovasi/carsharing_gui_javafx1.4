package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.CommonBusiness;
import it.unisalento.pps1920.carsharing.business.MessaggiBusiness;
import it.unisalento.pps1920.carsharing.business.PrenotazioneBusiness;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.DateUtil;
import it.unisalento.pps1920.carsharing.util.PdfHelper;
import it.unisalento.pps1920.carsharing.util.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;

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
    private void loadNuovoMessaggio() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("scriviMessaggio.fxml"));
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

    @FXML
    private void loadTabellaPrenotazioniPage() throws IOException {
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

        rootPaneStrutturaPage.getChildren().setAll(pane);
        rootPaneStrutturaPage.setPrefSize(1000, 600);
    }

    @FXML
    private void loadTuttePrenotazioni() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tabellaPrenotazioniPage.fxml"));
        Pane pane = (Pane) loader.load();
        TabellaPrenotazioniPageController controller = loader.<TabellaPrenotazioniPageController>getController();

        ObservableList<Prenotazione> prenotazioni = (ObservableList<Prenotazione>) FXCollections.observableArrayList(PrenotazioneBusiness.getInstance().getPrenotazioniPerAdmin());
        controller.setListPrenotazioni(prenotazioni);


        rootPaneStrutturaPage.getChildren().setAll(pane);
        rootPaneStrutturaPage.setPrefSize(1000, 600);
    }

    @FXML
    private void pdfTuttePrenotazioni() throws IOException {
        ArrayList<Prenotazione> pren = PrenotazioneBusiness.getInstance().getPrenotazioniPerAdmin();
        ArrayList<String> testo = new ArrayList<String>();
        for (int i = 0; i < pren.size(); i++){
            testo.add("ID: " + pren.get(i).getId() + " dal " + DateUtil.fromRomeToLondon(DateUtil.stringFromDate(pren.get(i).getDataInizio())) + " al " + DateUtil.fromRomeToLondon(DateUtil.stringFromDate(pren.get(i).getDataFine())) + " di " + pren.get(i).getCliente().getUsername() + ". Pagamento: " + pren.get(i).getPagamento());
        }
        PdfHelper.getInstance().creaPdfAdmin(testo);
    }

    @FXML
    private void loadVisualizzaProposte() throws IOException {
        FXMLLoader lo = new FXMLLoader(getClass().getResource("visualizzaProposte.fxml"));
        Pane pane = (Pane) lo.load();
        VisualizzaProposteController controller = lo.<VisualizzaProposteController>getController();

        ObservableList<PropostaCondivisione> prop = FXCollections.observableArrayList(CommonBusiness.getInstance().getProposteCondivisione()) ;
        controller.setListProposte(prop, true);
        rootPaneStrutturaPage.getChildren().setAll(pane);
        rootPaneStrutturaPage.setPrefSize(1000, 600);
    }

    @FXML
    private void loadVisualizzaMezziDaPreparare() throws IOException {
        FXMLLoader lo = new FXMLLoader(getClass().getResource("visualizzaMezziDaPreparare.fxml"));
        Pane pane = (Pane) lo.load();
        VisualizzaMezziDaPreparareController controller = lo.<VisualizzaMezziDaPreparareController>getController();
        ObservableList<MezzoDaPreparare> mezzi = FXCollections.observableArrayList(CommonBusiness.getInstance().getMezziDaPreparare()) ;
        controller.setListMezziDaPreparare(mezzi);
        rootPaneStrutturaPage.getChildren().setAll(pane);
        rootPaneStrutturaPage.setPrefSize(1000, 600);
    }

    @FXML
    private void loadTabMessaggi() throws IOException {
        FXMLLoader lo = new FXMLLoader(getClass().getResource("messaggiRicevuti.fxml"));
        Pane pane = (Pane) lo.load();
        MessaggiRicevutiController controller = lo.<MessaggiRicevutiController>getController();
        ObservableList<Messaggio> mess = FXCollections.observableArrayList(MessaggiBusiness.getInstance().getMessaggiDaLeggere(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)))) ;
        controller.setListMessaggi(mess);
        rootPaneStrutturaPage.getChildren().setAll(pane);
        rootPaneStrutturaPage.setPrefSize(1000, 600);
    }



    @FXML
    private void loadTabellaOperatore() throws IOException {
        FXMLLoader lo = new FXMLLoader(getClass().getResource("visualizzaPrenOperatore.fxml"));
        Pane pane = (Pane) lo.load();
        VisualizzaPrenOperatoreController controllerr = lo.<VisualizzaPrenOperatoreController>getController();
        ObservableList<Prenotazione> pren = FXCollections.observableArrayList(PrenotazioneBusiness.getInstance().getStazioniOperatore(((Utente)Session.getInstance().ottieni(Session.UTENTE_LOGGATO)))) ;
        controllerr.setListPrenOp(pren);
        rootPaneStrutturaPage.getChildren().setAll(pane);
        rootPaneStrutturaPage.setPrefSize(1000, 600);
    }

    @FXML
    private void loadVisualizzaMezziPronti() throws IOException {
        FXMLLoader lo = new FXMLLoader(getClass().getResource("mezzoProntoAPartire.fxml"));
        Pane pane = (Pane) lo.load();
        MezzoProntoAPartireController controller = lo.<MezzoProntoAPartireController>getController();

        ObservableList<MezzoDaPreparare> mezzoDaPreparare = FXCollections.observableArrayList(CommonBusiness.getInstance().getMezziProntiAPartire()) ;
        controller.setListMezziPronti(mezzoDaPreparare);
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
        } else {
            if (CommonBusiness.getInstance().checkAddetto(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId())){
                menuAddetto.setVisible(true);
            } else {
                if (CommonBusiness.getInstance().checkCliente(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId())){
                    menuCliente.setVisible(true);
                } else {
                    if (CommonBusiness.getInstance().checkOperatore(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId())){
                        menuOperatore.setVisible(true);
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

        //System.out.println("si è loggato: " + ((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getUsername());


    }


}

package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.CommonBusiness;
import it.unisalento.pps1920.carsharing.model.Mezzo;
import it.unisalento.pps1920.carsharing.model.PropostaCondivisione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class TabsRicercaPageController{

    @FXML
    private Pane tabellaPaneTabsRicerca;
    @FXML
    private Pane filtriPaneTabsRicerca;
    @FXML
    private Pane tabellaPaneMezzi;

    public void initialize() throws IOException {
        Pane paneTabella = null;
        Pane paneFiltri = null;
        Pane paneMezzi =null;
        try {
            paneTabella = FXMLLoader.load(getClass().getResource("tabellaOffertePane.fxml"));
            paneFiltri = FXMLLoader.load(getClass().getResource("filtriPane.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        FXMLLoader lo = new FXMLLoader(getClass().getResource("tabellaAutomezzi.fxml"));
        paneMezzi = (Pane) lo.load();
        TabellaAutomezziController controller = lo.<TabellaAutomezziController>getController();

        ObservableList<Mezzo> mezzo = FXCollections.observableArrayList(CommonBusiness.getInstance().getMezzi()) ;
        controller.setListAutomezzi(mezzo);
        tabellaPaneTabsRicerca.getChildren().setAll(paneTabella);
        filtriPaneTabsRicerca.getChildren().setAll(paneFiltri);
        tabellaPaneMezzi.getChildren().setAll(paneMezzi);


    }

    /*@FXML
    private void loadVisualizzaMezzi() throws IOException {
        FXMLLoader lo = new FXMLLoader(getClass().getResource("tabellaAutomezzi.fxml"));
        Pane pane = (Pane) lo.load();
        TabellaAutomezziController controller = lo.<TabellaAutomezziController>getController();

        ObservableList<Mezzo> mezzo = FXCollections.observableArrayList(CommonBusiness.getInstance().getMezzi()) ;
        controller.setListAutomezzi(mezzo);
        rootPaneStrutturaPage.getChildren().setAll(pane);
        rootPaneStrutturaPage.setPrefSize(1000, 600);
    }*/

    public void nuovaRicerca(){
        Pane paneFiltri = null;
        try {
            paneFiltri = FXMLLoader.load(getClass().getResource("filtriPane.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        filtriPaneTabsRicerca.getChildren().setAll(paneFiltri);
    }

}

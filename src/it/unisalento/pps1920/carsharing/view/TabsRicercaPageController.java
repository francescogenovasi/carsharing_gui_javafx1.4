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
    private Pane tabellaPaneOfferte;
    @FXML
    private Pane filtriPaneTabsRicerca;
    @FXML
    private Pane tabellaPaneMezzi;

    public void initialize() throws IOException {
        Pane paneTabella = null;
        Pane paneFiltri = null;
        Pane paneMezzi =null;
        try {
            paneFiltri = FXMLLoader.load(getClass().getResource("filtriPane.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        FXMLLoader lo = new FXMLLoader(getClass().getResource("tabellaAutomezzi.fxml"));
        paneMezzi = (Pane) lo.load();
        TabellaAutomezziController controller = lo.<TabellaAutomezziController>getController();
        ObservableList<Mezzo> mezzo = FXCollections.observableArrayList(CommonBusiness.getInstance().getMezzi()) ;
        controller.setListAutomezzi(mezzo);


        FXMLLoader lo2 = new FXMLLoader(getClass().getResource("tabellaOffertePane.fxml"));
        paneTabella = (Pane) lo2.load();
        TabellaOffertePaneController controller2 = lo2.<TabellaOffertePaneController>getController();
        ObservableList<Mezzo> mezzo2 = FXCollections.observableArrayList(CommonBusiness.getInstance().getOffertoneArray()) ;
        controller2.setListOfferteMezzi(mezzo2);

        tabellaPaneOfferte.getChildren().setAll(paneTabella);

        filtriPaneTabsRicerca.getChildren().setAll(paneFiltri);
        tabellaPaneMezzi.getChildren().setAll(paneMezzi);


    }


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

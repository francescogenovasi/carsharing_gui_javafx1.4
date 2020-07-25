package it.unisalento.pps1920.carsharing.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class TabsRicercaPageController{

    @FXML
    private Pane tabellaPaneTabsRicerca;
    @FXML
    private Pane filtriPaneTabsRicerca;

    public void initialize() {
        Pane paneTabella = null;
        Pane paneFiltri = null;
        try {
            paneTabella = FXMLLoader.load(getClass().getResource("tabellaOffertePane.fxml"));
            paneFiltri = FXMLLoader.load(getClass().getResource("filtriPane.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        tabellaPaneTabsRicerca.getChildren().setAll(paneTabella);
        filtriPaneTabsRicerca.getChildren().setAll(paneFiltri);
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

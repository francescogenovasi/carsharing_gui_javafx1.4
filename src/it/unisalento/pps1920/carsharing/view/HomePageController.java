package it.unisalento.pps1920.carsharing.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import java.io.IOException;


public class HomePageController{
    @FXML
    private Pane rootPaneHomePage;
    @FXML
    private Pane tabsPane;



    @FXML
    private void loadLoginPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
        Pane pane = (Pane) loader.load();
        LoginPageController controller = loader.<LoginPageController>getController();
        //controller.setText1(getEnteredText());
        rootPaneHomePage.getChildren().setAll(pane);
        rootPaneHomePage.setPrefSize(1000, 600);
    }

    @FXML
    private void loadRegistrazionePage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("registrazionePage.fxml"));
        Pane pane = (Pane) loader.load();
        RegistrazionePageController controller = loader.<RegistrazionePageController>getController();
        //controller.setText1(getEnteredText());
        rootPaneHomePage.getChildren().setAll(pane);
        rootPaneHomePage.setPrefSize(1000, 600);
    }

    public void initialize() {
        Pane pane1 = null;
        try {
            pane1 = FXMLLoader.load(getClass().getResource("tabsRicercaPage.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        tabsPane.getChildren().setAll(pane1);
    }
}

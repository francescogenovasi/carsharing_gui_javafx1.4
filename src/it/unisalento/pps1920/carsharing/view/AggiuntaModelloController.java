package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.CommonBusiness;
import it.unisalento.pps1920.carsharing.business.ModelloBusiness;
import it.unisalento.pps1920.carsharing.model.Modello;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class AggiuntaModelloController {
    private File file=null;
    private static final String VALORE_NULLO = "-";
    private boolean aggiuntaModello;
    @FXML
    private ImageView imageModello = new ImageView();
    @FXML
    private ComboBox<String> numPosti;
    @FXML
    private ComboBox<String> dimensione;
    @FXML
    private ComboBox<String> tipologia;
    @FXML
    private TextField nome;
    @FXML
    private ComboBox<String> euroCombo;
    @FXML
    private ComboBox<String> centesimiCombo;
    @FXML
    private Pane rootAggiungiModelloPane;

    ObservableList<String> posti = FXCollections.observableArrayList(CommonBusiness.getInstance().getPosti());
    ObservableList<String> dim = FXCollections.observableArrayList(CommonBusiness.getInstance().getDimensioni());
    ObservableList<String> tipo = FXCollections.observableArrayList(CommonBusiness.getInstance().getTipologia());
    ObservableList<String> euro = FXCollections.observableArrayList(CommonBusiness.getInstance().getEuro());
    ObservableList<String> centesimi = FXCollections.observableArrayList(CommonBusiness.getInstance().getCentesimi());


    public void initialize() {
        posti.add(VALORE_NULLO);
        numPosti.setItems(posti);
        numPosti.getSelectionModel().select(posti.size()-1);
        dim.add(VALORE_NULLO);
        dimensione.setItems(dim);
        dimensione.getSelectionModel().select(dim.size()-1);
        tipo.add(VALORE_NULLO);
        tipologia.setItems(tipo);
        tipologia.getSelectionModel().select(tipo.size()-1);
        euro.add(VALORE_NULLO);
        euroCombo.setItems(euro);
        euroCombo.getSelectionModel().select(euro.size()-1);
        centesimi.add(VALORE_NULLO);
        centesimiCombo.setItems(centesimi);
        centesimiCombo.getSelectionModel().select(centesimi.size()-1);
    }





    public void loadFoto() {
        Stage primaryStage = null;
        FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(primaryStage);
        Image image = new Image(file.toURI().toString());
        imageModello.setImage(image);
    }

    public void loadModello() throws IOException {
        if (nome.getText().equals("") || nome.getText().equals(null) || numPosti.getValue().equals(VALORE_NULLO) || dimensione.getValue().equals(VALORE_NULLO) || tipologia.getValue().equals(VALORE_NULLO) || file==null || euroCombo.getValue().equals(VALORE_NULLO) || centesimiCombo.getValue().equals(VALORE_NULLO)){
            AlertBox.display("Aggiunta Modello", "Campi mancanti");
        } else {
            Modello m = new Modello();
            m.setNome(nome.getText());
            m.setFoto(file);
            m.setNumPosti(Integer.parseInt(numPosti.getValue()));
            m.setTipologia(tipologia.getValue());
            m.setDimensione(dimensione.getValue());
            m.setTariffaBase(Float.parseFloat(euroCombo.getValue() + "." + centesimiCombo.getValue()));

            if (ModelloBusiness.getInstance().findIdModello(m.getNome()) == -1) {
                aggiuntaModello = ModelloBusiness.getInstance().salvaAggiuntaModello(m);
                if (aggiuntaModello) {
                    AlertBox.display("Aggiunta Modello", "Aggiunta modello avvenuta con successo");
                    Pane pane = FXMLLoader.load(getClass().getResource("homePage.fxml"));
                    rootAggiungiModelloPane.getChildren().setAll(pane);
                } else {
                    AlertBox.display("Aggiunta Modello", "Aggiunta modello fallita, riprovare");
                }
            }else{
                AlertBox.display("Aggiunta Modello", "Modello già presente");
            }

        }

    }

    @FXML
    private void goHome() throws IOException{
        Pane pane = FXMLLoader.load(getClass().getResource("tabsRicercaPage.fxml"));
        rootAggiungiModelloPane.getChildren().setAll(pane);
    }
}

package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.AccessorioBusiness;
import it.unisalento.pps1920.carsharing.business.CommonBusiness;
import it.unisalento.pps1920.carsharing.model.Accessorio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class AggiuntaAccessorioController {
    private boolean aggiuntaAccessorio;

    private static final String VALORE_NULLO = "-";

    @FXML
    private Pane rootAggiungiAccessorioPane;
    @FXML
    private TextField nome;
    @FXML
    private ComboBox<String> postiOccupati;
    @FXML
    private ComboBox<String> euroCombo;
    @FXML
    private ComboBox<String> centesimiCombo;

    ObservableList<String> posti = FXCollections.observableArrayList(CommonBusiness.getInstance().getPosti());
    ObservableList<String> euro = FXCollections.observableArrayList(CommonBusiness.getInstance().getEuro());
    ObservableList<String> centesimi = FXCollections.observableArrayList(CommonBusiness.getInstance().getCentesimi());

    public void initialize() {
        posti.add("0");
        posti.add(VALORE_NULLO);
        postiOccupati.setItems(posti);
        postiOccupati.getSelectionModel().select(posti.size()-1);
        euro.add(VALORE_NULLO);
        euroCombo.setItems(euro);
        euroCombo.getSelectionModel().select(euro.size()-1);
        centesimi.add(VALORE_NULLO);
        centesimiCombo.setItems(centesimi);
        centesimiCombo.getSelectionModel().select(centesimi.size()-1);


    }


    @FXML
    private void aggiungiAccessorio() throws IOException {
        //"" scrivo, cancello ma lascio nome vuoto mentre null non scrivo niente
        if (nome.getText().equals("") || nome.getText().equals(null) || postiOccupati.getValue().equals(VALORE_NULLO) || euroCombo.getValue().equals(VALORE_NULLO) || centesimiCombo.getValue().equals(VALORE_NULLO)){
            AlertBox.display("Aggiunta Accessorio", "Campi mancanti");
        } else {
            Accessorio a = new Accessorio();
            a.setNome(nome.getText());
            a.setPostiOccupati(Integer.parseInt(postiOccupati.getValue()));
            a.setCosto(Float.parseFloat(euroCombo.getValue() + "." + centesimiCombo.getValue()));

            //System.out.println("accessorio: " + " " + a.getNome() + " " + a.getCosto() + " " + a.getPostiOccupati());

            if (AccessorioBusiness.getInstance().findIdAccessorio(a.getNome()) == -1){ //significa che non ci sono accessori con quel nome
                aggiuntaAccessorio = AccessorioBusiness.getInstance().salvaAggiuntaAccessorio(a);

                if ( aggiuntaAccessorio ){
                    AlertBox.display("Aggiunta accessorio", "Aggiunta accessorio avvenuta con successo");
                    Pane pane = FXMLLoader.load(getClass().getResource("homePage.fxml"));
                    rootAggiungiAccessorioPane.getChildren().setAll(pane);
                } else {
                    AlertBox.display("Aggiunta accessorio", "Aggiunta accessorio fallita, riprovare");
                }
            } else {
                AlertBox.display("Aggiunta accessorio", "Accessorio già presente");
            }
        }
    }

    @FXML
    private void goHome() throws IOException{
        Pane pane = FXMLLoader.load(getClass().getResource("tabsRicercaPage.fxml"));
        rootAggiungiAccessorioPane.getChildren().setAll(pane);
    }
}

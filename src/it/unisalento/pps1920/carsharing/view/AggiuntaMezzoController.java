package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.CommonBusiness;
import it.unisalento.pps1920.carsharing.business.MezzoBusiness;
import it.unisalento.pps1920.carsharing.model.Mezzo;
import it.unisalento.pps1920.carsharing.model.Modello;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import java.io.IOException;

public class AggiuntaMezzoController {
    private static final String VALORE_NULLO = "-";
    private boolean aggiuntaMezzo;

    @FXML
    ComboBox<Modello> modello;
    @FXML
    ComboBox<String> motorizzazione;
    @FXML
    TextField targa;
    @FXML
    private Pane rootAggiungiMezzoPane;

    ObservableList<String> motor = FXCollections.observableArrayList(CommonBusiness.getInstance().getMotorizzazione());
    ObservableList<Modello> model = FXCollections.observableArrayList(CommonBusiness.getInstance().getModelli());


    public AggiuntaMezzoController() throws IOException {
    }

    public void initialize() {
        motor.add(VALORE_NULLO);
        motorizzazione.setItems(motor);
        motorizzazione.getSelectionModel().select(motor.size()-1);
        model.add(new Modello(0,VALORE_NULLO,0,null,VALORE_NULLO,VALORE_NULLO));
        modello.setItems(model);
        modello.getSelectionModel().select(model.size()-1);
    }

    public void loadMezzo() throws IOException {
        if (targa.getText().equals("") || modello.getValue().equals(VALORE_NULLO) || motorizzazione.getValue().equals(VALORE_NULLO)){
            AlertBox.display("Aggiunta Mezzo", "Campi mancanti");
        }else{

            if(MezzoBusiness.getInstance().checkTarga(targa.getText())) {
                Mezzo m = new Mezzo();
                m.setTarga(targa.getText());
                m.setPostiDisponibili(modello.getValue().getNumPosti());
                m.setModello(modello.getValue());
                m.setMotorizzazione(motorizzazione.getValue());

                if (MezzoBusiness.getInstance().findIdMezzo(m.getTarga()) == -1) {
                    aggiuntaMezzo = MezzoBusiness.getInstance().salvaAggiuntaMezzo(m);
                    if (aggiuntaMezzo) {
                        AlertBox.display("Aggiunta Mezzo", "Aggiunta mezzo avvenuta con successo");
                        Pane pane = FXMLLoader.load(getClass().getResource("homePage.fxml"));
                        rootAggiungiMezzoPane.getChildren().setAll(pane);
                    } else {
                        AlertBox.display("Aggiunta Mezzo", "Aggiunta mezzo fallita, riprovare");
                    }
                } else {
                    AlertBox.display("Aggiunta Mezzo", "Mezzo già presente");
                }
            }else{
                AlertBox.display("Aggiunta Mezzo", "Formato targa non valido");
            }

        }

    }

    @FXML
    private void goHome() throws IOException{
        Pane pane = FXMLLoader.load(getClass().getResource("tabsRicercaPage.fxml"));
        rootAggiungiMezzoPane.getChildren().setAll(pane);
    }

}

package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.MessaggiBusiness;
import it.unisalento.pps1920.carsharing.model.Messaggio;
import it.unisalento.pps1920.carsharing.model.Utente;
import it.unisalento.pps1920.carsharing.util.DateUtil;
import it.unisalento.pps1920.carsharing.util.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import javax.swing.text.DateFormatter;
import java.awt.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ScriviMessaggioController {

    private static final String VALORE_NULLO = "-";

    @FXML
    private TextArea testoField;
    @FXML
    private ComboBox<Utente> destinatario;
    @FXML
    private Pane rootScriviMessaggioPane;

    private ObservableList<Utente> destinatari = FXCollections.observableArrayList(MessaggiBusiness.getInstance().getDestinatari());

    public void initialize() {

        destinatari.add(new Utente(0, VALORE_NULLO, VALORE_NULLO, VALORE_NULLO));
        destinatario.setItems(destinatari);
        destinatario.getSelectionModel().select(1);
    }

    public void loadInviaMessaggio() {
        Messaggio m = new Messaggio();

        if (testoField.getText().equals("") || destinatario.getValue().equals(VALORE_NULLO)) {
            AlertBox.display("Nuovo Messaggio", "Campi mancanti");
        } else {
            if (destinatario.getValue().getId()==((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId()){
                AlertBox.display("Nuovo Messaggio", "Attenzione, ti stai auto-mandando un messaggio");
            }

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            String finaledata= formatter.format(date);
            m.setTesto(testoField.getText());
            m.setMittente(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)));
            m.setDestinatario(destinatario.getValue());
            m.setStato("Non letto");
            m.setDatainvio(finaledata);

            boolean re = MessaggiBusiness.getInstance().scriviMessaggio(m);
            if (re) {
                AlertBox.display("Nuovo Messaggio", "Messaggio Inviato Correttamente");
            } else {
                AlertBox.display("Nuovo Messaggio", "Errore nell'invio del Messaggio");
            }
        }
    }

    @FXML
    private void goHome() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("tabsRicercaPage.fxml"));
        rootScriviMessaggioPane.getChildren().setAll(pane);
    }



}

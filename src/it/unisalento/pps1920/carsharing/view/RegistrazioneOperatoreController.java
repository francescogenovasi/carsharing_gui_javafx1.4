package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.CommonBusiness;
import it.unisalento.pps1920.carsharing.business.RegistrazioneBusiness;
import it.unisalento.pps1920.carsharing.model.Addetto;
import it.unisalento.pps1920.carsharing.model.Operatore;
import it.unisalento.pps1920.carsharing.model.Stazione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class RegistrazioneOperatoreController {

    private boolean checkFieldsError;
    private boolean registrazioneOperatore;
    private static final String VALORE_NULLO = "-";

    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField checkPasswordField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label wrongPassword;
    @FXML
    private Pane rootPaneRegistrazionePage;
    @FXML
    private Label passRegistrazione;
    @FXML
    private Label failRegistrazione;
    @FXML
    private ComboBox<Stazione> stazione;

    ObservableList<Stazione> stazioni = (ObservableList<Stazione>) FXCollections.observableArrayList(CommonBusiness.getInstance().getStazioni());

    public RegistrazioneOperatoreController() throws IOException {
    }


    public void initialize() {
        wrongPassword.setVisible(false);
        passRegistrazione.setVisible(false);
        failRegistrazione.setVisible(false);
        stazioni.add(new Stazione(0, VALORE_NULLO, 0, 0, null));
        stazione.setItems(stazioni);
        stazione.getSelectionModel().select(stazioni.size()-1);
    }


    @FXML
    private void loadRegistrazione() throws IOException, InterruptedException {
        checkFieldsError = true;
        Operatore o = new Operatore();
        String password = passwordField.getText();
        String checkPassword = checkPasswordField.getText();

        if (password.equals(checkPassword)) { //controllo se le password corrispondono

            if (!CommonBusiness.getInstance().checkUsername(usernameField.getText())) {
                //vuol dire che esiste gia quell'username se la funzione checkUsername ritorna false
                AlertBox.display("Registrazione", "Username gia utilizzato");
            }
            if (!CommonBusiness.getInstance().checkEmail(emailField.getText())) {
                //vuol dire che esiste gia quella email se la funzione checkEmail ritorna false
                AlertBox.display("Registrazione", "Email gia utilizzata");
            }
            if((emailField.getText() == null) || (usernameField.getText() == null) || (passwordField.getText() == null) || (checkPasswordField.getText() == null) || (stazione.getValue().getNome().equals(VALORE_NULLO))){ //controllo sui campi
                //almeno uno dei campi è vuoto
                checkFieldsError = false;
                AlertBox.display("Registrazione", "campi mancanti");
            }
            if (CommonBusiness.getInstance().checkUsername(usernameField.getText()) && CommonBusiness.getInstance().checkEmail(emailField.getText()) && checkFieldsError) { // campi tutti presenti e corretti
                o.setUsername(usernameField.getText());
                o.setEmail(emailField.getText());
                o.setPassword(password);
                o.setIdstazione(stazione.getValue().getId());

                registrazioneOperatore = RegistrazioneBusiness.getInstance().inviaRegistrazioneOperatore(o);

                if ( registrazioneOperatore ){
                    AlertBox.display("Registrazione", "Registrazione avvenuta con successo");
                    Pane pane = FXMLLoader.load(getClass().getResource("tabsRicercaPage.fxml"));
                    rootPaneRegistrazionePage.getChildren().setAll(pane);
                } else {
                    AlertBox.display("Registrazione", "Errore registrazione, riprovare");
                }


            }
        } else {
            wrongPassword.setVisible(true);  //se le due password non sono uguali allora avviso l'utente
        }

    }

    @FXML
    private void loadFirstPage() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("tabsRicercaPage.fxml"));
        rootPaneRegistrazionePage.getChildren().setAll(pane);
    }
}

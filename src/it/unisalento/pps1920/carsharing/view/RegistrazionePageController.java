package it.unisalento.pps1920.carsharing.view;
import it.unisalento.pps1920.carsharing.business.CommonBusiness;
import it.unisalento.pps1920.carsharing.business.RegistrazioneBusiness;
import it.unisalento.pps1920.carsharing.model.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class RegistrazionePageController{

    private static final String VALORE_NULLO = "-";

    private boolean checkFieldsError;
    private boolean registrazioneCliente;

    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField checkPasswordField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ImageView imageCliente = new ImageView();
    @FXML
    private Button buttonFoto;
    @FXML
    private Label wrongPassword;
    @FXML
    private Pane rootPaneRegistrazionePage;
    @FXML
    private Label passRegistrazione;
    @FXML
    private Label failRegistrazione;
    @FXML
    private TextField nome;
    @FXML
    private TextField cognome;
    @FXML
    private TextField citta;
    @FXML
    private TextField cap;
    @FXML
    private TextField indirizzo;
    @FXML
    private TextField telefono;
    @FXML
    private ComboBox<String> eta;

    private File file=null;
    ObservableList<String> etaList = FXCollections.observableArrayList(CommonBusiness.getInstance().getEta());

    public void initialize() {
        wrongPassword.setVisible(false);
        passRegistrazione.setVisible(false);
        failRegistrazione.setVisible(false);
        etaList.add(VALORE_NULLO);
        eta.setItems(etaList);
        eta.getSelectionModel().select(etaList.size()-1);

    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }


    @FXML
    private void loadRegistrazione() throws IOException, InterruptedException {
        checkFieldsError = true;
        Cliente c = new Cliente();
        String password = passwordField.getText();
        String checkPassword = checkPasswordField.getText();

        if (password.equals(checkPassword)) { //controllo se le password corrispondono

            if (!CommonBusiness.getInstance().checkUsername(usernameField.getText())) {
                AlertBox.display("Registrazione", "Username gia utilizzato");
            }
            if (!CommonBusiness.getInstance().checkEmail(emailField.getText())) {
                AlertBox.display("Registrazione", "Email gia utilizzata");
            }
            if( (eta.getValue().equals(VALORE_NULLO)) || (nome.getText() == null) || (cognome.getText() == null) || (emailField.getText() == null) || (usernameField.getText() == null)
                    || (citta.getText() == null) || (indirizzo.getText() == null) || (cap.getText() == null) || (telefono.getText() == null)
                    || (passwordField.getText() == null) || (checkPasswordField.getText() == null) ){ //controllo sui campi
                //almeno uno dei campi è vuoto
                checkFieldsError = false;
                AlertBox.display("Registrazione", "campi mancanti");
            }

            if( !(isNumeric(cap.getText())) || ((cap.getText()).length() != 5) ){
                checkFieldsError = false;
                AlertBox.display("Registrazione", "inserire CAP valido");
            }

            if( !(isNumeric(telefono.getText())) || ((telefono.getText()).length() != 10) ){
                checkFieldsError = false;
                AlertBox.display("Registrazione", "inserire telefono valido");
            }

            if(file == null){
                checkFieldsError = false;
                AlertBox.display("Registrazione", "inserire Foto");
            }

            if (CommonBusiness.getInstance().checkUsername(usernameField.getText()) && CommonBusiness.getInstance().checkEmail(emailField.getText()) && checkFieldsError) { // campi tutti presenti (metto variabile e vedo se è false o true
                c.setNome(nome.getText());
                c.setCognome(cognome.getText());
                c.setCitta(citta.getText());
                c.setCap(Integer.parseInt(cap.getText()));
                c.setIndirizzo(indirizzo.getText());
                c.setTelefono(telefono.getText());
                c.setUsername(usernameField.getText());
                c.setEmail(emailField.getText());
                c.setPassword(password);
                c.setEta(Integer.parseInt(eta.getValue()));
                c.setFoto(file);

                registrazioneCliente = RegistrazioneBusiness.getInstance().inviaRegistrazioneCliente(c);

                if ( registrazioneCliente ){
                    AlertBox.display("Registrazione", "Registrazione avvenuta con successo");
                    Pane pane = FXMLLoader.load(getClass().getResource("homePage.fxml"));
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
        Pane pane = FXMLLoader.load(getClass().getResource("homePage.fxml"));
        rootPaneRegistrazionePage.getChildren().setAll(pane);
    }

    @FXML
    private void loadFoto() {

        Stage primaryStage = null;
        FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(primaryStage);
        Image image = new Image(file.toURI().toString());
        imageCliente.setImage(image);
    }


}

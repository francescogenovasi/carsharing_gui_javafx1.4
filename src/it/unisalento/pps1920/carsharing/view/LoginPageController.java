package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.LoginBusiness;
import it.unisalento.pps1920.carsharing.dao.interfaces.IClienteDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IUtenteDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.ClienteDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.UtenteDAO;
import it.unisalento.pps1920.carsharing.model.Cliente;
import it.unisalento.pps1920.carsharing.model.Utente;
import it.unisalento.pps1920.carsharing.util.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import java.awt.*;
import java.io.IOException;

public class LoginPageController{

    //dopo initialize va fatto il controllo e salvati i dati di sessione


    // Alex Modifiche
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label wrongAccess;
    // Fine Alex Modifiche

    @FXML
    private Pane rootPaneLoginPage;
    @FXML
    private void loadStruttura() throws IOException {
        //Alex Modifiche
        String username=usernameField.getText();
        String password=passwordField.getText();
        LoginBusiness utenteBusiness = new LoginBusiness();
        Utente utenteLoggato = utenteBusiness.loginBusiness(username, password);

        if(utenteLoggato!=null) { //Se ha trovato l'utente nel DB viene caricata una nuova schermata//
            Session.getInstance().inserisci(Session.UTENTE_LOGGATO, utenteLoggato);
            Pane pane = FXMLLoader.load(getClass().getResource("strutturaPage.fxml"));
            rootPaneLoginPage.getChildren().setAll(pane);
            rootPaneLoginPage.setPrefSize(1000, 600);
        }else{
            wrongAccess.setVisible(true); //Qui appare una scritta dove dice che i dati inseriti sono errati. Inizialmente è invisibile, sta nel login Page.fxml
        }
    }

    @FXML
    private void loadFirstPage() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("homePage.fxml"));
        rootPaneLoginPage.getChildren().setAll(pane);
    }

}

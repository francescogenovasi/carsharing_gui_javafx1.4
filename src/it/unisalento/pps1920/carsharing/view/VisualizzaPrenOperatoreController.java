package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.model.MezzoDaPreparare;
import it.unisalento.pps1920.carsharing.model.Operatore;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.util.DateUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class VisualizzaPrenOperatoreController {

    @FXML
    private TableView<Prenotazione> tabellaPrenOperatore;
    @FXML
    private Pane rootPaneTabellaPrenOperatore;


    ObservableList<Prenotazione> pren = FXCollections.observableArrayList();

    public VisualizzaPrenOperatoreController() {
    }

    public void setListPrenOp(ObservableList<Prenotazione> p){
        pren = p;

        TableColumn<Prenotazione, String> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Prenotazione, String> macchina = new TableColumn<>("Modello");
        macchina.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMezzo().getModello().getNome()));

        TableColumn<Prenotazione, String> partenza = new TableColumn<>("Data Partenza");
        partenza.setCellValueFactory(cellData -> new SimpleStringProperty(DateUtil.stringFromDate(cellData.getValue().getDataInizio())));

        TableColumn<Prenotazione, String> arrivo = new TableColumn<>("Data Arrivo");
        arrivo.setCellValueFactory(cellData -> new SimpleStringProperty(DateUtil.stringFromDate(cellData.getValue().getDataFine())));

        TableColumn<Prenotazione, String> targa = new TableColumn<>("Targa");
        targa.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMezzo().getTarga()));

        TableColumn<Prenotazione, String> nome = new TableColumn<>("Nome Intestatario");
        nome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getNome()));

        TableColumn<Prenotazione, String> cognome = new TableColumn<>("Cognome Intestatario");
        cognome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getCognome()));

        TableColumn<Prenotazione, Integer> posti = new TableColumn<>("Posti occupati");
        posti.setCellValueFactory(new PropertyValueFactory<>("numPostiOccupati"));


        tabellaPrenOperatore.getColumns().addAll(id,macchina, targa, nome,cognome, partenza, arrivo,posti);

        tabellaPrenOperatore.setItems(pren);
    }


}

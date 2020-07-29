package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.model.Mezzo;
import it.unisalento.pps1920.carsharing.model.MezzoDaPreparare;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.io.File;

public class TabellaAutomezziController {

    @FXML
    private TableView<Mezzo> tabellaAutomezzi;
    @FXML
    private Pane rootPaneTabellaAutomezzi;


    ObservableList<Mezzo> mezzi = FXCollections.observableArrayList();

    public void setListAutomezzi(ObservableList<Mezzo> m){
        mezzi = m;

        TableColumn<Mezzo, String> targa = new TableColumn<>("Targa");
        targa.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTarga()));

        TableColumn<Mezzo, String> modello = new TableColumn<>("Modello");
        modello.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModello().getNome()));

        TableColumn<Mezzo, String> motor = new TableColumn<>("Motorizzazzione");
        motor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMotorizzazione()));

        TableColumn<Mezzo, String> tariffa = new TableColumn<>("Tariffa Base");
        tariffa.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getModello().getTariffaBase())));

        TableColumn<Mezzo, String> tipologia = new TableColumn<>("Tipologia");
        tipologia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModello().getTipologia()));

        TableColumn<Mezzo, String> posti = new TableColumn<>("Numero Posti");
        posti.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getModello().getNumPosti())));


        tabellaAutomezzi.getColumns().addAll(targa, modello, motor, tipologia,tariffa , posti);

        tabellaAutomezzi.setItems(mezzi);
    }


}

package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.RicercaBusiness;
import it.unisalento.pps1920.carsharing.model.Mezzo;
import it.unisalento.pps1920.carsharing.model.MezzoDaPreparare;
import it.unisalento.pps1920.carsharing.model.PropostaCondivisione;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Date;

public class MezzoProntoAPartireController {


        @FXML
        private TableView<MezzoDaPreparare> tabellaMezzi;
        @FXML
        private Pane rootPaneTabellaMezziProntiAPartirePage;


        ObservableList<MezzoDaPreparare> mezzidap = FXCollections.observableArrayList();

        public void setListMezziPronti(ObservableList<MezzoDaPreparare> m){
            mezzidap=m;

            TableColumn<MezzoDaPreparare, String> targa = new TableColumn<>("Targa");
            targa.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTarga()));

            TableColumn<MezzoDaPreparare, String> datapr = new TableColumn<>("Data prelievo mezzo");
            datapr.setCellValueFactory(new PropertyValueFactory<>("dataInizio"));
            TableColumn<MezzoDaPreparare, String> dataco = new TableColumn<>("Data consegna mezzo");
            dataco.setCellValueFactory(new PropertyValueFactory<>("dataFine"));


            TableColumn<MezzoDaPreparare, Integer> postiOccupati = new TableColumn<>("Posti Occupati");
            postiOccupati.setCellValueFactory(new PropertyValueFactory<>("postiOccupati"));


            tabellaMezzi.getColumns().addAll(targa, datapr, dataco, postiOccupati);



            tabellaMezzi.setItems(mezzidap);
        }


    }



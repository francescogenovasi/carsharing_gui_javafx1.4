package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.CommonBusiness;
import it.unisalento.pps1920.carsharing.business.RicercaBusiness;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.Session;
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

            TableColumn<MezzoDaPreparare, String> modello = new TableColumn<>("Modello");
            modello.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMezzo().getModello().getNome()));
            TableColumn<MezzoDaPreparare, String> targa = new TableColumn<>("Targa");
            targa.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMezzo().getTarga()));
            TableColumn<MezzoDaPreparare, String> tipologia = new TableColumn<>("Tipologia");
            tipologia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMezzo().getModello().getTipologia()));

            TableColumn<MezzoDaPreparare, String> datapr = new TableColumn<>("Data prelievo mezzo");
            datapr.setCellValueFactory(new PropertyValueFactory<>("dataInizio"));
            TableColumn<MezzoDaPreparare, String> dataco = new TableColumn<>("Data consegna mezzo");
            dataco.setCellValueFactory(new PropertyValueFactory<>("dataFine"));
            TableColumn<MezzoDaPreparare, Integer> postiOccupati = new TableColumn<>("Posti Occupati");
            postiOccupati.setCellValueFactory(new PropertyValueFactory<>("postiOccupati"));


            tabellaMezzi.getColumns().addAll(modello,targa,tipologia, datapr, dataco, postiOccupati);

            addButtonToTable();


            tabellaMezzi.setItems(mezzidap);
        }




    private void addButtonToTable() {
        TableColumn<MezzoDaPreparare, Void> colBtn = new TableColumn("Mezzo Partito?");

        Callback<TableColumn<MezzoDaPreparare, Void>, TableCell<MezzoDaPreparare, Void>> cellFactory = new Callback<TableColumn<MezzoDaPreparare, Void>, TableCell<MezzoDaPreparare, Void>>() {
            @Override
            public TableCell<MezzoDaPreparare, Void> call(final TableColumn<MezzoDaPreparare, Void> param) {
                final TableCell<MezzoDaPreparare, Void> cell = new TableCell<MezzoDaPreparare, Void>() {

                    private final Button btn = new Button("Partito");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            int id = getTableView().getItems().get(getIndex()).getId();
                            boolean res=CommonBusiness.getInstance().setPartito(id);
                            if (res){
                                AlertBox.display("Mezzo Partito", "PARTITO");
                                try {
                                    ObservableList<MezzoDaPreparare> mdp = FXCollections.observableArrayList(CommonBusiness.getInstance().getMezziProntiAPartire()) ;
                                    setListMezziPronti(mdp);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        tabellaMezzi.getColumns().add(colBtn);

    }


}



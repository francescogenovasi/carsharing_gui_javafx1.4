package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.CommonBusiness;
import it.unisalento.pps1920.carsharing.business.MessaggiBusiness;
import it.unisalento.pps1920.carsharing.model.Messaggio;
import it.unisalento.pps1920.carsharing.model.MezzoDaPreparare;
import it.unisalento.pps1920.carsharing.model.Utente;
import it.unisalento.pps1920.carsharing.util.DateUtil;
import it.unisalento.pps1920.carsharing.util.Session;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Date;

public class MessaggiRicevutiController {

        @FXML
        private TableView<Messaggio> tabellaMessaggi;
        @FXML
        private Pane rootPaneTabellaMessaggiRicevutiPage;


        ObservableList<Messaggio> messaggiricevuti = FXCollections.observableArrayList();

        public void setListMessaggi(ObservableList<Messaggio> m){
            messaggiricevuti=m;

            TableColumn<Messaggio, String> mittente = new TableColumn<>("Mittente username");
            mittente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMittente().getUsername()));
            TableColumn<Messaggio, String> mittentemail = new TableColumn<>("Mittente email");
            mittentemail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMittente().getEmail()));
            TableColumn<Messaggio, String> data = new TableColumn<>("Data Invio");
            data.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDatainvio()));
            TableColumn<Messaggio, String> testo = new TableColumn<>("Testo");
            testo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTesto()));



            tabellaMessaggi.getColumns().addAll(mittente,testo,mittentemail,data);

            addButtonToTable();


            tabellaMessaggi.setItems(messaggiricevuti);
        }




        private void addButtonToTable() {
            TableColumn<Messaggio, Void> colBtn = new TableColumn("Messaggio letto?");

            Callback<TableColumn<Messaggio, Void>, TableCell<Messaggio, Void>> cellFactory = new Callback<TableColumn<Messaggio, Void>, TableCell<Messaggio, Void>>() {
                @Override
                public TableCell<Messaggio, Void> call(final TableColumn<Messaggio, Void> param) {
                    final TableCell<Messaggio, Void> cell = new TableCell<Messaggio, Void>() {

                        private final Button btn = new Button("Letto");

                        {
                            btn.setOnAction((ActionEvent event) -> {
                                Messaggio m = getTableView().getItems().get(getIndex());
                                boolean res= MessaggiBusiness.getInstance().setLetto(m);
                                if (res){
                                    AlertBox.display("Messaggio Letto", "LETTO");
                                    ObservableList<Messaggio> mdl = FXCollections.observableArrayList(MessaggiBusiness.getInstance().getMessaggiDaLeggere(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)))) ;
                                    setListMessaggi(mdl);

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

            tabellaMessaggi.getColumns().add(colBtn);

        }


    }





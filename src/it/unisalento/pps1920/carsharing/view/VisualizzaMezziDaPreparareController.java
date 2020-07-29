package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.CommonBusiness;

import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.DateUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class VisualizzaMezziDaPreparareController {
    @FXML
    private TableView<MezzoDaPreparare> tabellaMezziDaPreparare;
    @FXML
    private Pane rootPaneTabellaMezziDaPreparare;


    ObservableList<MezzoDaPreparare> mezzi = FXCollections.observableArrayList();

    public void setListMezziDaPreparare(ObservableList<MezzoDaPreparare> m){
        mezzi = m;

        TableColumn<MezzoDaPreparare, Integer> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<MezzoDaPreparare, String> targa = new TableColumn<>("Targa");
        targa.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMezzo().getTarga()));

        TableColumn<MezzoDaPreparare, String> modello = new TableColumn<>("Modello");
        modello.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMezzo().getModello().getNome()));

        TableColumn<MezzoDaPreparare, String> dataInizio = new TableColumn<>("inizio");
        dataInizio.setCellValueFactory(new PropertyValueFactory<>("dataInizio"));

        TableColumn<MezzoDaPreparare, String> dataFine = new TableColumn<>("fine");
        dataFine.setCellValueFactory(new PropertyValueFactory<>("dataFine"));

        TableColumn<MezzoDaPreparare, Integer> posti = new TableColumn<>("Posti occupati");
        posti.setCellValueFactory(new PropertyValueFactory<>("postiOccupati"));

        tabellaMezziDaPreparare.getColumns().addAll(id, targa, modello, dataInizio, dataFine, posti);
        //add colonna accessori
        addButtonAccessori();
        //add colonna stato (pronto, non pronto)
        addButtonPronta();

        tabellaMezziDaPreparare.setItems(mezzi);
    }

    private void addButtonAccessori() {
        TableColumn<MezzoDaPreparare, Void> colBtn = new TableColumn("");

        Callback<TableColumn<MezzoDaPreparare, Void>, TableCell<MezzoDaPreparare, Void>> cellFactory = new Callback<TableColumn<MezzoDaPreparare, Void>, TableCell<MezzoDaPreparare, Void>>() {
            @Override
            public TableCell<MezzoDaPreparare, Void> call(final TableColumn<MezzoDaPreparare, Void> param) {
                final TableCell<MezzoDaPreparare, Void> cell = new TableCell<MezzoDaPreparare, Void>() {

                    private final Button btn = new Button("Visualizza Accessori");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            String acc = "";
                            int idMezzo = getTableView().getItems().get(getIndex()).getMezzo().getId();
                            Date dataInizio = getTableView().getItems().get(getIndex()).getDataInizio();
                            Date dataFine = getTableView().getItems().get(getIndex()).getDataFine();
                            int[] pren = CommonBusiness.getInstance().prenotazioniFromDateEIdMezzo(idMezzo, DateUtil.fromRomeToLondon(DateUtil.fromRomeToLondon(DateUtil.stringFromDate(dataInizio))), DateUtil.fromRomeToLondon(DateUtil.fromRomeToLondon(DateUtil.stringFromDate(dataFine))));
                            for (int i = 0; i < pren.length; i++){
                                System.out.println(pren[i]);
                                List<Accessorio> a = CommonBusiness.getInstance().getAccessoriPrenotazione(pren[i]);
                                for (int j = 0; j < a.size(); j++) {
                                    acc = acc + a.get(j).getNome() + " \n";
                                }
                            }
                            if(acc.equals("")){
                                AlertBox.display("Accessori", "nessun accessorio");
                            } else {
                                AlertBox.display("Accessori", acc);
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

        tabellaMezziDaPreparare.getColumns().add(colBtn);
    }

    private void addButtonPronta() {
        TableColumn<MezzoDaPreparare, Void> colBtn = new TableColumn("");

        Callback<TableColumn<MezzoDaPreparare, Void>, TableCell<MezzoDaPreparare, Void>> cellFactory = new Callback<TableColumn<MezzoDaPreparare, Void>, TableCell<MezzoDaPreparare, Void>>() {
            @Override
            public TableCell<MezzoDaPreparare, Void> call(final TableColumn<MezzoDaPreparare, Void> param) {
                final TableCell<MezzoDaPreparare, Void> cell = new TableCell<MezzoDaPreparare, Void>() {

                    private final Button btn = new Button("Pronta");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            int id = getTableView().getItems().get(getIndex()).getId();
                            CommonBusiness.getInstance().setPronto(id);
                            ObservableList<MezzoDaPreparare> mez = null;
                            try {
                                mez = FXCollections.observableArrayList(CommonBusiness.getInstance().getMezziDaPreparare());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            setListMezziDaPreparare(mez);
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

        tabellaMezziDaPreparare.getColumns().add(colBtn);
    }


}

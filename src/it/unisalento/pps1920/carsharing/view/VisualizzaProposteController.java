package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.CommonBusiness;
import it.unisalento.pps1920.carsharing.business.RicercaBusiness;
import it.unisalento.pps1920.carsharing.model.PropostaCondivisione;
import it.unisalento.pps1920.carsharing.util.DateUtil;
import it.unisalento.pps1920.carsharing.util.Session;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.io.IOException;

public class VisualizzaProposteController {

    @FXML
    private TableView<PropostaCondivisione> tabellaProposteAltri;
    @FXML
    private Pane rootPaneTabellaProposteAltriPage;


    ObservableList<PropostaCondivisione> proposte = FXCollections.observableArrayList();

     public void setListProposte(ObservableList<PropostaCondivisione> p, boolean richiedi){
         proposte = p;

         TableColumn<PropostaCondivisione, Integer> id = new TableColumn<>("ID Proposta");
         id.setCellValueFactory(new PropertyValueFactory<>("id"));

         TableColumn<PropostaCondivisione, String> cli = new TableColumn<>("Proponente");
         cli.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getUsername()));

         TableColumn<PropostaCondivisione, String> mezzo = new TableColumn<>("mezzo");
         mezzo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMezzo().getModello().getNome()));

         TableColumn<PropostaCondivisione, Integer> postiOccupati = new TableColumn<>("posti occ");
         postiOccupati.setCellValueFactory(new PropertyValueFactory<>("numPostiOccupati"));

         TableColumn<PropostaCondivisione, String> partenza = new TableColumn<>("partenza");
         partenza.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPartenza().getNome()));

         TableColumn<PropostaCondivisione, String> arrivo = new TableColumn<>("arrivo");
         arrivo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArrivo().getNome()));

         TableColumn<PropostaCondivisione, String> localita = new TableColumn<>("localita");
         localita.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocalita().getCitta()));

         TableColumn<PropostaCondivisione, String> dataInizio = new TableColumn<>("inizio");
         dataInizio.setCellValueFactory(cellData -> new SimpleStringProperty(DateUtil.fromRomeToLondon(DateUtil.stringFromDate(cellData.getValue().getDataInizio()))));

         TableColumn<PropostaCondivisione, String> dataFine = new TableColumn<>("fine");
         dataFine.setCellValueFactory(cellData -> new SimpleStringProperty(DateUtil.fromRomeToLondon(DateUtil.stringFromDate(cellData.getValue().getDataFine()))));

         tabellaProposteAltri.getColumns().addAll(id, cli, mezzo, postiOccupati, partenza, arrivo, localita, dataInizio, dataFine);

         if(richiedi){
             addButtonToTable();
         }

         tabellaProposteAltri.setItems(proposte);
     }

    private void addButtonToTable() {
        TableColumn<PropostaCondivisione, Void> colBtn = new TableColumn("");

        Callback<TableColumn<PropostaCondivisione, Void>, TableCell<PropostaCondivisione, Void>> cellFactory = new Callback<TableColumn<PropostaCondivisione, Void>, TableCell<PropostaCondivisione, Void>>() {
            @Override
            public TableCell<PropostaCondivisione, Void> call(final TableColumn<PropostaCondivisione, Void> param) {
                final TableCell<PropostaCondivisione, Void> cell = new TableCell<PropostaCondivisione, Void>() {

                    private final Button btn = new Button("Richiedi sharing");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            int data = getTableView().getItems().get(getIndex()).getId();

                            FXMLLoader lo = new FXMLLoader(getClass().getResource("richiestaCondivisione.fxml"));
                            Pane pane = null;
                            try {
                                pane = (Pane) lo.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            RichiestaCondivisioneController controller = lo.<RichiestaCondivisioneController>getController();
                            PropostaCondivisione prop = null;
                            try {
                                prop = RicercaBusiness.getInstance().ricercaProposta(data);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                controller.initialize(prop);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            rootPaneTabellaProposteAltriPage.getChildren().setAll(pane);
                            rootPaneTabellaProposteAltriPage.setPrefSize(1000, 600);
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

        tabellaProposteAltri.getColumns().add(colBtn);

    }





}

package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.CommonBusiness;
import it.unisalento.pps1920.carsharing.business.PrenotazioneBusiness;
import it.unisalento.pps1920.carsharing.business.RichiestaBusiness;
import it.unisalento.pps1920.carsharing.dao.interfaces.IRichiestaCondivisioneDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.RichiestaCondivisioneDAO;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.DateUtil;
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

import java.io.IOException;

public class VisualizzaRichiesteController {
    @FXML
    private TableView<TabConfermaRichieste> tabellaRichieste;
    @FXML
    private Pane rootPaneTabellaRichieste;


    ObservableList<TabConfermaRichieste> richieste = FXCollections.observableArrayList();

    public void setListRichieste(ObservableList<TabConfermaRichieste> t){
        richieste = t;

        TableColumn<TabConfermaRichieste, Integer> idRichiesta = new TableColumn<>("ID richiesta");
        idRichiesta.setCellValueFactory(new PropertyValueFactory<>("idRichiesta"));

        TableColumn<TabConfermaRichieste, String> prop = new TableColumn<>("Proponente");
        prop.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProponente().getUsername()));

        TableColumn<TabConfermaRichieste, String> rich = new TableColumn<>("Richiedente");
        rich.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRichiedente().getUsername()));

        TableColumn<TabConfermaRichieste, String> dataInizio = new TableColumn<>("inizio");
        dataInizio.setCellValueFactory(cellData -> new SimpleStringProperty(DateUtil.fromRomeToLondon(DateUtil.stringFromDate(cellData.getValue().getDataInizio()))));

        TableColumn<TabConfermaRichieste, String> dataFine = new TableColumn<>("fine");
        dataFine.setCellValueFactory(cellData -> new SimpleStringProperty(DateUtil.fromRomeToLondon(DateUtil.stringFromDate(cellData.getValue().getDataFine()))));


        TableColumn<TabConfermaRichieste, String> partenza = new TableColumn<>("partenza");
        partenza.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPartenza().getNome()));

        TableColumn<TabConfermaRichieste, String> arrivo = new TableColumn<>("arrivo");
        arrivo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArrivo().getNome()));

        TableColumn<TabConfermaRichieste, String> localita = new TableColumn<>("localita");
        localita.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocalita().getCitta()));

        TableColumn<TabConfermaRichieste, Integer> postiOccupati = new TableColumn<>("posti occ");
        postiOccupati.setCellValueFactory(new PropertyValueFactory<>("numPostiOccupati"));


        tabellaRichieste.getColumns().addAll(idRichiesta, prop, rich, dataInizio, dataFine, partenza, arrivo, localita, postiOccupati);

        addButtonVisualizzaProfilo();
        addButtonConfermaSharing();
        addButtonRifiutaSharing();

        tabellaRichieste.setItems(richieste);
    }

    private void addButtonVisualizzaProfilo() {
        TableColumn<TabConfermaRichieste, Void> colBtn = new TableColumn("");

        Callback<TableColumn<TabConfermaRichieste, Void>, TableCell<TabConfermaRichieste, Void>> cellFactory = new Callback<TableColumn<TabConfermaRichieste, Void>, TableCell<TabConfermaRichieste, Void>>() {
            @Override
            public TableCell<TabConfermaRichieste, Void> call(final TableColumn<TabConfermaRichieste, Void> param) {
                final TableCell<TabConfermaRichieste, Void> cell = new TableCell<TabConfermaRichieste, Void>() {

                    private final Button btn = new Button("Visualizza Profilo");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            int data = getTableView().getItems().get(getIndex()).getIdRichiesta();
                            Cliente c = getTableView().getItems().get(getIndex()).getRichiedente();
                            try {
                                AlertBoxProfilo.display("prenotazione", c);
                            } catch (IOException e) {
                                e.printStackTrace();
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

        tabellaRichieste.getColumns().add(colBtn);
    }

    private void addButtonConfermaSharing() {
        TableColumn<TabConfermaRichieste, Void> colBtn = new TableColumn("");

        Callback<TableColumn<TabConfermaRichieste, Void>, TableCell<TabConfermaRichieste, Void>> cellFactory = new Callback<TableColumn<TabConfermaRichieste, Void>, TableCell<TabConfermaRichieste, Void>>() {
            @Override
            public TableCell<TabConfermaRichieste, Void> call(final TableColumn<TabConfermaRichieste, Void> param) {
                final TableCell<TabConfermaRichieste, Void> cell = new TableCell<TabConfermaRichieste, Void>() {

                    private final Button btn = new Button("Conferma");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            int data = getTableView().getItems().get(getIndex()).getIdRichiesta();
                            IRichiestaCondivisioneDAO rDAO = new RichiestaCondivisioneDAO();

                            RichiestaCondivisione richiesta = null;
                            try {
                                richiesta = rDAO.findById(data);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            try {
                                if(RichiestaBusiness.getInstance().numeroPostiDisponibili(richiesta.getProposta().getDataInizio(), richiesta.getProposta().getDataFine(), richiesta.getProposta().getMezzo().getId()) - richiesta.getNumPostiRichiesti() < 0 ){
                                    AlertBox.display("prenotazione", "Purtroppo non ci sono abbastanza posti anche per questa richiesta");
                                    PrenotazioneBusiness.getInstance().notificaRichiestaRifiutataPerMancanzaPosti(richiesta);
                                    RichiestaBusiness.getInstance().rifiutaRichiesta(data, true);
                                    ObservableList<TabConfermaRichieste> rich = FXCollections.observableArrayList(CommonBusiness.getInstance().getRichiesteInAttesa(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId())) ;
                                    setListRichieste(rich);
                                } else {
                                    try {
                                        RichiestaBusiness.getInstance().accettaRichiesta(data);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    AlertBox.display("prenotazione", "ACCETTATA");
                                    FXMLLoader lo = new FXMLLoader(getClass().getResource("visualizzaRichieste.fxml"));
                                    Pane pane = (Pane) lo.load();
                                    VisualizzaRichiesteController controller = lo.<VisualizzaRichiesteController>getController();

                                    ObservableList<TabConfermaRichieste> rich = FXCollections.observableArrayList(CommonBusiness.getInstance().getRichiesteInAttesa(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId())) ;
                                    controller.setListRichieste(rich);
                                    rootPaneTabellaRichieste.getChildren().setAll(pane);
                                    rootPaneTabellaRichieste.setPrefSize(1000, 600);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
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

        tabellaRichieste.getColumns().add(colBtn);
    }

    private void addButtonRifiutaSharing() {
        TableColumn<TabConfermaRichieste, Void> colBtn = new TableColumn("");

        Callback<TableColumn<TabConfermaRichieste, Void>, TableCell<TabConfermaRichieste, Void>> cellFactory = new Callback<TableColumn<TabConfermaRichieste, Void>, TableCell<TabConfermaRichieste, Void>>() {
            @Override
            public TableCell<TabConfermaRichieste, Void> call(final TableColumn<TabConfermaRichieste, Void> param) {
                final TableCell<TabConfermaRichieste, Void> cell = new TableCell<TabConfermaRichieste, Void>() {

                    private final Button btn = new Button("Rifiuta");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            int data = getTableView().getItems().get(getIndex()).getIdRichiesta();
                            try {
                                RichiestaBusiness.getInstance().rifiutaRichiesta(data, false);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            AlertBox.display("prenotazione", "RIFIUTATA");
                            FXMLLoader lo = new FXMLLoader(getClass().getResource("visualizzaRichieste.fxml"));
                            Pane pane = null;
                            try {
                                pane = (Pane) lo.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            VisualizzaRichiesteController controller = lo.<VisualizzaRichiesteController>getController();

                            ObservableList<TabConfermaRichieste> rich = null;
                            try {
                                rich = FXCollections.observableArrayList(CommonBusiness.getInstance().getRichiesteInAttesa(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            controller.setListRichieste(rich);
                            rootPaneTabellaRichieste.getChildren().setAll(pane);
                            rootPaneTabellaRichieste.setPrefSize(1000, 600);
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

        tabellaRichieste.getColumns().add(colBtn);
    }
}

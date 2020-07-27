package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.CommonBusiness;
import it.unisalento.pps1920.carsharing.business.PrenotazioneBusiness;
import it.unisalento.pps1920.carsharing.business.RichiestaBusiness;
import it.unisalento.pps1920.carsharing.dao.interfaces.IRichiestaCondivisioneDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.RichiestaCondivisioneDAO;
import it.unisalento.pps1920.carsharing.model.*;
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

        //add colonna accessori

        //add colonna stato (pronto, non pronto)


        tabellaMezziDaPreparare.getColumns().addAll(id, targa, modello, dataInizio, dataFine, posti);

        tabellaMezziDaPreparare.setItems(mezzi);
    }

    /* private void addButtonVisualizzaProfilo() {
        TableColumn<MezzoDaPreparare, Void> colBtn = new TableColumn("Accessori");

        Callback<TableColumn<MezzoDaPreparare, Void>, TableCell<MezzoDaPreparare, Void>> cellFactory = new Callback<TableColumn<MezzoDaPreparare, Void>, TableCell<MezzoDaPreparare, Void>>() {
            @Override
            public TableCell<MezzoDaPreparare, Void> call(final TableColumn<MezzoDaPreparare, Void> param) {
                final TableCell<MezzoDaPreparare, Void> cell = new TableCell<MezzoDaPreparare, Void>() {

                    private final Button btn = new Button("Visualizza Profilo");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            //PropostaCondivisione data = getTableView().getItems().get(getIndex());
                            int idMezzo = getTableView().getItems().get(getIndex()).getMezzo().getId();
                            Date dataInizio = getTableView().getItems().get(getIndex()).getDataInizio();
                            Date dataFine = getTableView().getItems().get(getIndex()).getDataFine();

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
    }*/

    /* private void addButtonVisualizzaProfilo() {
        TableColumn<TabConfermaRichieste, Void> colBtn = new TableColumn("");

        Callback<TableColumn<TabConfermaRichieste, Void>, TableCell<TabConfermaRichieste, Void>> cellFactory = new Callback<TableColumn<TabConfermaRichieste, Void>, TableCell<TabConfermaRichieste, Void>>() {
            @Override
            public TableCell<TabConfermaRichieste, Void> call(final TableColumn<TabConfermaRichieste, Void> param) {
                final TableCell<TabConfermaRichieste, Void> cell = new TableCell<TabConfermaRichieste, Void>() {

                    private final Button btn = new Button("Visualizza Profilo");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            //PropostaCondivisione data = getTableView().getItems().get(getIndex());
                            int data = getTableView().getItems().get(getIndex()).getIdRichiesta();
                            System.out.println("Richiesta selezionata: " + data);
                            //int idRich = getTableView().getItems().get(getIndex()).getRichiedente().getId();
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
                            //PropostaCondivisione data = getTableView().getItems().get(getIndex());
                            int data = getTableView().getItems().get(getIndex()).getIdRichiesta();
                            System.out.println("Richiesta selezionata: " + data);
                            //RichiestaBusiness.getInstance().numeroPostiDisponibili(p.getDataInizio(), p.getDataFine(), p.getMezzo().getTarga()) == 0
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
                                    try {
                                        ObservableList<TabConfermaRichieste> rich = FXCollections.observableArrayList(CommonBusiness.getInstance().getRichiesteInAttesa(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId())) ;
                                        setListRichieste(rich);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
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
                            //PropostaCondivisione data = getTableView().getItems().get(getIndex());
                            int data = getTableView().getItems().get(getIndex()).getIdRichiesta();
                            System.out.println("Richiesta selezionata: " + data);
                            try {
                                RichiestaBusiness.getInstance().rifiutaRichiesta(data, false);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            AlertBox.display("prenotazione", "RIFIUTATA");
                            try {
                                ObservableList<TabConfermaRichieste> rich = FXCollections.observableArrayList(CommonBusiness.getInstance().getRichiesteInAttesa(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId())) ;
                                setListRichieste(rich);
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
    }*/
}

package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.CommonBusiness;
import it.unisalento.pps1920.carsharing.business.PrenotazioneBusiness;
import it.unisalento.pps1920.carsharing.dao.interfaces.IPrenotazioneDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.PrenotazioneDAO;
import it.unisalento.pps1920.carsharing.model.MezzoDaPreparare;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.model.TabConfermaRichieste;
import it.unisalento.pps1920.carsharing.model.Utente;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.io.IOException;

public class TabellaPrenotazioniPageController{

    @FXML
    private TableView<Prenotazione> tabellaPrenotazioni;
    @FXML
    private Pane rootPaneTabellaPrenotazioniPage;



    //ObservableList<Prenotazione> prenotazioni = (ObservableList<Prenotazione>) FXCollections.observableArrayList(new PrenotazioneDAO().findAll()); //FXCollections.observableArrayList();
    ObservableList<Prenotazione> prenotazioni = FXCollections.observableArrayList(); //FXCollections.observableArrayList();

    public void setListPrenotazioni(ObservableList<Prenotazione> pren){
        prenotazioni = pren;
        //initialize();
        TableColumn idPrenotazione = new TableColumn("ID");
        //TableColumn dataPrenotazione= new TableColumn("Data Prenotazione");
        TableColumn<Prenotazione, String> dataPrenotazione= new TableColumn<Prenotazione, String>("Data Prenotazione");
        TableColumn<Prenotazione, String> idCliente = new TableColumn<Prenotazione, String>("Username Cliente");
        TableColumn<Prenotazione, String> idMezzo = new TableColumn<Prenotazione, String>("Targa Mezzo");
        TableColumn numPostiOcc = new TableColumn("Numero Posti Occupati");
        TableColumn<Prenotazione, String> idStazPart = new TableColumn<Prenotazione, String>("Stazione Partenza");
        TableColumn<Prenotazione, String> idStazArr = new TableColumn<Prenotazione, String>("Stazione Arrivo");
        TableColumn<Prenotazione, String> idLocalita = new TableColumn<Prenotazione, String>("Localita");
        //TableColumn dataInizio = new TableColumn("Data Inizio");
        //TableColumn dataFine = new TableColumn("Data Fine");

        float idPrenotazioneWidth = 50;
        float dataPrenotazioneWidth = 200;
        float idClienteWidth = 125;
        float idMezzoWidth = 100;
        float numPostiOccWidth = 50;
        float idStazPartWidth = 125;
        float idStazArrWidth = 125;
        float idLocalitaWidth = 125;
        float dataInizioWidth = 200;
        float dataFineWidth = 200;

        idPrenotazione.setPrefWidth(idPrenotazioneWidth);
        dataPrenotazione.setPrefWidth(dataPrenotazioneWidth);
        idCliente.setPrefWidth(idClienteWidth);
        idMezzo.setPrefWidth(idMezzoWidth);
        numPostiOcc.setPrefWidth(numPostiOccWidth);
        idStazPart.setPrefWidth(idStazPartWidth);
        idStazArr.setPrefWidth(idStazArrWidth);
        idLocalita.setPrefWidth(idLocalitaWidth);
        //dataInizio.setPrefWidth(dataInizioWidth);
        //dataFine.setPrefWidth(dataFineWidth);

//idPrenotazioneWidth + dataPrenotazioneWidth + idClienteWidth + idMezzoWidth + numPostiOccWidth + idStazPartWidth + idStazArrWidth + idLocalitaWidth + dataInizioWidth + dataFineWidth
        float prefWidth = 900;
        float prefHeight = 400;

        tabellaPrenotazioni.setPrefSize(prefWidth, prefHeight);

        TableColumn<Prenotazione, String> dataInizio = new TableColumn<>("inizio");
        dataInizio.setCellValueFactory(cellData -> new SimpleStringProperty(DateUtil.fromRomeToLondon(DateUtil.stringFromDate(cellData.getValue().getDataInizio()))));
        TableColumn<Prenotazione, String> dataFine = new TableColumn<>("fine");
        dataFine.setCellValueFactory(cellData -> new SimpleStringProperty(DateUtil.fromRomeToLondon(DateUtil.stringFromDate(cellData.getValue().getDataFine()))));
        TableColumn<Prenotazione, String> pagamento = new TableColumn<>("pagamento");
        pagamento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPagamento()));


        tabellaPrenotazioni.getColumns().addAll(idPrenotazione, dataPrenotazione, idCliente, idMezzo, numPostiOcc, idStazPart, idStazArr, idLocalita, dataInizio, dataFine, pagamento);


        idPrenotazione.setCellValueFactory(new PropertyValueFactory<Prenotazione, String>("id"));
        dataPrenotazione.setCellValueFactory(new PropertyValueFactory<Prenotazione,String>("data"));
        idCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getUsername()));
        idMezzo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMezzo().getTarga()));
        numPostiOcc.setCellValueFactory(new PropertyValueFactory<Prenotazione,String>("numPostiOccupati"));
        idStazPart.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPartenza().getNome()));
        idStazArr.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArrivo().getNome()));
        idLocalita.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocalita().getCitta()));
        //dataInizio.setCellValueFactory(new PropertyValueFactory<Prenotazione,String>("dataInizio"));
        //dataFine.setCellValueFactory(new PropertyValueFactory<Prenotazione,String>("dataFine"));


        addButtonElimina();

        //addButtonModifica();
        if (CommonBusiness.getInstance().checkCliente(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId())){
            addButtonModifica();
        }



        tabellaPrenotazioni.setItems(prenotazioni);
    }


    public void initialize() {

    }

    private void addButtonElimina() {
        TableColumn<Prenotazione, Void> colBtn = new TableColumn("");

        Callback<TableColumn<Prenotazione, Void>, TableCell<Prenotazione, Void>> cellFactory = new Callback<TableColumn<Prenotazione, Void>, TableCell<Prenotazione, Void>>() {
            @Override
            public TableCell<Prenotazione, Void> call(final TableColumn<Prenotazione, Void> param) {
                final TableCell<Prenotazione, Void> cell = new TableCell<Prenotazione, Void>() {

                    private final Button btn = new Button("Elimina");
                    //private final TilePane tp = new TilePane();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            System.out.println("cancellare: " + getTableView().getItems().get(getIndex()).getId());
                            try {
                                Prenotazione p = new PrenotazioneDAO().findById(getTableView().getItems().get(getIndex()).getId());
                                PrenotazioneBusiness.getInstance().eliminaPrenotazione(p);
                                AlertBox.display("Prenotazioni", "Prenotazione eliminata");
                                ObservableList<Prenotazione> prenotazioni = (ObservableList<Prenotazione>) FXCollections.observableArrayList(CommonBusiness.getInstance().getPrenotazioniUtente(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId()));
                                setListPrenotazioni(prenotazioni);
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

        tabellaPrenotazioni.getColumns().add(colBtn);
    }

    private void addButtonModifica() {
        TableColumn<Prenotazione, Void> colBtn = new TableColumn("");

        Callback<TableColumn<Prenotazione, Void>, TableCell<Prenotazione, Void>> cellFactory = new Callback<TableColumn<Prenotazione, Void>, TableCell<Prenotazione, Void>>() {
            @Override
            public TableCell<Prenotazione, Void> call(final TableColumn<Prenotazione, Void> param) {
                final TableCell<Prenotazione, Void> cell = new TableCell<Prenotazione, Void>() {

                    private final Button btn = new Button("Modifica");
                    //private final TilePane tp = new TilePane();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            System.out.println("modificare: " + getTableView().getItems().get(getIndex()).getId());
                            if (CommonBusiness.getInstance().propostaUgualeCliente(getTableView().getItems().get(getIndex()).getIdPropostaCondivisione(), ((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId())){
                                //modificabile
                                //AlertBox.display("Prenotazioni", "Lo hai proposto tu");
                                IPrenotazioneDAO pDAO = new PrenotazioneDAO();
                                try {
                                    Prenotazione p = pDAO.findById(getTableView().getItems().get(getIndex()).getId());

                                    FXMLLoader lo = new FXMLLoader(getClass().getResource("modificaPrenotazione.fxml"));
                                    Pane pane = (Pane) lo.load();
                                    ModificaPrenotazioneController controller = lo.<ModificaPrenotazioneController>getController();

                                    controller.setPrenotazione(p);
                                    rootPaneTabellaPrenotazioniPage.getChildren().setAll(pane);
                                    rootPaneTabellaPrenotazioniPage.setPrefSize(1000, 600);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                AlertBox.display("Prenotazioni", "Non puoi modificare questo sharing, non lo hai proposto tu");
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

        tabellaPrenotazioni.getColumns().add(colBtn);
    }


}

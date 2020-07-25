package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.util.DateUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

public class TabellaPrenotazioniPageController{

    @FXML
    private TableView<Prenotazione> tabellaPrenotazioni;


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
        TableColumn dataInizio = new TableColumn("Data Inizio");
        TableColumn dataFine = new TableColumn("Data Fine");

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
        dataInizio.setPrefWidth(dataInizioWidth);
        dataFine.setPrefWidth(dataFineWidth);

//idPrenotazioneWidth + dataPrenotazioneWidth + idClienteWidth + idMezzoWidth + numPostiOccWidth + idStazPartWidth + idStazArrWidth + idLocalitaWidth + dataInizioWidth + dataFineWidth
        float prefWidth = 900;
        float prefHeight = 400;

        tabellaPrenotazioni.setPrefSize(prefWidth, prefHeight);

        tabellaPrenotazioni.getColumns().addAll(idPrenotazione, dataPrenotazione, idCliente, idMezzo, numPostiOcc, idStazPart, idStazArr, idLocalita, dataInizio, dataFine);


        idPrenotazione.setCellValueFactory(new PropertyValueFactory<Prenotazione, String>("id"));
        dataPrenotazione.setCellValueFactory(new PropertyValueFactory<Prenotazione,String>("data"));
        idCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getUsername()));
        idMezzo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMezzo().getTarga()));
        numPostiOcc.setCellValueFactory(new PropertyValueFactory<Prenotazione,String>("numPostiOccupati"));
        idStazPart.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPartenza().getNome()));
        idStazArr.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArrivo().getNome()));
        idLocalita.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocalita().getCitta()));
        dataInizio.setCellValueFactory(new PropertyValueFactory<Prenotazione,String>("dataInizio"));
        dataFine.setCellValueFactory(new PropertyValueFactory<Prenotazione,String>("dataFine"));



        tabellaPrenotazioni.setItems(prenotazioni);
    }


    public void initialize() {

    }
}

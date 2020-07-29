package it.unisalento.pps1920.carsharing.business;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.*;
import it.unisalento.pps1920.carsharing.dao.mysql.*;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.DateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CommonBusiness {
    private static CommonBusiness instance;
    public static synchronized CommonBusiness getInstance(){
        if(instance == null){
            instance = new CommonBusiness();
        }
        return instance;
    }
    private CommonBusiness(){}

    private ObservableList<String> dimensioni = FXCollections.observableArrayList("Piccola", "Media", "Grande"); //enum nel db
    private ObservableList<String> motorizzazione = FXCollections.observableArrayList("Elettrica", "Benzina", "Disel"); //enum nel db
    private ObservableList<String> tipologia = FXCollections.observableArrayList("Auto", "Furgone", "Camion", "Camper"); //enum nel db
    private ObservableList<String> ore = FXCollections.observableArrayList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
    private ObservableList<String> minuti = FXCollections.observableArrayList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59");
    private ObservableList<String> eta = FXCollections.observableArrayList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100");
    private ObservableList<String> euro = FXCollections.observableArrayList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100");
    private ObservableList<String> centesimi = FXCollections.observableArrayList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99");
    private ObservableList<String> posti = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8");
    private ObservableList<String> posti100 = FXCollections.observableArrayList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100");
    private ObservableList<String> offerta = FXCollections.observableArrayList("Si", "No");

    public ObservableList<String> getPosti100(){
        return posti100;
    }

    public ObservableList<String> getOfferone() {return  offerta;}

    public ObservableList<String> getDimensioni(){
        return dimensioni;
    }

    public ObservableList<String> getMotorizzazione() {
        return motorizzazione;
    }

    public ObservableList<String> getTipologia() {
        return tipologia;
    }

    public ObservableList<String> getEuro() {
        return euro;
    }

    public ObservableList<String> getPosti() {
        return posti;
    }

    public ObservableList<String> getCentesimi() {
        return centesimi;
    }

    public ObservableList<String> getEta(){
        return eta;
    }

    public ObservableList<String> getOre(){
        return ore;
    }

    public ObservableList<String> getMinuti(){
        return minuti;
    }

    public ArrayList<Stazione> getStazioni() throws IOException {
        IStazioneDAO sDAO = new StazioneDAO();
        return sDAO.findAll();
    }

    public ArrayList<Mezzo> getOffertoneArray() throws IOException{
        IMezzoDAO mDAO=new MezzoDAO();
        return mDAO.getMezziOfferta();
    }

    public ArrayList<Localita> getLocalita() throws IOException {
        ILocalitaDAO lDAO = new LocalitaDAO();
        return lDAO.findAll();
    }

    public ArrayList<Cliente> getClienti() throws IOException {
        IClienteDAO cDAO = new ClienteDAO();
        return cDAO.findAll();
    }

    public ArrayList<Modello> getModelli() throws IOException {
        IModelloDAO mDAO = new ModelloDAO();
        return mDAO.findAll();
    }

    public ArrayList<Prenotazione> getPrenotazioni() throws IOException {
        IPrenotazioneDAO pDAO = new PrenotazioneDAO();
        return pDAO.findAll();
    }

    public ArrayList<PropostaCondivisione> getProposteCondivisione() throws IOException {
        IPropostaCondivisioneDAO pDAO = new PropostaCondivisioneDAO();
        return pDAO.getProposte();
    }

    public ArrayList<RichiestaCondivisione> getRichiesteCondivisione(int idCliente) throws IOException {
        IRichiestaCondivisioneDAO rDAO = new RichiestaCondivisioneDAO();
        return rDAO.getRichiesteCliente(idCliente);
    }

    public ArrayList<MezzoDaPreparare> getMezziProntiAPartire() throws IOException {
        IMezzoDaPreparareDAO mDAO = new MezzoDaPreparareDAO();
        return mDAO.getMezziPronti();
    }

    public ArrayList<Mezzo> getMezzi() throws IOException {
        IMezzoDAO mDAO = new MezzoDAO();
        return mDAO.findAll();
    }

    public ArrayList<MezzoDaPreparare> getMezziDaPreparare() throws IOException {
        IMezzoDaPreparareDAO mDAO = new MezzoDaPreparareDAO();
        return mDAO.getMezziDaPreparare();
    }

    public boolean setPartito(MezzoDaPreparare m) throws IOException {
        IMezzoDaPreparareDAO mDAO=new MezzoDaPreparareDAO();
        int a[]=mDAO.prenotazioniFromDateEIdMezzo2(m.getMezzo().getId(), DateUtil.stringFromDate(m.getDataInizio()), DateUtil.stringFromDate(m.getDataFine()));
        boolean res=mDAO.mezzoPartito(a,m.getId());
        return res;
    }

    public boolean setPagato(MezzoDaPreparare m) throws IOException {
        IMezzoDaPreparareDAO mDAO = new MezzoDaPreparareDAO();
        int a[]=mDAO.prenotazioniFromDateEIdMezzo2(m.getMezzo().getId(), DateUtil.stringFromDate(m.getDataInizio()), DateUtil.stringFromDate(m.getDataFine()));
        boolean res = mDAO.setPagato(a);
        return res;
    }


    public boolean setPronto(int id){
        IMezzoDaPreparareDAO mz=new MezzoDaPreparareDAO();
        boolean res = mz.mezzoPronto(id);
        return res;
    }


    public ArrayList<Prenotazione> getPrenotazioniUtente(int id) throws IOException {
        IPrenotazioneDAO pDAO = new PrenotazioneDAO();
        return pDAO.ricercaPerCliente(id);
    }

    public ArrayList<Accessorio> getAccessori() throws IOException {
        IAccessorioDAO aDAO = new AccessorioDAO();
        return aDAO.findAll();
    }

    public ArrayList<Accessorio> getAccessoriPrenotazione(int idPren){
        IAccessorioDAO aDAO = new AccessorioDAO();
        return aDAO.getAccessoriPrenotazione(idPren);
    }

    public boolean propostaUgualeCliente(int idProposta, int idCliente){
        IPropostaCondivisioneDAO pDAO = new PropostaCondivisioneDAO();
        return pDAO.propostaUgualeCliente(idProposta, idCliente);
    }

    public boolean checkCliente(int id){ //verifica se l'utente con l'id passato è un cliente o no
        IClienteDAO cDAO = new ClienteDAO();
        if (cDAO.checkCliente(id)){
            return true;
        }
        return false;
    }

    public boolean checkAmministratore(int id){ //verifica se l'utente con l'id passato è un admin o no
        IAmministratoreDAO aDAO = new AmministratoreDAO();
        if (aDAO.checkAmministratore(id)){
            return true;
        }
        return false;
    }

    public boolean checkAddetto(int id){ //verifica se l'utente con l'id passato è un addetto o no
        IAddettoDAO aDAO = new AddettoDAO();
        if (aDAO.checkAddetto(id)){
            return true;
        }
        return false;
    }

    public boolean checkOperatore(int id){ //verifica se l'utente con l'id passato è un addetto o no
        IOperatoreDAO oDAO = new OperatoreDAO();
        if (oDAO.checkOperatore(id)){
            return true;
        }
        return false;
    }

    public Cliente getCliente(int id) throws IOException {
        IClienteDAO cDAO = new ClienteDAO();
        return cDAO.findById(id);
    }

    public Accessorio getAccessorio(int id) throws IOException {
        IAccessorioDAO aDAO = new AccessorioDAO();
        return aDAO.findById(id);
    }

    public boolean checkUsername(String user){ //verifica se lo username è già utilizzato
        boolean res = new UtenteDAO().ricercaUsername(user);
        return res;
    }

    public boolean checkEmail(String email){ //verifica se lo username è già utilizzato
        boolean res = new UtenteDAO().ricercaEmail(email);
        return res;
    }

    public javafx.scene.image.Image getFotoModello(int id) throws IOException {
        IModelloDAO mDAO = new ModelloDAO();
        return mDAO.getFoto(id);
    }

    public javafx.scene.image.Image getFotoCliente(int id) throws IOException {
        IClienteDAO cDAO = new ClienteDAO();
        return cDAO.getFoto(id);
    }

    public int getIdPrenFromIdPropCon(int idProp){
        IPrenotazioneDAO pDAO = new PrenotazioneDAO();
        return pDAO.getIdPrenFromIdPropCon(idProp);
    }

    public ArrayList<TabConfermaRichieste> getRichiesteInAttesa(int idProponente) throws IOException {
        ITabConfermaDAO tDAO = new TabConfermaDAO();
        return tDAO.getElencoInAttesa(idProponente);
    }

    public int[] prenotazioniFromDateEIdMezzo(int idMezzo, String dataInizio, String dataFine){
        IPrenotazioneDAO pDAO = new PrenotazioneDAO();
        return pDAO.prenotazioniFromDateEIdMezzo(idMezzo, dataInizio, dataFine);
    }


}

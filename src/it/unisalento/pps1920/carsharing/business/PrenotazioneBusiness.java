package it.unisalento.pps1920.carsharing.business;

import it.unisalento.pps1920.carsharing.dao.interfaces.*;
import it.unisalento.pps1920.carsharing.dao.mysql.*;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.MailHelper;
import it.unisalento.pps1920.carsharing.util.PdfHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PrenotazioneBusiness {
    private static PrenotazioneBusiness instance;

    public static synchronized PrenotazioneBusiness getInstance() {
        if(instance == null){
            instance = new PrenotazioneBusiness();
        }
        return instance;
    }

    private PrenotazioneBusiness(){}

    public static boolean inviaPrenotazione(Prenotazione p, List<Accessorio> a){
        //logica di business

        //System.out.println("TESTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT1: " + p.getId()); //id assente
        //1. chiamare il dao prenotazione per salvare la prenotazione
        PropostaCondivisione prop = new PropostaCondivisione();
        prop.setData(p.getData());
        prop.setCliente(p.getCliente());
        prop.setMezzo(p.getMezzo());
        prop.setNumPostiOccupati(p.getNumPostiOccupati());
        prop.setPartenza(p.getPartenza());
        prop.setArrivo(p.getArrivo());
        prop.setLocalita(p.getLocalita());
        prop.setDataInizio(p.getDataInizio());
        prop.setDataFine(p.getDataFine());

        new PropostaCondivisioneDAO().salvaProposta(prop);
        //System.out.println("bhuuhuhuhuhuhuhuhhuhhuh: " + prop.getId());
        p.setIdPropostaCondivisione(prop.getId());


        float costo = p.getMezzo().getModello().getTariffaBase();
        //il costo dello sharing è dato dalla tariffa base sommato al costo di ogni accessorio scelto
        for (int i=0; i<a.size(); i++){
            costo = costo + a.get(i).getCosto();
        }

        //p.setCosto(costo);


        new PrenotazioneDAO().salvaPrenotazione(p, a, costo);
        new MezzoDAO().decrementaPosti(p.getMezzo(), p.getNumPostiOccupati());


        System.out.println("salvato");
        //System.out.println("TESTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT2: " + p.getId());//id presente


        //2. inviare mail all'addetto del parco automezzi

        String dest1 = p.getPartenza().getAddetto().getEmail();//addetto della stazione di partenza utente id 1
        String testo1 = "pren id" + p.getId() + "del " + p.getData() + "per il " + p.getDataInizio() + ".";
        MailHelper.getInstance().send(dest1, "ADD Nuova prenotazione", testo1);

        System.out.println("invio email addetto");

        //3. inviare mail di conferma all'utente
        String dest2 = p.getCliente().getEmail(); //cliente utente id 2 gc.pps
        String testo2 = "pren id" + p.getId() + "del " + p.getData() + "per il " + p.getDataInizio() + ".";
        MailHelper.getInstance().send(dest2, "CLI Prenotazione confermata!", testo2);

        System.out.println("inviata email cliente");

        //4. generare pdf per l'utente
        ArrayList<String> testo = new ArrayList<String>();
        testo.add("Codice prenotazione: "+p.getId());
        testo.add("Il giorno...");
        testo.add("Stampa questo file e presentati in stazione");
        //PdfHelper.getInstance().creaPdf(testo, p.getId()); //todo pdf commentato per non cambiare sempre path
        System.out.println("pdf ok");

        return true;
    }

    public boolean modificaPrenotazione(Prenotazione p){
        //logica di business
        return true;
    }

    /*public int calcoloCosto(){

    }*/


}

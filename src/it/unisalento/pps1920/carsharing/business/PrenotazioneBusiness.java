package it.unisalento.pps1920.carsharing.business;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.*;
import it.unisalento.pps1920.carsharing.dao.mysql.*;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.DateUtil;
import it.unisalento.pps1920.carsharing.util.MailHelper;
import it.unisalento.pps1920.carsharing.util.PdfHelper;
import it.unisalento.pps1920.carsharing.view.AlertBox;

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

    public static boolean inviaPrenotazione(Prenotazione p, List<Accessorio> a) throws IOException {
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


        new PrenotazioneDAO().salvaPrenotazione(p, a, costo, true);

        //new MezzoDAO().decrementaPosti(p.getMezzo(), p.getNumPostiOccupati());


        System.out.println("salvato");
        //System.out.println("TESTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT2: " + p.getId());//id presente


        //2. inviare mail all'addetto del parco automezzi

        String dest1 = p.getPartenza().getAddetto().getEmail();//addetto della stazione di partenza utente id 1
        String testo1 = "NUOVO MEZZO DA PREPARARE! \n";
        testo1 = testo1 + "Modello: " + p.getMezzo().getModello().getNome() + "\n";
        testo1 = testo1 + "Targa: " + p.getMezzo().getTarga() + "\n";
        if (a.size()>0){
            testo1 = testo1 + "Accessori: ";
            for (int i=0; i<a.size(); i++){
                testo1 = testo1 + " " + a.get(i).getNome();
            }
            testo1 = testo1 + "\n";
        }
        testo1 = testo1 + "Deve essere pronto per il: "+DateUtil.stringFromDate(p.getDataInizio()) + "\n";
        testo1 = testo1 + "Buon lavoro!";
        MailHelper.getInstance().send(dest1, "ADD Nuova prenotazione", testo1);

        System.out.println("invio email addetto");

        //3. inviare mail di conferma all'utente
        String dest2 = p.getCliente().getEmail(); //cliente utente id 2 gc.pps
        String testo = "Buongiorno! \n ";
        testo = testo + "Codice prenotazione: "+ p.getId() + "\n";
        testo = testo + "Prenotazione effettuata il: "+DateUtil.stringFromDate(p.getData()) + "\n";
        testo = testo + "Inizio : "+DateUtil.stringFromDate(p.getDataInizio()) + "\n";
        testo = testo + "Fine : "+DateUtil.stringFromDate(p.getDataFine()) + "\n";
        testo = testo + "Da : "+p.getPartenza().getNome() + "\n";
        testo = testo + "A : "+p.getArrivo().getNome() + "\n";
        testo = testo + "Numero posti prenotati : "+p.getNumPostiOccupati() + "\n";
        testo = testo + "Con : "+p.getMezzo().getModello().getNome() + " targato: " + p.getMezzo().getTarga() + "\n";
        testo = testo + "Stampa questo file e presentati in stazione" + "\n";
        testo = testo + "A presto :) "+ "\n";
        MailHelper.getInstance().send(dest2, "CLI Prenotazione confermata!", testo);

        System.out.println("inviata email cliente");

        //4. generare pdf per l'utente
        ArrayList<String> testoPDF = new ArrayList<String>();
        testoPDF.add("Codice prenotazione: "+ p.getId());
        testoPDF.add("Prenotazione effettuata il: "+DateUtil.stringFromDate(p.getData()));
        testoPDF.add("Inizio : "+DateUtil.stringFromDate(p.getDataInizio()));
        testoPDF.add("Fine : "+DateUtil.stringFromDate(p.getDataFine()));
        testoPDF.add("Da : "+p.getPartenza().getNome());
        testoPDF.add("A : "+p.getArrivo().getNome());
        testoPDF.add("Numero posti prenotati : "+p.getNumPostiOccupati());
        testoPDF.add("Con : "+p.getMezzo().getModello().getNome() + " targato: " + p.getMezzo().getTarga());
        testoPDF.add("Stampa questo file e presentati in stazione");
        testoPDF.add("A presto :) ");
        //PdfHelper.getInstance().creaPdf(testo, p.getId()); //todo pdf commentato per non cambiare sempre path
        System.out.println("pdf ok");

        return true;
    }

    public boolean modificaPrenotazione(Date inizio, Date fine, int posti, Stazione arrivo, Stazione partenza, Localita localita, Prenotazione oldPren) throws IOException {
        IPrenotazioneDAO pDAO = new PrenotazioneDAO();
        IPropostaCondivisioneDAO propDAO = new PropostaCondivisioneDAO();
        PropostaCondivisione prop = propDAO.findById(oldPren.getIdPropostaCondivisione());


        //controllo che le nuove date siano esatte
        ArrayList<MezzoDaPreparare> mezziPrenotati = new MezzoDaPreparareDAO().findAll(); //prendo tutti i mezzi da preparare
        for (int i = 0; i < mezziPrenotati.size(); i++){ //toglo la vecchia prenotazione
            if (mezziPrenotati.get(i).getMezzo().getId() == oldPren.getMezzo().getId()){
                mezziPrenotati.remove(i);
                break;
            }
        }
        Date dataInizio = oldPren.getDataInizio();
        Date dataFine = oldPren.getDataFine();
        if (inizio!=null){
            dataInizio = inizio;
        }
        if (fine!=null){
            dataFine = fine;
        }
        boolean error = false;
        for (int i = 0; i < mezziPrenotati.size(); i++){ //scorro i mezzi gia prenotati
            //for (int j = 0; j < mezzi.size(); j++){
                System.out.println(dataInizio.toString()+  " aaaaabbbbbbbbbaaaaa " + mezziPrenotati.get(i).getDataInizio());
                if (mezziPrenotati.get(i).getMezzo().getId() == oldPren.getMezzo().getId()){ //se l'id del mezzo della prenotazione è uguale a quello di uno dei mezzi appena presi
                    if (dataInizio.compareTo(mezziPrenotati.get(i).getDataInizio()) >= 0 && dataInizio.compareTo(mezziPrenotati.get(i).getDataFine()) <= 0){ //se la data di inizio si trova nel range della prenotazione già effettuata allora dai errore
                        //0 ->date uguali; <0 se d minore dell'altra; >0 se l'altra è maggiore di d
                        //System.out.println("erroreeeeeeeeeeeeeeeeeeeeeeeeeeeee data inizio");
                        error = true;
                    } else {
                        //System.out.println("esattooooooooooooooooooooooooooooo data inizio");
                        if (dataInizio.compareTo(mezziPrenotati.get(i).getDataInizio()) >= 0 && dataFine.compareTo(mezziPrenotati.get(i).getDataFine()) <= 0){
                            //System.out.println("erroreeeeeeeeeeeeeeeeeeeeeeeeeeeee data fine");
                            error = true;
                        } else {
                            //System.out.println("esattooooooooooooooooooooooooooooo data fine");

                            if ((dataInizio.compareTo(mezziPrenotati.get(i).getDataInizio()) <= 0 && dataFine.compareTo(mezziPrenotati.get(i).getDataInizio()) <= 0) || dataFine.compareTo(mezziPrenotati.get(i).getDataFine()) >= 0 && dataFine.compareTo(mezziPrenotati.get(i).getDataFine()) >= 0){
                                System.out.println("esattooooooooooooooooooooooooooooo tuttoooooooooooooooooooo");
                                //System.out.println(fine.toString() + " aaaaaaaaaa " + mezziPrenotati.get(i).getDataInizio());
                                error = false;
                            } else {
                                System.out.println("erroreeeeeeeeeeeeeeeeeeeeeeeeeeeee tuttoooooooooooo");
                                error = true;
                            }
                        }
                    }
                }
            //}
        }
        if (error){
            //AlertBox.display("BUBUIIHIHIHH", "data non modificabile");
            return false;
        } /*else {
            //AlertBox.display("BUBUIIHIHIHH", "data modificabile");
        }*/





        //controllo posti disponibili
        System.out.println(oldPren.getMezzo().getModello().getNumPosti() +"-("+ prop.getNumPostiOccupati() +"-"+ oldPren.getNumPostiOccupati() +")-"+ posti +">=" +oldPren.getMezzo().getModello().getNumPosti());
        if (oldPren.getMezzo().getModello().getNumPosti() - (prop.getNumPostiOccupati() - oldPren.getNumPostiOccupati()) - posti >= oldPren.getMezzo().getModello().getNumPosti() ){ //numpostimezzo-numpostioccupati-numpostioccupatidallaprenotazione+numpostinuovapren deve essere minore del numero di posti disponibili in macchina
            return false;
        } else {
            if (pDAO.modificaPrenotazione(inizio, fine, posti, arrivo, partenza, localita, oldPren)){
                new MezzoDaPreparareDAO().modificaTabella(inizio, fine, posti, arrivo, partenza, localita, oldPren);
                new PropostaCondivisioneDAO().modificaTabella(inizio, fine, posti, arrivo, partenza, localita, oldPren);
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean eliminaPrenotazione(Prenotazione p) throws IOException {
        //settare prenotazionevalida=0 nella tabella delle prenotazioni
        // correzione sul costo richiestabusiness 62

        IPrenotazioneDAO pDAO = new PrenotazioneDAO();
        /*decrementare il numero di posti della proposta.
        se è l'unico allora eliminare il mezzo da mezzi_da_preparare ed eliminare la proposta
        */
        System.out.println("numero clienti sharing: " + pDAO.getNumeroClientiSharing(p.getIdPropostaCondivisione()));
        if (pDAO.getNumeroClientiSharing(p.getIdPropostaCondivisione()) > 1 ){

            /*float costo;
            int numClientiSharing = pDAO.getNumeroClientiSharing(p.getIdPropostaCondivisione()) - 1; //clienti che stanno facendo lo sharing - quello che è sta eliminando

            float costoBaseModello = p.getMezzo().getModello().getTariffaBase() / numClientiSharing; //costo della macchina diviso numero clienti che fannno lo sharing

            costo = costoBaseModello;*/

            new PrenotazioneDAO().correggiCosto(p.getIdPropostaCondivisione(), false); //diminuisce il costo se ci sono piu clienti che fanno lo sharing

            //decrementa solo il numero di posti dalla proposta
            IPropostaCondivisioneDAO propDAO = new PropostaCondivisioneDAO();
            propDAO.updatePostiProposta(p.getIdPropostaCondivisione(), (-p.getNumPostiOccupati()));

            //decrementa il numero di posti dalla tabella mezzi_da_preparare
            IMezzoDaPreparareDAO mDAO = new MezzoDaPreparareDAO();
            mDAO.updateNumeroPosti(p);


        } else {
            //elimina il record della tabella  mezzi_da_preparare e setta proposta invalida
            IMezzoDaPreparareDAO mDAO = new MezzoDaPreparareDAO();
            mDAO.eliminaRecord(p);
            new PropostaCondivisioneDAO().setPropostaInvalida(p.getIdPropostaCondivisione());
        }
        boolean res1 = pDAO.setPrenotazioneInvalida(p.getId());

        // inviare mail di conferma all'utente
        String dest = p.getCliente().getEmail(); //cliente utente id 2 gc.pps
        String testo = "PRENOTAZIONE ELIMINATA! \n";
        testo = testo + "La prenotazione con codice prenotazione: "+ p.getId() + "\n";
        testo = testo + "Prenotazione effettuata il: "+DateUtil.stringFromDate(p.getData()) + "\n";
        testo = testo + "Inizio : "+DateUtil.stringFromDate(p.getDataInizio()) + "\n";
        testo = testo + "Fine : "+DateUtil.stringFromDate(p.getDataFine()) + "\n";
        testo = testo + "Da : "+p.getPartenza().getNome() + "\n";
        testo = testo + "A : "+p.getArrivo().getNome() + "\n";
        testo = testo + "Numero posti prenotati : "+p.getNumPostiOccupati() + "\n";
        testo = testo + "Con : "+p.getMezzo().getModello().getNome() + " targato: " + p.getMezzo().getTarga() + "\n";
        testo = testo + "è stata eliminata con successo \n Arrivederci!";
        MailHelper.getInstance().send(dest, "CLI Prenotazione eliminata!", testo);

        return true;
    }

    public void notificaRichiestaRifiutataPerMancanzaPosti(RichiestaCondivisione r){
        String dest = r.getCliente().getEmail();//addetto della stazione di partenza utente id 1
        String testo = "Purtroppo la richiesta " + r.getId() + " del " + r.getData() + " non è andata a buon fine. Qualcuno ha prenotato prima e non ci sono abbastanza posti per soddisfare la richiesta.";
        MailHelper.getInstance().send(dest, "Richiesta rifiutata per posti", testo);
    }

    public ArrayList<Prenotazione> getStazioniOperatore(Utente op) throws IOException {
        ArrayList<Prenotazione> pren =new ArrayList<Prenotazione>();
        IPrenotazioneDAO pDAO = (IPrenotazioneDAO) new PrenotazioneDAO();
        pren=pDAO.getPrenotazioniPerOperatore(op);
        return pren;
    }

    public ArrayList<Prenotazione> getPrenotazioniPerAdmin() throws IOException {
        IPrenotazioneDAO pDAO = new PrenotazioneDAO();
        return pDAO.findAllPrenotazioniPerAdmin();
    }



}

package it.unisalento.pps1920.carsharing.business;

import it.unisalento.pps1920.carsharing.dao.interfaces.IPrenotazioneDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IRichiestaCondivisioneDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.AccessorioDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.PrenotazioneDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.PropostaCondivisioneDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.RichiestaCondivisioneDAO;
import it.unisalento.pps1920.carsharing.model.Accessorio;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.model.PropostaCondivisione;
import it.unisalento.pps1920.carsharing.model.RichiestaCondivisione;
import it.unisalento.pps1920.carsharing.util.DateUtil;
import it.unisalento.pps1920.carsharing.util.MailHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RichiestaBusiness {

    private static RichiestaBusiness instance;

    public static synchronized RichiestaBusiness getInstance() {
        if(instance == null){
            instance = new RichiestaBusiness();
        }
        return instance;
    }

    private RichiestaBusiness(){}



    public static int richiestaToPrenotazione(RichiestaCondivisione r) throws IOException {
        Prenotazione pren = new Prenotazione();
        PropostaCondivisione prop = new PropostaCondivisioneDAO().findById(r.getProposta().getId());
        pren.setData(new Date());
        pren.setCliente(r.getCliente());
        pren.setMezzo(prop.getMezzo());
        pren.setNumPostiOccupati(r.getNumPostiRichiesti());
        pren.setPartenza(prop.getPartenza());
        pren.setArrivo(prop.getArrivo());
        pren.setLocalita(prop.getLocalita());
        String strDataInizio = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(prop.getDataInizio()));
        String strDataFine = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(prop.getDataFine()));
        Date inizio = DateUtil.dateTimeFromString(strDataInizio);
        Date fine = DateUtil.dateTimeFromString(strDataFine);
        pren.setDataInizio(inizio);
        pren.setDataFine(fine);
        pren.setIdPropostaCondivisione(prop.getId());


        List<Accessorio> acc = new ArrayList<Accessorio>();
        acc = new AccessorioDAO().getAccessoriRichiesta(r.getId());

        float costo;
        IPrenotazioneDAO pDAO = new PrenotazioneDAO();
        int numClientiSharing = pDAO.getNumeroClientiSharing(r.getProposta().getId()) + 1; //clienti che stanno facendo lo sharing + quello che è stato appena confermato

        float costoBaseModello = pren.getMezzo().getModello().getTariffaBase() / numClientiSharing; //costo della macchina diviso numero clienti che fannno lo sharing

        costo = costoBaseModello;


        for (int i=0; i<acc.size(); i++){
            costo = costo + acc.get(i).getCosto(); //aggiunta prezzo accessori
        }

        new PrenotazioneDAO().correggiCosto(r.getProposta().getId(), true); //diminuisce il costo se ci sono piu clienti che fanno lo sharing

        new PrenotazioneDAO().salvaPrenotazione(pren, acc, costo, false);

        new PropostaCondivisioneDAO().updatePostiProposta(prop.getId(), pren.getNumPostiOccupati());

        //System.out.println("iddddddddddddddddddddd: " + pren.getId());
        return pren.getId();
    }

    public static boolean inviaRichiestaCondivisione(RichiestaCondivisione r, List<Accessorio> a){
        boolean res = new RichiestaCondivisioneDAO().salvaRichiesta(r, a);
        if (res){
            //System.out.println("jihjihihihihihihihiuhihihihihihihihihihhiih: " + r.getId());
            String dest = r.getCliente().getEmail(); //cliente utente id 2 gc.pps
            String testo = "richiesta id" + r.getId() + "del  " + new Date().toString() + " fatta. In attesa di conferma";
            MailHelper.getInstance().send(dest, "CLI Richiesta sharing in attesa!", testo);
        }
        return res;
    }

    public static boolean accettaRichiesta(int idRichiesta) throws IOException {
        boolean res = new RichiestaCondivisioneDAO().accettaRichiesta(idRichiesta); //setta stato da "Attesa" a "Accettata"
        RichiestaCondivisione r = new RichiestaCondivisioneDAO().findById(idRichiesta);
        int id = richiestaToPrenotazione(r);

        //inviare mail di conferma all'utente
        String dest = r.getCliente().getEmail(); //cliente utente id 2 gc.pps
        String testo = "pren id" + id + ". Confermata il " + new Date().toString() + "la richiesta numero: " + idRichiesta + ".";
        MailHelper.getInstance().send(dest, "CLI Richiesta sharing accettata!", testo);


        return res;
    }

    public static boolean rifiutaRichiesta(int idRichiesta, boolean errorePosti) throws IOException {
        boolean res = new RichiestaCondivisioneDAO().rifiutaRichiesta(idRichiesta); //setta stato da "Attesa" a "Rifiutata"
        RichiestaCondivisione r = new RichiestaCondivisioneDAO().findById(idRichiesta);

        if ( !errorePosti ){
            //inviare mail di conferma all'utente
            String dest = r.getCliente().getEmail(); //cliente utente id 2 gc.pps
            String testo = "purtroppo la richiesta numero: " + idRichiesta + " del " + r.getData() + "è stata rifiutata";
            MailHelper.getInstance().send(dest, "CLI Richiesta sharing rifiutata!", testo);
        }


        return res;
    }

    public int numeroPostiDisponibili(Date dataInizio, Date dataFine, int idMezzo) throws IOException {
        IRichiestaCondivisioneDAO rDAO = new RichiestaCondivisioneDAO();
        return rDAO.numeroPostiDisponibili(dataInizio, dataFine, idMezzo);
    }
}

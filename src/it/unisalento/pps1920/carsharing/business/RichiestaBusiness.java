package it.unisalento.pps1920.carsharing.business;

import it.unisalento.pps1920.carsharing.dao.mysql.PrenotazioneDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.RichiestaCondivisioneDAO;
import it.unisalento.pps1920.carsharing.model.Accessorio;
import it.unisalento.pps1920.carsharing.model.RichiestaCondivisione;
import it.unisalento.pps1920.carsharing.util.MailHelper;

import java.io.IOException;
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
        int id = new PrenotazioneDAO().richiestaToPrenotazione(r);

        //inviare mail di conferma all'utente
        String dest = r.getCliente().getEmail(); //cliente utente id 2 gc.pps
        String testo = "pren id" + id + ". Confermata il " + new Date().toString() + "la richiesta numero: " + idRichiesta + ".";
        MailHelper.getInstance().send(dest, "CLI Richiesta sharing accettata!", testo);


        return res;
    }

    public static boolean rifiutaRichiesta(int idRichiesta) throws IOException {
        boolean res = new RichiestaCondivisioneDAO().rifiutaRichiesta(idRichiesta); //setta stato da "Attesa" a "Rifiutata"
        RichiestaCondivisione r = new RichiestaCondivisioneDAO().findById(idRichiesta);

        //inviare mail di conferma all'utente
        String dest = r.getCliente().getEmail(); //cliente utente id 2 gc.pps
        String testo = "purtroppo la richiesta numero: " + idRichiesta + " del " + r.getData() + "è stata rifiutata";
        MailHelper.getInstance().send(dest, "CLI Richiesta sharing rifiutata!", testo);


        return res;
    }
}

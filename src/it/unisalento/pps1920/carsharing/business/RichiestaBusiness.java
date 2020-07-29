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
        int numClientiSharing = pDAO.getNumeroClientiSharing(r.getProposta().getId()) + 1;

        float costoBaseModello = pren.getMezzo().getModello().getTariffaBase() / numClientiSharing;

        costo = costoBaseModello;


        for (int i=0; i<acc.size(); i++){
            costo = costo + acc.get(i).getCosto();
        }

        new PrenotazioneDAO().correggiCosto(r.getProposta().getId(), true);

        new PrenotazioneDAO().salvaPrenotazione(pren, acc, costo, false);

        new PropostaCondivisioneDAO().updatePostiProposta(prop.getId(), pren.getNumPostiOccupati());

        int [] a = pDAO.prenotazioniFromIdProposta(pren.getIdPropostaCondivisione());

        for (int i = 0; i < a.length; i++){
            Prenotazione p = pDAO.findById(a[i]);
            String dest = p.getCliente().getEmail();
            String testo = "AGGIUNTO NUOVO CLIENTE ALLO SHARING \n";
            testo = testo + "Allo sharing del "+ DateUtil.stringFromDate(p.getDataInizio()) + "\n";
            testo = testo + "fino al : "+ DateUtil.stringFromDate(p.getDataFine()) + "\n";
            testo = testo + "Da : "+p.getPartenza().getNome() + "\n";
            testo = testo + "A : "+p.getArrivo().getNome() + "\n";
            testo = testo + "Con : "+r.getProposta().getMezzo().getModello().getNome() + " targato: " + r.getProposta().getMezzo().getTarga() + "\n";
            testo = testo + "si sono aggiunte: "+r.getNumPostiRichiesti()+" persone per un totale di "+numClientiSharing+" persone\n A presto!";
            MailHelper.getInstance().send(dest, "CLI News sullo sharing!", testo);
        }
        return pren.getId();
    }

    public static boolean inviaRichiestaCondivisione(RichiestaCondivisione r, List<Accessorio> a){
        boolean res = new RichiestaCondivisioneDAO().salvaRichiesta(r, a);
        if (res){
            String dest = r.getCliente().getEmail();
            String testo = "RICHIESTA DI SHARING INVIATA! \n";
            testo = testo + "La richiesta con codice richiesta: "+ r.getId() + "\n";
            testo = testo + "effettuata il: "+DateUtil.stringFromDate(r.getData()) + "\n";
            testo = testo + "con nizio : "+DateUtil.stringFromDate(r.getProposta().getDataInizio()) + "\n";
            testo = testo + "Fine : "+DateUtil.stringFromDate(r.getProposta().getDataFine()) + "\n";
            testo = testo + "Da : "+r.getProposta().getPartenza().getNome() + "\n";
            testo = testo + "A : "+r.getProposta().getArrivo().getNome() + "\n";
            testo = testo + "Numero posti prenotati : "+r.getNumPostiRichiesti() + "\n";
            testo = testo + "Con : "+r.getProposta().getMezzo().getModello().getNome() + " targato: " + r.getProposta().getMezzo().getTarga() + "\n";
            testo = testo + "è stata fatta con successo \n A presto!";
            MailHelper.getInstance().send(dest, "CLI Richiesta sharing in attesa!", testo);
        }
        return res;
    }

    public static boolean accettaRichiesta(int idRichiesta) throws IOException {
        boolean res = new RichiestaCondivisioneDAO().accettaRichiesta(idRichiesta);
        RichiestaCondivisione r = new RichiestaCondivisioneDAO().findById(idRichiesta);
        int id = richiestaToPrenotazione(r);

        //inviare mail di conferma all'utente
        String dest = r.getCliente().getEmail();
        String testo = "RICHIESTA DI SHARING ACCETTATA! \n";
        testo = testo + "La richiesta con codice richiesta: "+ r.getId() + "\n";
        testo = testo + "effettuata il: "+DateUtil.stringFromDate(r.getData()) + "\n";
        testo = testo + "con nizio : "+DateUtil.stringFromDate(r.getProposta().getDataInizio()) + "\n";
        testo = testo + "Fine : "+DateUtil.stringFromDate(r.getProposta().getDataFine()) + "\n";
        testo = testo + "Da : "+r.getProposta().getPartenza().getNome() + "\n";
        testo = testo + "A : "+r.getProposta().getArrivo().getNome() + "\n";
        testo = testo + "Numero posti prenotati : "+r.getNumPostiRichiesti() + "\n";
        testo = testo + "Con : "+r.getProposta().getMezzo().getModello().getNome() + " targato: " + r.getProposta().getMezzo().getTarga() + "\n";
        testo = testo + "è stata accettata \n A presto!";
        MailHelper.getInstance().send(dest, "CLI Richiesta sharing accettata!", testo);


        return res;
    }

    public static boolean rifiutaRichiesta(int idRichiesta, boolean errorePosti) throws IOException {
        boolean res = new RichiestaCondivisioneDAO().rifiutaRichiesta(idRichiesta); //setta stato da "Attesa" a "Rifiutata"
        RichiestaCondivisione r = new RichiestaCondivisioneDAO().findById(idRichiesta);

        if ( !errorePosti ){
            //inviare mail all'utente
            String dest = r.getCliente().getEmail();
            String testo = "RICHIESTA DI SHARING RIFIUTATA! \n";
            testo = testo + "La richiesta con codice richiesta: "+ r.getId() + "\n";
            testo = testo + "effettuata il: "+DateUtil.stringFromDate(r.getData()) + "\n";
            testo = testo + "con inizio : "+DateUtil.stringFromDate(r.getProposta().getDataInizio()) + "\n";
            testo = testo + "Fine : "+DateUtil.stringFromDate(r.getProposta().getDataFine()) + "\n";
            testo = testo + "Da : "+r.getProposta().getPartenza().getNome() + "\n";
            testo = testo + "A : "+r.getProposta().getArrivo().getNome() + "\n";
            testo = testo + "Numero posti prenotati : "+r.getNumPostiRichiesti() + "\n";
            testo = testo + "Con : "+r.getProposta().getMezzo().getModello().getNome() + " targato: " + r.getProposta().getMezzo().getTarga() + "\n";
            testo = testo + "è stata rifiutata \n Arrivederci!";
            MailHelper.getInstance().send(dest, "CLI Richiesta sharing rifiutata!", testo);
        }


        return res;
    }

    public int numeroPostiDisponibili(Date dataInizio, Date dataFine, int idMezzo) throws IOException {
        IRichiestaCondivisioneDAO rDAO = new RichiestaCondivisioneDAO();
        return rDAO.numeroPostiDisponibili(dataInizio, dataFine, idMezzo);
    }
}

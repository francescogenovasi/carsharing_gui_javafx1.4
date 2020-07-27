package it.unisalento.pps1920.carsharing.business;

import it.unisalento.pps1920.carsharing.dao.interfaces.IMezzoDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IPrenotazioneDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IPropostaCondivisioneDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.MezzoDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.PrenotazioneDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.PropostaCondivisioneDAO;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.DateUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class RicercaBusiness {

    private static RicercaBusiness instance;

    public static synchronized RicercaBusiness getInstance() {
        if(instance == null){
            instance = new RicercaBusiness();
        }
        return instance;
    }

    private RicercaBusiness(){}

    public ArrayList<Prenotazione> cercaConFiltri(Stazione partenza, Stazione arrivo, Localita localita, int numPosti, Date inizio, Date fine, Modello modello, String dimensione, String motorizzazione, String tipologia) throws IOException {
        IPrenotazioneDAO pDAO = new PrenotazioneDAO();
        return pDAO.ricercaConFiltri(partenza, arrivo, localita, numPosti, inizio, fine, modello, dimensione, motorizzazione, tipologia);
    }

    public ArrayList<Mezzo> mezziPrenotabili(String dim, String tipologia, Date dataInizio, Date dataFine) throws IOException {
        IMezzoDAO mDAO = new MezzoDAO();
        return mDAO.findAllPrenotabili(dim, tipologia, dataInizio, dataFine);
    }

    public PropostaCondivisione ricercaProposta(int id) throws IOException {
        IPropostaCondivisioneDAO pc = new PropostaCondivisioneDAO();
        return pc.findById(id);
    }

}

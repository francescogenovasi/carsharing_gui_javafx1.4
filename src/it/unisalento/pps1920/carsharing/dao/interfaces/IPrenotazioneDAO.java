package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface IPrenotazioneDAO  extends IBaseDAO<Prenotazione> {
    /*
    creare altri metodi diversi da findbyid e find all che possono essere utili per la traccia
    per esempio: dammi tutte le prenotazioni di un cliente
     */
    public ArrayList<Prenotazione> prenotazioniCliente(int id) throws IOException;

    public void salvaPrenotazione(Prenotazione p, List<Accessorio> a, float costo, boolean nuovoInserimento) throws IOException;

    //public ArrayList<Prenotazione> ricercaConFiltri (Stazione partenza, Stazione arrivo, Localita localita, int numPosti, Date inizio, Date fine, Modello modello, String dimensione, String motorizzazione, String tipologia) throws IOException;

    public ArrayList<Prenotazione> ricercaPerCliente(int id) throws IOException;

    public int getIdPrenFromIdPropCon(int idProp);

    public int getNumeroClientiSharing(int idProposta);

    public void correggiCosto(int idProposta, boolean aggiunta) throws IOException;

    public int[] prenotazioniFromDateEIdMezzo(int idMezzo, String dataInizio, String dataFine);

    public boolean setPrenotazioneInvalida(int idPrenotazione);

    public ArrayList<Prenotazione> getPrenotazioniPerOperatore(Utente op) throws IOException;

    public boolean modificaPrenotazione(Date inizio, Date fine, int posti, Stazione arrivo, Stazione partenza, Localita localita, Prenotazione oldpren);
    public int[] prenotazioniFromIdProposta(int idProposta);
    public boolean modificaAltrePrenotazioni(Date inizio, Date fine, int posti, Stazione arrivo, Stazione partenza, Localita localita, Prenotazione oldPren);
    public ArrayList<Prenotazione> findAllPrenotazioniPerAdmin() throws IOException;


    }

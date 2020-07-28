package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.Localita;
import it.unisalento.pps1920.carsharing.model.MezzoDaPreparare;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.model.Stazione;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public interface IMezzoDaPreparareDAO extends IBaseDAO<MezzoDaPreparare> {

    public ArrayList<MezzoDaPreparare> getMezziPronti() throws IOException;
    public boolean mezzoPartito(int id);
    public ArrayList<MezzoDaPreparare> getMezziDaPreparare() throws IOException;
    public boolean mezzoPronto(int id);
    public boolean updateNumeroPosti(Prenotazione p);
    public boolean eliminaRecord(Prenotazione p);
    public boolean modificaTabella(Date inizio, Date fine, int posti, Stazione arrivo, Stazione partenza, Localita localita, Prenotazione oldPren);
}

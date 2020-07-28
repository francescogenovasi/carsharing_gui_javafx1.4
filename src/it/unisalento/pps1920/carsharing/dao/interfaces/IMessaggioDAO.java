package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.Messaggio;
import it.unisalento.pps1920.carsharing.model.Utente;

import java.util.ArrayList;

public interface IMessaggioDAO extends IBaseDAO<Messaggio> {
    public boolean ScriviMessaggio(Messaggio m);
    public boolean setLetto(Messaggio m);
    public ArrayList<Messaggio> getMessaggiDaLeggere(Utente u);
}

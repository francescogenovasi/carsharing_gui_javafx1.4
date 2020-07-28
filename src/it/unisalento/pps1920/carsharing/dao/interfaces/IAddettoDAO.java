package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.Addetto;
import it.unisalento.pps1920.carsharing.model.Utente;

import java.util.ArrayList;

public interface IAddettoDAO extends IBaseDAO<Addetto> {
    public boolean salvaRegistrazioneAddetto(Addetto a);
    public boolean checkAddetto(int id);
    public ArrayList<Utente> findAllFormaUtente();
    public Utente findIdAddetto(int id);
}

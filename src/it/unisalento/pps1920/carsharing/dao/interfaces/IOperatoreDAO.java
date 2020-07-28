package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.Operatore;
import it.unisalento.pps1920.carsharing.model.Utente;

import java.util.ArrayList;

public interface IOperatoreDAO extends IBaseDAO<Operatore> {
    public boolean checkOperatore(int id);
    public boolean salvaRegistrazioneOperatore(Operatore o);
    public ArrayList<Utente> findAllFormaUtente();
    public Utente findIdOperatore(int id);
}

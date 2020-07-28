package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.Amministratore;
import it.unisalento.pps1920.carsharing.model.Cliente;
import it.unisalento.pps1920.carsharing.model.Utente;

import java.util.ArrayList;

public interface IAmministratoreDAO extends IBaseDAO<Amministratore>{
    public boolean checkAmministratore(int id);
    public ArrayList<Utente> findAllFormaUtente();
    public Utente findIdAmministratore(int id);
}

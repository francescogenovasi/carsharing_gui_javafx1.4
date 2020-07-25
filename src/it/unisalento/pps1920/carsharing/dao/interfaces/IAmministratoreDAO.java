package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.Amministratore;
import it.unisalento.pps1920.carsharing.model.Cliente;

public interface IAmministratoreDAO extends IBaseDAO<Amministratore>{
    public boolean checkAmministratore(int id);
}

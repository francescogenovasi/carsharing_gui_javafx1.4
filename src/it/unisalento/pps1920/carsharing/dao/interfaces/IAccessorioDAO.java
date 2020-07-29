package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.Accessorio;

import java.util.ArrayList;

public interface IAccessorioDAO extends IBaseDAO<Accessorio>{
    public int findAccessorioId(String nome);
    public boolean salvaAccessorio(Accessorio a);
    public ArrayList<Accessorio> getAccessoriPrenotazione(int idpren);
    public ArrayList<Accessorio> getAccessoriRichiesta(int idRichiesta);
}

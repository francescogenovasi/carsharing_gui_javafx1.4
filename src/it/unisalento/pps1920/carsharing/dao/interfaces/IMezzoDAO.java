package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.Mezzo;

import java.io.IOException;
import java.util.ArrayList;

public interface IMezzoDAO extends IBaseDAO<Mezzo> {
    public ArrayList<Mezzo> findAllPrenotabili(String dim, int pos) throws IOException;
    public int findMezzoId(String targa);
}

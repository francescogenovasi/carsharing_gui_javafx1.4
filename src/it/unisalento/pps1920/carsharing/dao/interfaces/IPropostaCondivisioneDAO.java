package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.PropostaCondivisione;

import java.io.IOException;
import java.util.ArrayList;

public interface IPropostaCondivisioneDAO extends IBaseDAO<PropostaCondivisione> {
    public void salvaProposta(PropostaCondivisione p);
    public ArrayList<PropostaCondivisione> getProposte() throws IOException;
}

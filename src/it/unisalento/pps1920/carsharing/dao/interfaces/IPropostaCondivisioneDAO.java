package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.Localita;
import it.unisalento.pps1920.carsharing.model.Modello;
import it.unisalento.pps1920.carsharing.model.PropostaCondivisione;
import it.unisalento.pps1920.carsharing.model.Stazione;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public interface IPropostaCondivisioneDAO extends IBaseDAO<PropostaCondivisione> {
    public void salvaProposta(PropostaCondivisione p);
    public ArrayList<PropostaCondivisione> getProposte() throws IOException;
    public boolean setPropostaInvalida(int idProposta);
    public ArrayList<PropostaCondivisione> ricercaConFiltri (Stazione partenza, Stazione arrivo, Localita localita, int numPosti, Date inizio, Date fine, Modello modello, String dimensione, String motorizzazione, String tipologia) throws IOException;
    public boolean updatePostiProposta(int idProposta, int posti);

}

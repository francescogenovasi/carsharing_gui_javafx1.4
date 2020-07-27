package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.Accessorio;
import it.unisalento.pps1920.carsharing.model.PropostaCondivisione;
import it.unisalento.pps1920.carsharing.model.RichiestaCondivisione;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface IRichiestaCondivisioneDAO  extends IBaseDAO<RichiestaCondivisione> {
    public boolean salvaRichiesta(RichiestaCondivisione r, List<Accessorio> a);
    public boolean accettaRichiesta(int idRichiesta);
    public boolean rifiutaRichiesta(int idRichiesta);
    public int numeroPostiDisponibili(Date dataInizio, Date dataFine, int idMezzo) throws IOException;
    public ArrayList<RichiestaCondivisione> getRichiesteCliente(int idCliente) throws IOException;
}

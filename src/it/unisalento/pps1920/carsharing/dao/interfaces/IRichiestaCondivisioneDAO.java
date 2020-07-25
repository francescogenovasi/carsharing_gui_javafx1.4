package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.Accessorio;
import it.unisalento.pps1920.carsharing.model.PropostaCondivisione;
import it.unisalento.pps1920.carsharing.model.RichiestaCondivisione;

import java.util.List;

public interface IRichiestaCondivisioneDAO  extends IBaseDAO<RichiestaCondivisione> {
    public boolean salvaRichiesta(RichiestaCondivisione r, List<Accessorio> a);
}

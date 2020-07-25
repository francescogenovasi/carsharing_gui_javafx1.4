package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.Addetto;

public interface IAddettoDAO extends IBaseDAO<Addetto> {
    public boolean salvaRegistrazioneAddetto(Addetto a);
    public boolean checkAddetto(int id);
}

package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.Operatore;

public interface IOperatoreDAO extends IBaseDAO<Operatore> {
    public boolean checkOperatore(int id);
    public boolean salvaRegistrazioneOperatore(Operatore o);
}

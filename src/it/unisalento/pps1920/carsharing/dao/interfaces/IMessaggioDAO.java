package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.Messaggio;

public interface IMessaggioDAO extends IBaseDAO<Messaggio> {
    public boolean ScriviMessaggio(Messaggio m);
}

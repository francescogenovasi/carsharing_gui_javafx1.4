package it.unisalento.pps1920.carsharing.business;

import it.unisalento.pps1920.carsharing.dao.mysql.ModelloDAO;
import it.unisalento.pps1920.carsharing.model.Modello;

public class ModelloBusiness {
    private static ModelloBusiness instance;
    public static synchronized ModelloBusiness getInstance(){
        if(instance == null){
            instance = new ModelloBusiness();
        }
        return instance;
    }
    private ModelloBusiness(){}

    public boolean salvaAggiuntaModello(Modello m){
        boolean res = new ModelloDAO().salvaModello(m);
        return res;
    }

    public int findIdModello(String nome){
        return new ModelloDAO().findModelloId(nome);
    }

}

package it.unisalento.pps1920.carsharing.business;

import it.unisalento.pps1920.carsharing.dao.mysql.AccessorioDAO;
import it.unisalento.pps1920.carsharing.model.Accessorio;

public class AccessorioBusiness {
    private static AccessorioBusiness instance;
    public static synchronized AccessorioBusiness getInstance(){
        if(instance == null){
            instance = new AccessorioBusiness();
        }
        return instance;
    }
    private AccessorioBusiness(){}

    public boolean salvaAggiuntaAccessorio(Accessorio a){
        boolean res = new AccessorioDAO().salvaAccessorio(a);
        return res;
    }

    public int findIdAccessorio(String nome){
        return new AccessorioDAO().findAccessorioId(nome);
    }



}

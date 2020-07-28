package it.unisalento.pps1920.carsharing.business;

import it.unisalento.pps1920.carsharing.dao.interfaces.IAddettoDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IAmministratoreDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IOperatoreDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.AddettoDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.AmministratoreDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.OperatoreDAO;
import it.unisalento.pps1920.carsharing.model.Addetto;
import it.unisalento.pps1920.carsharing.model.Amministratore;
import it.unisalento.pps1920.carsharing.model.Operatore;
import it.unisalento.pps1920.carsharing.model.Utente;

import java.io.IOException;
import java.util.ArrayList;

public class MessaggiBusiness {
    private static MessaggiBusiness instance;
    public static synchronized MessaggiBusiness getInstance(){
        if(instance == null){
            instance = new MessaggiBusiness();
        }
        return instance;
    }

    private MessaggiBusiness(){}


    public ArrayList<Utente> getDestinatari(){
        ArrayList<Utente> u = new ArrayList<Utente>();
        ArrayList<Utente> am = new ArrayList<Utente>();
        ArrayList<Utente> ad = new ArrayList<Utente>();
        ArrayList<Utente> op = new ArrayList<Utente>();
        IAmministratoreDAO aDAO = new AmministratoreDAO();
        IOperatoreDAO oDAO = new OperatoreDAO();
        IAddettoDAO adDAO = new AddettoDAO();
        am=aDAO.findAllFormaUtente();
        ad=adDAO.findAllFormaUtente();
        op=oDAO.findAllFormaUtente();

        int i;
        for(i=0; i<am.size(); i++){
            u.add(am.get(i));
        }

        int j;
        for(j=0; j<(ad.size()); j++){
            u.add(ad.get(j));
        }

        int k;
        for(k=0; k<(op.size()); k++){
            u.add(op.get(k));
        }

        return u;
    }


}

package it.unisalento.pps1920.carsharing.business;

import it.unisalento.pps1920.carsharing.dao.interfaces.IAddettoDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IAmministratoreDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IMessaggioDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IOperatoreDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.AddettoDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.AmministratoreDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.MessaggioDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.OperatoreDAO;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.Session;

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

        for(int i=0; i<am.size(); i++){
            u.add(am.get(i));
        }

        for(int j=0; j<(ad.size()); j++){
            u.add(ad.get(j));
        }

        for(int k=0; k<(op.size()); k++){
            u.add(op.get(k));
        }

        return u;
    }

    public boolean scriviMessaggio(Messaggio m){
        IMessaggioDAO meDAO = new MessaggioDAO();
        boolean res= meDAO.ScriviMessaggio(m);
        return res;
    }

    public ArrayList<Messaggio> getMessaggiDaLeggere (Utente destinatario){
        ArrayList<Messaggio> ricevuti = new ArrayList<Messaggio>();
        IMessaggioDAO meDAO = new MessaggioDAO();
        ricevuti=meDAO.getMessaggiDaLeggere(destinatario);
        return ricevuti;
    }

    public boolean setLetto(Messaggio m){
        boolean res;
        IMessaggioDAO meDAO = new MessaggioDAO();
        res=meDAO.setLetto(m);
        return res;
    }

}

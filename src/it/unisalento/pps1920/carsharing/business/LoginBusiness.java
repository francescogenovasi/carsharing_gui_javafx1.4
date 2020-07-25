package it.unisalento.pps1920.carsharing.business;

import it.unisalento.pps1920.carsharing.dao.mysql.UtenteDAO;
import it.unisalento.pps1920.carsharing.model.Utente;

public class LoginBusiness {
    public Utente loginBusiness(String user, String pass){
        UtenteDAO utDAO=new UtenteDAO();
        Utente ut=null;
        ut=utDAO.ricercaLog(user,pass);
        return ut;
    }
}

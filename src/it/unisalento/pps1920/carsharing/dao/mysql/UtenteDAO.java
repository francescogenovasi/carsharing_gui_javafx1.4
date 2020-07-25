package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.IModelloDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IUtenteDAO;
import it.unisalento.pps1920.carsharing.model.Mezzo;
import it.unisalento.pps1920.carsharing.model.Modello;
import it.unisalento.pps1920.carsharing.model.Utente;


import java.util.ArrayList;

public class UtenteDAO implements IUtenteDAO {


    public Utente ricercaLog(String username, String password){
        Utente utd = new Utente();
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT idutente, username, password, email FROM utente WHERE username = '" + username + "' AND password= '" + password + "';");

        if(res.size() == 1){
            String riga[] = res.get(0);
            utd.setId(Integer.parseInt(riga[0]));
            utd.setUsername(riga[1]);
            utd.setEmail(riga[3]);
            return utd;
        }else{
            return null;
        }
    }

    public boolean ricercaEmail(String email){
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM utente WHERE email = '" + email + "' ;");
        if(res.size() == 1){
            System.out.println("ATTENTO EMAIL USATA");
            return false;
        }else{
            System.out.println("EMAIL NON USATA");
            return true;
        }
    }

    public boolean ricercaUsername(String username){
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM utente WHERE username = '" + username + "' ;");
        if(res.size() == 1){
            System.out.println("ATTENTO USER USATO");
            return false;
        }else{
            System.out.println("USER NON USATO");
            return true;
        }
    }

    public boolean salvaRegistrazioneUtente(Utente u){
        //boolean res = DbConnection.getInstance().eseguiAggiornamento("INSERT INTO utente (username, password, email) VALUES ('"+username+"','"+password+"' ,'"+email+"') ;");
        String sql = "INSERT INTO utente (idutente, username, password, email) VALUES (NULL, '" + u.getUsername() + "', '" + u.getPassword() + "', '" + u.getEmail() + "');";
        System.out.println(sql);
        boolean res1 = DbConnection.getInstance().eseguiAggiornamento(sql);
        sql = "SELECT last_insert_id()";
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery(sql);
        u.setId(Integer.parseInt(res.get(0)[0]));
        System.out.println("id utente inserito:" + u.getId());
        return res1;
    }


    @Override
    public Utente findById(int id) {
        Utente u = null;

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM utente WHERE idutente = " + id + ";");

        if(res.size() == 1){
            String riga[] = res.get(0);
            u = new Utente();

            u.setId(Integer.parseInt(riga[0]));
            u.setUsername(riga[1]);
            u.setPassword(riga[2]);
            u.setEmail(riga[3]);

        }
        return u;
    }

    @Override
    public ArrayList<Utente> findAll() {
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM utente;"); //query

        ArrayList<Utente> utenti = new ArrayList<Utente>();//istanziare model con risultati query

        for(String[] riga : res) {
            Utente u = findById(Integer.parseInt(riga[0]));
            utenti.add(u);
        }

        return utenti;
    }
}

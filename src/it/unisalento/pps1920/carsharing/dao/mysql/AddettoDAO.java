package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.IAddettoDAO;
import it.unisalento.pps1920.carsharing.model.Addetto;
import it.unisalento.pps1920.carsharing.model.Cliente;
import it.unisalento.pps1920.carsharing.model.Utente;

import java.util.ArrayList;

public class AddettoDAO implements IAddettoDAO {

    @Override
    public Addetto findById(int id) {
        Addetto a = null;

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT A.utente_idutente, U.username, U.password, U.email FROM addetto AS A INNER JOIN utente as U  ON U.idutente = A.utente_idutente WHERE A.utente_idutente = "+id+";");

        if(res.size()==1) {
            String[] riga = res.get(0);
            a = new Addetto();
            a.setId(Integer.parseInt(riga[0]));
            a.setUsername(riga[1]);
            a.setEmail(riga[3]);
        }
        return a;
    }

    @Override
    public ArrayList<Addetto> findAll() { //potrebbe non funzionare, potrebbe mancare qualche inner join
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM addetto;");

        ArrayList<Addetto> addetti = new ArrayList<Addetto>(); //istanziare model con risultati query

        for(String[] riga : res) {
            Addetto a = findById(Integer.parseInt(riga[0]));
            addetti.add(a);
        }

        return addetti;
    }

    @Override
    public boolean salvaRegistrazioneAddetto(Addetto a){
        String sql = "INSERT INTO addetto (utente_idutente, stazione_idstazione) VALUES (" + a.getId() + ", " + a.getIdstazione() + ");";
        boolean res = DbConnection.getInstance().eseguiAggiornamento(sql);
        return res;
    }

    @Override
    public boolean checkAddetto(int id){
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM addetto WHERE utente_idutente = " + id + ";");
        if (res.size() == 1){
            return true;
        }
        return false;
    }

    @Override
    public Utente findIdAddetto(int id) {
        Utente u = null;
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT A.utente_idutente, U.username, U.password, U.email FROM addetto AS A INNER JOIN utente as U  ON U.idutente = A.utente_idutente WHERE A.utente_idutente = "+id+";");
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
    public ArrayList<Utente> findAllFormaUtente() {
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT A.utente_idutente, U.username, U.password, U.email FROM addetto AS A INNER JOIN utente as U  ON U.idutente = A.utente_idutente ;");

        ArrayList<Utente> ut = new ArrayList<Utente>(); //istanziare model con risultati query

        for(String[] riga : res) {
            Utente u = findIdAddetto(Integer.parseInt(riga[0]));
            ut.add(u);
        }

        return ut;
    }

}

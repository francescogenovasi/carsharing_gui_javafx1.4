package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.IOperatoreDAO;
import it.unisalento.pps1920.carsharing.model.Addetto;
import it.unisalento.pps1920.carsharing.model.Operatore;
import it.unisalento.pps1920.carsharing.model.Utente;

import java.util.ArrayList;

public class OperatoreDAO implements IOperatoreDAO {
    @Override
    public boolean checkOperatore(int id) {
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM operatore WHERE utente_idutente = " + id + ";");
        if (res.size() == 1){
            return true;
        }
        return false;
    }

    @Override
    public Operatore findById(int id) {
        Operatore o = null;

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT O.utente_idutente, U.username, U.password, U.email, O.stazione_idstazione FROM operatore AS O INNER JOIN utente as U  ON U.idutente = O.utente_idutente WHERE O.utente_idutente = "+id+";");

        if(res.size()==1) {
            String[] riga = res.get(0);
            o = new Operatore();
            o.setId(Integer.parseInt(riga[0]));
            o.setPassword(riga[2]);
            o.setUsername(riga[1]);
            o.setEmail(riga[3]);
            o.setIdstazione(Integer.parseInt(riga[4]));
        }
        return o;
    }

    @Override
    public ArrayList<Operatore> findAll() {
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM operatore;");

        ArrayList<Operatore> operatori = new ArrayList<Operatore>();

        for(String[] riga : res) {
            Operatore o = findById(Integer.parseInt(riga[0]));
            operatori.add(o);
        }

        return operatori;
    }

    @Override
    public boolean salvaRegistrazioneOperatore(Operatore o){
        String sql = "INSERT INTO operatore (utente_idutente, stazione_idstazione) VALUES (" + o.getId() + ", " + o.getIdstazione() + ");";
        boolean res = DbConnection.getInstance().eseguiAggiornamento(sql);
        return res;
    }

    @Override
    public Utente findIdOperatore(int id) {
        Utente u = null;
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT O.utente_idutente, U.username, U.password, U.email FROM operatore AS O INNER JOIN utente as U  ON U.idutente = O.utente_idutente WHERE O.utente_idutente = "+id+";");
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
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT O.utente_idutente, U.username, U.password, U.email FROM operatore AS O INNER JOIN utente as U  ON U.idutente = O.utente_idutente ;");

        ArrayList<Utente> ut = new ArrayList<Utente>(); //istanziare model con risultati query

        for(String[] riga : res) {
            Utente u = findIdOperatore(Integer.parseInt(riga[0]));
            ut.add(u);
        }

        return ut;
    }

}

package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.IAmministratoreDAO;
import it.unisalento.pps1920.carsharing.model.Amministratore;
import it.unisalento.pps1920.carsharing.model.Cliente;

import java.util.ArrayList;

public class AmministratoreDAO implements IAmministratoreDAO {
    @Override
    public Amministratore findById(int id) {
        Amministratore a = null;
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT amministratore.utente_idutente, utente.username, utente.password, utente.email FROM amministratore INNER JOIN utente ON utente.idutente = amministratore.utente_idutente WHERE amministratore.utente_idutente = " + id + ";");
        if(res.size() == 1){
            String riga[] = res.get(0);
            a = new Amministratore();

            a.setId(Integer.parseInt(riga[0]));
            a.setUsername(riga[1]);
            a.setPassword(riga[2]);
            a.setEmail(riga[3]);
        }

        return a;
    }

    @Override
    public ArrayList<Amministratore> findAll() {
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT A.utente_idutente, U.username, U.password, U.email FROM cliente AS A INNER JOIN utente as U  ON U.idutente = A.utente_idutente"); //query SELECT C.utente_idutente, U.username, U.password, U.email FROM cliente AS C INNER JOIN utente as U  ON U.idutente = C.utente_idutente

        ArrayList<Amministratore> amministratori = new ArrayList<Amministratore>(); //istanziare model con risultati query

        for(String[] riga : res) {
            Amministratore a = findById(Integer.parseInt(riga[0]));
            amministratori.add(a);
        }

        return amministratori;
    }

    public boolean checkAmministratore(int id){
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM amministratore WHERE utente_idutente = " + id + ";");
        if (res.size() == 1){
            return true;
        }
        return false;
    }
}

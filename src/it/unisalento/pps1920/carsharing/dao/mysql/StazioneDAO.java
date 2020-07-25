package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.IAddettoDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IStazioneDAO;
import it.unisalento.pps1920.carsharing.model.Stazione;

import java.io.IOException;
import java.util.ArrayList;

public class StazioneDAO implements IStazioneDAO {
    @Override
    public Stazione findById(int id) throws IOException {
        Stazione s = null;

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT S.idstazione, S.nome, S.latitudine, S.longitudine, S.addetto_utente_idutente FROM stazione AS S INNER JOIN addetto AS A ON S.addetto_utente_idutente = A.utente_idutente WHERE S.idstazione = "+id+";");

        if(res.size() == 1){
            String riga[] = res.get(0);
            s = new Stazione();

            s.setId(Integer.parseInt(riga[0]));
            s.setNome(riga[1]);
            s.setLatitudine(Double.parseDouble(riga[2]));
            s.setLongitudine(Double.parseDouble(riga[3]));

            IAddettoDAO aDao = new AddettoDAO();
            s.setAddetto(aDao.findById(Integer.parseInt(riga[4])));
        }
        return s;
    }

    @Override
    public ArrayList<Stazione> findAll() throws IOException { //potrebbe non funzionare, potrebbe mancare qualche inner join
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM stazione;"); //query

        ArrayList<Stazione> stazioni = new ArrayList<Stazione>(); //istanziare model con risultati query

        for(String[] riga : res) {
            Stazione s = findById(Integer.parseInt(riga[0]));
            stazioni.add(s);
        }

        return stazioni;
    }
}

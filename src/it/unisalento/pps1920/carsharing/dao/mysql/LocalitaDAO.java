package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.ILocalitaDAO;
import it.unisalento.pps1920.carsharing.model.Localita;

import java.util.ArrayList;

public class LocalitaDAO implements ILocalitaDAO {
    @Override
    public Localita findById(int id) {
        Localita loc = null;
        ArrayList<String[]> ris = DbConnection.getInstance().eseguiQuery("SELECT * FROM localita WHERE idlocalita=" + id + ";");
        if (ris.size() == 1){
            String[] riga = ris.get(0);
            loc = new Localita();
            loc.setId(Integer.parseInt(riga[0]));
            loc.setCitta(riga[1]);
            loc.setLatitudine(Double.parseDouble(riga[2]));
            loc.setLongitudine(Double.parseDouble(riga[3]));
        } else {
            //LOCALITA NON TROVATA
            loc = null;
        }
        return loc;
    }

    @Override
    public ArrayList<Localita> findAll() {
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM localita;");
        ArrayList<Localita> locs = new ArrayList<Localita>();
        for(String[] riga : res) {
            /*Localita l = new Localita();
            l.setId(Integer.parseInt(riga[0]));
            l.setCitta(riga[1]);
            l.setLatitudine(Double.parseDouble(riga[2]));
            l.setLongitudine(Double.parseDouble(riga[3]));*/

            Localita l = findById(Integer.parseInt(riga[0]));
            locs.add(l);
        }
        return locs;
    }
}

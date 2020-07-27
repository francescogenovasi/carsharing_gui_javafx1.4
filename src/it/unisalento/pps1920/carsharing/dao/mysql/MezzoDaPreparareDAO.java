package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.IMezzoDaPreparareDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IModelloDAO;
import it.unisalento.pps1920.carsharing.model.Mezzo;
import it.unisalento.pps1920.carsharing.model.MezzoDaPreparare;
import it.unisalento.pps1920.carsharing.model.Modello;
import it.unisalento.pps1920.carsharing.util.DateUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class MezzoDaPreparareDAO implements IMezzoDaPreparareDAO {
    @Override
    public MezzoDaPreparare findById(int id) throws IOException {
        MezzoDaPreparare m = null;
        MezzoDAO mezz=new MezzoDAO();

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM mezzi_da_preparare WHERE idmezzi_da_preparare = " + id + ";");

        if(res.size() == 1){
            String riga[] = res.get(0);
            m = new MezzoDaPreparare(0,null,null,0,null,null);
            m.setId(Integer.parseInt(riga[0]));
            m.setMezzo(mezz.findById(Integer.parseInt(riga[1])));
            m.setDataInizio(DateUtil.dateTimeFromString(riga[2]));
            m.setDataFine(DateUtil.dateTimeFromString(riga[3]));
            m.setPostiOccupati(Integer.parseInt(riga[4]));
            m.setStato(riga[5]);

        }
        return m;
    }
    @Override
    public ArrayList<MezzoDaPreparare> findAll() throws IOException {
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM mezzi_da_preparare;"); //query

        ArrayList<MezzoDaPreparare> mezzi = new ArrayList<MezzoDaPreparare>();//istanziare model con risultati query

        for(String[] riga : res) {
            MezzoDaPreparare l = findById(Integer.parseInt(riga[0]));
            mezzi.add(l);
        }

        return mezzi;
    }

    @Override
    public ArrayList<MezzoDaPreparare> getMezziPronti() throws IOException {
        ArrayList<MezzoDaPreparare> mezzipronti = new ArrayList<MezzoDaPreparare>();

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM mezzi_da_preparare WHERE `stato` = 'Pronto' ;");

        for (String[] riga : res){
            MezzoDaPreparare m = findById(Integer.parseInt(riga[0]));
            mezzipronti.add(m);
        }
        return mezzipronti;
    }

}

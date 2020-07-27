package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.IMezzoDaPreparareDAO;
import it.unisalento.pps1920.carsharing.model.MezzoDaPreparare;
import it.unisalento.pps1920.carsharing.util.DateUtil;

import java.io.IOException;
import java.util.ArrayList;

public class MezzoDaPreparareDAO implements IMezzoDaPreparareDAO {
    @Override
    public MezzoDaPreparare findById(int id) throws IOException {
        MezzoDaPreparare m = null;
        MezzoDAO mezz=new MezzoDAO();

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM mezzi_da_preparare WHERE idmezzi_da_preparare = " + id + ";");

        if(res.size() == 1){
            String riga[] = res.get(0);
            m = new MezzoDaPreparare(0,null,null,0,null,null,null);
            m.setId(Integer.parseInt(riga[0]));
            m.setMezzo(mezz.findById(Integer.parseInt(riga[1])));
            m.setDataInizio(DateUtil.dateTimeFromString(riga[2]));
            m.setDataFine(DateUtil.dateTimeFromString(riga[3]));
            m.setPostiOccupati(Integer.parseInt(riga[4]));
            m.setStatoAddetto(riga[5]);
            m.setStatoOperatore(riga[6]);

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

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM mezzi_da_preparare WHERE `stato_addetto` = 'Pronto' AND `stato_operatore` = 'Non partito' ;");

        for (String[] riga : res){
            MezzoDaPreparare m = findById(Integer.parseInt(riga[0]));
            mezzipronti.add(m);
        }
        return mezzipronti;
    }

    @Override
    public ArrayList<MezzoDaPreparare> getMezziDaPreparare() throws IOException {
        ArrayList<MezzoDaPreparare> mezziDaPreparare = new ArrayList<MezzoDaPreparare>();

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM mezzi_da_preparare WHERE `stato_addetto` = 'Non pronto' AND `stato_operatore` = 'Non partito' ;");

        for (String[] riga : res){
            MezzoDaPreparare m = findById(Integer.parseInt(riga[0]));
            mezziDaPreparare.add(m);
        }
        return mezziDaPreparare;
    }

    public boolean mezzoPartito(int id){
        String sql="UPDATE mezzi_da_preparare SET `stato_operatore` = 'Partito' WHERE idmezzi_da_preparare="+id+";";
        boolean res =DbConnection.getInstance().eseguiAggiornamento(sql);
        return res;
    }

}

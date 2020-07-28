package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.IMezzoDaPreparareDAO;
import it.unisalento.pps1920.carsharing.model.Localita;
import it.unisalento.pps1920.carsharing.model.MezzoDaPreparare;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.model.Stazione;
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

    public boolean mezzoPronto(int id){
        String sql="UPDATE mezzi_da_preparare SET `stato_addetto` = 'Pronto' WHERE idmezzi_da_preparare="+id+";";
        boolean res =DbConnection.getInstance().eseguiAggiornamento(sql);
        return res;
    }

    @Override
    public boolean updateNumeroPosti(Prenotazione p){
        //decrementa il numero di posti dalla tabella mezzi_da_preparare
        String strDataInizio = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(p.getDataInizio()));
        String strDataFine = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(p.getDataFine()));
        System.out.println("SELECT * FROM mezzi_da_preparare WHERE mezzo_idmezzo = " + p.getMezzo().getId() + " AND dataInizio = '" + DateUtil.fromRomeToLondon(strDataInizio) + "' AND dataFine = '" + DateUtil.fromRomeToLondon(strDataFine) +"' AND stato_addetto = 'Non Pronto' ;");
        ArrayList<String[]> res2 = DbConnection.getInstance().eseguiQuery("SELECT * FROM mezzi_da_preparare WHERE mezzo_idmezzo = " + p.getMezzo().getId() + " AND dataInizio = '" + DateUtil.fromRomeToLondon(strDataInizio) + "' AND dataFine = '" + DateUtil.fromRomeToLondon(strDataFine) +"' AND stato_addetto = 'Non Pronto' ;");
        if(res2.size() == 1){
            String[] riga = res2.get(0);
            int vecchiPostiOccupati = Integer.parseInt(riga[4]);
            int nuoviPostiOccupati = vecchiPostiOccupati - p.getNumPostiOccupati();
            DbConnection.getInstance().eseguiAggiornamento("UPDATE mezzi_da_preparare SET posti_occupati = "+ nuoviPostiOccupati +" WHERE idmezzi_da_preparare = " + Integer.parseInt(riga[0]) + ";");

        }
        return true;
    }

    @Override
    public boolean eliminaRecord(Prenotazione p){
        String strDataInizio = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(p.getDataInizio()));
        String strDataFine = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(p.getDataFine()));
        System.out.println("SELECT * FROM mezzi_da_preparare WHERE mezzo_idmezzo = " + p.getMezzo().getId() + " AND dataInizio = '" + DateUtil.fromRomeToLondon(strDataInizio) + "' AND dataFine = '" + DateUtil.fromRomeToLondon(strDataFine) +"' AND stato_addetto = 'Non Pronto' ;");
        ArrayList<String[]> res2 = DbConnection.getInstance().eseguiQuery("SELECT * FROM mezzi_da_preparare WHERE mezzo_idmezzo = " + p.getMezzo().getId() + " AND dataInizio = '" + DateUtil.fromRomeToLondon(strDataInizio) + "' AND dataFine = '" + DateUtil.fromRomeToLondon(strDataFine) +"' AND stato_addetto = 'Non Pronto' ;");
        if(res2.size() == 1){
            String[] riga = res2.get(0);
            DbConnection.getInstance().eseguiAggiornamento("DELETE FROM mezzi_da_preparare WHERE idmezzi_da_preparare = " + Integer.parseInt(riga[0]) + ";");

        }
        return true;
    }

    @Override
    public boolean modificaTabella(Date inizio, Date fine, int posti, Stazione arrivo, Stazione partenza, Localita localita, Prenotazione oldPren){
        //modificare le altre tabelle
        if(posti != oldPren.getNumPostiOccupati()){
            int numPostiAggiornati = 0;
            String strDataInizioVecchia = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(oldPren.getDataInizio()));
            String strDataFineVecchia = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(oldPren.getDataFine()));
            ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT posti_occupati FROM mezzi_da_preparare WHERE mezzo_idmezzo = " + oldPren.getMezzo().getId() + " AND dataInizio = '" + DateUtil.fromRomeToLondon(strDataInizioVecchia) + "' AND dataFine = '" + DateUtil.fromRomeToLondon(strDataFineVecchia) +"' ;");
            if(res.size() == 1){
                String[] riga = res.get(0);
                numPostiAggiornati = Integer.parseInt(riga[0])-oldPren.getNumPostiOccupati()+posti;
            }
            System.out.println("UPDATE mezzi_da_preparare SET posti_occupati="+numPostiAggiornati+" WHERE mezzo_idmezzo="+oldPren.getMezzo().getId()+" AND dataInizio='"+DateUtil.fromRomeToLondon(strDataInizioVecchia)+"' AND dataFine ='"+DateUtil.fromRomeToLondon(strDataFineVecchia)+"';");
            DbConnection.getInstance().eseguiAggiornamento("UPDATE mezzi_da_preparare SET posti_occupati="+numPostiAggiornati+" WHERE mezzo_idmezzo="+oldPren.getMezzo().getId()+" AND dataInizio='"+DateUtil.fromRomeToLondon(strDataInizioVecchia)+"' AND dataFine ='"+DateUtil.fromRomeToLondon(strDataFineVecchia)+"';");
        }
        if (inizio != null && fine==null){
            String strDataInizio = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(inizio));
            String strDataInizioVecchia = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(oldPren.getDataInizio()));
            String strDataFineVecchia = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(oldPren.getDataFine()));
            DbConnection.getInstance().eseguiAggiornamento("UPDATE mezzi_da_preparare SET dataInizio='"+strDataInizio+"' WHERE mezzo_idmezzo="+oldPren.getMezzo().getId()+" AND dataInizio='"+DateUtil.fromRomeToLondon(strDataInizioVecchia)+"' AND dataFine ='"+DateUtil.fromRomeToLondon(strDataFineVecchia)+"';");
        }
        if ((fine != null) && (inizio==null)){
            String strDataFine = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(fine));
            String strDataInizioVecchia = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(oldPren.getDataInizio()));
            String strDataFineVecchia = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(oldPren.getDataFine()));
            DbConnection.getInstance().eseguiAggiornamento("UPDATE mezzi_da_preparare SET dataFine='"+strDataFine+"' WHERE mezzo_idmezzo="+oldPren.getMezzo().getId()+" AND dataInizio='"+DateUtil.fromRomeToLondon(strDataInizioVecchia)+"' AND dataFine ='"+DateUtil.fromRomeToLondon(strDataFineVecchia)+"';");
        }
        if ((fine != null) && (inizio!=null)){
            String strDataInizio = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(inizio));
            String strDataFine = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(fine));
            String strDataInizioVecchia = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(oldPren.getDataInizio()));
            String strDataFineVecchia = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(oldPren.getDataFine()));
            DbConnection.getInstance().eseguiAggiornamento("UPDATE mezzi_da_preparare SET dataFine='"+strDataFine+"', dataInizio='"+strDataInizio+"' WHERE mezzo_idmezzo="+oldPren.getMezzo().getId()+" AND dataInizio='"+DateUtil.fromRomeToLondon(strDataInizioVecchia)+"' AND dataFine ='"+DateUtil.fromRomeToLondon(strDataFineVecchia)+"';");
        }
        return true;
    }

}

package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.IMezzoDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IMezzoDaPreparareDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IModelloDAO;
import it.unisalento.pps1920.carsharing.model.Mezzo;
import it.unisalento.pps1920.carsharing.model.MezzoDaPreparare;
import it.unisalento.pps1920.carsharing.model.Modello;
import it.unisalento.pps1920.carsharing.util.DateUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class MezzoDAO implements IMezzoDAO {

    @Override
        public Mezzo findById(int id) throws IOException {
            Mezzo m = null;
            IModelloDAO mDAO = new ModelloDAO();

            ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM mezzo WHERE idmezzo = " + id + ";");

            if(res.size() == 1){
                String riga[] = res.get(0);
                m = new Mezzo();
                m.setId(Integer.parseInt(riga[0]));
                m.setTarga(riga[1]);
                Modello modello= mDAO.findById(Integer.parseInt(riga[2]));
                m.setModello(modello);
                m.setMotorizzazione(riga[3]);
                m.setOfferta(riga[4]);
            }
            return m;
        }

    @Override
    public ArrayList<Mezzo> findAll() throws IOException {
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM mezzo;"); //query

        ArrayList<Mezzo> mezzi = new ArrayList<Mezzo>();//istanziare model con risultati query

        for(String[] riga : res) {
            Mezzo l = findById(Integer.parseInt(riga[0]));
            mezzi.add(l);
        }

        return mezzi;
    }

    @Override
    public ArrayList<Mezzo> findAllPrenotabili(String dim, String tipologia, Date dataInizio, Date dataFine) throws IOException {//restituisce i modelli che possono essere prenotati
        String q;

        if (dim.equals(null) || dim.equals("-")){
            q = "SELECT * FROM mezzo INNER JOIN modello ON mezzo.modello_idmodello = modello.idmodello WHERE modello.tipologia = '"+ tipologia +"';";
        } else {
            q = "SELECT * FROM mezzo INNER JOIN modello ON mezzo.modello_idmodello = modello.idmodello WHERE modello.tipologia = '"+ tipologia + "' AND modello.dimensione = '" + dim +"';";
        }
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery(q);

        ArrayList<Mezzo> mezzi = new ArrayList<Mezzo>();

        for(String[] riga : res) {
            Mezzo l = findById(Integer.parseInt(riga[0]));
            mezzi.add(l);
        }

        ArrayList<MezzoDaPreparare> mezziPrenotati = new MezzoDaPreparareDAO().findAll();
        for (int i = 0; i < mezziPrenotati.size(); i++){
            for (int j = 0; j < mezzi.size(); j++){
                if (mezziPrenotati.get(i).getMezzo().getId() == mezzi.get(j).getId()){
                    if (dataInizio.compareTo(mezziPrenotati.get(i).getDataInizio()) >= 0 && dataInizio.compareTo(mezziPrenotati.get(i).getDataFine()) <= 0){
                        mezzi.remove(j);
                    } else {
                        if (dataFine.compareTo(mezziPrenotati.get(i).getDataInizio()) >= 0 && dataFine.compareTo(mezziPrenotati.get(i).getDataFine()) <= 0){
                            mezzi.remove(j);
                        } else {
                            if ((dataInizio.compareTo(mezziPrenotati.get(i).getDataInizio()) <= 0 && dataFine.compareTo(mezziPrenotati.get(i).getDataInizio()) <= 0) || dataInizio.compareTo(mezziPrenotati.get(i).getDataFine()) >= 0 && dataFine.compareTo(mezziPrenotati.get(i).getDataFine()) >= 0){
                                //prenotabile in quell'arco di tempo
                            } else {
                                mezzi.remove(j);
                            }
                        }
                    }
                }
            }
        }

        return mezzi;
    }

    @Override
    public ArrayList<Mezzo> getMezziOfferta() throws IOException {

        String sql="SELECT idmezzo FROM mezzo WHERE offerta='Si';";
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery(sql);
        ArrayList<Mezzo> mezzi = new ArrayList<Mezzo>();

        for(String[] riga : res) {
            Mezzo l = findById(Integer.parseInt(riga[0]));
            mezzi.add(l);
        }
        return mezzi;
    }

    @Override
    public boolean salvaMezzo(Mezzo m){
        String sql = "INSERT INTO mezzo (targa, modello_idmodello, motorizzazione, offerta) VALUES ('" + m.getTarga() + "', '" + m.getModello().getId() + "' , '" + m.getMotorizzazione() + "', '"+m.getOfferta()+"');";
        System.out.println(sql);
        boolean res = DbConnection.getInstance().eseguiAggiornamento(sql);
        sql = "SELECT last_insert_id()";
        ArrayList<String[]> res1 = DbConnection.getInstance().eseguiQuery(sql);
        m.setId(Integer.parseInt(res1.get(0)[0]));
        System.out.println("id modello inserito:" + m.getId());
        return res;
    }

    @Override
    public int findMezzoId(String targa){
        int id = -1;
        ArrayList<String[]> ris = DbConnection.getInstance().eseguiQuery("SELECT * FROM mezzo WHERE targa='" + targa + "';");
        if (ris.size() == 1){
            String[] riga = ris.get(0);
            id=Integer.parseInt(riga[0]);
        }
        return id;
    }

}

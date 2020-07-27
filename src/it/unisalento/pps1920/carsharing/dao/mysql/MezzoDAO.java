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


    public ArrayList<Mezzo> findAllPrenotabili(String dim, String tipologia, Date dataInizio, Date dataFine) throws IOException {//restituisce i modelli che possono essere prenotati
        String q;

        //vedere tutti i mezzi che hanno dimensione e tipologia richiesti

        if (dim.equals(null) || dim.equals("-")){
            q = "SELECT * FROM mezzo INNER JOIN modello ON mezzo.modello_idmodello = modello.idmodello WHERE modello.tipologia = '"+ tipologia +"';";
        } else {
            q = "SELECT * FROM mezzo INNER JOIN modello ON mezzo.modello_idmodello = modello.idmodello WHERE modello.tipologia = '"+ tipologia + "' AND modello.dimensione = '" + dim +"';";
        }

        /*if (dim.equals("-")){
            q = "SELECT * FROM mezzo WHERE postidisponibili >= " + pos + ";";
        } else {
            q = "SELECT * FROM mezzo INNER JOIN modello ON mezzo.modello_idmodello = modello.idmodello WHERE modello.dimensione = '" + dim + "' AND mezzo.postidisponibili >= " + pos + ";";

        }*/
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery(q); //query

        ArrayList<Mezzo> mezzi = new ArrayList<Mezzo>();//istanziare model con risultati query

        for(String[] riga : res) {
            Mezzo l = findById(Integer.parseInt(riga[0]));
            mezzi.add(l);
        }

        ArrayList<MezzoDaPreparare> mezziPrenotati = new MezzoDaPreparareDAO().findAll();
        for (int i = 0; i < mezziPrenotati.size(); i++){
            for (int j = 0; j < mezzi.size(); j++){
                System.out.println(dataInizio.toString() + " aaaaabbbbbbbbbaaaaa " + mezziPrenotati.get(i).getDataInizio());
                if (mezziPrenotati.get(i).getMezzo().getId() == mezzi.get(j).getId()){
                    if (dataInizio.compareTo(mezziPrenotati.get(i).getDataInizio()) >= 0 && dataInizio.compareTo(mezziPrenotati.get(i).getDataFine()) <= 0){ //se la data di inizio si trova nel range della prenotazione già effettuata allora dai errore
                        //0 ->date uguali; <0 se d minore dell'altra; >0 se l'altra è maggiore di d
                        //System.out.println("erroreeeeeeeeeeeeeeeeeeeeeeeeeeeee data inizio");
                        mezzi.remove(j);
                    } else {
                        //System.out.println("esattooooooooooooooooooooooooooooo data inizio");
                        if (dataFine.compareTo(mezziPrenotati.get(i).getDataInizio()) >= 0 && dataFine.compareTo(mezziPrenotati.get(i).getDataFine()) <= 0){
                            //System.out.println("erroreeeeeeeeeeeeeeeeeeeeeeeeeeeee data fine");
                            mezzi.remove(j);
                        } else {
                            //System.out.println("esattooooooooooooooooooooooooooooo data fine");

                            if ((dataInizio.compareTo(mezziPrenotati.get(i).getDataInizio()) <= 0 && dataFine.compareTo(mezziPrenotati.get(i).getDataInizio()) <= 0) || dataInizio.compareTo(mezziPrenotati.get(i).getDataFine()) >= 0 && dataFine.compareTo(mezziPrenotati.get(i).getDataFine()) >= 0){
                                System.out.println("esattooooooooooooooooooooooooooooo tuttoooooooooooooooooooo");
                                System.out.println(dataInizio.toString() + " aaaaaaaaaa " + mezziPrenotati.get(i).getDataInizio());
                            } else {
                                System.out.println("erroreeeeeeeeeeeeeeeeeeeeeeeeeeeee tuttoooooooooooo");
                                mezzi.remove(j);
                            }
                        }
                    }
                }
            }
        }

        return mezzi;
    }


    public boolean salvaMezzo(Mezzo m){
        String sql = "INSERT INTO mezzo (targa, modello_idmodello, postidisponibili, motorizzazione) VALUES ('" + m.getTarga() + "', '" + m.getModello().getId() + "', '" + m.getPostiDisponibili() + "' , '" + m.getMotorizzazione() + "');";
        System.out.println(sql);
        boolean res = DbConnection.getInstance().eseguiAggiornamento(sql);
        sql = "SELECT last_insert_id()";
        ArrayList<String[]> res1 = DbConnection.getInstance().eseguiQuery(sql);
        m.setId(Integer.parseInt(res1.get(0)[0]));
        System.out.println("id modello inserito:" + m.getId());
        return res;
    }

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

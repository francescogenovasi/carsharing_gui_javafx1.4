package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.IMessaggioDAO;
import it.unisalento.pps1920.carsharing.model.Cliente;
import it.unisalento.pps1920.carsharing.model.Messaggio;
import it.unisalento.pps1920.carsharing.model.Utente;

import java.util.ArrayList;

public class MessaggioDAO implements IMessaggioDAO {

    public Messaggio findById(int id){
        Messaggio m = null;
        MessaggioDAO mezz=new MessaggioDAO();

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM messaggi WHERE idmessaggi = " + id + ";");

        if(res.size() == 1){
            String riga[] = res.get(0);
            m = new Messaggio(0,null,null,null,null);
            UtenteDAO u=new UtenteDAO();
            m.setIdmessaggi(Integer.parseInt(riga[0]));
            m.setTesto(riga[1]);
            m.setStato(riga[2]);
            m.setMittente(u.findById(Integer.parseInt(riga[3])));
            m.setDestinatario(u.findById(Integer.parseInt(riga[3])));
        }
        return m;
    }
    public ArrayList<Messaggio> findAll(){
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM messaggi;"); //query

        ArrayList<Messaggio> mess = new ArrayList<Messaggio>();//istanziare model con risultati query

        for(String[] riga : res) {
            Messaggio l = findById(Integer.parseInt(riga[0]));
            mess.add(l);
        }

        return mess;
    }

    public boolean ScriviMessaggio(Messaggio m){
        String sql="INSERT INTO messaggi (testo, stato, mittente, destinatario) VALUES ('"+m.getTesto()+"', '"+m.getStato()+"','"+m.getMittente().getId()+"', '"+m.getDestinatario().getId()+"');";
        boolean res=DbConnection.getInstance().eseguiAggiornamento(sql);
        return res;
    }
}

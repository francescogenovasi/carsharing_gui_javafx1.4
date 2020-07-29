package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.IMessaggioDAO;
import it.unisalento.pps1920.carsharing.model.Cliente;
import it.unisalento.pps1920.carsharing.model.Messaggio;
import it.unisalento.pps1920.carsharing.model.Utente;
import it.unisalento.pps1920.carsharing.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;

public class MessaggioDAO implements IMessaggioDAO {

    @Override
    public Messaggio findById(int id){
        Messaggio m = null;
        MessaggioDAO mezz=new MessaggioDAO();

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM messaggi WHERE idmessaggi = " + id + ";");

        if(res.size() == 1){
            String riga[] = res.get(0);
            m = new Messaggio(0,null,null,null,null,null);
            UtenteDAO u=new UtenteDAO();
            m.setIdmessaggi(Integer.parseInt(riga[0]));
            m.setTesto(riga[1]);
            m.setStato(riga[2]);
            m.setMittente(u.findById(Integer.parseInt(riga[3])));
            m.setDestinatario(u.findById(Integer.parseInt(riga[4])));
            m.setDatainvio((riga[5]));
        }
        return m;
    }

    @Override
    public ArrayList<Messaggio> findAll(){
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM messaggi;"); //query

        ArrayList<Messaggio> mess = new ArrayList<Messaggio>();//istanziare model con risultati query

        for(String[] riga : res) {
            Messaggio l = findById(Integer.parseInt(riga[0]));
            mess.add(l);
        }

        return mess;
    }

    @Override
    public boolean ScriviMessaggio(Messaggio m){
        String sql="INSERT INTO messaggi (testo, stato, mittente, destinatario, dataInvio) VALUES ('"+m.getTesto()+"', '"+m.getStato()+"','"+m.getMittente().getId()+"', '"+m.getDestinatario().getId()+"', '"+m.getDatainvio()+"');";
        boolean res=DbConnection.getInstance().eseguiAggiornamento(sql);
        return res;
    }

    @Override
    public ArrayList<Messaggio> getMessaggiDaLeggere(Utente u){

        ArrayList<Messaggio> messaggiRicevuti = new ArrayList<Messaggio>();
        String sql="SELECT * FROM messaggi WHERE destinatario = "+u.getId()+" AND stato = 'Non letto';";

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery(sql);

        for (String[] riga : res){
            Messaggio m = findById(Integer.parseInt(riga[0]));
            messaggiRicevuti.add(m);
        }
        return messaggiRicevuti;
    }

    @Override
    public boolean setLetto(Messaggio m){
        String sql="UPDATE `messaggi` SET `stato` = 'Letto' WHERE (`idmessaggi` = '"+m.getIdmessaggi()+"');";
        boolean res=DbConnection.getInstance().eseguiAggiornamento(sql);
        return res;
    }
}

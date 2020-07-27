package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.business.CommonBusiness;
import it.unisalento.pps1920.carsharing.dao.interfaces.*;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.DateUtil;
import it.unisalento.pps1920.carsharing.util.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class PropostaCondivisioneDAO implements IPropostaCondivisioneDAO {

    @Override
    public PropostaCondivisione findById(int id) throws IOException {
        PropostaCondivisione p = null;
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM proposta_condivisione WHERE idproposta_condivisione = " + id + ";");
        if (res.size()==1){
            String[] riga = res.get(0);
            p = new PropostaCondivisione();

            p.setId(Integer.parseInt(riga[0]));
            p.setData(DateUtil.dateTimeFromString(riga[1]));
            IClienteDAO cDao = new ClienteDAO();
            Cliente cliente = cDao.findById(Integer.parseInt(riga[2]));;
            p.setCliente(cliente);
            IMezzoDAO mDao = new MezzoDAO();
            Mezzo mezzo = mDao.findById(Integer.parseInt(riga[3]));;
            p.setMezzo(mezzo);
            p.setNumPostiOccupati(Integer.parseInt(riga[4]));
            IStazioneDAO sDao = new StazioneDAO();
            Stazione partenza = sDao.findById(Integer.parseInt(riga[5]));
            Stazione arrivo = sDao.findById(Integer.parseInt(riga[6]));;
            p.setArrivo(arrivo);
            p.setPartenza(partenza);
            ILocalitaDAO lDao = new LocalitaDAO();
            Localita l = lDao.findById(Integer.parseInt(riga[7]));
            p.setLocalita(l);
            p.setDataInizio(DateUtil.dateTimeFromString(riga[8]));
            p.setDataFine(DateUtil.dateTimeFromString(riga[9]));
        }
        return p;
    }

    @Override
    public ArrayList<PropostaCondivisione> findAll() throws IOException {
        ArrayList<PropostaCondivisione> proposte = new ArrayList<PropostaCondivisione>();

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM proposta_condivisione");

        for (String[] riga : res){
            PropostaCondivisione p = findById(Integer.parseInt(riga[0]));
            proposte.add(p);
        }
        return proposte;
    }

    @Override
    public void salvaProposta(PropostaCondivisione p) {

        String strDataPrenotazione = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(p.getData()));
        String strDataInizio = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(p.getDataInizio()));
        String strDataFine = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(p.getDataFine()));



        //String sql = "INSERT INTO prenotazione VALUES (NULL, '"+strDataPrenotazione+"',"+p.getCliente().getId()+","+p.getMezzo().getId()+","+p.getNumPostiOccupati()+","+p.getPartenza().getId()+","+p.getArrivo().getId()+","+p.getLocalita().getId()+",'"+strDataInizio+"','"+strDataFine+"');";
        String sql = "INSERT INTO proposta_condivisione VALUES (NULL, '"+strDataPrenotazione+"',"+p.getCliente().getId()+","+p.getMezzo().getId()+","+p.getNumPostiOccupati()+","+p.getPartenza().getId()+","+p.getArrivo().getId()+","+p.getLocalita().getId()+",'"+strDataInizio+"','"+strDataFine+"', 1);";

        System.out.println(sql);
        DbConnection.getInstance().eseguiAggiornamento(sql);

        sql = "SELECT last_insert_id()";
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery(sql);
        p.setId(Integer.parseInt(res.get(0)[0]));
        System.out.println("id proposta inserita:" + p.getId());

        /*String sql_acc;

         for (int i=0; i<a.size(); i++){
            sql_acc = "INSERT INTO pren_acc VALUES (" + p.getId() + ", " + a.get(i).getId() + ");";
            System.out.println(sql_acc);
            DbConnection.getInstance().eseguiAggiornamento(sql_acc);
        }*/
    }

    @Override
    public ArrayList<PropostaCondivisione> getProposte() throws IOException {
        ArrayList<PropostaCondivisione> proposte = new ArrayList<PropostaCondivisione>();

        String strDataAttuale = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(new Date()));

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM proposta_condivisione WHERE cliente_idcliente != " + ((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId() + " AND dataInizio >= '" + strDataAttuale + "'AND propostavalida = 1;");

        ArrayList<RichiestaCondivisione> richiesteEffettuate = CommonBusiness.getInstance().getRichiesteCondivisione(((Utente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO)).getId());

        for (String[] riga : res){
            PropostaCondivisione p = findById(Integer.parseInt(riga[0]));
            proposte.add(p);
        }

        for (int i = 0; i < richiesteEffettuate.size(); i++){
            for (int j = 0; j < proposte.size(); j++){
                if ( richiesteEffettuate.get(i).getProposta().getId() == proposte.get(j).getId() ){
                    proposte.remove(j);
                }
            }
        }

        return proposte;
    }

    public boolean setPropostaInvalida(int idProposta){
        return DbConnection.getInstance().eseguiAggiornamento("UPDATE proposta_condivisione SET propostavalida = 0 WHERE idproposta_condivisione = " + idProposta + ";");
    }
}

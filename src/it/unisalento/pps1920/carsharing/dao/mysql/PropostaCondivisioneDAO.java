package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.business.CommonBusiness;
import it.unisalento.pps1920.carsharing.dao.interfaces.*;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.DateUtil;
import it.unisalento.pps1920.carsharing.util.Session;
import it.unisalento.pps1920.carsharing.view.AlertBox;

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

    @Override
    public ArrayList<PropostaCondivisione> ricercaConFiltri(Stazione partenza, Stazione arrivo, Localita localita, int numPosti, Date inizio, Date fine, Modello modello, String dimensione, String motorizzazione, String tipologia) throws IOException {
        ArrayList<PropostaCondivisione> proposte = new ArrayList<PropostaCondivisione>();

        //SELECT * FROM prenotazione inner join mezzo on prenotazione.mezzo_idmezzo = mezzo.idmezzo where mezzo.postidisponibili<6;
        String query = "SELECT * FROM (proposta_condivisione INNER JOIN mezzo ON proposta_condivisione.mezzo_idmezzo = mezzo.idmezzo) INNER JOIN modello ON mezzo.modello_idmodello = modello.idmodello WHERE propostavalida=1";

        if ((partenza != (null))){
            query = query + " AND idstazione_partenza = " + partenza.getId();
        }
        if ((arrivo != (null))){
            query = query + " AND idstazione_arrivo = " + arrivo.getId();
        }
        if ((localita != (null))){
            query = query + " AND localita_idlocalita = " + localita.getId();
        }

        if ((inizio != (null))){//inizio.toString()
            query = query + " AND dataInizio >= STR_TO_DATE('" + DateUtil.fromRomeToLondon(DateUtil.stringFromDate(inizio)) + "', '%Y-%m-%d %H:%i:%s')";
            //System.out.println(DateUtil.stringFromDate(inizio));
            inizio = DateUtil.modificaOrarioData(inizio, "23", "59");
            //System.out.println(DateUtil.stringFromDate(inizio));
            query = query + " AND dataInizio <= STR_TO_DATE('" + DateUtil.fromRomeToLondon(DateUtil.stringFromDate(inizio)) + "', '%Y-%m-%d %H:%i:%s')";
        } else {
            Date d = new Date();
            query = query + " AND dataInizio >= STR_TO_DATE('" + DateUtil.fromRomeToLondon(DateUtil.stringFromDate(d)) + "', '%Y-%m-%d %H:%i:%s')";
        }
        if ((fine != (null))){
            query = query + " AND dataFine = '" + DateUtil.dateTimeFromString(DateUtil.fromRomeToLondon(DateUtil.stringFromDate(fine))).toString() + "'";
        }

        if ((modello != (null))){
            query = query + " AND mezzo.modello_idmodello = " + modello.getId();
        }
        if ((dimensione != (null))){
            query = query + " AND modello.dimensione = '" + dimensione + "'";
        }
        if ((motorizzazione != (null))){
            query = query + " AND mezzo.motorizzazione = '" + motorizzazione + "'";
        }
        if ((tipologia != (null))){
            query = query + " AND modello.tipologia = '" + tipologia + "'";
        }
        //query = query + " AND prenotazionevalida = 1;";

        System.out.println(query);

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery(query);
        //ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM prenotazione");


        if (res.size() > 0){//
            for (String[] riga : res){
                PropostaCondivisione p = findById(Integer.parseInt(riga[0]));
                proposte.add(p);
            }
        } else {
            AlertBox.display("Errore ricerca", "nessun elemento corrisponde ai criteri di ricerca");
        }

        for (int i = 0; i < proposte.size(); i++){
            System.out.println("mod :" + proposte.get(i).getMezzo().getModello().getNumPosti() + " posti occupati: " + proposte.get(i).getNumPostiOccupati() + " posti richiesti: " + numPosti);
            if (( proposte.get(i).getMezzo().getModello().getNumPosti() - proposte.get(i).getNumPostiOccupati() ) < numPosti || ( proposte.get(i).getMezzo().getModello().getNumPosti() - proposte.get(i).getNumPostiOccupati() ) == 0){
                proposte.remove(i);
            }
        }

        return proposte;
    }

    @Override
    public boolean updatePostiProposta(int idProposta, int posti) {
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT num_posti_occupati FROM proposta_condivisione WHERE idproposta_condivisione = "+idProposta+";");
        String[] riga = res.get(0);
        int vecchiPosti = Integer.parseInt(riga[0]);
        int nuoviPostiOccupati = vecchiPosti + posti;
        return DbConnection.getInstance().eseguiAggiornamento("UPDATE proposta_condivisione SET num_posti_occupati = "+nuoviPostiOccupati+" WHERE idproposta_condivisione = " + idProposta + ";");
    }

    @Override
    public boolean propostaUgualeCliente(int idProposta, int idCliente){
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM proposta_condivisione WHERE idproposta_condivisione = "+idProposta+" AND cliente_idcliente = "+idCliente+";");
        if (res.size()==1){
            return true;
        } else {
            return false;
        }
    }

    public boolean modificaTabella(Date inizio, Date fine, int posti, Stazione arrivo, Stazione partenza, Localita localita, Prenotazione oldPren) throws IOException {
        PropostaCondivisione prop = findById(oldPren.getIdPropostaCondivisione());

        if(posti != oldPren.getNumPostiOccupati()){
            int numPostiAggiornati = prop.getNumPostiOccupati()-oldPren.getNumPostiOccupati()+posti;
            DbConnection.getInstance().eseguiAggiornamento("UPDATE proposta_condivisione SET num_posti_occupati="+numPostiAggiornati+" WHERE idproposta_condivisione="+prop.getId()+";");
        }
        if (inizio != null){
            String strDataInizio = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(inizio));
            DbConnection.getInstance().eseguiAggiornamento("UPDATE proposta_condivisione SET dataInizio='"+strDataInizio+"' WHERE idproposta_condivisione="+prop.getId()+";");
        }
        if (fine != null){
            String strDataFine = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(fine));
            DbConnection.getInstance().eseguiAggiornamento("UPDATE proposta_condivisione SET dataInizio='"+strDataFine+"' WHERE idproposta_condivisione="+prop.getId()+";");
        }
        if (arrivo != null){
            DbConnection.getInstance().eseguiAggiornamento("UPDATE proposta_condivisione SET idstazione_arrivo="+arrivo.getId()+" WHERE idproposta_condivisione="+prop.getId()+";");
        }
        if (partenza != null){
            DbConnection.getInstance().eseguiAggiornamento("UPDATE proposta_condivisione SET idstazione_partenza="+partenza.getId()+" WHERE idproposta_condivisione="+prop.getId()+";");
        }
        if (localita != null){
            DbConnection.getInstance().eseguiAggiornamento("UPDATE proposta_condivisione SET localita_idlocalita="+localita.getId()+" WHERE idproposta_condivisione="+prop.getId()+";");
        }

        return true;
    }
}

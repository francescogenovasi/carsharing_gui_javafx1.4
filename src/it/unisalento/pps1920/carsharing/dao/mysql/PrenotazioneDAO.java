package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.*;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.DateUtil;
import it.unisalento.pps1920.carsharing.view.AlertBox;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PrenotazioneDAO implements IPrenotazioneDAO {

    @Override
    public Prenotazione findById(int id) throws IOException {
        Prenotazione p = null;
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM prenotazione WHERE idprenotazione = " + id + ";");
        if (res.size()==1){
            String[] riga = res.get(0);
            p = new Prenotazione();

            p.setId(Integer.parseInt(riga[0]));
            p.setNumPostiOccupati(Integer.parseInt(riga[4]));
            p.setData(DateUtil.dateTimeFromString(riga[1]));

            p.setDataInizio(DateUtil.dateTimeFromString(riga[8]));
            p.setDataFine(DateUtil.dateTimeFromString(riga[9]));

            ILocalitaDAO lDao = new LocalitaDAO();
            Localita l = lDao.findById(Integer.parseInt(riga[7]));
            p.setLocalita(l);

            IStazioneDAO sDao = new StazioneDAO();
            Stazione partenza = sDao.findById(Integer.parseInt(riga[5]));
            Stazione arrivo = sDao.findById(Integer.parseInt(riga[6]));;
            p.setArrivo(arrivo);
            p.setPartenza(partenza);

            IClienteDAO cDao = new ClienteDAO();
            Cliente cliente = cDao.findById(Integer.parseInt(riga[2]));;
            p.setCliente(cliente);

            IMezzoDAO mDao = new MezzoDAO();
            Mezzo mezzo = mDao.findById(Integer.parseInt(riga[3]));;
            p.setMezzo(mezzo);
        }
        return p;
    }

    @Override
    public ArrayList<Prenotazione> findAll() throws IOException {
        ArrayList<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM prenotazione");

        for (String[] riga : res){
            Prenotazione p = findById(Integer.parseInt(riga[0]));
            prenotazioni.add(p);
        }
        return prenotazioni;
    }

    @Override
    public ArrayList<Prenotazione> prenotazioniCliente(int id) throws IOException {
        ArrayList<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM prenotazione WHERE cliente_idcliente=" + id + ";");

        for (String[] riga : res){
            Prenotazione p = findById(Integer.parseInt(riga[0]));
            prenotazioni.add(p);
        }
        return prenotazioni;
    }

    @Override
    public void salvaPrenotazione(Prenotazione p, List<Accessorio> a) {

        String strDataPrenotazione = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(p.getData()));
        String strDataInizio = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(p.getDataInizio()));
        String strDataFine = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(p.getDataFine()));



        //String sql = "INSERT INTO prenotazione VALUES (NULL, '"+strDataPrenotazione+"',"+p.getCliente().getId()+","+p.getMezzo().getId()+","+p.getNumPostiOccupati()+","+p.getPartenza().getId()+","+p.getArrivo().getId()+","+p.getLocalita().getId()+",'"+strDataInizio+"','"+strDataFine+"');";
        String sql = "INSERT INTO prenotazione VALUES (NULL, '"+strDataPrenotazione+"',"+p.getCliente().getId()+","+p.getMezzo().getId()+","+p.getNumPostiOccupati()+","+p.getPartenza().getId()+","+p.getArrivo().getId()+","+p.getLocalita().getId()+",'"+strDataInizio+"','"+strDataFine+ "', " + p.getIdPropostaCondivisione() +", 0, 0, 1);";

        System.out.println(sql);
        DbConnection.getInstance().eseguiAggiornamento(sql);

        sql = "SELECT last_insert_id()";
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery(sql);
        p.setId(Integer.parseInt(res.get(0)[0]));
        System.out.println("id prenotazione inserita:" + p.getId());

        String sql_acc;

        for (int i=0; i<a.size(); i++){
            sql_acc = "INSERT INTO pren_acc VALUES (" + p.getId() + ", " + a.get(i).getId() + ");";
            System.out.println(sql_acc);
            DbConnection.getInstance().eseguiAggiornamento(sql_acc);
        }
    }

    @Override
    public ArrayList<Prenotazione> ricercaConFiltri(Stazione partenza, Stazione arrivo, Localita localita, int numPosti, Date inizio, Date fine, Modello modello, String dimensione, String motorizzazione, String tipologia) throws IOException {
        ArrayList<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();

        //SELECT * FROM prenotazione inner join mezzo on prenotazione.mezzo_idmezzo = mezzo.idmezzo where mezzo.postidisponibili<6;
        String query = "SELECT * FROM (prenotazione INNER JOIN mezzo ON prenotazione.mezzo_idmezzo = mezzo.idmezzo) INNER JOIN modello ON mezzo.modello_idmodello = modello.idmodello WHERE mezzo.postidisponibili >= " + numPosti;

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
        query = query + " AND prenotazionevalida = 1;";

        System.out.println(query);

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery(query);
        //ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM prenotazione");


        if (res.size() > 0){//
            for (String[] riga : res){
                Prenotazione p = findById(Integer.parseInt(riga[0]));
                prenotazioni.add(p);
            }
        } else {
            AlertBox.display("Errore ricerca", "nessun elemento corrisponde ai criteri di ricerca");
        }


        return prenotazioni;
    }

    @Override
    public ArrayList<Prenotazione> ricercaPerCliente(int id) throws IOException {
        ArrayList<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM prenotazione WHERE cliente_idcliente=" + id + ";");

        for (String[] riga : res){
            Prenotazione p = findById(Integer.parseInt(riga[0]));
            prenotazioni.add(p);
        }

        return prenotazioni;
    }

    @Override
    public int getIdPrenFromIdPropCon(int idProp){
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT idprenotazione FROM prenotazione WHERE idproposta_condivisione=" + idProp + ";");
        if (res.size()==1){
            String[] riga = res.get(0);
            return Integer.parseInt(riga[0]);
        } else {
            return -1;
        }
    }

    @Override
    public int richiestaToPrenotazione(RichiestaCondivisione r) throws IOException {
        Prenotazione pren = new Prenotazione();
        PropostaCondivisione prop = new PropostaCondivisioneDAO().findById(r.getProposta().getId());
        pren.setData(new Date());
        pren.setCliente(prop.getCliente());
        pren.setMezzo(prop.getMezzo());
        pren.setNumPostiOccupati(r.getNumPostiRichiesti());
        pren.setPartenza(prop.getPartenza());
        pren.setArrivo(prop.getArrivo());
        pren.setLocalita(prop.getLocalita());
        pren.setDataInizio(prop.getDataInizio());
        pren.setDataFine(prop.getDataFine());
        pren.setIdPropostaCondivisione(prop.getId());

        List<Accessorio> acc = new ArrayList<Accessorio>();
        acc = new AccessorioDAO().getAccessoriRichiesta(r.getId());

        salvaPrenotazione(pren, acc);

        //System.out.println("iddddddddddddddddddddd: " + pren.getId());
        return pren.getId();
    }

}

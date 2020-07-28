package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.business.RichiestaBusiness;
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

            p.setIdPropostaCondivisione(Integer.parseInt(riga[10]));
            p.setCosto(Float.parseFloat(riga[11]));
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

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM prenotazione WHERE cliente_idcliente=" + id + " AND dataInizio >= '"+DateUtil.fromRomeToLondon(DateUtil.stringFromDate(new Date()))+"';");


        for (String[] riga : res){
            Prenotazione p = findById(Integer.parseInt(riga[0]));
            prenotazioni.add(p);
        }
        return prenotazioni;
    }

    @Override
    public void salvaPrenotazione(Prenotazione p, List<Accessorio> a, float costo, boolean nuovoInserimento) throws IOException {

        String strDataPrenotazione = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(p.getData()));
        String strDataInizio = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(p.getDataInizio()));
        String strDataFine = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(p.getDataFine()));


        /* float costo = p.getMezzo().getModello().getTariffaBase();
        //il costo dello sharing è dato dalla tariffa base sommato al costo di ogni accessorio scelto
        for (int i=0; i<a.size(); i++){
            costo = costo + a.get(i).getCosto();
        }*/

        p.setCosto(costo);


        //String sql = "INSERT INTO prenotazione VALUES (NULL, '"+strDataPrenotazione+"',"+p.getCliente().getId()+","+p.getMezzo().getId()+","+p.getNumPostiOccupati()+","+p.getPartenza().getId()+","+p.getArrivo().getId()+","+p.getLocalita().getId()+",'"+strDataInizio+"','"+strDataFine+"');";
        String sql = "INSERT INTO prenotazione VALUES (NULL, '"+strDataPrenotazione+"',"+p.getCliente().getId()+","+p.getMezzo().getId()+","+p.getNumPostiOccupati()+","+p.getPartenza().getId()+","+p.getArrivo().getId()+","+p.getLocalita().getId()+",'"+strDataInizio+"','"+strDataFine+ "', " + p.getIdPropostaCondivisione() + ", " + p.getCosto() + ", 0, 1);";

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

        if (nuovoInserimento){
            DbConnection.getInstance().eseguiAggiornamento("INSERT INTO mezzi_da_preparare VALUES (NULL, '"+ p.getMezzo().getId()+"', '" + strDataInizio + "', '" + strDataFine + "', "+ p.getNumPostiOccupati() + ", 'Non Pronto', 'Non partito')");
        } else {
            System.out.println("SELECT * FROM mezzi_da_preparare WHERE mezzo_idmezzo = " + p.getMezzo().getId() + " AND dataInizio = '" + strDataInizio + "' AND dataFine = '" + strDataFine +"' AND stato_addetto = 'Non Pronto' ;");
            ArrayList<String[]> res1 = DbConnection.getInstance().eseguiQuery("SELECT * FROM mezzi_da_preparare WHERE mezzo_idmezzo = " + p.getMezzo().getId() + " AND dataInizio = '" + strDataInizio + "' AND dataFine = '" + strDataFine +"' AND stato_addetto = 'Non Pronto' ;");
            if(res1.size() == 1){
                String[] riga = res1.get(0);
                int vecchiPostiOccupati = Integer.parseInt(riga[4]);
                int nuoviPostiOccupati = vecchiPostiOccupati + p.getNumPostiOccupati();
                DbConnection.getInstance().eseguiAggiornamento("UPDATE mezzi_da_preparare SET posti_occupati = "+ nuoviPostiOccupati +" WHERE idmezzi_da_preparare = " + Integer.parseInt(riga[0]) + ";");
                //se con quest'ultima richiesta la macchina è piena allora la proposta non è piu valida e visualizzabile
                if (RichiestaBusiness.getInstance().numeroPostiDisponibili(p.getDataInizio(), p.getDataFine(), p.getMezzo().getId()) == 0){
                    IPropostaCondivisioneDAO pDAO = new PropostaCondivisioneDAO();
                    pDAO.setPropostaInvalida(p.getIdPropostaCondivisione());
                }

            }
        }
    }

    /*@Override
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
    }*/


    @Override
    public ArrayList<Prenotazione> ricercaPerCliente(int id) throws IOException {
        ArrayList<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM prenotazione WHERE cliente_idcliente=" + id + " AND dataInizio >= '"+DateUtil.fromRomeToLondon(DateUtil.stringFromDate(new Date()))+"'AND prenotazionevalida=1;");
        System.out.println("SELECT * FROM prenotazione WHERE cliente_idcliente=" + id + " AND dataInizio >= ' "+DateUtil.fromRomeToLondon(DateUtil.stringFromDate(new Date()))+" ' AND prenotazionevalida=1;");

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
    public int getNumeroClientiSharing(int idProposta){
        System.out.println("SELECT count(*) FROM (SELECT * FROM prenotazione WHERE idproposta_condivisione = " + idProposta + " AND prenotazionevalida=1) AS a;");
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT count(*) FROM (SELECT * FROM prenotazione WHERE idproposta_condivisione = " + idProposta + " AND prenotazionevalida=1) AS a;");
        if (res.size() == 1){
            String[] riga = res.get(0);
            return Integer.parseInt(riga[0]);
        } else {
            return -1;
        }
    }

    public void correggiCosto(int idProposta, boolean aggiunta) throws IOException { //se aggiungo/eliminano clienti allo sharing allora il costo varia
        int clientiPrima = getNumeroClientiSharing(idProposta);
        int clientiDopo;
        if (aggiunta){
            clientiDopo = clientiPrima + 1;
        } else {
            clientiDopo = clientiPrima - 1;
        }
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT idprenotazione, mezzo_idmezzo, costo_tot from prenotazione where idproposta_condivisione = " + idProposta + ";");
        for (String[] riga : res){
            int idPren = Integer.parseInt(riga[0]);
            IMezzoDAO mDAO = new MezzoDAO();
            Mezzo m = mDAO.findById(Integer.parseInt(riga[1]));

            float vecchioCosto = m.getModello().getTariffaBase() / clientiPrima ;
            float nuovoCosto = m.getModello().getTariffaBase() / clientiDopo ;
            float vecchioTotale = Float.parseFloat(riga[2]);
            float nuovoTotale = vecchioTotale - vecchioCosto + nuovoCosto;

            DbConnection.getInstance().eseguiAggiornamento("UPDATE prenotazione SET costo_tot = " + nuovoTotale + " WHERE idprenotazione = " + idPren + ";");

        }
    }

    public int[] prenotazioniFromDateEIdMezzo(int idMezzo, String dataInizio, String dataFine){
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT idprenotazione FROM carsharing.prenotazione where mezzo_idmezzo=" + idMezzo + " and dataInizio='"+dataInizio+"' and dataFine='"+dataFine+"';");
        int[] a = new int[res.size()];
        for (int i = 0; i < res.size(); i++){
            String[] riga = res.get(i);
            a[i] = Integer.parseInt(riga[0]);
        }
        return a;
    }

    @Override
    public boolean setPrenotazioneInvalida(int idPrenotazione){
        return DbConnection.getInstance().eseguiAggiornamento("UPDATE prenotazione SET prenotazionevalida=0 WHERE idprenotazione = "+ idPrenotazione+";");
    }

    public ArrayList<Prenotazione> getPrenotazioniPerOperatore(Operatore op) throws IOException {
        ArrayList<Prenotazione> pren = new ArrayList<Prenotazione>();
        String sql="SELECT * FROM prenotazione WHERE idstazione_partenza ="+op.getIdstazione()+";";

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery(sql);

        for (String[] riga : res){
            Prenotazione p = findById(Integer.parseInt(riga[0]));
            pren.add(p);
        }

        return pren;
    }

    @Override
    public boolean modificaPrenotazione(Date inizio, Date fine, int posti, Stazione arrivo, Stazione partenza, Localita localita, Prenotazione oldPren){
        String query = "UPDATE prenotazione SET ";
        boolean preceduto = false;
        if (inizio != null){
            if (preceduto){
                query = query + ", ";
            }
            String strDataInizio = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(inizio));
            query = query + " dataInizio = '"+strDataInizio+"'";
            preceduto = true;

        }
        if (fine != null){
            if (preceduto){
                query = query + ", ";
            }
            String strDataFine = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(fine));
            query = query + " dataInizio = '"+strDataFine+"' ";
            preceduto = true;
        }
        if (posti != oldPren.getNumPostiOccupati()){
            if (preceduto){
                query = query + ", ";
            }
            query = query + "num_posti_occupati = "+posti+" ";
            preceduto = true;
        }
        if (localita != null){
            if (preceduto){
                query = query + ", ";
            }
            query = query + "localita_idlocalita= "+localita.getId()+" ";
            preceduto = true;
        }
        if (partenza != null){
            if (preceduto){
                query = query + ", ";
            }
            query = query + "idstazione_partenza= "+partenza.getId()+" ";
            preceduto = true;
        }
        if (localita != null){
            if (preceduto){
                query = query + ", ";
            }
            query = query + "idstazione_arrivo= "+arrivo.getId()+" ";
            preceduto = true;
        }
        query = query + " WHERE idprenotazione = " + oldPren.getId() +";";
        System.out.println(query);
        return true;
    }


}

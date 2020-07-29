package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.*;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.DateUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RichiestaCondivisioneDAO implements IRichiestaCondivisioneDAO {
    @Override
    public boolean salvaRichiesta(RichiestaCondivisione r, List<Accessorio> a) {
        String strDataPrenotazione = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(r.getData()));

        String sql = "INSERT INTO richiesta_condivisione VALUES (NULL, '"+strDataPrenotazione+"',"+r.getProposta().getId()+","+r.getCliente().getId()+", "+r.getNumPostiRichiesti()+", '"+r.getStato()+"');";

        boolean res1 = DbConnection.getInstance().eseguiAggiornamento(sql);

        sql = "SELECT last_insert_id()";
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery(sql);
        r.setId(Integer.parseInt(res.get(0)[0]));

        String sql_acc;

        for (int i=0; i<a.size(); i++){
            sql_acc = "INSERT INTO rich_acc VALUES (" + r.getId() + ", " + a.get(i).getId() + ");";
            DbConnection.getInstance().eseguiAggiornamento(sql_acc);
        }

        return res1;
    }

    @Override
    public RichiestaCondivisione findById(int id) throws IOException {
        RichiestaCondivisione r = null;
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM richiesta_condivisione WHERE idrichiesta_condivisione = " + id + ";");
        if (res.size()==1){
            String[] riga = res.get(0);
            r = new RichiestaCondivisione();

            r.setId(Integer.parseInt(riga[0]));
            r.setData(DateUtil.dateTimeFromString(riga[1]));
            IPropostaCondivisioneDAO pDao = new PropostaCondivisioneDAO();
            PropostaCondivisione prop = pDao.findById(Integer.parseInt(riga[2]));
            r.setProposta(prop);
            IClienteDAO cDao = new ClienteDAO();
            Cliente cliente = cDao.findById(Integer.parseInt(riga[3]));;
            r.setCliente(cliente);
            r.setStato(riga[5]);
            r.setNumPostiRichiesti(Integer.parseInt(riga[4]));
        }
        return r;
    }

    @Override
    public ArrayList<RichiestaCondivisione> findAll() throws IOException {
        ArrayList<RichiestaCondivisione> richieste = new ArrayList<RichiestaCondivisione>();

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM richiesta_condivisione");

        for (String[] riga : res){
            RichiestaCondivisione r = findById(Integer.parseInt(riga[0]));
            richieste.add(r);
        }
        return richieste;
    }

    @Override
    public boolean accettaRichiesta(int idRichiesta){
        boolean res = DbConnection.getInstance().eseguiAggiornamento("UPDATE richiesta_condivisione SET stato = 'Accettata' WHERE idrichiesta_condivisione = "+ idRichiesta + ";");
        return res;
    }

    @Override
    public boolean rifiutaRichiesta(int idRichiesta){
        boolean res = DbConnection.getInstance().eseguiAggiornamento("UPDATE richiesta_condivisione SET stato = 'Rifiutata' WHERE idrichiesta_condivisione = "+ idRichiesta + ";");
        return res;
    }

    @Override
    public int numeroPostiDisponibili(Date dataInizio, Date dataFine, int idMezzo) throws IOException {
        String strDataInizio = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(dataInizio));
        String strDataFine = DateUtil.fromRomeToLondon(DateUtil.stringFromDate(dataFine));

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM mezzi_da_preparare WHERE mezzo_idmezzo = " + idMezzo + " AND dataInizio = '" + DateUtil.fromRomeToLondon(strDataInizio) + "' AND dataFine = '" + DateUtil.fromRomeToLondon(strDataFine) + "';");
        if (res.size()==1){
            String[] riga = res.get(0);
            IMezzoDAO mDAO = new MezzoDAO();
            Mezzo m = mDAO.findById(idMezzo);
            return (m.getModello().getNumPosti() - Integer.parseInt(riga[4]));

        }
        return 0;
    }

    @Override
    public ArrayList<RichiestaCondivisione> getRichiesteCliente(int idCliente) throws IOException {
        ArrayList<RichiestaCondivisione> richieste = new ArrayList<RichiestaCondivisione>();

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM richiesta_condivisione WHERE cliente_idcliente=" + idCliente + ";");

        for (String[] riga : res){
            RichiestaCondivisione r = findById(Integer.parseInt(riga[0]));
            richieste.add(r);
        }
        return richieste;
    }
}

package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.*;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.DateUtil;

import java.io.IOException;
import java.util.ArrayList;

public class TabConfermaDAO implements ITabConfermaDAO {
    @Override
    public ArrayList<TabConfermaRichieste> getElencoInAttesa(int idClienteProponente) throws IOException {
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT R.idrichiesta_condivisione, R.cliente_idcliente, P.dataInizio, P.dataFine, P.idstazione_partenza, P.idstazione_arrivo, P.localita_idlocalita, P.mezzo_idmezzo, R.posti_richiesti from proposta_condivisione AS P INNER JOIN richiesta_condivisione AS R ON P.idproposta_condivisione = R.idproposta_condivisione WHERE P.cliente_idcliente = "+ idClienteProponente +" AND R.stato='Attesa';");
        ArrayList<TabConfermaRichieste> richieste = new ArrayList<TabConfermaRichieste>();

        for (int i=0; i< res.size(); i++){
            String riga[] = res.get(i);
            TabConfermaRichieste t = new TabConfermaRichieste();

            t.setIdRichiesta(Integer.parseInt(riga[0]));

            IClienteDAO cDAO = new ClienteDAO();
            Cliente proponente = cDAO.findById(idClienteProponente);
            Cliente richiedente = cDAO.findById(Integer.parseInt(riga[1]));
            t.setProponente(proponente);
            t.setRichiedente(richiedente);

            t.setDataInizio(DateUtil.dateTimeFromString(riga[2]));
            t.setDataFine(DateUtil.dateTimeFromString(riga[3]));

            IStazioneDAO sDao = new StazioneDAO();
            Stazione partenza = sDao.findById(Integer.parseInt(riga[4]));
            Stazione arrivo = sDao.findById(Integer.parseInt(riga[5]));;
            t.setPartenza(partenza);
            t.setArrivo(arrivo);

            ILocalitaDAO lDao = new LocalitaDAO();
            Localita l = lDao.findById(Integer.parseInt(riga[6]));
            t.setLocalita(l);

            IMezzoDAO mDao = new MezzoDAO();
            Mezzo mezzo = mDao.findById(Integer.parseInt(riga[7]));;
            t.setMezzo(mezzo);

            t.setNumPostiOccupati(Integer.parseInt(riga[8]));

            richieste.add(t);
        }
        return richieste;
    }

    @Override
    public TabConfermaRichieste findById(int id) {
        return null;
    }

    @Override
    public ArrayList<TabConfermaRichieste> findAll() {
        return null;
    }
}

package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.TabConfermaRichieste;

import java.io.IOException;
import java.util.ArrayList;

public interface ITabConfermaDAO extends IBaseDAO<TabConfermaRichieste>{
    public ArrayList<TabConfermaRichieste> getElencoInAttesa(int idClienteProponente) throws IOException;
}

package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.IAccessorioDAO;
import it.unisalento.pps1920.carsharing.model.Accessorio;
import it.unisalento.pps1920.carsharing.model.Prenotazione;

import java.util.ArrayList;

public class AccessorioDAO implements IAccessorioDAO {
    @Override
    public Accessorio findById(int id) {
        Accessorio a = null;
        ArrayList<String[]> ris = DbConnection.getInstance().eseguiQuery("SELECT * FROM accessorio WHERE idaccessorio=" + id + ";");
        if (ris.size() == 1){
            String[] riga = ris.get(0);
            a = new Accessorio();
            a.setId(Integer.parseInt(riga[0]));
            a.setNome(riga[1]);
            a.setPostiOccupati(Integer.parseInt(riga[2]));
            a.setCosto(Float.parseFloat(riga[3]));
        } else {
            //ACCESSORIO NON TROVATO
            a = null;
        }
        return a;
    }

    @Override
    public ArrayList<Accessorio> findAll() {
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM accessorio;");
        ArrayList<Accessorio> acc = new ArrayList<Accessorio>();
        for(String[] riga : res) {
            Accessorio a = findById(Integer.parseInt(riga[0]));
            acc.add(a);
        }
        return acc;
    }

    @Override
    public boolean salvaAccessorio(Accessorio a){
        String sql = "INSERT INTO accessorio (idaccessorio, nome, postioccupati, costo) VALUES ( null, '" + a.getNome() + "', " + a.getPostiOccupati() + ", " + a.getCosto() + ");";
        boolean res = DbConnection.getInstance().eseguiAggiornamento(sql);
        return res;
    }

    @Override
    public int findAccessorioId(String nome){
        int id = -1;
        ArrayList<String[]> ris = DbConnection.getInstance().eseguiQuery("SELECT * FROM accessorio WHERE nome='" + nome + "';");
        if (ris.size() == 1){
            String[] riga = ris.get(0);
            id=Integer.parseInt(riga[0]);
        }
        return id;
    }

    @Override
    public ArrayList<Accessorio> getAccessoriPrenotazione(int idPren){
        ArrayList<Accessorio> acc = new ArrayList<Accessorio>();
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM pren_acc WHERE prenotazione_idprenotazione=" + idPren + ";");
        for (String[] riga : res){
            Accessorio a = findById(Integer.parseInt(riga[1]));
            acc.add(a);
        }
        return acc;
    }

    @Override
    public ArrayList<Accessorio> getAccessoriRichiesta(int idRichiesta){
        ArrayList<Accessorio> acc = new ArrayList<Accessorio>();
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM rich_acc WHERE idrichiesta_condivisione = " + idRichiesta + ";");
        for (String[] riga : res){
            Accessorio a = findById(Integer.parseInt(riga[1]));
            acc.add(a);
        }
        return acc;
    }
}

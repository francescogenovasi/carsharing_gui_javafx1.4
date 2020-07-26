package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.IModelloDAO;
import it.unisalento.pps1920.carsharing.model.Mezzo;
import it.unisalento.pps1920.carsharing.model.Modello;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ModelloDAO implements IModelloDAO {
    @Override
    public Modello findById(int id) throws IOException {
        Modello m  = null;

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM modello WHERE idmodello = " + id + ";");

        if(res.size() == 1){
            String riga[] = res.get(0);
            m = new Modello();

            m.setId(Integer.parseInt(riga[0]));
            m.setNome(riga[1]);
            m.setNumPosti(Integer.parseInt(riga[2]));
            m.setDimensione(riga[4]);
            m.setTipologia(riga[5]);
            m.setTariffaBase(Float.parseFloat(riga[6]));

            byte[] foto = DbConnection.getInstance().getFoto("SELECT foto FROM modello WHERE idmodello = " + id +";");
            ByteArrayInputStream bis = new ByteArrayInputStream(foto);
            BufferedImage bImage = ImageIO.read(bis);
            ImageIO.write(bImage, "jpg", new File("src/temp.jpg") );
            File file = new File("src/temp.jpg");
            m.setFoto(file);
            file.delete();
        }
        return m;
    }

    @Override
    public ArrayList<Modello> findAll() throws IOException {
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM modello;"); //query

        ArrayList<Modello> modelli = new ArrayList<Modello>(); //istanziare model con risultati query

        for(String[] riga : res) {
            Modello m = findById(Integer.parseInt(riga[0]));
            modelli.add(m);
        }

        return modelli;
    }


    public Image getFoto(int id) throws IOException {
        String query = "SELECT foto FROM modello WHERE idmodello = " + id + " ;";
        byte[] prova = DbConnection.getInstance().getFoto(query);
        ByteArrayInputStream bis = new ByteArrayInputStream(prova);
        BufferedImage bImage2 = ImageIO.read(bis);
        ImageIO.write(bImage2, "jpg", new File("src/temp.jpg") );
        File file = new File("src/temp.jpg");
        Image image = new Image(file.toURI().toString());
        file.delete();
        return image;
    }

    public boolean salvaModello(Modello m){
        String sql = "INSERT INTO modello (nome, num_posti, dimensione, tipologia, tariffa_base) VALUES ('" + m.getNome() + "', '" + m.getNumPosti() + "', '" + m.getDimensione() + "' , '" + m.getTipologia() + "', " + m.getTariffaBase() + ");";
        System.out.println(sql);
        boolean res = DbConnection.getInstance().eseguiAggiornamento(sql);
        sql = "SELECT last_insert_id()";
        ArrayList<String[]> res1 = DbConnection.getInstance().eseguiQuery(sql);
        m.setId(Integer.parseInt(res1.get(0)[0]));
        sql="UPDATE modello SET foto=(?) WHERE idmodello="+m.getId()+";";
        DbConnection.getInstance().addFoto(m.getFoto(),sql);
        System.out.println("id modello inserito:" + m.getId());
        return res;
    }

    public int findModelloId(String nome){
        int id = -1;
        ArrayList<String[]> ris = DbConnection.getInstance().eseguiQuery("SELECT * FROM modello WHERE nome='" + nome + "';");
        if (ris.size() == 1){
            String[] riga = ris.get(0);
            id=Integer.parseInt(riga[0]);
        }
        return id;
    }

}

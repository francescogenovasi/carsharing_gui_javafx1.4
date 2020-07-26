package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.IClienteDAO;
import it.unisalento.pps1920.carsharing.model.Cliente;
import it.unisalento.pps1920.carsharing.model.Utente;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ClienteDAO implements IClienteDAO {
    @Override
    public Cliente findById(int id) throws IOException {

        Cliente c = null;

        //ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM cliente WHERE idcliente =" + id + ";");
        //ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT C.utente_idutente, U.username, U.password, U.email FROM cliente AS C INNER JOIN utente as U  ON U.idutente = C.utente_idutente WHERE C.utente_idutente = "+id+";");
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT cliente.utente_idutente, utente.username, utente.password, utente.email, cliente.nome, cliente.cognome, cliente.telefono, cliente.citta, cliente.cap, cliente.indirizzo, cliente.eta FROM cliente INNER JOIN utente ON utente.idutente = cliente.utente_idutente WHERE cliente.utente_idutente = "+id+";");

        if(res.size() == 1){
            String riga[] = res.get(0);
            c = new Cliente();

            c.setId(Integer.parseInt(riga[0]));
            c.setUsername(riga[1]);
            c.setEmail(riga[3]);
            c.setNome(riga[4]);
            c.setCognome(riga[5]);
            c.setTelefono(riga[6]);
            c.setCitta(riga[7]);
            c.setCap(Integer.parseInt(riga[8]));
            c.setIndirizzo(riga[9]);
            c.setEta(Integer.parseInt(riga[10]));
            byte[] foto = DbConnection.getInstance().getFoto("SELECT foto FROM cliente INNER JOIN utente ON utente.idutente = cliente.utente_idutente WHERE cliente.utente_idutente = "+id+";");
            ByteArrayInputStream bis = new ByteArrayInputStream(foto);
            BufferedImage bImage = ImageIO.read(bis);
            ImageIO.write(bImage, "jpg", new File("src/temp.jpg") );
            File file = new File("src/temp.jpg");
            c.setFoto(file);
            file.delete();
        }

        return c;
    }

    @Override
    public ArrayList<Cliente> findAll() throws IOException { //potrebbe non funzionare, potrebbe mancare qualche inner join
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT C.utente_idutente, U.username, U.password, U.email FROM cliente AS C INNER JOIN utente as U  ON U.idutente = C.utente_idutente"); //query SELECT C.utente_idutente, U.username, U.password, U.email FROM cliente AS C INNER JOIN utente as U  ON U.idutente = C.utente_idutente

        ArrayList<Cliente> clienti = new ArrayList<Cliente>(); //istanziare model con risultati query

        for(String[] riga : res) {
            Cliente l = findById(Integer.parseInt(riga[0]));
            clienti.add(l);
        }

        return clienti;
    }

    public boolean checkCliente(int id){
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM cliente WHERE utente_idutente = " + id + ";");
        if (res.size() == 1){
            return true;
        }
        return false;
    }

    public boolean salvaRegistrazioneCliente(Cliente c){
        //boolean res = DbConnection.getInstance().eseguiAggiornamento("INSERT INTO utente (username, password, email) VALUES ('"+username+"','"+password+"' ,'"+email+"') ;");
        String sql = "INSERT INTO cliente (utente_idutente, nome, cognome, telefono, citta, cap, indirizzo, eta, foto) VALUES (" + c.getId() +", '" + c.getNome() + "', '" + c.getCognome() + "', '" + c.getTelefono() + "', '" + c.getCitta() + "', '" + c.getCap() + "', '" + c.getIndirizzo() + "', '" + c.getEta() +"', NULL);";
        System.out.println(sql);
        boolean res = DbConnection.getInstance().eseguiAggiornamento(sql);
        sql="SELECT last_insert_id()";
        ArrayList<String[]> re=DbConnection.getInstance().eseguiQuery(sql);
        int idd=Integer.parseInt(re.get(0)[0]);
        sql="UPDATE cliente SET foto=(?) WHERE utente_idutente="+idd+";";
        DbConnection.getInstance().addFoto(c.getFoto(),sql);
        System.out.println("id cliente inserito:" + c.getId());
        return res;
    }
}

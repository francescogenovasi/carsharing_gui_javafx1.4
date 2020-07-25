package it.unisalento.pps1920.carsharing.business;
import it.unisalento.pps1920.carsharing.dao.mysql.AddettoDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.ClienteDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.OperatoreDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.UtenteDAO;
import it.unisalento.pps1920.carsharing.model.Addetto;
import it.unisalento.pps1920.carsharing.model.Cliente;
import it.unisalento.pps1920.carsharing.model.Operatore;
import it.unisalento.pps1920.carsharing.model.Utente;
import it.unisalento.pps1920.carsharing.view.AlertBox;

public class RegistrazioneBusiness {
    private static RegistrazioneBusiness instance;

    public static synchronized RegistrazioneBusiness getInstance() {
        if(instance == null){
            instance = new RegistrazioneBusiness();
        }
        return instance;
    }

    private RegistrazioneBusiness(){}

    public static boolean inviaRegistrazioneCliente(Cliente c){
        Utente u = new Utente();
        u.setUsername(c.getUsername());
        u.setEmail(c.getEmail());
        u.setPassword(c.getPassword());

        //System.out.println("TESTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT1: " + u.getId()); //id assente

        boolean res1 = new UtenteDAO().salvaRegistrazioneUtente(u);

        //System.out.println("TESTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT2: " + u.getId()); //id presente
        if (res1){
            System.out.println("registrazione utente ok");
        }else{
            System.out.println("registrazione utente fallita");
        }
        c.setId(u.getId());

        boolean res2 = new ClienteDAO().salvaRegistrazioneCliente(c);
        if (res2){
            System.out.println("registrazione cliente ok");

        }else{
            System.out.println("registrazione cliente fallita");
        }
        if ( res1 && res2 ){
            return true;
        } else {
            return false;
        }
    }

    public static boolean inviaRegistrazioneAddetto(Addetto a){

        Utente u = new Utente();
        u.setUsername(a.getUsername());
        u.setEmail(a.getEmail());
        u.setPassword(a.getPassword());

        //System.out.println("TESTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT1: " + u.getId()); //id assente

        boolean res1 = new UtenteDAO().salvaRegistrazioneUtente(u);

        //System.out.println("TESTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT2: " + u.getId()); //id presente
        if (res1){
            System.out.println("registrazione utente ok");
        }else{
            System.out.println("registrazione utente fallita");
        }
        a.setId(u.getId());

        boolean res2 = new AddettoDAO().salvaRegistrazioneAddetto(a);
        if (res2){
            System.out.println("registrazione addetto ok");

        }else{
            System.out.println("registrazione addetto fallita");
        }
        if ( res1 && res2 ){
            return true;
        } else {
            return false;
        }

    }

    public static boolean inviaRegistrazioneOperatore(Operatore o){

        Utente u = new Utente();
        u.setUsername(o.getUsername());
        u.setEmail(o.getEmail());
        u.setPassword(o.getPassword());

        //System.out.println("TESTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT1: " + u.getId()); //id assente

        boolean res1 = new UtenteDAO().salvaRegistrazioneUtente(u);

        //System.out.println("TESTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT2: " + u.getId()); //id presente
        if (res1){
            System.out.println("registrazione utente ok");
        }else{
            System.out.println("registrazione utente fallita");
        }
        o.setId(u.getId());

        boolean res2 = new OperatoreDAO().salvaRegistrazioneOperatore(o);
        if (res2){
            System.out.println("registrazione operatore ok");

        }else{
            System.out.println("registrazione operatore fallita");
        }
        if ( res1 && res2 ){
            return true;
        } else {
            return false;
        }

    }
}

package it.unisalento.pps1920.carsharing.util;

import it.unisalento.pps1920.carsharing.model.Cliente;
import it.unisalento.pps1920.carsharing.model.Utente;

import java.util.HashMap;

public class Session {
    private static Session instance;

    private HashMap<String, Object> mappa = new HashMap<String, Object>();

    //definisco le chiavi
    public static final String UTENTE_LOGGATO = "UTENTE_LOGGATO";

    //variabili che vogliamo ricordare
    private Utente utenteLoggato;
    //prenotazione in lavorazione, .....

    public static synchronized Session getInstance(){
        if(instance == null){
            instance = new Session();
        }
        return instance;
    }

    private Session(){

    }

    public Utente getUtenteLoggato() {
        return utenteLoggato;
    }

    public void setUtenteLoggato(Utente utenteLoggato) {
        this.utenteLoggato = utenteLoggato;
    }





    public void inserisci(String chiave, Object valore){
        mappa.put(chiave, valore);
    }

    public Object ottieni(String chiave){
        return mappa.get(chiave);
    }

    public void rimuovi(String chiave){
        mappa.remove(chiave);
    }

    /* 1
    //variabili che vogliamo ricordare
    private Cliente clienteLoggato;
    //prenotazione in lavorazione, .....

    public synchronized Session getSession(){
        if(instance == null){
            instance = new Session();
        }
        return instance;
    }

    private Session(){

    }

    public Cliente getClienteLoggato() {
        return clienteLoggato;
    }

    public void setClienteLoggato(Cliente clienteLoggato) {
        this.clienteLoggato = clienteLoggato;
    }*/
}

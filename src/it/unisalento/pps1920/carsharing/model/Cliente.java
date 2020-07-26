package it.unisalento.pps1920.carsharing.model;

import java.io.File;

public class Cliente extends Utente{

    private String nome;
    private String cognome;
    private String telefono;
    private String citta;
    private int cap;
    private String indirizzo;
    private int eta;
    private File foto;

    public Cliente(){

    }
    public Cliente(int id, String username, String password, String email, String nome, String cognome, String telefono, String citta, int cap, String indirizzo, int eta, File foto) {
        super(id, username, password, email);
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.citta = citta;
        this.cap = cap;
        this.indirizzo = indirizzo;
        this.eta = eta;
        this.foto=foto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public int getEta() {
        return eta;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }

    public File getFoto() {
        return foto;
    }

    public void setFoto(File foto) {
        this.foto = foto;
    }


    @Override
    public String toString(){
        return getUsername();
    }
}

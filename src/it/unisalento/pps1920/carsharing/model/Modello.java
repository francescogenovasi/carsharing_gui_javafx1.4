package it.unisalento.pps1920.carsharing.model;

import java.io.File;

public class Modello {
    private int id;
    private String nome;
    private int numPosti;
    private File foto;
    private String tipologia;
    private String dimensione;


    public Modello(){

    }
    //todo add foto a modello
    public Modello(int id, String nome, int numPosti, File foto, String tipologia, String dimensione) {
        this.id = id;
        this.nome = nome;
        this.numPosti = numPosti;
        this.foto=foto;
        this.dimensione=dimensione;
        this.tipologia=tipologia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setDimensione(String dimensione) {
        this.dimensione = dimensione;
    }

    public String getDimensione() {
        return dimensione;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumPosti() {
        return numPosti;
    }

    public void setNumPosti(int numPosti) {
        this.numPosti = numPosti;
    }

    public File getFoto() {
        return foto;
    }

    public void setFoto(File foto) {
        this.foto = foto;
    }

    @Override
    public String toString(){
        return nome;
    }
}

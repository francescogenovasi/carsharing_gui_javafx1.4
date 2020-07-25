package it.unisalento.pps1920.carsharing.model;

public class Stazione {
    private int id;
    private String nome;
    private double latitudine;
    private double longitudine;
    private Addetto addetto;

    public Stazione(){

    }

    public Stazione(int id, String nome, double latitudine, double longitudine, Addetto addetto) {
        this.id = id;
        this.nome = nome;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.addetto = addetto;
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }

    public Addetto getAddetto() {
        return addetto;
    }

    public void setAddetto(Addetto addetto) {
        this.addetto = addetto;
    }

    @Override
    public String toString(){
        return nome;
    }
}

package it.unisalento.pps1920.carsharing.model;

public class Mezzo {
    private int id;
    private String targa;
    private Modello modello;
    private int postiDisponibili;
    private String motorizzazione;

    public Mezzo(){

    }

    public Mezzo(int id, String targa, Modello modello, int postiDisponibili, String motorizzazione) {
        this.id = id;
        this.targa = targa;
        this.modello = modello;
        this.postiDisponibili=postiDisponibili;
        this.motorizzazione=motorizzazione;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }

    public Modello getModello() {
        return modello;
    }

    public void setModello(Modello modello) {
        this.modello = modello;
    }

    public String getMotorizzazione() {
        return motorizzazione;
    }

    public void setMotorizzazione(String motorizzazione) {
        this.motorizzazione = motorizzazione;
    }

    public int getPostiDisponibili() {
        return postiDisponibili;
    }

    public void setPostiDisponibili(int postiDisponibili) {
        this.postiDisponibili = postiDisponibili;
    }

    @Override
    public String toString(){
        return modello.getNome() + " " + targa;
    }
}

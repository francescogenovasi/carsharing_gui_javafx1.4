package it.unisalento.pps1920.carsharing.model;

public class Mezzo {
    private int id;
    private String targa;
    private Modello modello;
    private String motorizzazione;
    private String offerta;

    public Mezzo(){

    }

    public Mezzo(int id, String targa, Modello modello, String motorizzazione,String offerta) {
        this.id = id;
        this.targa = targa;
        this.modello = modello;
        this.motorizzazione=motorizzazione;
        this.offerta=offerta;
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

    public String getOfferta() {
        return offerta;
    }

    public void setOfferta(String offerta) {
        this.offerta = offerta;
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

    @Override
    public String toString(){
        return modello.getNome() + " " + targa;
    }
}

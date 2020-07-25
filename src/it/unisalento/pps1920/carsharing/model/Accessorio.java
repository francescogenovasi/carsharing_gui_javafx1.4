package it.unisalento.pps1920.carsharing.model;

public class Accessorio {
    private int id;
    private String nome;
    int postiOccupati;
    float costo;

    public Accessorio(){

    }
    public Accessorio(int id, String nome, int postiOccupati, float costo) {
        this.id = id;
        this.nome = nome;
        this.postiOccupati = postiOccupati;
        this.costo = costo;
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

    public int getPostiOccupati() {
        return postiOccupati;
    }

    public void setPostiOccupati(int postiOccupati) {
        this.postiOccupati = postiOccupati;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }
}

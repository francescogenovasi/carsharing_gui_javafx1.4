package it.unisalento.pps1920.carsharing.model;

import java.util.Date;

public class MezzoDaPreparare {
    private int id;
    private Mezzo mezzo;
    private Date dataInizio;
    private int postiOccupati;
    private Date dataFine;
    private String stato;


    public MezzoDaPreparare(int id, Mezzo mezzo, Date dataInizio, int postiOccupati, Date dataFine, String stato) {
        this.id = id;
        this.mezzo=mezzo;
        this.dataFine = dataFine;
        this.postiOccupati=postiOccupati;
        this.dataInizio=dataInizio;
        this.stato=stato;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public void setPostiOccupati(int postiOccupati) {
        this.postiOccupati = postiOccupati;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public int getPostiOccupati() {
        return postiOccupati;
    }

    public Date getDataFine() {
        return dataFine;
    }
}

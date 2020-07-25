package it.unisalento.pps1920.carsharing.model;

import java.util.Date;

public class TabConfermaRichieste {
    private int idRichiesta;
    private Cliente proponente;
    private Cliente richiedente;
    private Date dataInizio;
    private Date dataFine;
    private Stazione partenza;
    private Stazione arrivo;
    private Localita localita;
    private Mezzo mezzo;
    private int numPostiOccupati;

    public TabConfermaRichieste(){

    }

    public TabConfermaRichieste(int idRichiesta, Cliente proponente, Cliente richiedente, Date dataInizio, Date dataFine, Stazione partenza, Stazione arrivo, Localita localita, int numPostiOccupati, Mezzo mezzo) {
        this.proponente = proponente;
        this.richiedente = richiedente;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.partenza = partenza;
        this.arrivo = arrivo;
        this.localita = localita;
        this.numPostiOccupati = numPostiOccupati;
        this.mezzo = mezzo;
    }

    public int getIdRichiesta() {
        return idRichiesta;
    }

    public void setIdRichiesta(int idRichiesta) {
        this.idRichiesta = idRichiesta;
    }

    public Cliente getProponente() {
        return proponente;
    }

    public void setProponente(Cliente proponente) {
        this.proponente = proponente;
    }

    public Cliente getRichiedente() {
        return richiedente;
    }

    public void setRichiedente(Cliente richiedente) {
        this.richiedente = richiedente;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public Stazione getPartenza() {
        return partenza;
    }

    public void setPartenza(Stazione partenza) {
        this.partenza = partenza;
    }

    public Stazione getArrivo() {
        return arrivo;
    }

    public void setArrivo(Stazione arrivo) {
        this.arrivo = arrivo;
    }

    public Localita getLocalita() {
        return localita;
    }

    public void setLocalita(Localita localita) {
        this.localita = localita;
    }

    public int getNumPostiOccupati() {
        return numPostiOccupati;
    }

    public void setNumPostiOccupati(int numPostiOccupati) {
        this.numPostiOccupati = numPostiOccupati;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }
}

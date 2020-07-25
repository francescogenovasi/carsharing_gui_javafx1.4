package it.unisalento.pps1920.carsharing.model;

import java.util.Date;

public class Prenotazione {

    private int id;
    private Date data;
    private Cliente cliente;
    private Mezzo mezzo;
    private int numPostiOccupati;
    private Stazione partenza;
    private Stazione arrivo;
    private Localita localita;
    private Date dataInizio;
    private Date dataFine;
    private int idPropostaCondivisione;
    private boolean pagamento;
    private boolean pronta;

    public Prenotazione(){

    }

    public Prenotazione(int id, Date data, Cliente cliente, Mezzo mezzo, int numPostiOccupati, Stazione partenza, Stazione arrivo, Localita localita, int idPropostaCondivisione, boolean pagamento, boolean pronta) {
        this.id = id;
        this.data = data;
        this.cliente = cliente;
        this.mezzo = mezzo;
        this.numPostiOccupati = numPostiOccupati;
        this.partenza = partenza;
        this.arrivo = arrivo;
        this.localita = localita;
        this.idPropostaCondivisione = idPropostaCondivisione;
        this.pagamento = pagamento;
        this.pronta = pronta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    public int getNumPostiOccupati() {
        return numPostiOccupati;
    }

    public void setNumPostiOccupati(int numPostiOccupati) {
        this.numPostiOccupati = numPostiOccupati;
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

    public boolean isPagamento() {
        return pagamento;
    }

    public void setPagamento(boolean pagamento) {
        this.pagamento = pagamento;
    }

    public boolean isPronta() {
        return pronta;
    }

    public void setPronta(boolean pronta) {
        this.pronta = pronta;
    }

    public int getIdPropostaCondivisione() {
        return idPropostaCondivisione;
    }

    public void setIdPropostaCondivisione(int idPropostaCondivisione) {
        this.idPropostaCondivisione = idPropostaCondivisione;
    }
}

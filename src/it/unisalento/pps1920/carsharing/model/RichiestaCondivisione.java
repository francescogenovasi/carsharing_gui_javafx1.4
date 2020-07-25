package it.unisalento.pps1920.carsharing.model;

import java.util.Date;

public class RichiestaCondivisione {
    private int id;
    private Date data;
    private PropostaCondivisione proposta;
    private Cliente cliente;
    private int numPostiRichiesti;
    private String stato;

    public RichiestaCondivisione (){

    }

    public RichiestaCondivisione(int id, Date data, PropostaCondivisione proposta, Cliente cliente, int numPostiRichiesti, String stato) {
        this.id = id;
        this.data = data;
        this.proposta = proposta;
        this.cliente = cliente;
        this.numPostiRichiesti = numPostiRichiesti;
        this.stato = stato;
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

    public PropostaCondivisione getProposta() {
        return proposta;
    }

    public void setProposta(PropostaCondivisione proposta) {
        this.proposta = proposta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public int getNumPostiRichiesti() {
        return numPostiRichiesti;
    }

    public void setNumPostiRichiesti(int numPostiRichiesti) {
        this.numPostiRichiesti = numPostiRichiesti;
    }
}

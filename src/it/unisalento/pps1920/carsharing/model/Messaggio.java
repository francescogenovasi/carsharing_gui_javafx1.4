package it.unisalento.pps1920.carsharing.model;

public class Messaggio {
    private int idmessaggi;
    private String testo;
    private Utente mittente;
    private Utente destinatario;
    private String stato;

    public Messaggio(){}

    public Messaggio(int idmessaggi, String testo, Utente mittente, Utente destinatario, String stato){
        this.idmessaggi=idmessaggi;
        this.testo=testo;
        this.destinatario=destinatario;
        this.mittente=mittente;
        this.stato=stato;
    }

    public int getIdmessaggi() {
        return idmessaggi;
    }

    public void setIdmessaggi(int idmessaggi) {
        this.idmessaggi = idmessaggi;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public Utente getMittente() {
        return mittente;
    }

    public void setMittente(Utente mittente) {
        this.mittente = mittente;
    }

    public Utente getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Utente destinatario) {
        this.destinatario = destinatario;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }
}

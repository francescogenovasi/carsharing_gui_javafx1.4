package it.unisalento.pps1920.carsharing.model;

public class Addetto extends Utente {

    private int idstazione;

    public Addetto() {
    }

    public Addetto(int id, String username, String password, String email, int idstazione) {
        super(id, username, password, email);
        this.idstazione = idstazione;
    }

    public int getIdstazione() {
        return idstazione;
    }

    public void setIdstazione(int idstazione) {
        this.idstazione = idstazione;
    }
}

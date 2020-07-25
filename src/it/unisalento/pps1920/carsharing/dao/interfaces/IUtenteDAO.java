package it.unisalento.pps1920.carsharing.dao.interfaces;
import it.unisalento.pps1920.carsharing.model.Utente;

public interface IUtenteDAO extends IBaseDAO{
    public boolean ricercaUsername(String username);
    public boolean ricercaEmail(String email);
    public Utente ricercaLog(String username, String password);
    public boolean salvaRegistrazioneUtente(Utente u);
}

package it.unisalento.pps1920.carsharing.business;

import it.unisalento.pps1920.carsharing.dao.mysql.MezzoDAO;
import it.unisalento.pps1920.carsharing.model.Mezzo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MezzoBusiness {
    private static MezzoBusiness instance;

    public static synchronized MezzoBusiness getInstance() {
        if (instance == null) {
            instance = new MezzoBusiness();
        }
        return instance;
    }

    private MezzoBusiness() {
    }

    public boolean salvaAggiuntaMezzo(Mezzo m) {
        boolean res = new MezzoDAO().salvaMezzo(m);
        return res;
    }

    public int findIdMezzo(String targa) {
        return new MezzoDAO().findMezzoId(targa);
    }

    public boolean checkTarga(String targaa) {
        Matcher m = Pattern.compile("[A-Z][A-Z]([A-Z]|\\d)\\d\\d").matcher(targaa);
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }
}
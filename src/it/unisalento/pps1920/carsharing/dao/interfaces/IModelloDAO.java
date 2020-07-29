package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.Modello;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.ArrayList;

public interface IModelloDAO extends IBaseDAO<Modello> {
    public Image getFoto(int id) throws IOException;
    public boolean salvaModello(Modello m);
    public int findModelloId(String nome);
}

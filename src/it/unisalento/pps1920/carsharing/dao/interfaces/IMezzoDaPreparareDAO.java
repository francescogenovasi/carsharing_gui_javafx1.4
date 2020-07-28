package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.MezzoDaPreparare;

import java.io.IOException;
import java.util.ArrayList;

public interface IMezzoDaPreparareDAO extends IBaseDAO<MezzoDaPreparare> {

    public ArrayList<MezzoDaPreparare> getMezziPronti() throws IOException;
    public boolean mezzoPartito(int id);
    public ArrayList<MezzoDaPreparare> getMezziDaPreparare() throws IOException;
    public boolean mezzoPronto(int id);
}

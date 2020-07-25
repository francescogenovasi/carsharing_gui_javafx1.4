package it.unisalento.pps1920.carsharing.dao.interfaces;

import java.io.IOException;
import java.util.ArrayList;

public interface IBaseDAO<T>{
    public T findById(int id) throws IOException;

    public ArrayList<T> findAll() throws IOException;

}

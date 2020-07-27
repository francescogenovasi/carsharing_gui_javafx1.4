package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.Cliente;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.IOException;

public interface IClienteDAO extends IBaseDAO<Cliente> {
    public boolean checkCliente(int id);
    public boolean salvaRegistrazioneCliente(Cliente c);
    public Image getFoto(int id) throws IOException;
}

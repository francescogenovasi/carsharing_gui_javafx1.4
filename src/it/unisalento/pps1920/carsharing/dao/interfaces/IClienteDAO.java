package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.Cliente;

public interface IClienteDAO extends IBaseDAO<Cliente> {
    public boolean checkCliente(int id);
    public boolean salvaRegistrazioneCliente(Cliente c);
}

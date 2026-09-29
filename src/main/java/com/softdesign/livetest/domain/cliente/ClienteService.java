package com.softdesign.livetest.domain.cliente;

import java.util.List;

public interface ClienteService {

    Cliente create(Cliente cliente);

    Cliente update(Cliente cliente);

    Cliente getById(String clientId);

    List<Cliente> listAll();

}

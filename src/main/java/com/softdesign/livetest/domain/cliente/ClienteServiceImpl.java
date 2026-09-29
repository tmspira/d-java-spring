package com.softdesign.livetest.domain.cliente;

import org.springframework.stereotype.Service;

import com.softdesign.livetest.api.exception.ServiceException;
import com.softdesign.livetest.repository.cliente.ClienteMongoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import static com.softdesign.livetest.api.exception.ApplicationError.CLIENT_NOT_FOUND;


@Service
public class ClienteServiceImpl implements ClienteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClienteServiceImpl.class);

    private final ClienteMongoRepository clienteMongoRepository;


    public ClienteServiceImpl(ClienteMongoRepository clienteMongoRepository) {
        this.clienteMongoRepository = clienteMongoRepository;
    }

    @Override
    public Cliente create(Cliente cliente) {
        return clienteMongoRepository.insert(cliente);
    }


    @Override
    public Cliente update(Cliente cliente) {
        var optionalCliente = clienteMongoRepository.findById(cliente.id());

        if (optionalCliente.isEmpty()) {
            throw new ServiceException(CLIENT_NOT_FOUND,cliente.id());
        }

        return clienteMongoRepository.save(cliente);
    }

    @Override
    public Cliente getById(String clientId) {
        var optionalCliente = clienteMongoRepository.findById(clientId);

        return optionalCliente.orElseThrow(() ->
                new ServiceException(CLIENT_NOT_FOUND, clientId));
    }

    @Override
    public List<Cliente> listAll() {
        var clientes = clienteMongoRepository.findAll();

        return clientes;
    }

}
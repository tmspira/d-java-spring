package com.softdesign.livetest.domain.venda;

import com.softdesign.livetest.api.exception.ServiceException;
import com.softdesign.livetest.repository.venda.VendaMongoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.softdesign.livetest.api.exception.ApplicationError.SALE_NOT_FOUND;


@Service
public class VendaServiceImpl implements VendaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VendaServiceImpl.class);

    private final VendaMongoRepository vendaMongoRepository;

    public VendaServiceImpl(VendaMongoRepository vendaMongoRepository) {
        this.vendaMongoRepository = vendaMongoRepository;
    }

    @Override
    public Venda create(Venda venda) {
        return vendaMongoRepository.insert(venda);
    }

    @Override
    public Venda update(Venda venda) {
        var optionalProduto = vendaMongoRepository.findById(venda.id());

        if (optionalProduto.isEmpty()) {
            throw new ServiceException(SALE_NOT_FOUND, venda.id());
        }

        return vendaMongoRepository.save(venda);
    }

    @Override
    public Venda getById(String vendaId) {
        var optionalvenda = vendaMongoRepository.findById(vendaId);

        return optionalvenda.orElseThrow(() ->
                new ServiceException(SALE_NOT_FOUND, vendaId));
    }

    @Override
    public List<Venda> listAll() {
        var vendas = vendaMongoRepository.findAll();

        return vendas;
    }

}


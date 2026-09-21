package com.softdesign.livetest.migration.mongo;

import com.softdesign.livetest.domain.produto.Produto;
import com.softdesign.livetest.repository.cliente.ClienteMongoRepository;
import com.softdesign.livetest.repository.produto.ProdutoMongoRepository;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;

import java.math.BigDecimal;

@ChangeUnit(id = "0002", order = "2", author = "ricardo.camelo")
public class DatabaseChangeLog0002 {

    @Execution
    public void execution(ProdutoMongoRepository produtoMongoRepository) {
        produtoMongoRepository.save(new Produto(null, "Sacolé", "Sabor laranja", new BigDecimal("3.00")));
        produtoMongoRepository.save(new Produto(null, "Parafuso", "Tamanho médio", new BigDecimal("1.00")));
        produtoMongoRepository.save(new Produto(null, "Ovos", "Duzia de ovos", new BigDecimal("10.00")));
    }

    @RollbackExecution
    public void rollbackExecution(ClienteMongoRepository clienteMongoRepository) {
    }
}

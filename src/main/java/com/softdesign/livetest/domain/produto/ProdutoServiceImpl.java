package com.softdesign.livetest.domain.produto;

import com.softdesign.livetest.api.exception.ServiceException;
import com.softdesign.livetest.repository.produto.ProdutoMongoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.softdesign.livetest.api.exception.ApplicationError.PRODUCT_NOT_FOUND;


@Service
public class ProdutoServiceImpl implements ProdutoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoServiceImpl.class);

    private final ProdutoMongoRepository produtoMongoRepository;

    public ProdutoServiceImpl(ProdutoMongoRepository produtoMongoRepository) {
        this.produtoMongoRepository = produtoMongoRepository;
    }

    @Override
    public Produto create(Produto produto) {
        return produtoMongoRepository.insert(produto);
    }

    @Override
    public Produto update(Produto produto) {
        var optionalProduto = produtoMongoRepository.findById(produto.id());

        if (optionalProduto.isEmpty()) {
            throw new ServiceException(PRODUCT_NOT_FOUND, produto.id());
        }

        return produtoMongoRepository.save(produto);
    }

    @Override
    public Produto getById(String produtoId) {
        var optionalProduto = produtoMongoRepository.findById(produtoId);

        return optionalProduto.orElseThrow(() ->
                new ServiceException(PRODUCT_NOT_FOUND));
    }

    @Override
    public List<Produto> findAllByIds(List<String> produtoIds) {
        return produtoMongoRepository.findAllById(produtoIds);
    }

    @Override
    public List<Produto> listAll() {
        var produtos = produtoMongoRepository.findAll();

        return produtos;
    }

}

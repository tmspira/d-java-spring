package com.softdesign.livetest.domain.produto;


import java.util.List;

public interface ProdutoService {

    Produto create(Produto produto);

    Produto update(Produto produto);

    Produto getById(String produtoId);

    List<Produto> findAllByIds(List<String> produtoIds);

    List<Produto> listAll();

}

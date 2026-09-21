package com.softdesign.livetest.api.produto;

import com.softdesign.livetest.domain.produto.Produto;

import java.math.BigDecimal;
import java.util.List;

public record ProdutoDTO(String id, String nome, String descricao, BigDecimal valor) {


    public Produto toProduto() {
        return new Produto(this.id(), this.nome(), this.descricao(), this.valor());
    }

    public static ProdutoDTO from(Produto produto) {
        return new ProdutoDTO(produto.id(), produto.nome(), produto.descricao(), produto.valor());
    }

    public static List<ProdutoDTO> from(List<Produto> produtos) {
        return produtos.stream().map(ProdutoDTO::from).toList();
    }

}

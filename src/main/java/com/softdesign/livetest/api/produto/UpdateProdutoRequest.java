package com.softdesign.livetest.api.produto;

import com.softdesign.livetest.domain.produto.Produto;

import java.math.BigDecimal;

public record UpdateProdutoRequest(String nome, String descricao, BigDecimal valor) {

    public Produto toProduto(String id) {
        return new Produto(id, this.nome(), this.descricao(), this.valor());
    }

}

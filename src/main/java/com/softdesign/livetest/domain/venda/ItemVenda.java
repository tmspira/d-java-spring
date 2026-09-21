package com.softdesign.livetest.domain.venda;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record ItemVenda(@JsonProperty("id")String produtoId, @JsonProperty("qtd")Integer quantidade, BigDecimal valorTotal) {

    public ItemVenda withQuantidade(Integer quantidade) {
        return new ItemVenda(this.produtoId, quantidade, this.valorTotal);
    }

    public ItemVenda withValorTotal(BigDecimal valorTotal) {
        return new ItemVenda(this.produtoId, this.quantidade, valorTotal);
    }

}

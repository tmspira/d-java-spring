package com.softdesign.livetest.api.venda;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.softdesign.livetest.domain.venda.ItemVenda;

import java.math.BigDecimal;

public record ItemVendaRequest(@JsonProperty("id") String produtoId,
                               @JsonProperty("qtd") Integer quantidade) {

    public ItemVenda toItemVenda() {
        return new ItemVenda(
                produtoId,
                quantidade,
                BigDecimal.ZERO
        );
    }

}

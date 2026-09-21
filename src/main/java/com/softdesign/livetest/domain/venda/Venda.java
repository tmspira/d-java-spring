package com.softdesign.livetest.domain.venda;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.List;

public record Venda (@Id String id, String clienteId, BigDecimal valorTotal, List<ItemVenda> items) {

    public Venda withValorTotal (BigDecimal valorTotal) {
        return new Venda(this.id, this.clienteId, valorTotal, this.items);
    }

}

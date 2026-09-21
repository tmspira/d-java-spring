package com.softdesign.livetest.api.venda;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.softdesign.livetest.domain.venda.ItemVenda;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record CreateVendaRequest(String clienteId, @JsonProperty("products") List<ItemVendaRequest> itens) {

    public List<ItemVenda> toItensVenda() {
        return itens.stream()
                .map(ItemVendaRequest::toItemVenda)
                .map(item -> item.withValorTotal(
                        item.valorTotal() == null
                                ? BigDecimal.ZERO
                                : item.valorTotal()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

}

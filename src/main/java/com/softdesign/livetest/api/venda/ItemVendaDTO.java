package com.softdesign.livetest.api.venda;

import com.softdesign.livetest.domain.venda.ItemVenda;

import java.math.BigDecimal;
import java.util.List;


public record ItemVendaDTO(String produtoId, Integer quantidade, BigDecimal valorTotal) {

    public static ItemVendaDTO from(ItemVenda itemVenda) {
        return new ItemVendaDTO(itemVenda.produtoId(), itemVenda.quantidade(), itemVenda.valorTotal());
    }

    public static List<ItemVendaDTO> from(List<ItemVenda> itemsVenda) {
        return itemsVenda.stream().map(ItemVendaDTO::from).toList();
    }

}

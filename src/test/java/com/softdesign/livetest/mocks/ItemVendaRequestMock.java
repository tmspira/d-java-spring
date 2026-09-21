package com.softdesign.livetest.mocks;

import com.softdesign.livetest.api.venda.ItemVendaRequest;

public class ItemVendaRequestMock {

    public static ItemVendaRequest criar() {
        return criar(
                "123",
                2
        );
    }

    public static ItemVendaRequest criar(String produtoId, Integer quantidade) {
        return new ItemVendaRequest(
                produtoId,
                quantidade
        );
    }
}

package com.softdesign.livetest.mocks;

import com.softdesign.livetest.api.venda.CreateVendaRequest;
import com.softdesign.livetest.api.venda.ItemVendaRequest;

import java.util.List;

public class CreateVendaRequestMock {

    public static CreateVendaRequest criar() {
        return criar(
                "6aac0b75d92d4b4273472f35",
                List.of(ItemVendaRequestMock.criar())
        );
    }

    public static CreateVendaRequest criar(
            String clienteId,
            List<ItemVendaRequest> itens) {

        return new CreateVendaRequest(
                clienteId,
                itens
        );
    }
}

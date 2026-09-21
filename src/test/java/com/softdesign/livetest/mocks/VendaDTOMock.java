package com.softdesign.livetest.mocks;

import com.softdesign.livetest.api.venda.VendaDTO;

import java.math.BigDecimal;
import java.util.List;

public class VendaDTOMock {

    public static VendaDTO criar() {
        return new VendaDTO(
                "123",
                "123",
                new BigDecimal("3.00"),
                List.of(ItemVendaDTOMock.criar())
        );
    }

    public static VendaDTO criarComDoisItens() {
        return new VendaDTO(
                "123",
                "123",
                new BigDecimal("6.00"),
                List.of(
                        ItemVendaDTOMock.criar(),
                        ItemVendaDTOMock.criar()
                )
        );
    }
}



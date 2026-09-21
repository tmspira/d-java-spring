package com.softdesign.livetest.mocks;

import com.softdesign.livetest.api.venda.ItemVendaDTO;

import java.math.BigDecimal;

public class ItemVendaDTOMock {

    public static ItemVendaDTO criar() {
        return new ItemVendaDTO(
                "123",
                2,
                new BigDecimal("3.00")
        );
    }
}





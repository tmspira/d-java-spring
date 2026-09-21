package com.softdesign.livetest.mocks;

import com.softdesign.livetest.domain.venda.ItemVenda;

import java.math.BigDecimal;

public class ItemVendaMock {

    public static ItemVenda criar() {
        return new ItemVenda(
                "123",
                2,
                new BigDecimal("3.00")
        );
    }
}



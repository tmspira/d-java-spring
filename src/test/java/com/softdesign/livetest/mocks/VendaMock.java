package com.softdesign.livetest.mocks;

import com.softdesign.livetest.domain.venda.Venda;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VendaMock {

    public static Venda criar() {
        return new Venda(
                "123",
                "123",
                new BigDecimal("3.00"),
                new ArrayList<>( java.util.List.of(ItemVendaMock.criar()))
        );
    }

    public static Venda criarComDoisItens() {
        return new Venda(
                "123",
                "123",
                new BigDecimal("6.00"),
                List.of(
                        ItemVendaMock.criar(),
                        ItemVendaMock.criar()
                )
        );
    }
}



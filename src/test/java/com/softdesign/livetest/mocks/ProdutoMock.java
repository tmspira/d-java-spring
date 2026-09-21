package com.softdesign.livetest.mocks;

import com.softdesign.livetest.domain.produto.Produto;

import java.math.BigDecimal;

public class ProdutoMock {

    public static Produto criar() {
        return new Produto(
                "123",
                "Macarrão",
                "Lamen",
                new BigDecimal("1.50")
        );
    }
}



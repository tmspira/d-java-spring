package com.softdesign.livetest.mocks;

import com.softdesign.livetest.api.produto.ProdutoDTO;

import java.math.BigDecimal;

public class ProdutoDTOMock {

    public static ProdutoDTO criar() {
        return new ProdutoDTO(
                "123",
                "Macarrão",
                "Lamen",
                new BigDecimal("1.50")
        );
    }
}


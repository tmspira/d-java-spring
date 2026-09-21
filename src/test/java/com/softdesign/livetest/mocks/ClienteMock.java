package com.softdesign.livetest.mocks;

import com.softdesign.livetest.domain.cliente.Cliente;
import com.softdesign.livetest.domain.cliente.Endereco;

public class ClienteMock {

    public static Cliente criar() {
        return new Cliente("123", "João", 33,
                new Endereco(
                        "Rua da esperança",
                        "33",
                        "123"
                )
        );
    }
}



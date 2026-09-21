package com.softdesign.livetest.mocks;

import com.softdesign.livetest.domain.cliente.Endereco;


public class EnderecoMock {

    public static Endereco criar() {
        return new Endereco(
                "Rua da esperança",
                "33",
                "123"
        );
    }
}





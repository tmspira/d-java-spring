package com.softdesign.livetest.mocks;


import com.softdesign.livetest.api.cliente.EnderecoDTO;

public class EnderecoDTOMock {

    public static EnderecoDTO criar() {
        return new EnderecoDTO(
                "Rua da esperança",
                "33",
                "123"
        );
    }
}



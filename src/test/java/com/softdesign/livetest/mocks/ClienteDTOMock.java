package com.softdesign.livetest.mocks;

import com.softdesign.livetest.api.cliente.ClienteDTO;
import com.softdesign.livetest.api.cliente.EnderecoDTO;

public class ClienteDTOMock {

    public static ClienteDTO criar() {
        return new ClienteDTO("123", "João", 33,
                new EnderecoDTO(
                        "Rua da esperança",
                        "33",
                        "123"
                )
        );
    }
}

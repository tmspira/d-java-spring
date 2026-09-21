package com.softdesign.livetest.api.cliente;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.softdesign.livetest.domain.cliente.Cliente;

public record UpdateClienteRequest(String nome, Integer idade, @JsonProperty("endereco") EnderecoDTO enderecoDTO) {

    public Cliente toCliente(String id) {
        return new Cliente(id, this.nome, this.idade, this.enderecoDTO.toEndereco());
    }

}

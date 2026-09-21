package com.softdesign.livetest.api.cliente;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.softdesign.livetest.domain.cliente.Cliente;

public record CreateClienteRequest(String nome, Integer idade, @JsonProperty("endereco") EnderecoDTO enderecoDTO) {

    public Cliente toCliente() {
        return new Cliente(null, this.nome, this.idade, this.enderecoDTO.toEndereco());
    }

}

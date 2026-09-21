package com.softdesign.livetest.api.venda;

// Ver possibilidade se deve ser deletado após implantação de endpoint de
// venda com lista de itens
public record AdicionarItemVendaRequest(String produtoId, Integer quantidade) {
}

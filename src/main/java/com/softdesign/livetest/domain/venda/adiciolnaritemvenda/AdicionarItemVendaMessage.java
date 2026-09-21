package com.softdesign.livetest.domain.venda.adiciolnaritemvenda;

// Ver possibilidade se deve ser deletado após implantação de endpoint de
// venda com lista de itens
public record AdicionarItemVendaMessage(String vendId, String produtoId, Integer quantidade) {
}

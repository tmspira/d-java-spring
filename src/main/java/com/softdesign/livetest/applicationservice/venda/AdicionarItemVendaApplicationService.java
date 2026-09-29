package com.softdesign.livetest.applicationservice.venda;

// Ver possibilidade se deve ser deletado após implantação de endpoint de
// venda com lista de itens

public interface AdicionarItemVendaApplicationService {

    void executar(String vendId, String produtoId, Integer quantidade);

}

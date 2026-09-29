package com.softdesign.livetest.domain.venda;


import java.util.List;

public interface VendaService {

    Venda create(Venda venda);

    Venda update(Venda venda);

    Venda getById(String vendaId);

    List<Venda> listAll();

}

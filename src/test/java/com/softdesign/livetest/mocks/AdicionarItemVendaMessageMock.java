package com.softdesign.livetest.mocks;

import com.softdesign.livetest.domain.venda.adiciolnaritemvenda.AdicionarItemVendaMessage;

public class AdicionarItemVendaMessageMock {

    public static AdicionarItemVendaMessage criar() {
        return new AdicionarItemVendaMessage( "123", "123", 2 );
    }

}

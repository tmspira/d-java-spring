package com.softdesign.livetest.applicationservice.venda;

import com.softdesign.livetest.api.venda.CreateVendaRequest;
import com.softdesign.livetest.domain.venda.Venda;

public interface CreateVendaApplicationService {

    Venda execute(CreateVendaRequest request);

}

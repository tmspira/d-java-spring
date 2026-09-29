package com.softdesign.livetest.api.venda;

import com.softdesign.livetest.applicationservice.venda.CreateVendaApplicationService;
import com.softdesign.livetest.domain.venda.VendaService;
import com.softdesign.livetest.domain.venda.adiciolnaritemvenda.AdicionarItemVendaMessage;
import com.softdesign.livetest.domain.venda.adiciolnaritemvenda.AdicionarItemVendaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
public class VendasController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VendasController.class);

    private final VendaService vendaService;

    private final CreateVendaApplicationService createVendaApplicationService;

    private final AdicionarItemVendaProducer adicionarItemVendaProducer;

    public VendasController(
            VendaService vendaService,
            CreateVendaApplicationService createVendaApplicationService,
            AdicionarItemVendaProducer adicionarItemVendaProducer) {
        this.vendaService = vendaService;
        this.createVendaApplicationService = createVendaApplicationService;
        this.adicionarItemVendaProducer = adicionarItemVendaProducer;
    }

    @GetMapping("/vendas")
    public ResponseEntity<List<VendaDTO>> listAll() {
        var vendas = vendaService.listAll();
        return ResponseEntity.ok().body(VendaDTO.from(vendas));
    }

    @GetMapping("/vendas/{id}")
    public ResponseEntity<VendaDTO> get(@PathVariable("id") String id) {
        var venda = vendaService.getById(id);
        return ResponseEntity.ok().body(VendaDTO.from(venda));
    }

    @PostMapping("/vendas")
    public VendaDTO create(@RequestBody CreateVendaRequest createVendaRequest) {
        var venda = createVendaApplicationService.execute(createVendaRequest);
        return VendaDTO.from(venda);
    }

    @PostMapping("/vendas/{id}/item")
    public ResponseEntity<Void> addItemVenda(@PathVariable("id") String id,
                                             @RequestBody AdicionarItemVendaRequest adicionarItemVendaRequest) {
        adicionarItemVendaProducer.send(new AdicionarItemVendaMessage(
                id,
                adicionarItemVendaRequest.produtoId(),
                adicionarItemVendaRequest.quantidade()));
        return ResponseEntity.accepted().build();

    }

}

package com.softdesign.livetest.applicationservice.venda;

import com.softdesign.livetest.api.venda.CreateVendaRequest;
import com.softdesign.livetest.api.venda.ItemVendaRequest;
import com.softdesign.livetest.domain.cliente.ClienteService;
import com.softdesign.livetest.domain.produto.ProdutoNotFound;
import com.softdesign.livetest.domain.produto.ProdutoService;
import com.softdesign.livetest.domain.venda.ItemVenda;
import com.softdesign.livetest.domain.venda.QuantidadeInvalida;
import com.softdesign.livetest.domain.venda.Venda;
import com.softdesign.livetest.domain.venda.VendaService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import static java.util.Objects.isNull;

@Service
public class CreateVendaApplicationService {

    private final VendaService vendaService;

    private final ClienteService clienteService;

    private final ProdutoService produtoService;

    public CreateVendaApplicationService(VendaService vendaService, ClienteService clienteService, ProdutoService produtoService) {
        this.vendaService = vendaService;
        this.clienteService = clienteService;
        this.produtoService = produtoService;
    }

    public Venda execute(CreateVendaRequest request) {

        validarItensVazios(request.itens());
        var itens = request.toItensVenda();
        validarQuantidadeItens (itens);
        preencheValorDosItensComProdutos(itens);

        itens = tratarItensRepetidosNalista(itens);
        var totalVenda = calculaVenda(itens);
        var cliente = clienteService.getById(request.clienteId());

        var venda = new Venda(null, cliente.id(), totalVenda, itens);

        return vendaService.create(venda);
    }

    private BigDecimal calculaVenda(List<ItemVenda> itens) {
        return itens.stream()
                .map(ItemVenda::valorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void validarItensVazios(List<ItemVendaRequest> itens) {
        if (isNull(itens) || itens.stream().anyMatch(item -> isNull(item.produtoId()) || item.produtoId().isBlank())) {
            throw new ProdutoNotFound("ID de algum produto não encontrado");
        }
    }

    private void preencheValorDosItensComProdutos(List<ItemVenda> itens) {
        itens.replaceAll(item -> {
            var produto = produtoService.getById(item.produtoId());
            return item.withValorTotal(produto.valor().multiply(BigDecimal.valueOf(item.quantidade())));
        });
    }

    private void validarQuantidadeItens (List<ItemVenda> itens) {
        itens.forEach (item -> {
            if (isNull(item.quantidade()) || item.quantidade() <= 0) {
                throw new QuantidadeInvalida("Quantidade do produto " + item.produtoId() + " deve ser maior que zero");
            }
        });
    }

    private List<ItemVenda> tratarItensRepetidosNalista (List<ItemVenda> itens) {
        Map<String, ItemVenda> itensConsolidados = new LinkedHashMap<>();
        itens.forEach(item ->
                itensConsolidados.merge(
                        item.produtoId(),
                        item,
                        (existente, repetido) -> existente
                                .withQuantidade(existente.quantidade() + repetido.quantidade())
                                .withValorTotal(existente.valorTotal().add(repetido.valorTotal()))
                ));
        return new ArrayList<>((itensConsolidados.values()));
    }

}

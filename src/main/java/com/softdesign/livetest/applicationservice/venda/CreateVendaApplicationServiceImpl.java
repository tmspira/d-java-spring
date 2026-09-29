package com.softdesign.livetest.applicationservice.venda;

import com.softdesign.livetest.api.exception.ServiceException;
import com.softdesign.livetest.api.venda.CreateVendaRequest;
import com.softdesign.livetest.api.venda.ItemVendaRequest;
import com.softdesign.livetest.domain.cliente.ClienteService;
import com.softdesign.livetest.domain.produto.ProdutoService;
import com.softdesign.livetest.domain.venda.ItemVenda;
import com.softdesign.livetest.domain.venda.Venda;
import com.softdesign.livetest.domain.venda.VendaService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import static com.softdesign.livetest.api.exception.ApplicationError.INVALID_QUANTITY;
import static com.softdesign.livetest.api.exception.ApplicationError.PRODUCT_NOT_FOUND;
import static java.util.Objects.isNull;

@Service
public class CreateVendaApplicationServiceImpl implements CreateVendaApplicationService {

    private final VendaService vendaService;

    private final ClienteService clienteService;

    private final ProdutoService produtoService;

    public CreateVendaApplicationServiceImpl(VendaService vendaService, ClienteService clienteService, ProdutoService produtoService) {
        this.vendaService = vendaService;
        this.clienteService = clienteService;
        this.produtoService = produtoService;
    }

    @Override
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
            throw new ServiceException(PRODUCT_NOT_FOUND);
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
                throw new ServiceException(INVALID_QUANTITY, item.produtoId());
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

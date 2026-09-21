package com.softdesign.livetest.applicationservice.venda;


import com.softdesign.livetest.domain.produto.ProdutoService;
import com.softdesign.livetest.domain.venda.Venda;
import com.softdesign.livetest.domain.venda.VendaService;
import com.softdesign.livetest.mocks.ProdutoMock;
import com.softdesign.livetest.mocks.VendaMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdicionarItemVendaApplicationServiceTest {

    @Mock
    private VendaService vendaService;

    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private AdicionarItemVendaApplicationService adicionarItemVendaApplicationService;

    @Test
    void deveAdicionarNovoItemNaVenda() {
        var venda = VendaMock.criar();
        venda.items().clear();

        var produto = ProdutoMock.criar();

        when(vendaService.getById(venda.id()))
                .thenReturn(venda);
        when(produtoService.getById(produto.id()))
                .thenReturn(produto);

        adicionarItemVendaApplicationService.executar(venda.id(), produto.id(), 2);

        assertEquals(1, venda.items().size());
        assertEquals(produto.id(), venda.items().get(0).produtoId());
        assertEquals(2, venda.items().get(0).quantidade());
        assertEquals(new BigDecimal("3.00"), venda.items().get(0).valorTotal());
        assertEquals(new BigDecimal("3.00"), venda.valorTotal());

        verify(vendaService).getById(venda.id());
        verify(produtoService).getById(produto.id());
        verify(vendaService).update(venda);
    }


    @Test void deveAdicionarQuantidadeAoItemExistente() {
        var venda = VendaMock.criar();
        var produto = ProdutoMock.criar();

        when(vendaService.getById(venda.id()))
                .thenReturn(venda);
        when(produtoService.getById(produto.id()))
                .thenReturn(produto);

        adicionarItemVendaApplicationService.executar( venda.id(), produto.id(), 2 );

        var item = venda.items().get(0);

        assertEquals(produto.id(), item.produtoId());
        assertEquals(4, item.quantidade());
        assertEquals( new BigDecimal("6.00"), item.valorTotal() );

        verify(vendaService).getById(venda.id());
        verify(produtoService).getById(produto.id());

        var captor = ArgumentCaptor.forClass(Venda.class);

        verify(vendaService).update(captor.capture());

        var vendaAtualizada = captor.getValue();

        assertEquals(venda.id(), vendaAtualizada.id());
        assertEquals(venda.clienteId(), vendaAtualizada.clienteId());
        assertEquals( new BigDecimal("6.00"), vendaAtualizada.valorTotal() );
        assertEquals(1, vendaAtualizada.items().size());
        assertEquals(4, vendaAtualizada.items().get(0).quantidade());
        assertEquals( new BigDecimal("6.00"), vendaAtualizada.items().get(0).valorTotal() );

    }

    @Test void deveRecalcularValorTotalDaVendaAoAdicionarItem() {
        var venda = VendaMock.criar();
        var produto = ProdutoMock.criar();

        when(vendaService.getById(venda.id()))
                .thenReturn(venda);
        when(produtoService.getById(produto.id()))
                .thenReturn(produto);

        adicionarItemVendaApplicationService.executar( venda.id(), produto.id(), 2 );

        assertEquals( new BigDecimal("6.00"), venda.items().get(0).valorTotal() );

        var captor = ArgumentCaptor.forClass(Venda.class);
        verify(vendaService).update(captor.capture());
        var vendaAtualizada = captor.getValue();

        assertEquals( new BigDecimal("6.00"), vendaAtualizada.valorTotal() );
    }

    @Test
    void deveAtualizarVendaComValorTotalCalculado() {
        var venda = VendaMock.criar();
        var produto = ProdutoMock.criar();

        when(vendaService.getById(venda.id()))
                .thenReturn(venda);
        when(produtoService.getById(produto.id()))
                .thenReturn(produto);

        adicionarItemVendaApplicationService.executar(venda.id(), produto.id(), 2);

        var vendaCaptor = ArgumentCaptor.forClass(Venda.class);

        verify(vendaService).update(vendaCaptor.capture());

        var vendaAtualizada = vendaCaptor.getValue();

        assertEquals(venda.id(), vendaAtualizada.id());
        assertEquals(venda.clienteId(), vendaAtualizada.clienteId());
        assertEquals(new BigDecimal("6.00"), vendaAtualizada.valorTotal());
        assertEquals(1, vendaAtualizada.items().size());
    }
}



package com.softdesign.livetest.applicationservice.venda;

import com.softdesign.livetest.api.exception.ServiceException;
import com.softdesign.livetest.domain.cliente.ClienteService;
import com.softdesign.livetest.domain.produto.ProdutoService;
import com.softdesign.livetest.domain.venda.Venda;
import com.softdesign.livetest.domain.venda.VendaService;
import com.softdesign.livetest.mocks.ClienteMock;
import com.softdesign.livetest.mocks.CreateVendaRequestMock;
import com.softdesign.livetest.mocks.ItemVendaRequestMock;
import com.softdesign.livetest.mocks.ProdutoMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static com.softdesign.livetest.api.exception.ApplicationError.PRODUCT_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateVendaApplicationServiceImplTest {

    @Mock
    private VendaService vendaService;

    @Mock
    private ClienteService clienteService;

    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private CreateVendaApplicationServiceImpl applicationService;

    @Test
    void deveCriarVendaComSucesso() {
        var cliente = ClienteMock.criar();
        var produto = ProdutoMock.criar();

        var request = CreateVendaRequestMock.criar(
                cliente.id(),
                List.of(ItemVendaRequestMock.criar(produto.id(), 2)));

        when(clienteService.getById(cliente.id()))
                .thenReturn(cliente);

        when(produtoService.getById(produto.id()))
                .thenReturn(produto);

        when(vendaService.create(any(Venda.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var resultado = applicationService.execute(request);

        assertNotNull(resultado);
        assertEquals(cliente.id(), resultado.clienteId());
        assertEquals(1, resultado.items().size());

        var item = resultado.items().get(0);

        assertEquals(produto.id(), item.produtoId());
        assertEquals(2, item.quantidade());

        var valorEsperado = produto.valor().multiply(BigDecimal.valueOf(2));

        assertEquals(valorEsperado, item.valorTotal());
        assertEquals(valorEsperado, resultado.valorTotal());

        verify(clienteService).getById(cliente.id());
        verify(produtoService).getById(produto.id());
        verify(vendaService).create(any(Venda.class));
    }

    @Test
    void deveLancarProdutoNotFoundQuandoListaDeItensForNula() {
        var cliente = ClienteMock.criar();

        var request = CreateVendaRequestMock.criar(cliente.id(), null);

        assertThrows(ServiceException.class, () -> applicationService.execute(request));

        verifyNoInteractions(produtoService, clienteService, vendaService);
    }

    @Test
    void deveLancarProdutoNotFoundQuandoProdutoIdForNulo() {
        var cliente = ClienteMock.criar();

        var item = ItemVendaRequestMock.criar(null, 2);

        var request = CreateVendaRequestMock.criar(cliente.id(), List.of(item));

        var exception = assertThrows(ServiceException.class, () -> applicationService.execute(request));

        assertEquals("Produto não encontrado", exception.getMessage());

        verifyNoInteractions(produtoService, clienteService, vendaService);
    }

    @Test
    void deveLancarProdutoNotFoundQuandoProdutoIdForVazio() {
        var cliente = ClienteMock.criar();

        var item = ItemVendaRequestMock.criar("", 2);

        var request = CreateVendaRequestMock.criar(cliente.id(), List.of(item));

        assertThrows(ServiceException.class, () -> applicationService.execute(request));

        verifyNoInteractions(produtoService, clienteService, vendaService);
    }

    @Test
    void deveLancarQuantidadeInvalidaQuandoQuantidadeForNula() {
        var cliente = ClienteMock.criar();
        var produto = ProdutoMock.criar();

        var item = ItemVendaRequestMock.criar(
                produto.id(),
                null
        );

        var request = CreateVendaRequestMock.criar(cliente.id(), List.of(item));

        var exception = assertThrows(ServiceException.class, () -> applicationService.execute(request));

        assertEquals("Quantidade inválida do produto " + produto.id(), exception.getMessage());

        verifyNoInteractions(produtoService, clienteService, vendaService);
    }

    @Test
    void deveLancarQuantidadeInvalidaQuandoQuantidadeForZero() {
        var cliente = ClienteMock.criar();
        var produto = ProdutoMock.criar();

        var request = CreateVendaRequestMock.criar(
                cliente.id(),
                List.of(ItemVendaRequestMock.criar(produto.id(), 0)));

        assertThrows(ServiceException.class, () -> applicationService.execute(request));

        verifyNoInteractions(produtoService, clienteService, vendaService);
    }

    @Test
    void deveLancarQuantidadeInvalidaQuandoQuantidadeForNegativa() {
        var cliente = ClienteMock.criar();
        var produto = ProdutoMock.criar();

        var request = CreateVendaRequestMock.criar(
                cliente.id(),
                List.of(
                        ItemVendaRequestMock.criar(produto.id(), -1)
                )
        );

        assertThrows(
                ServiceException.class,
                () -> applicationService.execute(request)
        );

        verifyNoInteractions(produtoService, clienteService, vendaService);
    }

    @Test
    void deveLancarProdutoNotFoundQuandoProdutoNaoExistir() {
        var cliente = ClienteMock.criar();
        var produto = ProdutoMock.criar();

        var request = CreateVendaRequestMock.criar(
                cliente.id(),
                List.of(
                        ItemVendaRequestMock.criar(produto.id(), 2)
                )
        );

        when(produtoService.getById(produto.id()))
                .thenThrow(new ServiceException(PRODUCT_NOT_FOUND));

        var exception = assertThrows(
                ServiceException.class,
                () -> applicationService.execute(request)
        );

        assertEquals(
                "Produto não encontrado",
                exception.getMessage()
        );

        verify(produtoService).getById(produto.id());
        verifyNoInteractions(clienteService, vendaService);
    }

    @Test
    void deveConsolidarProdutosRepetidos() {
        var cliente = ClienteMock.criar();
        var produto = ProdutoMock.criar();

        var item1 = ItemVendaRequestMock.criar(produto.id(), 2);
        var item2 = ItemVendaRequestMock.criar(produto.id(), 3);

        var request = CreateVendaRequestMock.criar(
                cliente.id(),
                List.of(item1, item2)
        );

        when(clienteService.getById(cliente.id()))
                .thenReturn(cliente);

        when(produtoService.getById(produto.id()))
                .thenReturn(produto);

        when(vendaService.create(any(Venda.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var resultado = applicationService.execute(request);

        assertEquals(1, resultado.items().size());

        var item = resultado.items().get(0);

        assertEquals(produto.id(), item.produtoId());
        assertEquals(5, item.quantidade());

        var valorEsperado = produto.valor()
                .multiply(BigDecimal.valueOf(5));

        assertEquals(valorEsperado, item.valorTotal());
        assertEquals(valorEsperado, resultado.valorTotal());

        verify(produtoService, times(2)).getById(produto.id());
    }
}
package com.softdesign.livetest.domain.produto;

import com.softdesign.livetest.mocks.ProdutoMock;
import com.softdesign.livetest.repository.produto.ProdutoMongoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoMongoRepository produtoMongoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void deveCriarProduto() {
        var produto = ProdutoMock.criar();

        when(produtoMongoRepository.insert(produto))
                .thenReturn(produto);

        var result = produtoService.create(produto);

        assertEquals(produto, result);

        verify(produtoMongoRepository)
                .insert(produto);
    }

    @Test
    void deveAtualizarProduto() {
        var produto = ProdutoMock.criar();

        when(produtoMongoRepository.findById(produto.id()))
                .thenReturn(Optional.of(produto));

        when(produtoMongoRepository.save(produto))
                .thenReturn(produto);

        var result = produtoService.update(produto);

        assertEquals(produto, result);

        verify(produtoMongoRepository)
                .findById(produto.id());

        verify(produtoMongoRepository)
                .save(produto);
    }

    @Test
    void deveLancarExcecaoAoAtualizarProdutoInexistente() {
        var produto = ProdutoMock.criar();

        when(produtoMongoRepository.findById(produto.id()))
                .thenReturn(Optional.empty());

        assertThrows(
                ProdutoNotFound.class,
                () -> produtoService.update(produto)
        );

        verify(produtoMongoRepository)
                .findById(produto.id());
    }

    @Test
    void deveBuscarProdutoPorId() {
        var produto = ProdutoMock.criar();

        when(produtoMongoRepository.findById(produto.id()))
                .thenReturn(Optional.of(produto));

        var result = produtoService.getById(produto.id());

        assertEquals(produto, result);

        verify(produtoMongoRepository)
                .findById(produto.id());
    }

    @Test
    void deveLancarExcecaoAoBuscarProdutoInexistente() {
        var produto = ProdutoMock.criar();

        when(produtoMongoRepository.findById(produto.id()))
                .thenReturn(Optional.empty());

        assertThrows(
                ProdutoNotFound.class,
                () -> produtoService.getById(produto.id())
        );

        verify(produtoMongoRepository)
                .findById(produto.id());
    }

    @Test
    void deveBuscarProdutosPorIds() {
        var produto1 = ProdutoMock.criar();
        var produto2 = ProdutoMock.criar();

        var ids = List.of(
                produto1.id(),
                produto2.id()
        );

        var produtos = List.of(
                produto1,
                produto2
        );

        when(produtoMongoRepository.findAllById(ids))
                .thenReturn(produtos);

        var result = produtoService.findAllByIds(ids);

        assertEquals(produtos, result);

        verify(produtoMongoRepository)
                .findAllById(ids);
    }

    @Test
    void deveListarTodosOsProdutos() {
        var produto1 = ProdutoMock.criar();
        var produto2 = ProdutoMock.criar();

        var produtos = List.of(
                produto1,
                produto2
        );

        when(produtoMongoRepository.findAll())
                .thenReturn(produtos);

        var result = produtoService.listAll();

        assertEquals(produtos, result);

        verify(produtoMongoRepository)
                .findAll();
    }
}
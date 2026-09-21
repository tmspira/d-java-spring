package com.softdesign.livetest.domain.venda;

import com.softdesign.livetest.mocks.VendaMock;
import com.softdesign.livetest.repository.venda.VendaMongoRepository;
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
class VendaServiceTest {

    @Mock
    private VendaMongoRepository vendaMongoRepository;

    @InjectMocks
    private VendaService vendaService;

    @Test
    void deveCriarVenda() {
        var venda = VendaMock.criar();

        when(vendaMongoRepository.insert(venda))
                .thenReturn(venda);

        var resultado = vendaService.create(venda);

        assertEquals(venda, resultado);

        verify(vendaMongoRepository).insert(venda);
    }

    @Test
    void deveAtualizarVenda() {
        var venda = VendaMock.criar();

        when(vendaMongoRepository.findById(venda.id()))
                .thenReturn(Optional.of(venda));

        when(vendaMongoRepository.save(venda))
                .thenReturn(venda);

        var resultado = vendaService.update(venda);

        assertEquals(venda, resultado);

        verify(vendaMongoRepository).findById(venda.id());
        verify(vendaMongoRepository).save(venda);
    }

    @Test
    void deveLancarExcecaoAoAtualizarVendaInexistente() {
        var venda = VendaMock.criar();

        when(vendaMongoRepository.findById(venda.id()))
                .thenReturn(Optional.empty());

        var exception = assertThrows(
                VendaNotFound.class,
                () -> vendaService.update(venda)
        );

        assertEquals(
                "venda não encontrada - " + venda.id(),
                exception.getMessage()
        );

        verify(vendaMongoRepository).findById(venda.id());
    }

    @Test
    void deveBuscarVendaPorId() {
        var venda = VendaMock.criar();

        when(vendaMongoRepository.findById(venda.id()))
                .thenReturn(Optional.of(venda));

        var resultado = vendaService.getById(venda.id());

        assertEquals(venda, resultado);

        verify(vendaMongoRepository).findById(venda.id());
    }

    @Test
    void deveLancarExcecaoAoBuscarVendaInexistente() {
        var vendaId = "123";

        when(vendaMongoRepository.findById(vendaId))
                .thenReturn(Optional.empty());

        var exception = assertThrows(
                VendaNotFound.class,
                () -> vendaService.getById(vendaId)
        );

        assertEquals(
                "venda não encontrada - " + vendaId,
                exception.getMessage()
        );

        verify(vendaMongoRepository).findById(vendaId);
    }

    @Test
    void deveListarTodasAsVendas() {
        var vendas = List.of(
                VendaMock.criar(),
                VendaMock.criarComDoisItens()
        );

        when(vendaMongoRepository.findAll())
                .thenReturn(vendas);

        var resultado = vendaService.listAll();

        assertEquals(vendas, resultado);

        verify(vendaMongoRepository).findAll();
    }
}

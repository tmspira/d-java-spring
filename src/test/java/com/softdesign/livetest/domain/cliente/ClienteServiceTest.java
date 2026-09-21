package com.softdesign.livetest.domain.cliente;

import com.softdesign.livetest.mocks.ClienteMock;
import com.softdesign.livetest.repository.cliente.ClienteMongoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteMongoRepository clienteMongoRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = ClienteMock.criar();
    }

    @Test
    void deveCriarCliente() {

        when(clienteMongoRepository.insert(cliente))
                .thenReturn(cliente);

        var resultado = clienteService.create(cliente);

        assertEquals(cliente, resultado);

        verify(clienteMongoRepository).insert(cliente);
    }

    @Test
    void deveAtualizarCliente() {

        when(clienteMongoRepository.findById(cliente.id()))
                .thenReturn(Optional.of(cliente));
        when(clienteMongoRepository.save(cliente))
        .thenReturn(cliente);

        var resultado = clienteService.update(cliente);

        assertEquals(cliente, resultado);

        verify(clienteMongoRepository).findById(cliente.id());
        verify(clienteMongoRepository).save(cliente);
    }

    @Test
    void deveLancarExcecaoAoAtualizarClienteInexistente() {

        when(clienteMongoRepository.findById(cliente.id()))
                .thenReturn(Optional.empty());

        var exception = assertThrows(ClienteNotFound.class,
                () -> clienteService.update(cliente));

        assertEquals("cliente não encontrado - " + cliente.id(), exception.getMessage());

        verify(clienteMongoRepository).findById(cliente.id());
        verify(clienteMongoRepository, never()).save(any());
    }

    @Test
    void deveBuscarClientePorId() {

        when(clienteMongoRepository.findById(cliente.id()))
                .thenReturn(Optional.of(cliente));

        var resultado = clienteService.getById(cliente.id());

        assertEquals(cliente, resultado);

        verify(clienteMongoRepository).findById(cliente.id());
    }

    @Test
    void deveLancarExcecaoAoBuscarClienteInexistente() {

        when(clienteMongoRepository.findById(cliente.id()))
                .thenReturn(Optional.empty());

        var exception = assertThrows(ClienteNotFound.class,
                () -> clienteService.getById(cliente.id()));

        assertEquals("cliente não encontrado - " + cliente.id(), exception.getMessage());

        verify(clienteMongoRepository).findById(cliente.id());
    }

    @Test
    void deveListarTodosOsClientes() {

        when(clienteMongoRepository.findAll())
                .thenReturn(List.of(cliente));

        var resultado = clienteService.listAll();

        assertEquals(1, resultado.size());
        assertEquals(cliente, resultado.getFirst());

        verify(clienteMongoRepository).findAll();
    }
}

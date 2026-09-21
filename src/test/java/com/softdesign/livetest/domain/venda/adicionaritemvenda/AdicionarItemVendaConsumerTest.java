package com.softdesign.livetest.domain.venda.adicionaritemvenda;

import com.softdesign.livetest.applicationservice.venda.AdicionarItemVendaApplicationService;
import com.softdesign.livetest.domain.venda.adiciolnaritemvenda.AdicionarItemVendaConsumer;
import com.softdesign.livetest.domain.venda.adiciolnaritemvenda.AdicionarItemVendaMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AdicionarItemVendaConsumerTest {

    @Mock
    private AdicionarItemVendaApplicationService adicionarItemVendaApplicationService;

    @InjectMocks
    private AdicionarItemVendaConsumer adicionarItemVendaConsumer;

    @Test
    void deveConsumirMensagemEExecutarApplicationService() {
        var message = new AdicionarItemVendaMessage(
                "123",
                "456",
                2
        );

        adicionarItemVendaConsumer.consume(message);

        verify(adicionarItemVendaApplicationService)
                .executar(
                        message.vendId(),
                        message.produtoId(),
                        message.quantidade()
                );
    }

    @Test
    void devePropagarExcecaoQuandoApplicationServiceFalhar() {
        var message = new AdicionarItemVendaMessage(
                "123",
                "456",
                2
        );

        var exception = new RuntimeException("Erro ao adicionar item");

        doThrow(exception)
                .when(adicionarItemVendaApplicationService)
                .executar(
                        message.vendId(),
                        message.produtoId(),
                        message.quantidade()
                );

        var thrown = assertThrows(
                RuntimeException.class,
                () -> adicionarItemVendaConsumer.consume(message)
        );

        assertEquals("Erro ao adicionar item", thrown.getMessage());

        verify(adicionarItemVendaApplicationService)
                .executar(
                        message.vendId(),
                        message.produtoId(),
                        message.quantidade()
                );
    }

    @Test
    void deveExecutarHandleDltSemProcessarNovamente() {
        var message = new AdicionarItemVendaMessage(
                "123",
                "456",
                2
        );

        var exception = new RuntimeException("Erro ao processar mensagem");

        adicionarItemVendaConsumer.handleDlt(message, exception);

        verify(adicionarItemVendaApplicationService, never())
                .executar(
                        message.vendId(),
                        message.produtoId(),
                        message.quantidade()
                );
    }
}
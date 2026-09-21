package com.softdesign.livetest.domain.venda.adicionaritemvenda;

import com.softdesign.livetest.domain.venda.adiciolnaritemvenda.AdicionarItemVendaMessage;
import com.softdesign.livetest.domain.venda.adiciolnaritemvenda.AdicionarItemVendaProducer;
import com.softdesign.livetest.mocks.AdicionarItemVendaMessageMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdicionarItemVendaProducerTest {

    @Mock
    private KafkaTemplate<String, AdicionarItemVendaMessage> kafkaTemplate;

    @InjectMocks
    private AdicionarItemVendaProducer adicionarItemVendaProducer;

    @Test
    void deveEnviarMensagemParaKafka() {
        var message = AdicionarItemVendaMessageMock.criar();

        var future = CompletableFuture.completedFuture(
                (SendResult<String, AdicionarItemVendaMessage>) null
        );

        when(kafkaTemplate.send("adicionar-item-venda", message)).thenReturn(future);

        adicionarItemVendaProducer.send(message);

        verify(kafkaTemplate).send("adicionar-item-venda", message);
    }

    @Test
    void deveTratarErroAoEnviarMensagem() {
        var message = AdicionarItemVendaMessageMock.criar();

        var future = new CompletableFuture<SendResult<String, AdicionarItemVendaMessage>>();

        when(kafkaTemplate.send("adicionar-item-venda", message)).thenReturn(future);

        adicionarItemVendaProducer.send(message);

        future.completeExceptionally(new RuntimeException("Erro ao enviar mensagem"));

        verify(kafkaTemplate).send("adicionar-item-venda", message);
    }
}


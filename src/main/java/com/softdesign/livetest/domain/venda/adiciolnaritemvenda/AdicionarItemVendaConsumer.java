package com.softdesign.livetest.domain.venda.adiciolnaritemvenda;

import com.softdesign.livetest.applicationservice.venda.AdicionarItemVendaApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Service
public class AdicionarItemVendaConsumer {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(AdicionarItemVendaConsumer.class);

    private final AdicionarItemVendaApplicationService adicionarItemVendaApplicationService;

    public AdicionarItemVendaConsumer(
            AdicionarItemVendaApplicationService adicionarItemVendaApplicationService) {
        this.adicionarItemVendaApplicationService = adicionarItemVendaApplicationService;
    }

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 1000),
            dltTopicSuffix = "-dlt"
    )
    @KafkaListener(
            topics = "adicionar-item-venda",
            groupId = "livetest-java-spring-group"
    )
    public void consume(AdicionarItemVendaMessage adicionarItemVendaMessage) {

        LOGGER.info(
                "#### -> Consumed message -> {}",
                adicionarItemVendaMessage
        );

        adicionarItemVendaApplicationService.executar(
                adicionarItemVendaMessage.vendId(),
                adicionarItemVendaMessage.produtoId(),
                adicionarItemVendaMessage.quantidade()
        );
    }

    @DltHandler
    public void handleDlt(
            AdicionarItemVendaMessage adicionarItemVendaMessage,
            Exception exception) {

        LOGGER.error(
                "Kafka DLT - falha ao processar item de venda: {}",
                adicionarItemVendaMessage,
                exception
        );
    }
}
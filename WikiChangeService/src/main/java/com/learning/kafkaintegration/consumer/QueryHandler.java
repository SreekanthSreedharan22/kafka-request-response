package com.learning.kafkaintegration.consumer;

import com.google.gson.Gson;
import com.learning.kafkaintegration.messages.Message;
import com.learning.kafkaintegration.messages.RequestMessage;
import com.learning.kafkaintegration.model.WikiChangeType;
import com.learning.kafkaintegration.service.WikiChangeFilterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class QueryHandler {

    private WikiChangeFilterService wikiChangeFilterService;

    @Autowired
    public QueryHandler(WikiChangeFilterService wikiChangeFilterService) {
        this.wikiChangeFilterService = wikiChangeFilterService;
    }

    @KafkaListener(topics = {"#{'${kafka.wikichanges.consumer.topic}'.split(',')}"},
    containerFactory = "requestMessageKafkaListenerContainerFactory")
    public void receive(@Payload RequestMessage requestMessage) {
        log.info("requestMessage received: {}", requestMessage);

        initiateDataFetch(requestMessage);
    }

    private void initiateDataFetch(RequestMessage requestMessage) {
        log.info("in initiateDataFetch(), received requestMessage for correlationId: {} for wikiChangeType: {}",
                requestMessage.getCorrelationId(), requestMessage.getWikiChangeType());

        wikiChangeFilterService.fetchWikiChangesByFilter(requestMessage.getWikiChangeType(),
                requestMessage.getCorrelationId());
    }
}

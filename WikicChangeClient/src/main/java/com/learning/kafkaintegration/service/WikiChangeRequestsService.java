package com.learning.kafkaintegration.service;

import com.learning.kafkaintegration.messages.RequestMessage;
import com.learning.kafkaintegration.model.WikiChange;
import com.learning.kafkaintegration.model.WikiChangeType;
import com.learning.kafkaintegration.producer.QuerySender;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class WikiChangeRequestsService {
    QuerySender querySender;

    @Autowired
    public WikiChangeRequestsService(QuerySender querySender) {
        this.querySender = querySender;
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<List<WikiChange>> getWikiChangeByFilter(WikiChangeType wikiChangeType) throws InterruptedException {
        log.info("in WikiChangeRequestsService.getWikiChangeByFilter()");

        String correlationId = RandomStringUtils.random(15, true, true);
//        RequestMessage requestMessage = new RequestMessage(correlationId, wikiChangeType);
        RequestMessage requestMessage = new RequestMessage(wikiChangeType);

        log.info("sending a RequestMessage with correlationId: {} for wikiChangeType: {}", correlationId, wikiChangeType);
        querySender.sendRequestMessage(requestMessage, correlationId);

        return CompletableFuture.completedFuture(List.of());
    }
}

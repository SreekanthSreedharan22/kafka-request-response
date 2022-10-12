package com.learning.kafkaintegration.service;

import com.learning.kafkaintegration.model.WikiChange;
import com.learning.kafkaintegration.model.WikiChangeType;
import com.learning.kafkaintegration.producer.ResponseGenerator;
import com.learning.kafkaintegration.repo.WikiChangeRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
public class WikiChangeFilterService {

    private WikiChangeRepo wikiChangeRepo;

    private ResponseGenerator responseGenerator;

    @Autowired
    public WikiChangeFilterService(WikiChangeRepo wikiChangeRepo, ResponseGenerator responseGenerator) {
        this.wikiChangeRepo = wikiChangeRepo;
        this.responseGenerator = responseGenerator;
    }

    public void fetchWikiChangesByFilter(WikiChangeType wikiChangeType, String correlationId) {
        log.info("in fetchWikiChangesByFilter(), wikiChangeType: {}, correlationId: {} ", wikiChangeType, correlationId);

        List<WikiChange> wikiChangeList = wikiChangeRepo.getWikiChangesByType(wikiChangeType);
        log.info("filtered WikiChange records count: {}, correlationId: {}", wikiChangeList.size(), correlationId);

        if(! CollectionUtils.isEmpty(wikiChangeList)) {
            sendResultSet(wikiChangeList, correlationId);
        }
    }

    private void sendResultSet(List<WikiChange> wikiChangeList, String correlationId) {
        log.info("in sendResultSet(), correlationId: {} ", correlationId);

        responseGenerator.sendResponse(wikiChangeList, correlationId);
    }
}

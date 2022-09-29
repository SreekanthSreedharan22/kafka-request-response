package com.learning.kafkaintegration.repo;

import com.learning.kafkaintegration.helper.MessageHelper;
import com.learning.kafkaintegration.messages.RequestMessage;
import com.learning.kafkaintegration.model.WikiChange;
import com.learning.kafkaintegration.model.WikiChangeType;
import com.learning.kafkaintegration.producer.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class WikiChangeRepo {
    private MessageHelper messageHelper;

    private List<WikiChange> wikiChanges;

    @Autowired
    public WikiChangeRepo(MessageHelper messageHelper) {
        this.messageHelper = messageHelper;

    }

    public List<WikiChange> getWikiChangesByType(WikiChangeType wikiChangeType) {
        return wikiChanges.stream()
                .filter(wikiChange -> StringUtils.isNotBlank(wikiChange.getType()) && wikiChange.getType().equals(wikiChangeType.getValue()))
                .collect(Collectors.toList());
    }

    @EventListener(ApplicationReadyEvent.class)
    private void loadWikiChangeData() {
        wikiChanges = messageHelper.loadJsonData();
        log.info("in loadWikiChangeData(), {} wikichange records loaded", wikiChanges.size());
    }
}

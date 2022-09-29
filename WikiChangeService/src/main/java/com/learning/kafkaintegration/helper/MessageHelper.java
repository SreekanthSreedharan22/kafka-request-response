package com.learning.kafkaintegration.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.learning.kafkaintegration.model.WikiChange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Component
public class MessageHelper {
    public List<WikiChange> loadJsonData()  {
        InputStream inputStream = MessageHelper.class.getResourceAsStream("/wikiChange.json");
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<WikiChange>> typeReference = new TypeReference<List<WikiChange>>() {
        };

        List<WikiChange> wikiChangeList = null;
        try {
            wikiChangeList = objectMapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            log.info("Exception when parsing json file: {}", e);
        }

        return wikiChangeList;
    }
}

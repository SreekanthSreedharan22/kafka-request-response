package com.learning.kafkaintegration.controller;

import com.learning.kafkaintegration.model.WikiChange;
import com.learning.kafkaintegration.model.WikiChangeType;
import com.learning.kafkaintegration.service.WikiChangeRequestsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
public class WikiChangeRequestsController {
    private WikiChangeRequestsService wikiChangeRequestsService;

    @Autowired
    public WikiChangeRequestsController(WikiChangeRequestsService wikiChangeRequestsService) {
        this.wikiChangeRequestsService = wikiChangeRequestsService;
    }

    @GetMapping("/wikichanges/{filter}")
    public ResponseEntity<?> getWikiChangeByFilter(@PathVariable String filter) {
        ResponseEntity<List<WikiChange>> responseEntity = null;

        Optional<WikiChangeType> wikiChangeTypeOptional = switch (filter) {
            case "new" : yield Optional.of(WikiChangeType.NEW);
            case "categorize" : yield Optional.of(WikiChangeType.CATEGORIZE);
            case "edit": yield  Optional.of(WikiChangeType.EDIT);
            case "log": yield Optional.of(WikiChangeType.LOG);
            default: yield Optional.empty();
        };

         if(wikiChangeTypeOptional.isPresent()) {
             log.info("change type: {}", wikiChangeTypeOptional.get().getValue());

             CompletableFuture<List<WikiChange>> future = null;
             try {
                 future = wikiChangeRequestsService.getWikiChangeByFilter(wikiChangeTypeOptional.get());
             } catch (InterruptedException e) {
                 throw new RuntimeException(e);
             }

             List<WikiChange> wikiChangeList = future.join();
             log.info("back to controller method");

             responseEntity = responseEntity = ResponseEntity.ok(wikiChangeList);
         } else {
             responseEntity = ResponseEntity.badRequest().build();
         }

        return responseEntity;
    }
}

package com.learning.kafkaintegration.producer;

import com.learning.kafkaintegration.messages.ResponseMessage;
import com.learning.kafkaintegration.model.WikiChange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Iterator;
import java.util.List;

@Slf4j
@Component
public class ResponseGenerator {
    @Value("${kafka.wikichanges.producer.topic}")
    private String topic;

    private KafkaTemplate<String, ResponseMessage> responseMessageKafkaTemplate;

    @Autowired
    public ResponseGenerator(KafkaTemplate<String, ResponseMessage> responseMessageKafkaTemplate) {
        this.responseMessageKafkaTemplate = responseMessageKafkaTemplate;
    }

    public void sendResponse(List<WikiChange> wikiChangeList, String correlationId) {
        log.info("in sendResponse(), wikiChangeList.size(): {}", wikiChangeList.size());
        log.info("pushing response messages for correlationId: {} to kafka", correlationId);

        for(Iterator<WikiChange> iterator = wikiChangeList.iterator(); iterator.hasNext();) {
            Message<ResponseMessage> message = MessageBuilder
                    .withPayload(new ResponseMessage(iterator.next()))
                    .setHeader(KafkaHeaders.TOPIC, topic)
                    .setHeader(KafkaHeaders.MESSAGE_KEY, correlationId)
                    .setHeader("is-last-record", iterator.hasNext() ? "false" : "true")
                    .build();

            sendResponseMessage(message);
        }
    }

    private void sendResponseMessage(Message message) {
        responseMessageKafkaTemplate.send(message)
                .addCallback(new ListenableFutureCallback<SendResult<String, ResponseMessage>>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        log.error("Sending message to kafka failed!!! message: {}, error: {}", message, ex);

                        //TODO: add retry?
                    }

                    @Override
                    public void onSuccess(SendResult<String, ResponseMessage> result) {
                        log.info("sent message successfully to partition {} of topic {} with key: {}",
                                result.getRecordMetadata().partition(), topic, result.getProducerRecord().key());

                    }
                });
    }
}

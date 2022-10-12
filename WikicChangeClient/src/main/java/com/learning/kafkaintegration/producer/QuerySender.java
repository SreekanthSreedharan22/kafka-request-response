package com.learning.kafkaintegration.producer;

import com.learning.kafkaintegration.messages.RequestMessage;
import com.learning.kafkaintegration.messages.ResponseMessage;
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

@Slf4j
@Component
public class QuerySender {
    @Value("${kafka.wikichanges.producer.topic}")
    private String topic;

    private KafkaTemplate<String, RequestMessage> kafkaTemplate;

    @Autowired
    public QuerySender(KafkaTemplate<String, RequestMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendRequestMessage(RequestMessage requestMessage, String correlationId) {
        Message<RequestMessage> message = MessageBuilder
                .withPayload(requestMessage)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.MESSAGE_KEY, correlationId)
                .build();

        kafkaTemplate.send(message)
                .addCallback(new ListenableFutureCallback<SendResult<String, RequestMessage>>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        log.error("Sending message to kafka failed!!! message: {}, error: {}", requestMessage, ex);

                        //TODO: add retry?
                    }

                    @Override
                    public void onSuccess(SendResult<String, RequestMessage> result) {
                        log.info("sent message successfully to partition {} with key: {}",
                                result.getRecordMetadata().partition(), result.getProducerRecord().key());
                    }
                });
    }

}

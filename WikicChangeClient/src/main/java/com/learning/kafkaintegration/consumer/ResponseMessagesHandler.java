package com.learning.kafkaintegration.consumer;

import com.learning.kafkaintegration.messages.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component

public class ResponseMessagesHandler {

    @KafkaListener(topics = {"#{'${kafka.wikichanges.consumer.topic}'.split(',')}"},
    containerFactory = "responseMessageKafkaListenerContainerFactory")
    public void receiveResponseMessage(@Payload ResponseMessage responseMessage,
                                       @Header(value = KafkaHeaders.RECEIVED_MESSAGE_KEY) String correlationId,
                                       @Header(value = "is-last-record", required = false) String isLastRecord) {
        log.info("responseMessage received, responseMessage: {}", responseMessage);

        if(StringUtils.isNotBlank(isLastRecord) && isLastRecord.equals("true")) {
            log.info(">>>> last message received for correlationId: {}", correlationId);
        }
    }

}

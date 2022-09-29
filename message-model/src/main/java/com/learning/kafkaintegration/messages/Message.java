package com.learning.kafkaintegration.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public sealed class Message extends MessageMetadata permits RequestMessage, ResponseMessage, ResponseEndMessage {
    public Message(String correlationId) {
        super(correlationId);
    }

    public Message() {
    }
}

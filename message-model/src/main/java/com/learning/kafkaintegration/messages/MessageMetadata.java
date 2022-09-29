package com.learning.kafkaintegration.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MessageMetadata {
    private String correlationId;

    public MessageMetadata(String correlationId) {
        this.correlationId = correlationId;
    }

    public MessageMetadata() {
    }
}

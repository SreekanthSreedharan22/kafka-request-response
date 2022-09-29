package com.learning.kafkaintegration.messages;

import com.learning.kafkaintegration.model.WikiChangeType;
import lombok.Data;

@Data
public final class RequestMessage extends Message {
    private WikiChangeType wikiChangeType;

    // needed for jackson deserialization
    public RequestMessage() {
    }

    public RequestMessage(String correlationId, WikiChangeType wikiChangeType) {
        super(correlationId);
        this.wikiChangeType = wikiChangeType;
    }
}

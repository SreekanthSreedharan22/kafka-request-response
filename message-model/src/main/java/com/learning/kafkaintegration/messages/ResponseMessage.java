package com.learning.kafkaintegration.messages;

import com.learning.kafkaintegration.model.WikiChange;
import lombok.Data;

@Data
public final class ResponseMessage extends Message {
    private WikiChange wikiChange;

    // needed for jackson deserialization
    public ResponseMessage() {
        super();
    }

    public ResponseMessage(String correlationId, WikiChange wikiChange) {
        super(correlationId);
        this.wikiChange = wikiChange;
    }
}


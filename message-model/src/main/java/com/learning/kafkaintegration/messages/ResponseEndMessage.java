package com.learning.kafkaintegration.messages;

import lombok.Data;

@Data
public final class ResponseEndMessage extends Message {
    private Integer resultsetCount;

    public ResponseEndMessage() {
        super();
    }

    public ResponseEndMessage(String correlationId, Integer resultsetCount) {
        super(correlationId);
        this.resultsetCount = resultsetCount;
    }
}

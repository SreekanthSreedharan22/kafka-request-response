package com.learning.kafkaintegration.messages;

import com.learning.kafkaintegration.model.WikiChange;
import lombok.Data;

@Data
public final class ResponseMessage extends Message {

    //    payload
    private WikiChange wikiChange;

    // needed for jackson deserialization
    public ResponseMessage() {
        super();
    }


    public ResponseMessage(WikiChange wikiChange) {
        this.wikiChange = wikiChange;
    }
}


package com.learning.kafkaintegration.messages;

import lombok.Data;

@Data
public sealed class Message extends MessageMetadata permits RequestMessage, ResponseMessage {

    // any domain specific base properties could be added here



    public Message() {
    }
}

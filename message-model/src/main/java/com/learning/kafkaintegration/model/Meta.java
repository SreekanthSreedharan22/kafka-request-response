package com.learning.kafkaintegration.model;

import lombok.Data;

@Data
public class Meta {
    private String _id;
    private String uri;
    private String request_id;
    private String dt;
    private String domain;
    private String stream;
    private String topic;
    private Integer partition;
    private Offset offset;

}

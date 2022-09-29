package com.learning.kafkaintegration.model;

import lombok.Data;

@Data
public class Revision {
    private Long old;
    private Long revised;

}
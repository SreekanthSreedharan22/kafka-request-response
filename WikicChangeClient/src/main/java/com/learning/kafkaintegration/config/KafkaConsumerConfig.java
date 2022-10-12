package com.learning.kafkaintegration.config;

import com.learning.kafkaintegration.messages.ResponseMessage;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {


    private ConsumerFactory<String, Object> consumerFactory;

    @Autowired
    public KafkaConsumerConfig(ConsumerFactory<String, Object> consumerFactory) {
        this.consumerFactory = consumerFactory;
    }

    private Map<String, Object> consumerConfig() {
        return new HashMap<>(consumerFactory.getConfigurationProperties());

    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ResponseMessage> responseMessageKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ResponseMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        DefaultKafkaConsumerFactory<String, ResponseMessage> cf = new DefaultKafkaConsumerFactory<>(consumerConfig(),
                new StringDeserializer(), new JsonDeserializer<>(ResponseMessage.class, false));

        factory.setConsumerFactory(cf);
//        factory.setMessageConverter(new JsonMessageConverter());

        return factory;
    }
}

package com.learning.kafkaintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class WikiChangeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WikiChangeServiceApplication.class, args);
		System.out.println("done!");
	}

}

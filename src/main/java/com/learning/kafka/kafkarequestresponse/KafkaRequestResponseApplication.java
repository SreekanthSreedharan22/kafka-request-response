package com.learning.kafka.kafkarequestresponse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaRequestResponseApplication {

	public static void main(String[] args) {

		SpringApplication.run(KafkaRequestResponseApplication.class, args);
		System.out.println("done!");
	}

}

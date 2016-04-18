package fr.ippon.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@EnableCircuitBreaker
@SpringBootApplication
public class SuggestSearcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuggestSearcherApplication.class, args);
	}
}

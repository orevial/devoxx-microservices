package fr.ippon.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@EnableCircuitBreaker
@SpringBootApplication
public class SearcherApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SearcherApplication.class, args);
	}
}

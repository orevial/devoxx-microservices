package fr.ippon.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.ComponentScan;

@EnableCircuitBreaker
@SpringBootApplication
public class SearcherApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SearcherApplication.class, args);
	}
}
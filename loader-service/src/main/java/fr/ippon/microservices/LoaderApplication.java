package fr.ippon.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("fr.ippon.microservices")
@EnableCircuitBreaker
@SpringBootApplication
public class LoaderApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(LoaderApplication.class, args);
    }
}

package fr.ippon.microservices.kpi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
@ComponentScan("fr.ippon.microservices")
public class KpiApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(KpiApplication.class, args);
    }
}

package fr.ippon.microservices.kpi.config;

import fr.ippon.microservices.kpi.settings.ESSettings;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

@Configuration
public class KpiConfig {

    @Inject
    private ESSettings esSettings;

    @Bean
    public Client getESClient() {
        return new TransportClient().addTransportAddress(new InetSocketTransportAddress(esSettings.getUrl(), esSettings.getPort()));
    }
}

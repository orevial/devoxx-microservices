package fr.ippon.microservices.config;

import fr.ippon.microservices.settings.ESSettings;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
@EnableDiscoveryClient
public class SuggestSearcherConfig {

    @Inject
    private ESSettings esSettings;

    @Bean
    public Client getESClient() throws UnknownHostException {
        return TransportClient.builder().build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esSettings.getUrl()),  esSettings.getPort()));
    }
}

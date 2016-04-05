package fr.ippon.microservices.kpi.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.inject.Inject;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fr.ippon.microservices.kpi.settings.ESSettings;

@Configuration
public class KpiConfig {

    @Inject
    private ESSettings esSettings;

    @Bean
    public Client getESClient() throws UnknownHostException {
    	return TransportClient.builder().build()
    	        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esSettings.getUrl()),  esSettings.getPort()));
    	
    }
}

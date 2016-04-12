package fr.ippon.microservices.model;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by olivier.revial on 24/03/2016.
 */
public class DiscoveryResponse {

    private Map<String, DiscoveryServiceResponse> services = new HashMap<>();

    @JsonValue
    public Map<String, DiscoveryServiceResponse> getServices() {
        return services;
    }

    public void addServiceInstances(String serviceName, DiscoveryServiceResponse discoveryServiceResponse) {
        this.services.put(serviceName, discoveryServiceResponse);
    }
}

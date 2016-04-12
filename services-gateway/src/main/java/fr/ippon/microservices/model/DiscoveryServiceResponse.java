package fr.ippon.microservices.model;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.curator.x.discovery.ServiceInstance;
import org.springframework.cloud.zookeeper.discovery.ZookeeperInstance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by olivier.revial on 24/03/2016.
 */
public class DiscoveryServiceResponse {

    private final List<SimpleServiceInstance> services = new ArrayList<>();

    public DiscoveryServiceResponse(Collection<ServiceInstance<ZookeeperInstance>> services) {
        for (ServiceInstance serviceInstance : services) {
            this.services.add(new SimpleServiceInstance(serviceInstance));
        }
    }

    @JsonValue
    public List<SimpleServiceInstance> getServices() {
        return services;
    }
}

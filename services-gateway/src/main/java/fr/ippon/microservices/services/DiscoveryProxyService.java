package fr.ippon.microservices.services;

import fr.ippon.microservices.model.DiscoveryResponse;
import fr.ippon.microservices.model.DiscoveryServiceResponse;
import org.apache.curator.x.discovery.ServiceInstance;
import org.springframework.cloud.zookeeper.discovery.ZookeeperInstance;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;

@Service
public class DiscoveryProxyService {

    @Inject
    private ZookeeperDiscoveryService discoveryService;

    public DiscoveryResponse listAllServicesInstances()
            throws Exception {
        DiscoveryResponse discoveryResponse = new DiscoveryResponse();

        Map<String, List<ServiceInstance<ZookeeperInstance>>> services = new HashMap<>();

        for (ServiceInstance<ZookeeperInstance> instance : discoveryService.getAllServicesInstances()) {
            if (services.containsKey(instance.getName())) {
                services.get(instance.getName()).add(instance);
            } else {
                List<ServiceInstance<ZookeeperInstance>> createdList = new ArrayList<>();
                createdList.add(instance);
                services.put(instance.getName(), createdList);
            }
        }
        for (Map.Entry<String, List<ServiceInstance<ZookeeperInstance>>> entry : services.entrySet()) {
            discoveryResponse.addServiceInstances(entry.getKey(), new DiscoveryServiceResponse(entry.getValue()));
        }
        return discoveryResponse;
    }

    public DiscoveryServiceResponse listServiceInstances(String serviceName) throws Exception {
        Collection<ServiceInstance<ZookeeperInstance>> instances = discoveryService.getAllServiceInstances(serviceName);
        return new DiscoveryServiceResponse(instances);
    }
}

package fr.ippon.microservices.services;

import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.zookeeper.discovery.ZookeeperInstance;
import org.springframework.cloud.zookeeper.discovery.ZookeeperServiceDiscovery;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class ZookeeperDiscoveryService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    protected ZookeeperServiceDiscovery zookeeperServiceDiscovery;

    public Collection<ServiceInstance<ZookeeperInstance>> getAllServicesInstances()
            throws Exception {
        ServiceDiscovery<ZookeeperInstance> discovery = zookeeperServiceDiscovery.getServiceDiscovery();
        Collection<ServiceInstance<ZookeeperInstance>> instances = new ArrayList<>();
        if (discovery != null) {
            Collection<String> servicesNames = discovery.queryForNames();
            for (String serviceName : servicesNames) {
                instances.addAll(getAllServiceInstances(serviceName));
            }
        }
        return instances;
    }

    public Collection<ServiceInstance<ZookeeperInstance>> getAllServiceInstances(String microServiceName) {
        try {
            return zookeeperServiceDiscovery.getServiceDiscovery().queryForInstances(microServiceName);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }
}
